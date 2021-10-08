
//Aggregate Transformations
//Simple Agg- when after aggregation, we get a single row ex-total no. of records, sum of all quantities
//Grouping Aggregates- when we do a group by. Output can hv multiple rows
//window aggregates- deals with fixed window size
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._


object SDFS16 extends App {
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
invoicedf.select(
    count("*").as ("row_count"), 
    sum("Quantity").as ("total_quantity"),
    avg("UnitPrice").as ("avg_price"),
    countDistinct("InvoiceNo"). as("count_distinct")
    ).show
    
    invoicedf.select(
    count("StockCode").as ("row_count"), //count(stockCode) will be 2 less since 2 rows have null stock code
    sum("Quantity").as ("total_quantity"),
    avg("UnitPrice").as ("avg_price"),
    countDistinct("InvoiceNo"). as("count_distinct")
    ).show
//alternative string expr method   
invoicedf.selectExpr(
"count(*) as row_count", "sum(Quantity) as total_quantity","avg(UnitPrice) as avg_price","count(Distinct(InvoiceNo)) as count_distinct"
).show


invoicedf.createOrReplaceTempView("sales")
spark.sql("select count(*) , sum(Quantity),avg(UnitPrice),count(distinct(InvoiceNo)) from sales" ).show





spark.stop

}