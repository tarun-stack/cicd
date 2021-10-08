
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField

object SparkJoin extends App {
   Logger.getLogger("org").setLevel(Level.ERROR)
  
  val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
 val ordersdf = spark.read
.json("file:///home/ron/orders.json")
//ordersdf.show

val custSchema = StructType(List(StructField("cust_id",IntegerType),
                                  StructField("cust_fname",StringType),
                                  StructField("cust_lname",StringType),
                                  StructField("cust_email",StringType),
                                  StructField("cust_pwd",StringType),
                                  StructField("cust_street",StringType),
                                  StructField("cust_city",StringType),
                                  StructField("cust_state",StringType),
                                  StructField("cust_zip",StringType)))
val custdf = spark.read
.schema(custSchema)
.csv("file:///home/ron/output.csv")
//custdf.show

val joinCondition= ordersdf.col("order_customer_id")=== custdf.col("cust_id")//if join colmns names are same will lead to ambiguity
//Suppose ordersdf also has cust_id, it can be renamed prior to join
//ordersdf.withColumnRenamed("cust_id","order_customer_id")

//val joinType = "inner"
//val joineddf = ordersdf.join(custdf,joinCondition,joinType) //matching recors from both sides, we wont see customer who nvr placed order
//joineddf.show 

//Suppose orders df cust_id was n't renamed, then joined table will hv 2 cust_ids. we can drop one
//val joineddf = ordersdf.join(custdf,joinCondition,joinType).sort("order_customer_id").drop(ordersdf.col("cust_id"))
//                  .select("order_id","cust_id","cust_fname").sort("order_id").withColumn("order_id",expr("coalesce(order_id,-1)"))

//if order_id is null, it shows -1

//outer join--matching records+non matching records from both left and right tables
val joinType = "right"

spark.sql("SET spark.sql.autoBroadcastJoinThreshold = -1")// dont broadcast smaller table to all machines
val joineddf2 = ordersdf.join(custdf,joinCondition,joinType).sort("order_customer_id")
joineddf2.show


//for join data is moved from one exchange to another exchange i.e. from one executor's buffer to another executor's buffer.
//All records with same key go to same reduce exchange.
//in inner join smaller table is broadcasted to all machines and hence shuffling willn't occur and stages are less. Might not be case always

//simple join where shuffle reqd happens when both tables are large
//broadcast join when one table is small (else out of memory error). If both table small dont use spark

//ordersdf.join(broadcast(custdf),joinCondition,joinType).sort("order_customer_id") //suppose custdf is smaller

//custdf is broadcasted to all excecutors





spark.stop
}