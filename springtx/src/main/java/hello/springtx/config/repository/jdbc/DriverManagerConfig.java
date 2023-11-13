package hello.springtx.config.repository.jdbc;

import hello.springtx.repository.ItemRepository;
import hello.springtx.repository.jdbc.ItemDriverManagerRepository;
import hello.springtx.service.ItemService;
import hello.springtx.service.ItemDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DriverManagerConfig {

    @Bean
    public ItemRepository itemRepository() {
        log.info("ItemDriverManagerRepository Injected");
        return new ItemDriverManagerRepository();
    }

    @Bean
    public ItemService itemService() {
        log.info("ItemServiceV1 Injected");
        return new ItemDBService(itemRepository());
    }
}
