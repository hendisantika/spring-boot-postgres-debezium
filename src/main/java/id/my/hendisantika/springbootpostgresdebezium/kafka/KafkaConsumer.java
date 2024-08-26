package id.my.hendisantika.springbootpostgresdebezium.kafka;

import id.my.hendisantika.springbootpostgresdebezium.model.PolicyMessageCDC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    @KafkaListener(
            topics = "policy.public.policy",
            groupId = "policy-group")
    public void debeziumListener(PolicyMessageCDC policyMessageCDC) {
        if (policyMessageCDC.getOp().equals(CREATE)) {
            log.info("polis number after data capture = [{}] & polis holder after data capture = [{}]", policyMessageCDC.getAfter().getPolicyNumber(), policyMessageCDC.getAfter().getPolicyHolder());
        }

        if (policyMessageCDC.getOp().equals(UPDATE)) {
            if (!Objects.isNull(policyMessageCDC.getBefore())) {
                log.info("polis number before data capture = [{}] & polis holder before data capture = [{}]", policyMessageCDC.getBefore().getPolicyNumber(), policyMessageCDC.getBefore().getPolicyHolder());
            }
        }
    }
}
