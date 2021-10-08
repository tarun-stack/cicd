import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row

object DataFramesExample extends App{
   Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
  val spark = SparkSession.builder() //treat spark session like driver
 // .appName("App 1")
 //.master("local[2]")
 // .getOrCreate
.config(sparkconf).getOrCreate()

//processing
//val ordersDF = spark.read.csv("file:///home/ron/orders.csv")
//DF can also be represented as below:
val ordersDF:Dataset[Row] = spark.read//read is action, deserialization
.option("header",true)
.option("inferSchema",true)//not to be used in prod, this is also action
.csv("file:///home/ron/orders.csv")
//ordersDF.show()//by default gives 20 rows.//also an action
//ordersDF.printSchema()//by default all String
val groupedOrdersDF = ordersDF
.repartition(4)//wide tx, stage 1, 4 tasks
.where("order_customer_id > 1000") //filter, narrow tx
.select("order_id","order_customer_id")//map, narrow tx
.groupBy("order_customer_id")// wide tx , all above tx are high level code
.count()
groupedOrdersDF.show(50)//--3 stages as 2 wide tx 
groupedOrdersDF.foreach(x=>{//this is low level code. sent directly to exceutors
  println(x)
})
Logger.getLogger(getClass.getName).info("My job completed")
spark.stop

//upon debug high level code is in driver where they are compiled
//, low level code is in executor/daemon






}