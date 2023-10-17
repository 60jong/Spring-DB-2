package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.JdbcItemRepositoryV1;
import hello.itemservice.repository.JdbcItemRepositoryV2;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateV2Config {

    @Bean
    public ItemService itemService(DataSource dataSource) {
        return new ItemServiceV1(itemRepository(dataSource));
    }

    @Bean
    public ItemRepository itemRepository(DataSource dataSource) {
        return new JdbcItemRepositoryV2(dataSource);
    }

}
