package hello.springtx.config.repository.jpa;

import hello.springtx.repository.ItemRepository;
import hello.springtx.repository.jpa.ItemJpaRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JpaConfig {

    @Bean
    public ItemRepository itemRepository(EntityManager em) {
        return new ItemJpaRepository(em);
    }
}
