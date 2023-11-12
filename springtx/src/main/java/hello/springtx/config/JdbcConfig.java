package hello.springtx.config;

import hello.springtx.repository.ItemRepository;
import hello.springtx.repository.jdbc.ItemJdbcRepository;
import hello.springtx.repository.memory.ItemMemoryRepository;
import hello.springtx.service.ItemService;
import hello.springtx.service.ItemServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JdbcConfig {

    @Bean
    public ItemRepository itemRepository() {
        log.info("ItemJdbcRepository Injected");
        return new ItemJdbcRepository();
    }

    @Bean
    public ItemService itemService() {
        log.info("ItemServiceV1 Injected");
        return new ItemServiceV1(itemRepository());
    }
}
