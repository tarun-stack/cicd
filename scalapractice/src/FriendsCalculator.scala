
//find avg no. of connections for an age

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
 
object FriendsCalculator extends App{
    
    def parseLine(line: String)={
      val fields = line.split(",")
      val age = fields(2).toInt
      val numfriends= fields(3).toInt
      (age,numfriends)
      
    }
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

val input= spark.sparkContext.textFile("hdfs://localhost:9000/user/ron/data/friendsdata.csv")
val mappedInput = input.map(parseLine)
val mappedFinal = mappedInput.map(x=>(x._1,(x._2,1))) //or .mapValues(x=>(x,1))
val totalsbyAge= mappedFinal.reduceByKey((x,y)=>  (x._1 + y._1, x._2 + y._2))
val avgbyAge = totalsbyAge.map(x=> (x._1,x._2._1/x._2._2)).sortBy(x=>x._2)
//input- (33,100)... //output- (33,(100,1))...
//       (33,120)    //        (33,(120,1))
//---------------------------------------------
//(33,(220,2)) reducebykey only works on value part. so x._1 means 100, y._1 means 120
//(33,220/2)=(33,110) avgbyage
  
  avgbyAge.collect.foreach(println)
  }