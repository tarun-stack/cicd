
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._

object SDFS13 extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
 
 val ordersDF = spark.read
.option("header",true)
.option("inferSchema",true)
.csv("file:///home/ron/orders.csv")


 ordersDF.select("order_id","order_status").show //column strings
 import spark.implicits._
 //ordersDF.select(column("order_id"),col("order_status"),$"order_customer_id",'order_status)//column objects
 ordersDF.select($"order_id",$"order_status",$"order_customer_id",'order_status).show //column,col used in pyspark and scala
 //column object and column strings cant be mixed
 //ordersDF.select("order_id","concat(order_status,'_STATUS')") //col strings or column objects cant be mixed with col expresssion
 
 //col expression to column object
 //ordersDF.select($"order_id",expr("concat(order_status,'_STATUS')")).show(false)//expr converts to col object,rest hv to be col obj too
 
 ordersDF.selectExpr("order_id","concat(order_status,'_STATUS')").show(false)//or keep all col string and put everything in expr
 
 
 
 
 
 
 
 
 
}