
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.reflect.internal.util.TableDef.Column
import org.apache.spark.sql.UDFRegistration
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Row


object DFPractice {
  def main (args:Array[String]){
  Logger.getLogger("org").setLevel(Level.ERROR)
  val sconf = new SparkConf().setAppName("demo").setMaster("local[*]")
  val spark = SparkSession.builder().config(sconf).getOrCreate()
  
  def replacenull(x:String):String={
        if(x=="" | x==null) 
          "NA"
        else x
  }
 
  
val ordrDF = spark.read
.option("header",true)
.option("inferSchema",true)
.csv("file:///home/ron/order_data.csv")

 import spark.implicits._ 
  
  val myFunc = udf(replacenull(_:String):String)
//ordrDF.select(myFunc(col("StockCode"))).show
//ordrDF.withColumn("Not null Col", myFunc(col("StockCode"))).show
  
//val ordrDF2 = ordrDF.groupBy("Country").avg("UnitPrice")
//ordrDF2.show(10)

//ordrDF.createOrReplaceTempView("ordrview")
//spark.sql("select Country, avg(UnitPrice) from ordrview group by Country").show()
//spark.udf.register("myFunc", replacenull(_:String):String)
//spark.sql("select CustomerID,myFunc(StockCode) from ordrview").show(20)
//ordrDF.filter(col("StockCode").isNotNull).show()
//ordrDF.filter(row => !row.anyNull).show //removes rows with NA in any column, same as above


val ordrMap = ordrDF.select("StockCode","UnitPrice").rdd.map(x=>(x.get(0)->x.get(1))).collectAsMap()
//ways to access map k,v
ordrMap.foreach (keyVal => println(keyVal._1 + "=" + keyVal._2))
//for((k,v)<-ordrMap)println(s"$k :$v")
ordrMap.foreach{case(k,v)=>println(s"$k :$v")}// mark {}
ordrMap.foreach(m=>println(m._1,m._2))
for((k,v)<-ordrMap)println(ordrMap.get(k))
//ordrMap.get("84536B")
//df.select is of Any type. So cast to rdd and then as map

  val l = List(14688,17653)
  
// ordrDF.filter(col("CustomerID").isin(l:_*)).show
 
  // ordrDF.select(udf(regexp_replace(col("Description"), "CAKE", "PUDDING")))
  
  //to find highest or lowest in a column
  ordrDF.createOrReplaceTempView("myview")
  val rankedDF =spark.sql("select StockCode,UnitPrice, dense_rank() over (order by UnitPrice desc) rank from myview group by StockCode,UnitPrice ")
   //rankedDF.filter(x => x(2) == 1).show
   //rankedDF.filter('rank===1).show
 
  ordrDF.withColumn("coalesce_op", coalesce('StockCode,'Description,'UnitPrice,'CustomerID)).show
  case class DataUnit(s1: Int, s2: String, s3:Boolean, s4:Double)

/*val df1 = spark.sparkContext.textFile("file:///home/ron/Fix_width")
  .map(l => (l.substring(0, 3).trim(), l.substring(3, 13).trim(), l.substring(13,18).trim(), l.substring(18,22).trim()))
  .map({ case (e1, e2, e3, e4) => DataUnit(e1.toInt, e2, e3.toBoolean, e4.toDouble) })//RDD of Dataunit row type
  .toDF
  df1.show*/
   
   //use of broadcast variable//////////////////////////////////////////////
  
 /* val countrymap:scala.collection.mutable.Map[String,String] = scala.collection.mutable.Map("UK"->"United Kingdom","AU"->"Australia")
  var c:String =""
  val countrymap1:scala.collection.mutable.Map[String,String]=scala.collection.mutable.Map()
  countrymap.keys.foreach(k=> {c=countrymap(k);countrymap(k)=k;countrymap1.put(c,k)}) //expr with ;
  val b = spark.sparkContext.broadcast(countrymap1)
  b.value.keys.foreach(key=>println(s"$key : $b.value.get(key)"))
  val lookdDf= ordrDF.map(row => {
                                  val country = b.value.get(row.getString(7))        //func
                                  (row.getString(0),country)
  }).toDF("ID","Country").show(150,false)*/
 spark.stop
  }
}