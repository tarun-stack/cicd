import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.io.Source


object LogLevel extends App{
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
    
  val myList=List("WARN: Tuesday 4 Sep 0405",
                 "ERROR: Tuesday 4 Sep 0408",
                 "ERROR: Tuesday 4 Sep 0408",
                 "ERROR: Tuesday 4 Sep 0408",
                 "ERROR: Tuesday 4 Sep 0408",
                 "ERROR: Tuesday 4 Sep 0408"
    
  )
  val logsRDD = spark.sparkContext.parallelize(myList)//local file can be distributed across cluster by making it RDD
  println(spark.sparkContext.defaultParallelism)
  println(spark.sparkContext.defaultMinPartitions)
  println(logsRDD.getNumPartitions) //equal to default parallelism
  val pairRDD = logsRDD.map(x=> (x.split(":")(0),1))
  val resultRDD = pairRDD.reduceByKey((x,y)=>x+y)//reduceByKey(_+_)
  resultRDD.collect.foreach(println)
  
}
