package id.my.hendisantika.springbootpostgresdebezium.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 04/10/24
 * Time: 09.33
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String index() {
        log.info("Spring Boot Postgres Debezium --> {} ", LocalDateTime.now());
        return "Spring Boot Postgres Debezium --> {} " + LocalDateTime.now();
    }
}
