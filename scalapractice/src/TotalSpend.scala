
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
object TotalSpend extends App{
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
val sortedTotal= input.map(x=>(x.split(",")(0),x.split(",")(2).toFloat)).reduceByKey((x,y)=>x+y).sortBy(x=>x._2)
//val result = sortedTotal.collect //collect can cause driver crash in case of large o/p file
//result.foreach(println)
sortedTotal.saveAsTextFile("file:///home/ron/myrdd")

}