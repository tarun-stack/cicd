
package main.scala

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
import org.apache.log4j.Level
import org.apache.log4j.Logger

object SparkStreaming2 extends App {
 Logger.getLogger("org").setLevel(Level.ERROR)
 def updatefunc(newValues:Seq[Int], prevValues: Option[Int]) ={
   val newCount = prevValues.getOrElse(0)+newValues.sum
   Some(newCount)
 }
 
 //publisher nc -lk 9995
val spark = SparkSession.builder().config(new SparkConf().setMaster("local[*]")).enableHiveSupport().getOrCreate()
  //val ssc = new StreamingContext(spark.sparkContext,Seconds(10))
val ssc = new StreamingContext(spark.sparkContext,Seconds(2)) //batch interval updated for reduceBykey and Window
val lines = ssc.socketTextStream("127.0.0.1",9995) //lines is DStream
ssc.checkpoint(".")
def summaryFunc(x:String,y:String)={x.toInt+y.toInt}
def inverseFunc(x:String,y:String)={x.toInt-y.toInt}
//val words = lines.reduceByWindow(summaryFunc,inverseFunc,Seconds(10),Seconds(4))//pair RDD not reqd unlike reducebykeyandwindow
val words = lines.countByWindow(Seconds(10),Seconds(4)) //counts no. of lines in rdd

//gives last 10 sec summary and then sum of numbers decreses after sliding window of every 2 secs
//Summary Func (x+y)--add new RDDs, Inverse Func(x-y)-- substract RDDs which go away
words.print
ssc.start()
ssc.awaitTermination()
}
