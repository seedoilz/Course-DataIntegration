import org.apache.flink.api.common.functions.FilterFunction;
import org.json.JSONObject;

public class MyFilter implements FilterFunction<String> {
    String tb;

    public MyFilter(String tb){
        this.tb = tb;
    }
    @Override
    public boolean filter(String s) throws Exception {
        JSONObject jsonObj = new JSONObject(s);
        String eventTypeStr = jsonObj.getString("eventType");
        return this.tb.equals(eventTypeStr);
    }
}