import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf


object CCFraud extends App {
   val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   sparkconf.set("spark.deploy-mode","client")
   //sparkconf.set("yarn.resourcemanager.address", "127.0.0.1:8032");
  sparkconf.set("spark.sql.warehouse.dir", s"hdfs://localhost:9000/user/hive/warehouse/")
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .enableHiveSupport()
 .getOrCreate()
 val hiveContext = new org.apache.spark.sql.hive.HiveContext(spark.sparkContext)
 val myDF= hiveContext.sql("""with cte_2 as 
   (from card_transactions_bucketed select card_id,member_id,postcode,pos_id,transaction_dt,status,
   avg(amount) over (partition by card_id order by transaction_dt desc rows between 10 preceding and current row ) as running_avg, 
   stddev(amount)  over (partition by card_id order by transaction_dt desc rows between 10 preceding and current row ) as running_dev), 
   cte_1 as (select c.member_id,m.score, pos_id-LEAD(pos_id,1) over (partition by card_id order by transaction_dt desc) as pos_lag,
   from_unixtime(unix_timestamp(transaction_dt),'dd-MMM-yy hh.mm.ss a') - LEAD(from_unixtime(unix_timestamp(transaction_dt),'dd-MMM-yy hh.mm.ss a'),1) over (partition by card_id order by transaction_dt desc) as date_diff
    from card_transactions_bucketed c join member_score_bucketed m where c.member_id = cast(m.member_id as bigint))
select distinct cte_2.member_id as memberID, coalesce(abs(cte_1.pos_lag)/cte_1.date_diff,0) as slope , 
cte_1.score as memberScore ,cte_2.card_id,FIRST_VALUE(cte_2.running_avg+3*(cte_2.running_dev)) over (partition by cte_2.card_id,cte_2.member_id ) as ucl 
from cte_2 join cte_1 on cte_1.member_id=cte_2.member_id where cte_1.score>200 and coalesce(abs(cte_1.pos_lag)/cte_1.date_diff,0)<6""")
 
myDF.write
.saveAsTable("credit_approval")
 
 
 
 
 
 
}