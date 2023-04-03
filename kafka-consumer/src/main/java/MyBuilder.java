import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class MyBuilder implements JdbcStatementBuilder<String> {

    @Override
    public void accept(PreparedStatement preparedStatement, String s) throws SQLException {
        JSONObject jsonObj = new JSONObject(s);
        JSONObject eventBodyObj = jsonObj.getJSONObject("eventBody");
        AtomicInteger i = new AtomicInteger();
        eventBodyObj.keys().forEachRemaining(key -> {
            try {
                preparedStatement.setString(i.get() +1, eventBodyObj.getString(key));
                i.getAndIncrement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}