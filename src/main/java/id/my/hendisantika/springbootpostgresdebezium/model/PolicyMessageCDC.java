package id.my.hendisantika.springbootpostgresdebezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 26/08/24
 * Time: 19.48
 * To change this template use File | Settings | File Templates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyMessageCDC {
    @JsonProperty("before")
    private Before before;

    @JsonProperty("after")
    private After after;

    @JsonProperty("source")
    private Source source;

    @JsonProperty("op")
    private String op;

    @JsonProperty("tn_ms")
    private Long tnMs;

    @JsonProperty("transaction")
    private Object transaction;
}
