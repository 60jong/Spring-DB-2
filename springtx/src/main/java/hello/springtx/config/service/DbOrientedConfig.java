package hello.springtx.config.service;

import hello.springtx.repository.ItemRepository;
import hello.springtx.service.ItemDBService;
import hello.springtx.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DbOrientedConfig {

    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        log.info("ItemDBService Injected");
        return new ItemDBService(itemRepository);
    }
}
