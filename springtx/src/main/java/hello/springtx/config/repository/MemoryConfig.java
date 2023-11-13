package hello.springtx.config.repository;

import hello.springtx.repository.memory.ItemMemoryRepository;
import hello.springtx.repository.ItemRepository;
import hello.springtx.service.ItemService;
import hello.springtx.service.ItemDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MemoryConfig {

    @Bean
    public ItemRepository itemRepository() {
        log.info("ItemMemoryRepository Injected");
        return new ItemMemoryRepository();
    }

    @Bean
    public ItemService itemService() {
        log.info("ItemServiceV1 Injected");
        return new ItemDBService(itemRepository());
    }
}
