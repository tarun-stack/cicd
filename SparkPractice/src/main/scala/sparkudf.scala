import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._

case class Person(name:String,age:Int,city:String)
object SDFS14 extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)
  def ageCheck(age:Int)={                                //UDF
    if(age>18) "Y" else "N"
  }
  
  
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
 
 val df = spark.read
.option("header",true)
.option("inferSchema",true)
.csv("file:///home/ron/dataset1")


import spark.implicits._ //called when conversion from df to ds
val df1= df.toDF("name","age","city")  //same as df1:Dataset[Row]

//column object expression UDF
val parseAgeFunction = udf(ageCheck(_:Int):String) //UDF registration with driver which sends serialized func to executors
val df2 = df1.withColumn("adult?",parseAgeFunction(col("age"))) //or $"age" i.e. col obj

df2.show

val ds1 = df1.as[Person]//Generic Dataset[row] type to Person type
ds1.groupByKey(x=>x.name)   //ds always gives compile time safety. throws error if name changed to name1.
//ds1.filter(x=>x.age)

  val df3 = ds1.toDF() //convert DS back to DF
  
 val ds2 = ds1.toDF().as[Person] //convert DF to DS
  
 // sql/string expresion UDF
  spark.udf.register("parseAgeFunction",ageCheck(_:Int):String)  //sql expr UDFs are registerd in catalog
  //spark.udf.register("parseAgeFunction",(x:Int)=>{if (x>18) "Y" else "N"}) //anonymous funcn style 
  
 val df4 =  df1.withColumn("adult?",expr("parseAgeFunction(age)"))
 df4.show //same result as df2
  
 spark.catalog.listFunctions().filter(x=>x.name=="parseAgeFunction").show //to check if funcn in catalog?
 
 df4.createOrReplaceTempView("peopletable")
 spark.sql("select name,age,city,parseAgeFunction(age) from peopletable").show //since UDF is in catalog, gives same result as df2,df4
  spark.stop()
  
  
}