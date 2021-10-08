
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SaveMode

object DfSpark3 extends App{
   Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
  val spark = SparkSession.builder() //treat spark session like driver
 // .appName("App 1")
 //.master("local[2]")
 // .getOrCreate
.config(sparkconf).getOrCreate()

val orderSchema = StructType(List(StructField("order_id",IntegerType,false),
                                  StructField("order_date",TimestampType),
                                  StructField("customer_id",IntegerType,true),
                                  StructField("order_status",StringType)))

var orderSchemaDDL= "order_id Int,order_date Timestamp,customer_id Int,order_status String"
val ordersDF = spark.read
.format("csv")
.option("header",true)
//.option("inferSchema",true)
.schema(orderSchema)
//.schema(orderSchemaDDL) //DDL Style of explicit schema
.option("path","file:///home/ron/orders.csv")
.load
ordersDF.write
.format("csv")
.mode(SaveMode.Overwrite)
.option("path","file:///home/ron/sparkoutput")//1 file created
.save()

println("ordersDF has" +ordersDF.rdd.getNumPartitions + "partitions")
val ordersrep= ordersDF.repartition(4)
println("ordersrep has" +ordersrep.rdd.getNumPartitions + "partitions")
//No. of files in folder = no. of partitions


ordersrep.write
.format("csv")//internal support for csv,parquet,json. Add spark-avro jar for avro
.partitionBy("order_status")//4 csv files in each partitioned folder
.option("maxRecorsPerFile",2000)
.mode(SaveMode.Overwrite)
.option("path","file:///home/ron/sparkoutput2")//4 files created
.save()

val ordersDF2 = spark.read
//.option("multiline",true).json("file:///home/ron/players.json") if json nested
.format("json")
.option("path","file:///home/ron/players.json")
.option("mode","DROPMALFORMED") //drops malformed rows, others are permissive and failfast
.option("badRecordPath","file:///home/ron/players_malformed")

.load
ordersDF2.printSchema()
ordersDF2.show

//ordersDF2.show(false) //shows entire row data in _corrupt_record column. by default show truncates data


val ordersDF3 = spark.read
.format("parquet") // by default spark considers format as parquet
.option("path","file:///home/ron/users.parquet")//parquet is self describing. No infer needed
.load

ordersDF3.printSchema()
ordersDF3.show
}