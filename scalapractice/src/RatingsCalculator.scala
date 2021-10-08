

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level

object RatingsCalculator extends App{
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

val input= spark.sparkContext.textFile("hdfs://localhost:9000/user/ron/data/moviedata.data")
//want only one column. i.e. rating
//val ratings= input.map(x=>x.split("\t")(2)).map(x=>(x,1))
val mappedInputresult=input.map(x=>x.split("\t")(2)).countByValue //no need of adding x,1 and reduceby()
//val result = ratings.reduceByKey((x,y)=>x+y).collect//result is no more RDD. Its an array stored in local variable
//result.foreach(println)
mappedInputresult.foreach(println)

//countbyvalue is action which results in local variable. hence, no collect
//reducebyKey is transformation whih results in RDD

//after countbyvalue, all operations done on local variable. hence no parallelism. 
//Should be used where we know it is final operation
    
    
    
    
    
    
    
    
}