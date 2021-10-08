package main.scala
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._


object SDFS17 extends App {
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
.csv("file:///home/ron/order_data.csv")

//col object method
val summaryDF= invoicedf.groupBy("Country","InvoiceNo")
   .agg(sum("Quantity").as ("Total Quantity"), 
    sum(expr("Quantity*UnitPrice")).as("InvoiceValue")
   )
  summaryDF.show  
   
//alternative string expr method   
val summaryDF1 = invoicedf.groupBy("Country","InvoiceNo")
.agg(expr("sum(Quantity) as total_quantity"),
     expr("sum(Quantity*UnitPrice) as InvoiceValue")
    )
summaryDF1.show 



//spark sql method
invoicedf.createOrReplaceTempView("sales")
spark.sql("""select Country, InvoiceNo, sum(Quantity),sum(Quantity*UnitPrice)
             from sales group by Country,InvoiceNo """ ).show





spark.stop

}