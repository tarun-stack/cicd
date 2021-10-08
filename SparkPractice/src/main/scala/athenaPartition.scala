import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


object athenaPartition extends App{
   val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
 
 val studentsdf = spark.read
.option("header",true)
.option("inferSchema",true)
.csv("file:///home/ron/students.csv")

studentsdf.write.
partitionBy("subject").
parquet("file:///home/ron/students_op.parquet")

spark.stop

}