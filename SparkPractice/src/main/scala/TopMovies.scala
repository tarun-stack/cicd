import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.storage.StorageLevel

object TopMovies extends App{
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

val ratingsRDD = spark.sparkContext.textFile("file:///home/ron/ratings.dat")

val mappedRDD = ratingsRDD.map(x=>{
val fields = x.split("::")
(fields(1),fields(2))
})
//(1193,(5.0,1)) (movieid,(rating,1))..
val newmapped= mappedRDD.mapValues(x=>(x.toFloat,1.0))
val reducedRDD = newmapped.reduceByKey((x,y)=>(x._1+y._1,x._2+y._2))
val filteredRDD = reducedRDD.filter(x=>x._2._2>100)//1st criteria. min 100 people sud hv rated.

val ratingsProcessedRDD= filteredRDD.mapValues(x=>x._1/x._2).filter(x=>x._2>4.5)//2nd criteria..rating min 4

//ratingsProcessedRDD.collect.foreach(println) //(101,4.6)

val moviesRDD = spark.sparkContext.textFile("file:///home/ron/movies.dat")
val mappedmoviesRDD = moviesRDD.map(x=>{
val fields = x.split("::")
(fields(0),fields(1))
})//(101,ToyStory)

//o/p joined RDD (101,(4.6,ToyStory))
val joinedRDD= mappedmoviesRDD.join(ratingsProcessedRDD)//try to broadcast movies rdd as its a small file.

val finalRDD= joinedRDD.map(x=>x._2._1)
finalRDD.collect.foreach(println)







}