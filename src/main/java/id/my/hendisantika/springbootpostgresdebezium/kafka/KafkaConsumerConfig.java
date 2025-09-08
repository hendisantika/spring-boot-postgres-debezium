package id.my.hendisantika.springbootpostgresdebezium.kafka;

import id.my.hendisantika.springbootpostgresdebezium.deserializer.PolicyDeserializer;
import id.my.hendisantika.springbootpostgresdebezium.model.Policy;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 26/08/24
 * Time: 19.58
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Policy> kafkaListenerContainerFactory(ConsumerFactory<String, Policy> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Policy> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Policy> consumerFactory() {
        Map<String, Object> map = new HashMap<>();
        map.put("bootstrap.servers", "localhost:29092");
        map.put("key.deserializer", StringDeserializer.class);
        map.put("value.deserializer", PolicyDeserializer.class);
        map.put("auto.offset.reset", "earliest");
        return new DefaultKafkaConsumerFactory<>(map);
    }
}
