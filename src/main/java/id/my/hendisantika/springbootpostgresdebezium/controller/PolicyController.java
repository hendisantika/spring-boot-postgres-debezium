package id.my.hendisantika.springbootpostgresdebezium.controller;

import id.my.hendisantika.springbootpostgresdebezium.model.Policy;
import id.my.hendisantika.springbootpostgresdebezium.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 26/08/24
 * Time: 19.59
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping(value = "/add-policy")
    public ResponseEntity<Policy> addPolicy(@RequestBody Policy policy) {
        return ResponseEntity.ok()
                .body(policyService.addPolicy(policy));
    }

    @PostMapping(value = "/update-policy/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable("id") Long id, @RequestBody Policy policy) {
        return ResponseEntity.ok()
                .body(policyService.updatePolicy(id, policy));
    }

    @GetMapping(value = "/get-allpolicies")
    public ResponseEntity<List<Policy>> getAllPolicies() {
        return ResponseEntity.ok()
                .body(policyService.getAllPolicies());
    }
}
