


  
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.io.Source

object ExcludeBoringWords extends App{
  def loadBoringWords:Set[String]= {
    var boringWords:Set[String]=Set()
    
    val lines = Source.fromFile("src/boringwords.txt").getLines
    for(line<-lines){
      boringWords+=line //boringwords is var as we r appending
    }
      boringWords
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
    
  val input= spark.sparkContext.textFile("hdfs://localhost:9000/user/ron/data/bigdatacampaign.csv")
  var nameSet = spark.sparkContext.broadcast(loadBoringWords)
//val mappedInput = input.map(x=>(x.split(",")(0),x.split(",")(10).toFloat))

val mappedInput = input.map(x=>(x.split(",")(10).toFloat,x.split(",")(0)))

val words = mappedInput.flatMapValues(x=>x.split(" "))
val finalmapped = words.map(x=>(x._2.toLowerCase(),x._1))
val filteredRDD = finalmapped.filter(x => !nameSet.value(x._1))// check if (big,24) big exists in nameset?
val totalSorted = finalmapped.reduceByKey((x,y) => x+y).sortBy(x=>x._2,false)
totalSorted.collect.foreach(println)
}