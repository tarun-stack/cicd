
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.storage.StorageLevel

object TotalSpendFilter extends App{
   Logger.getLogger("org").setLevel(Level.ERROR)
 val username = System.getProperty("user.name")
val spark = SparkSession.
    builder.
    config("spark.ui.port", "0").
    config("spark.master", "local[*]").
     config("spark.sql.warehouse.dir", s"hdfs:///user/hive/warehouse/").
    enableHiveSupport.
    appName(s"${username} | Spark SQL - Getting Started").
    getOrCreate 

val input= spark.sparkContext.textFile("hdfs://localhost:9000/user/ron/data/customerorders.csv")
val sortedTotal= input.map(x=>(x.split(",")(0),x.split(",")(2).toFloat)).reduceByKey((x,y)=>x+y)//dont cache baseRDD
val premiumCustomers = sortedTotal.filter(x=>x._2>5000)
//val doubledAmt = premiumCustomers.map(x=>(x._1,x._2*2)).cache
//if we dont hv enough memort cache will skip caching, it wont give error
val doubledAmt = premiumCustomers.map(x=>(x._1,x._2*2)).persist(StorageLevel.MEMORY_AND_DISK)
val result = doubledAmt.collect 
result.foreach(println)
println(doubledAmt.count) //count is an action, calls collect and count, starts from cached doubleAmt
println(doubledAmt.toDebugString)//read from bottom to top to check lineage
}