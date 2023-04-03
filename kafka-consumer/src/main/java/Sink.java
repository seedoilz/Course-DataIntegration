//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
//import org.json.JSONObject;
//import ru.yandex.clickhouse.ClickHouseDataSource;
//import ru.yandex.clickhouse.settings.ClickHouseProperties;
//
//import javax.persistence.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//public class Sink extends RichSinkFunction<String> {
//    //private static final int batchsize = 1000;
//    private int current = 0;
//    private String[] batchString = new String[1000];
//
//    @Override
//    public void invoke(String s, Context context) throws Exception {
//        JSONObject jsonObj = new JSONObject(s);
//        JSONObject eventBodyObj = jsonObj.getJSONObject("eventBody");
//        String eventTypeStr = jsonObj.getString("eventType");
//        String eventDateStr = jsonObj.getString("eventDate");
//        String tableStr = "dm.dm_v_tr_" + eventTypeStr + "_mx";
//        if(eventTypeStr.equals("shop")){
//            tableStr = "dm.v_tr_" + eventTypeStr + "_mx";
//        }
//        StringBuilder sql = new StringBuilder("");
//        StringBuilder sql1 = new StringBuilder("");
//        StringBuilder sql2 = new StringBuilder("");
//        ArrayList<String> res = new ArrayList<String>();
//        sql.append("INSERT INTO ").append(tableStr).append(" ");
//        sql1.append("(");
//        sql2.append("(");
//        eventBodyObj.keys().forEachRemaining(key -> {
//            sql1.append(key).append(",");
//            sql2.append(eventBodyObj.getString(key)).append(",");
////            res.add;
//        });
//        sql1.deleteCharAt(sql1.length() - 1).append(")");
//        sql2.deleteCharAt(sql2.length() - 1).append(")");
//        sql.append(sql1).append(" VALUES ").append(sql2);
//
//        PreparedStatement preparedStatement = conn.prepareStatement("select * from dm.pri_credit_info");
//        preparedStatement = conn.prepareStatement();
//        for(int i = 0; i < res.size(); i++){
//            //数据类型不同在此更改。
//            preparedStatement.setString(i+1, res.get(i));
//        }
//        if(current<1000){
//            batchString[current++] = sql.toString();
//        }else{
//            String url = "jdbc:clickhouse://127.0.0.1:8123/dm";
//            ClickHouseProperties properties = new ClickHouseProperties();
//            properties.setUser("default");
//            properties.setPassword("");
////        properties.setSessionId("default-session-id");
//            ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
//            Connection conn = dataSource.getConnection();
//            Statement statement = conn.createStatement();
//            for (int i = 0 ; i < 1000; i ++){
//                statement.addBatch(batchString[i]);
//            }
//
//            statement.executeBatch();
//            conn.commit();
//            current = 0;
//        }
////        preparedStatement.addBatch(sql.toString());
////        if(current>=batchsize){
////            preparedStatement.executeBatch();
////            current=0;
////        }
//
//        System.out.println(sql);
//    }
//
//
////    public void setConnection(Connection connection) throws SQLException {
////        this.conn = connection;
////    }
//}
