package id.my.hendisantika.springbootpostgresdebezium.service;

import id.my.hendisantika.springbootpostgresdebezium.model.Policy;
import id.my.hendisantika.springbootpostgresdebezium.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 26/08/24
 * Time: 19.50
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;

    public Policy addPolicy(Policy policy) {
        log.info("addPolicy = [{}]", policy);
        policy.setStartDate(LocalDateTime.now());
        LocalDateTime endDate = LocalDateTime.of(2029, 1, 30, 23, 59, 59, 999999999);
        policy.setEndDate(endDate);
        return policyRepository.save(policy);
    }
}
