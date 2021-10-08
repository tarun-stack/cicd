
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import java.sql.Timestamp

case class OrdersData(order_id:Int,order_date:Timestamp,order_customer_id:Int,order_status:String)
object DFExample2 extends App{
   Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
  val spark = SparkSession.builder() //treat spark session like driver
 // .appName("App 1")
 //.master("local[2]")
 // .getOrCreate
.config(sparkconf).getOrCreate()


val ordersDF:Dataset[Row] = spark.read
.option("header",true)
.option("inferSchema",true)
.csv("file:///home/ron/orders.csv")

import spark.implicits._
val orderDS = ordersDF.as[OrdersData] //dataset of a specific type

//ordersDF.filter("order_ids<10").show //ids not caught at compile time
  //orderDS.filter("order_ids<10") //still same problem,but can be solves as below
orderDS.filter(x=>x.order_id<10).show   //ids will give compile time error now


Logger.getLogger(getClass.getName).info("My job completed")
spark.stop
}