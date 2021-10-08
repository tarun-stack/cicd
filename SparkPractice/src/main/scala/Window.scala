import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

//Window Agg.

object SDFS18 extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)
  
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
 
 val invoicedf = spark.read
.option("header",true)
.option("inferSchema",true)
.csv("file:///home/ron/windowdata.csv")

val myWindow = Window.partitionBy("country")
.orderBy("weeknum")
.rowsBetween(Window.unboundedPreceding, Window.currentRow)

val myDF = invoicedf.withColumn("RunningTotal", sum("invoicevalue").over(myWindow))
myDF.show






spark.stop

}