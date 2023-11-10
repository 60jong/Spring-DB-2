package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jdbctemplate.JdbcItemRepositoryV1;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateV1Config {

    @Bean
    public ItemService itemService(DataSource dataSource) {
        return new ItemServiceV1(itemRepository(dataSource));
    }

    @Bean
    public ItemRepository itemRepository(DataSource dataSource) {
        return new JdbcItemRepositoryV1(dataSource);
    }

}
