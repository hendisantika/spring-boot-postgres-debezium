package id.my.hendisantika.springbootpostgresdebezium.repository;

import id.my.hendisantika.springbootpostgresdebezium.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-postgres-debezium
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 26/08/24
 * Time: 19.49
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
