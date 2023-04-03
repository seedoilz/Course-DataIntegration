import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.json.JSONObject;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    static String[] a = new String[14];
    private static final String a1 = "INSERT INTO dm.dm_v_tr_djk_mx (tran_date,tran_type,tran_desc,rev_ind,tran_time,pur_date,mer_type,val_date,uid,card_no,mer_code,acct_no,tran_type_desc,tran_amt_sign,tran_amt,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static void main(String[] args) throws Exception {
//        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "201250210");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, Time.of(10, TimeUnit.SECONDS)));
        FlinkKafkaConsumer<String> consumer =new FlinkKafkaConsumer<String>("dm",new SimpleStringSchema(),props);
        consumer.setStartFromGroupOffsets();
        consumer.setStartFromEarliest();    // 设置每次都从头消费

        DataStream<String> source = env.addSource(new FlinkKafkaConsumer<String>
                ("dm",new SimpleStringSchema(),props));


        String url = "jdbc:clickhouse://127.0.0.1:8123/dm";
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser("default");
        properties.setPassword("");
//        properties.setSessionId("default-session-id");
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        Connection conn = dataSource.getConnection();

        a[0] = "INSERT INTO dm.dm_v_tr_djk_mx (tran_date,tran_type,tran_desc,rev_ind,tran_time,pur_date,mer_type,val_date,uid,card_no,mer_code,acct_no,tran_type_desc,tran_amt_sign,tran_amt,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[1] = "INSERT INTO dm.dm_v_tr_dsf_mx (payer_open_bank,tran_date,tran_code,dc_flag,busi_sub_type,payee_acct_no,payer_acct_no,payee_open_bank,tran_sts,uid,busi_type,payee_name,tran_teller_no,tran_amt,tran_log_no,send_bank,payer_name,etl_dt,channel_flg,tran_org) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[2] = "INSERT INTO dm.dm_v_tr_etc_mx (tran_date,real_amt,tran_amt_fen,tran_place,tran_time,conces_amt,mob_phone,uid,card_no,car_no,cust_name,etl_dt,etc_acct) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[3] = "INSERT INTO dm.dm_v_tr_gzdf_mx (tran_date,batch_no,uid,is_secu_card,acct_no,belong_org,ent_acct,cust_name,ent_name,eng_cert_no,tran_amt,trna_channel,tran_log_no,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[4] = "INSERT INTO dm.dm_v_tr_huanb_mx (tran_date,tran_code,pprd_rfn_amt,tran_flag,tran_type,pay_term,dscrp_code,remark,tran_time,bal,pprd_amotz_intr,uid,acct_no,dr_cr_code,tran_teller_no,cust_name,tran_amt,tran_log_no,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[5] = "INSERT INTO dm.dm_v_tr_huanx_mx (tran_date,tran_code,intc_strt_date,tran_flag,tran_type,pay_term,dscrp_code,tran_time,intc_end_date,uid,intr,cac_intc_pr,acct_no,dr_cr_code,tran_teller_no,cust_name,tran_amt,tran_log_no,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[6] = "INSERT INTO dm.dm_v_tr_sa_mx (dr_amt,sys_date,curr_type,oppo_acct_no,dscrp_code,remark,tran_time,channel_flag,bal,uid,card_no,remark_1,oppo_cust_name,tran_teller_no,cust_name,cr_amt,tran_card_no,etl_dt,oppo_bank_no,open_org,tran_date,tran_code,tran_type,agt_cust_name,agt_cert_no,det_n,acct_no,src_dt,tran_amt,tran_log_no,agt_cert_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[7] = "INSERT INTO dm.dm_v_tr_sdrq_mx (tran_date,batch_no,tran_type,tran_amt_fen,return_msg,hosehld_no,tran_sts,uid,acct_no,tran_teller_no,cust_name,tran_log_no,etl_dt,channel_flg,tran_org) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[8] = "INSERT INTO dm.dm_v_tr_grwy_mx (uid,mch_channel,login_type,ebank_cust_no,tran_date,tran_time,tran_code,tran_sts,return_code,return_msg,sys_type,payer_acct_no,payer_acct_name,payee_acct_no,payee_acct_name,tran_amt,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[9] = "INSERT INTO dm.dm_v_tr_sbyb_mx (uid,tran_date,tran_sts,tran_type,tran_teller_no,tran_amt_fen,cust_name,return_msg,etl_dt,tran_org) VALUES (?,?,?,?,?,?,?,?,?,?)";
        a[10] = "INSERT INTO dm.dm_v_tr_contract_mx (reg_org,loan_use_add,artificial_no,loan_pert,dull_bal,finsh_date,owed_int_out,bal,owed_int_in,operator,extend_times,frz_amt,shift_type,finsh_type,shift_bal,vouch_type,fine_intr_int,etl_dt,five_class,inte_settle_type,fine_pr_int,mgr_no,pay_times,loan_use,dlay_days,occur_date,reson_type,buss_type,reg_date,base_rate,mge_org,occur_type,class_date,apply_type,matu_date,norm_bal,curr_type,uid,rate_float,operate_org,actu_out_amt,rate,buss_amt,putout_date,is_credit_cyc,float_type,cust_name,pay_type,due_intr_days,term_day,term_mth,is_bad,con_crl_type,direction,contract_no,term_year,operate_date,apply_no,pay_source,base_rate_type,is_vc_vouch,src_dt,sts_flag,dlay_bal,loan_cust_no,register) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[11] = "INSERT INTO dm.dm_v_tr_duebill_mx (reg_org,receipt_no,pay_cyc,intr_type,pay_freq,owed_int_out,bal,owed_int_in,operator,extend_times,cust_no,buss_rate,vouch_type,fine_intr_int,pay_back_acct,etl_dt,actu_buss_rate,putout_acct,fine_pr_int,mgr_no,pay_times,loan_use,dlay_days,actu_matu_date,occur_date,buss_type,mge_org,acct_no,dlay_amt,matu_date,norm_bal,dull_amt,bad_debt_amt,loan_channel,curr_type,uid,operate_org,buss_amt,putout_date,cust_name,due_intr_days,pay_type,ten_class,contract_no,pay_acct,src_dt,subject_no,loan_cust_no,intr_cyc,register) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[12] = "INSERT INTO dm.dm_v_tr_sjyh_mx (tran_date,ebank_cust_no,tran_code,login_type,return_msg,tran_time,sys_type,payee_acct_no,payer_acct_no,uid,tran_sts,payee_acct_name,mch_channel,tran_amt,etl_dt,return_code,payer_acct_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        a[13] = "INSERT INTO dm.v_tr_shop_mx (tran_date,tran_channel,shop_code,hlw_tran_type,tran_time,shop_name,order_code,uid,score_num,current_status,pay_channel,tran_amt,legal_name,etl_dt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        MyBuilder builder = new MyBuilder();
        JdbcConnectionOptions connectionOptions = new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                .withUrl(url)
                .withDriverName("ru.yandex.clickhouse.ClickHouseDriver")
                .withUsername("default")
                .withPassword("")
                .build();

        SinkFunction<String> b1 = JdbcSink.sink(a[0],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b2 = JdbcSink.sink(a[1],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b3 = JdbcSink.sink(a[2],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b4 = JdbcSink.sink(a[3],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b5 = JdbcSink.sink(a[4],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b6 = JdbcSink.sink(a[5],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b7 = JdbcSink.sink(a[6],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b8 = JdbcSink.sink(a[7],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b9 = JdbcSink.sink(a[8],builder, JdbcExecutionOptions.builder().withBatchSize(1).build(), connectionOptions);
        SinkFunction<String> b10 = JdbcSink.sink(a[9],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b11 = JdbcSink.sink(a[10],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b12 = JdbcSink.sink(a[11],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b13 = JdbcSink.sink(a[12],builder, JdbcExecutionOptions.builder().build(), connectionOptions);
        SinkFunction<String> b14 = JdbcSink.sink(a[13],builder, JdbcExecutionOptions.builder().build(), connectionOptions);

        source.filter(new MyFilter("djk")).addSink(b1);
        source.filter(new MyFilter("dsf")).addSink(b2);
        source.filter(new MyFilter("etc")).addSink(b3);
        source.filter(new MyFilter("gzdf")).addSink(b4);
        source.filter(new MyFilter("huanb")).addSink(b5);
        source.filter(new MyFilter("huanx")).addSink(b6);
        source.filter(new MyFilter("sa")).addSink(b7);
        source.filter(new MyFilter("sdrq")).addSink(b8);
        source.filter(new MyFilter("grwy")).addSink(b9);
        source.filter(new MyFilter("sbyb")).addSink(b10);
        source.filter(new MyFilter("contract")).addSink(b11);
        source.filter(new MyFilter("duebill")).addSink(b12);
        source.filter(new MyFilter("sjyh")).addSink(b13);
        source.filter(new MyFilter("shop")).addSink(b14);

        source.print();
        env.execute();
    }
}
