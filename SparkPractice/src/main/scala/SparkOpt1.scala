package main.scala
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._
object SparkOpt1 extends App {
  
 Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
 
 val ordersDF = spark.read
.option("header",true)
.option("inferSchema",true)//not recommended
.csv("file:///home/ron/orders.csv")
//Sort Aggregation vs Hash Aggregation
//orders.csv 2.6 gb, Find no. of orders placed by each customer each month. Grouping based on order_customer_id and month-count of no. of orders
ordersDF.createOrReplaceTempView("orders")
//spark-shell --master yarn --conf spark.dynamic Allocation.enabled=false --num-executors 11 --conf spark.ui.port=4063 (by default 1 core per exec)
//grabbed 11 executors. check in UI->Exceutors
//spark.sql("""select order_customer_id, date_format(order_date,'MMMM') orderdt, count(1) cnt,
//  first(date_format(order_date,'M')) monthnum from orders group by order_customer_id, orderdt order by cast(monthnum as int)""").show
//will sort month in dictionary oredr. so add monthnum
  //11 tasks run in parallel as 11 cpu cores. took 3.9 mins.Uses sort aggregate
  spark.sql("""select order_customer_id, date_format(order_date,'MMMM') orderdt, count(1) cnt,
  first(cast(date_format(order_date,'M')as int)) monthnum from orders group by order_customer_id, orderdt order by monthnum """).show
//added cast. took 1.2 min. Uses Hash aggregate
//spark.sql("""select order_customer_id, date_format(order_date,'MMMM') orderdt, count(1) cnt,
//  first(date_format(order_date,'M')) monthnum from orders group by order_customer_id, orderdt order by cast(monthnum as int)""").explain//uses sort agg.
  

}