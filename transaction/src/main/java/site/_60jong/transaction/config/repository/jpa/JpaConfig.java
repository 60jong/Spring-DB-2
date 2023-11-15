package site._60jong.transaction.config.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.jpa.ItemJpaRepository;

@Slf4j
@Configuration
public class JpaConfig {

    @Bean
    public ItemRepository itemRepository(EntityManager em) {
        return new ItemJpaRepository(em);
    }
}
