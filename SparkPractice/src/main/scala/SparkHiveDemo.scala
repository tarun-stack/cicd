import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SaveMode


object SparkHiveDemo extends App {
  // Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","yarn")
   //sparkconf.set("spark.hadoop.fs.defaultFS","hdfs://localhost:9000")
   //sparkconf.set("spark.deploy-mode","client")
  // sparkconf.set("yarn.resourcemanager.address", "localhost:8032");
  // sparkconf.set("spark.sql.warehouse.dir", s"hdfs://localhost:9000/user/hive/warehouse/")
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .enableHiveSupport()
 .getOrCreate()
 
 
 val ordersDF = spark.read
.format("csv")
.option("header",true)
.option("inferSchema",true)
.option("path","file:///home/ron/orders.csv")
.load
//ordersDF.createOrReplaceTempView("orders") //like a distributed rdbms table
//val resultDF = spark.sql("select order_status, count(*) as order_count from orders group by order_status order by order_count DESC")
//resultDF.show
spark.sql("create database if not exists retail")

ordersDF.write
.format("csv")
.mode(SaveMode.Overwrite)
.bucketBy(4, "order_customer_id")
.sortBy("order_customer_id")
.saveAsTable("retail.orders1")
spark.catalog.listTables("retail").show
}