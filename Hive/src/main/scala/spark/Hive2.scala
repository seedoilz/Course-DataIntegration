package spark

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.dsl.expressions.StringToAttributeConversionHelper
import org.apache.spark.sql.functions.{col, column, isnull, lit, when}
import org.apache.spark.sql.types.{DecimalType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

import scala.language.postfixOps

/*
  1.spark版本变更为2.3.3，部署模式local即可。也可探索其他模式。
  2.由于远程调试出现的各种问题，且远程调试并非作业重点，这里重新建议使用spark-submit方式
  3.本代码及spark命令均为最简单配置。如运行出现资源问题，请根据你的机器情况调整conf的配置以及spark-submit的参数，具体指分配CPU核数和分配内存。

  调试：
    当前代码中集成了spark-sql，可在开发机如windows运行调试;
    需要在开发机本地下载hadoop，因为hadoop基于Linux编写，在开发机本地调试需要其中的一些文件，如模拟Linux目录系统的winutils.exe；
    请修改System.setProperty("hadoop.home.dir", "your hadoop path in windows like E:\\hadoop-x.x.x")

  部署：
    注释掉System.setProperty("hadoop.home.dir", "your hadoop path in windows like E:\\hadoop-x.x.x")；
    修改pom.xml中<scope.mode>compile</scope.mode>为<scope.mode>provided</scope.mode>
    打包 mvn clean package
    上传到你的Linux机器

    注意在~/base_profile文件中配置$SPARK_HOME,并source ~/base_profile,或在bin目录下启动spark-submit
    spark-submit Spark2DB-1.0.jar
 */


object Hive2 {
    // parameters
    LoggerUtil.setSparkLogLevels()

    def main(args: Array[String]): Unit = {
        //System.setProperty("hadoop.home.dir", "E:\\hadoop-2.7.4")

        val conf = new SparkConf()
          .setAppName(this.getClass.getSimpleName)
          .setMaster("local[*]")

        val session = SparkSession.builder()
          .config(conf)
          .getOrCreate()

        val reader = session.read.format("jdbc")
          .option("url", "jdbc:hive2://172.29.4.17:10000/default")
          .option("user", "student")
          .option("password", "nju2023")
          .option("driver", "org.apache.hive.jdbc.HiveDriver")
        val registerHiveDqlDialect = new RegisterHiveSqlDialect()
        registerHiveDqlDialect.register()

            val tblNameDsts = List(
              "dm_v_as_djk_info",
              "dm_v_as_djkfq_info",
              "pri_cust_asset_acct_info",
              "pri_cust_asset_info",
              "pri_cust_base_info",
              "pri_cust_liab_acct_info",
              "pri_cust_liab_info",
              "pri_credit_info",
              "pri_star_info"
            )

            for (tblNameDst <- tblNameDsts) {
              var df = reader.option("dbtable", tblNameDst).load()
              val columnNames = df.columns.toList.map(name => name.substring(tblNameDst.length + 1)).toArray
              df = df.toDF(columnNames: _*)
              // code
              df = df.distinct()
              val filterCond = df.columns.filter(_ != "uid").map(col => s"$col IS NOT NULL").mkString(" OR ")
              df = df.filter(s"uid IS NOT NULL AND ($filterCond)")
              val schema = df.schema
              schema.foreach(field => {
                val fieldName = field.name
                val fieldType = field.dataType
                if (DecimalType.is32BitDecimalType(fieldType) || DecimalType.is64BitDecimalType(fieldType)) {
                  df = df.withColumn(fieldName, when(col(fieldName).isNull, lit(0)).otherwise(col(fieldName)))
                }
              })
              df.show(1000)
              var index = df.columns.head
              if ( tblNameDst.equals("pri_cust_liab_acct_info")){
                index = "(loan_cust_no, acct_no)"
              }

              df.write
                .format("jdbc")
                .mode(SaveMode.Overwrite)
                .option("driver", "com.github.housepower.jdbc.ClickHouseDriver")
                .option("url", "jdbc:clickhouse://127.0.0.1:9000")
                .option("user", "default")
                .option("password", "")
                .option("dbtable", "dm."+ tblNameDst)
                .option("truncate", "true")
                .option("batchsize", 20000)
                .option("isolationLevel", "NONE")
                .option("createTableOptions", s"ENGINE = MergeTree() ORDER BY $index")
                .save
            }

        //ETL 小任务
        val tblNameDst = "pri_cust_contact_info"
        var df = reader.option("dbtable", tblNameDst).load()
        val columnNames = df.columns.toList.map(name => name.substring(tblNameDst.length + 1)).toArray
        df = df.toDF(columnNames: _*)
        df = df.distinct()
        val filterCond = df.columns.filter(_ != "uid").map(col => s"$col IS NOT NULL").mkString(" OR ")
        df = df.filter(s"uid IS NOT NULL AND ($filterCond)")

        df = df.withColumn("address", when(df("con_type")=!="TEL" && df("con_type")=!="OTH" && df("con_type")=!="MOB",df("contact")).otherwise(""))

        val rdd = df.rdd.map{ row =>
            val uid = row.getString(0)
            val contact = row.getString(2)
            val address = row.getString(6)
            (uid, (contact, address))
        }.reduceByKey{ (a, b) =>
            val (contact1, address1) = a
            val (contact2, address2) = b
            var address=""
            if(address1!="") {
                address = address1
            }
            if(address2!=""){
                if(address!="") {
                    address += "," + address2
                }else{
                    address = address2
                }
            }
            var contact = ""
            if(address1==""){
                contact=contact1
            }
            if(address2==""){
                if(contact!=""){
                    contact+="," +contact2
                }else {
                    contact = contact2
                }
            }
            (contact, address)
        }
        val row: RDD[Row] = rdd.map{ case (uid, (contact, address))=>
            Row(uid, contact, address)
        }
        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Rdd2DF")
        val sparkSession: SparkSession = SparkSession.builder().config(config).getOrCreate()
        val schema = StructType(Seq(
            StructField("uid", StringType,true),
            StructField("contact_phone", StringType,true),
            StructField("contact_address", StringType,true)
        ))
        val res_ = sparkSession.createDataFrame(row,schema)
        res_.show(2000,false)


            res_.write
              .format("jdbc")
              .mode(SaveMode.Overwrite)
              .option("driver", "com.github.housepower.jdbc.ClickHouseDriver")
              .option("url", "jdbc:clickhouse://127.0.0.1:9000")
              .option("user", "default")
              .option("password", "")
              .option("dbtable", "dm."+ tblNameDst)
              .option("truncate", "true")
              .option("batchsize", 20000)
              .option("isolationLevel", "NONE")
              .option("createTableOptions", s"ENGINE = MergeTree() ORDER BY uid")
              .save

        session.close()




    }

}
