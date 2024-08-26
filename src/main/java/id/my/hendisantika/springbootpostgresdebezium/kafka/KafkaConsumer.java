package id.my.hendisantika.springbootpostgresdebezium.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 26/08/24
 * Time: 19.54
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
public class KafkaConsumer {
    private static final String CREATE = "c";
    private static final String UPDATE = "u";
}
