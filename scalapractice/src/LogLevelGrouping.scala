import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
object LogLevelGrouping extends App{
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
  
   val baserdd= spark.sparkContext.textFile("file:///home/ron/bigLog.txt")
   val mappedrdd = baserdd.map(x=>(x.split(":")(0),x.split(":")(1)))
   mappedrdd.groupByKey.collect().foreach(x=>println(x._1,x._2.size))// shuffling occurs and we get max 2 full partitions 
  // depending upon no. of keysi.e. ERROR, WARN since similar keys go to one executor. So rest 9 partitions are empty, even more in cluster mode
//1 executor handles 1 partition. If exceutor handles 420 mb, but it gets more it throws out of mem error.
   //Fat executor reqd in this case. This scenario is key skew. Solved by Salting i.e. creating more keys
   //WARN1  ERROR1
   //WARN2  ERROR2
   //WARN10  ERROR10 we create 20 keys instead of original 2. hence 10 filled partitions use 20 exceutors.
   //If file size is 8 gb, each executor is of 8/20=420mb
   
   scala.io.StdIn.readLine() //in prod, history server allows not to use this.
//in dag, no. of jobs=no. of actions
//no. of tasks=no. of partitions
//350mb/32=11 blocks as block size in local is 32mb (unlike 128mb in hdfs)=>rdd has 11 partitions.

}