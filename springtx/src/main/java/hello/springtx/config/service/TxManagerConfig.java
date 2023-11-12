package hello.springtx.config.service;

import hello.springtx.repository.ItemRepository;
import hello.springtx.service.ItemEntityTxManagerService;
import hello.springtx.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class TxManagerConfig {

    @Bean
    public ItemService itemService(ItemRepository itemRepository, PlatformTransactionManager ptm) {
        log.info("ItemDBService Injected");
        return new ItemEntityTxManagerService(itemRepository, ptm);
    }
}
