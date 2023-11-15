package site._60jong.transaction.config.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.service.ItemDBService;
import site._60jong.transaction.service.ItemService;

@Slf4j
@Configuration
public class DbOrientedConfig {

    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        log.info("ItemDBService Injected");
        return new ItemDBService(itemRepository);
    }
}
