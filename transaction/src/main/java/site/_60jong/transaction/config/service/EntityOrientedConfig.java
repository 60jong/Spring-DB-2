package site._60jong.transaction.config.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site._60jong.transaction.repository.item.jpa.ItemJpaRepository;
import site._60jong.transaction.service.ItemEntityService;
import site._60jong.transaction.service.ItemService;

@Slf4j
@Configuration
public class EntityOrientedConfig {

    @Bean
    public ItemService itemService(ItemJpaRepository itemRepository) {
        log.info("ItemDBService Injected");
        return new ItemEntityService(itemRepository);
    }
}
