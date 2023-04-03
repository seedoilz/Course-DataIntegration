import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.utils.Time;

import java.io.*;
import java.math.BigDecimal;
import java.util.Properties;

// kafka生产者代码
public class ProducerFastStart {

    public static void main(String[] args) {
        Properties props = new Properties();
        //kafka 集群，broker-list
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "0");
        //重试次数
        props.put("retries", 1);
        //批次大小
        props.put("batch.size", 16384);
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        String lineStr = "";
        int count = 0;
        long lasttime = 0;
        try{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    "data.txt")));
            lasttime = System.currentTimeMillis();
            while((lineStr = br.readLine()) != null){
                count++;
                ProducerRecord<String, String> record = new ProducerRecord<>("dm", lineStr);
                producer.send(record);
                if(System.currentTimeMillis() - lasttime >= 1000){
                    System.out.println(count);
                    lasttime = System.currentTimeMillis();
                    count = 0;
                }
            }
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    "special.txt")));
            while((lineStr = br.readLine()) != null){
                ProducerRecord<String, String> record = new ProducerRecord<>("dm", lineStr);
                producer.send(record);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}