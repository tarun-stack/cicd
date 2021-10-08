import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DateType

object SDFS15 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
   
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 val myList = List ((1,"2013-07-25 00:00:00.0",11599,"CLOSED"),
(2,"2013-09-25 00:00:00.0",256,"PENDING_PAYMENT"),
(3,"2015-07-21 00:00:00.0",12111,"COMPLETE"),
(4,"2014-06-20 00:00:00.0",8827,"CLOSED"),
(5,"2013-07-25 00:00:00.0",11599,"COMPLETE"))

import spark.implicits._
val rdd = spark.sparkContext.parallelize(myList)
val ordersDF = rdd.toDF().toDF("orderid","orderdate","customerid","status")
// val ordersDF= spark.createDataFrame(myList).toDF("orderid","orderdate","customerid","status") //should work..hv to check
//1. convert date to unix timestamp
val newDF = ordersDF.withColumn("orderdate", unix_timestamp(col("orderdate").cast(DateType)))//with can add new col or update a col
newDF.printSchema

//UDF with withCol??
//2. Add new col,Drop duplicates, drop orderid and sort
val newDF1 = newDF.withColumn("newid", monotonically_increasing_id()).dropDuplicates("orderdate","customerid").drop("orderid")
.sort("orderdate")
newDF1.show


spark.stop
}