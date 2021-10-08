

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.collection.Seq
import scala.reflect.api.materializeTypeTag

object Interview extends App{
   val jsonschema = new StructType().add("eid",IntegerType,true)
 																	.add("ename",StringType,true)
 																	.add("edept",StringType,true)
 val eSeq = Seq((1,"Ram","Science"),(2,"Ganesh","Maths"))
 val cols = Array("eid","ename","edept")
 
 val spark = SparkSession.builder().master("local[*]").getOrCreate()
 import spark.implicits._
 val eDF = spark.sparkContext.parallelize(eSeq).toDF(cols:_*)
 //eDF.show()
 
 val df = spark.read
 .option("escape","\"")
 .option("header",true)
 .option("multiline",true)
 .csv("file:///home/ron/newproduct.csv")
 df.show
 
 //read to and from json
 val df2 = eDF.toJSON //.show(false)
 val df3 = df2.withColumn("record",from_json(df2("value"),jsonschema))
    // df3.show(false)
 df3.select("record.*").drop("value").show

     //reading from mixed csv
    /* val newdf = df.withColumn("jsondata",json_tuple(df("models"),"model"))
 newdf.select(json_tuple(newdf("jsondata"),"type","version")).show
   
  newdf
  .withColumn("type",json_tuple(newdf("model"),"type"))
  .withColumn("version",json_tuple(newdf("model"),"version"))
  .drop("models","jsondata").show(false)*/
   
   
   
   
   
   
   
   
   
   
   
 spark.stop
}