

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level



object WordCount extends App{
  //def main(args:Array[String]){//use extend apps to bypass
    
  //}
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
//val sc = new SparkContext("yarn-client","wordcount")
    //val conf = new SparkConf().setAppName("WordCount").setMaster("local")
          //  .set("spark.hadoop.yarn.resourcemanager.hostname", "localhost")
           // .set("spark.hadoop.yarn.resourcemanager.address", "localhost:8032")
val input= spark.sparkContext.textFile("hdfs://localhost:9000/user/ron/data/search_data.txt")
val words = input.flatMap(x=>x.split(" "))
words.map(_.toLowerCase()) //or x=>X.tolowercase
val wordMap = words.map(x=>(x,1))
val finalCount = wordMap.reduceByKey((x,y)=>x+y)
//result.saveAsTextFile("/user/ron/data/sparkWCwithIDE")

val revTupple = finalCount.map(x=>(x._2,x._1))
//val sortedResult=revTupple.sortByKey(false).map(x=>(x._2,x._1))//false for desc. Again reverse to see in proper format
val sortedResult=finalCount.sortBy(x=>(x._2))
//sortedResult.collect.foreach(println)
val results=sortedResult.collect
for(result <- results)
{
  val word=result._1
  val count=result._2
  println(s"$word:$count")
}
//scala.io.stdIn.readLine() to see DAG
//val words = input.flatMap(x=>x.split(" ")).map(_.toLowerCase()).map(x=>(x,1)).reduceByKey((x,y)=>x+y).collect.foreach(println)

}