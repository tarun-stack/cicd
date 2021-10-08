import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.storage.StorageLevel

object LogLevelGroupingOptimized extends App{
 // Logger.getLogger("org").setLevel(Level.ERROR)
 val username = System.getProperty("user.name")
 val spark = SparkSession.
    builder.
    config("spark.ui.port", "0").
    config("spark.master", "local[*]").
     config("spark.sql.warehouse.dir", s"hdfs:///user/hive/warehouse/").
    enableHiveSupport.
    appName(s"${username} | Spark SQL - Getting Started").
    getOrCreate 
   val random = new scala.util.Random
   val start=1
   val end =60
   val baserdd= spark.sparkContext.textFile("file:///home/ron/bigLog.txt")
   //baserdd.cache -->we r caching entire file's data into memory which takes lot of space and might throw OOM error.
   //baserdd.persist(StorageLevel.DISK_ONLY)//this is ok as it takes same file size as original in disk.
   //baserdd.persist(StorageLevel.MEMORY_AND_DISK) //takes bit more time than memory only
   //baserdd.persist(StorageLevel.MEMORY_ONLY_SER) //data kept in serialized form (in bytes) which takes less space,
   //it slightly takes more time than deserialized(data stored as objects)
   val mappedrdd = baserdd.map(x=>{
     var num = start+random.nextInt((end-start)+1)
     (x.split(":")(0)+num,x.split(":")(1))// Salted number added -- start and end num can be manipulated to fix each partition size.
   })                                      //Here, 60*2=120 unique keys are generated divided into 66 partitions (8.5gb file/128mb)
   //66 partitions in 4 containers. So 15 tasks appx per container. In stage 2 i.e. after groupByKey also, 66 partitions also.
   val rdd1 = mappedrdd.groupByKey 
   //Stage 2 begins
   val rdd2 = rdd1.map(x=>(x._1,x._2.size))//i can cache here
   rdd2.cache// caching happens only if rdd4.collect called. happens in unified memory. Unpersist to remove cache.
   rdd2.unpersist()
   rdd2.persist(StorageLevel.DISK_ONLY) // same as cache, but persists gives option to store in disk(serialized)
                                       //instead of on heap mem.(deserialized which takes more space)
   
   val rdd3 = rdd2.map(x=>{
     if (x._1.substring(0,4)=="WARN")
       ("WARN",x._2)
       else
       ("ERROR",x._2)
   })
val rdd4= rdd3.reduceByKey(_+_)
  rdd4.collect().foreach(println) //if i rerun this line after job completion, only last stage or the stage having 
  //this action is executed, not from stage0. This is spark internal optimization.
  
   //scala.io.StdIn.readLine()
  //spark-shell ==conf spark.dynamicAllocation.enabled=false --master yarn --num-executors 4 --executor-cores 2 --executor-memory 2G
  //Num tasks in parallel= 2*4=8
  //in spark shell (16+8)/66 means 16 task completed, 8 running parallely out of 66 tasks (66 parttions as per 128mb blocks)
  //if num exceutor is 20 job takes lvery less time to complete.
  spark.stop
}