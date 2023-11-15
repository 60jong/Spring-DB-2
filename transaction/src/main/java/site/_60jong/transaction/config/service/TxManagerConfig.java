package site._60jong.transaction.config.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.service.ItemEntityTxManagerService;
import site._60jong.transaction.service.ItemService;

@Slf4j
@Configuration
public class TxManagerConfig {

    @Bean
    public ItemService itemService(ItemRepository itemRepository, PlatformTransactionManager ptm) {
        log.info("ItemDBService Injected");
        return new ItemEntityTxManagerService(itemRepository, ptm);
    }
}
