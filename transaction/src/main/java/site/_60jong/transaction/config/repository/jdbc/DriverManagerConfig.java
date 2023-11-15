package site._60jong.transaction.config.repository.jdbc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.jdbc.ItemDriverManagerRepository;
import site._60jong.transaction.service.ItemDBService;
import site._60jong.transaction.service.ItemService;

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
