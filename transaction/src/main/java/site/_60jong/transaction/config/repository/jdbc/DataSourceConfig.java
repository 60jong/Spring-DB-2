package site._60jong.transaction.config.repository.jdbc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.jdbc.ItemDataSourceRepository;

import javax.sql.DataSource;

import static site._60jong.transaction.config.repository.jdbc.JdbcConnectionConst.*;


@Slf4j
@Configuration
public class DataSourceConfig {

    private final DataSource dataSource;

    public DataSourceConfig() {
        this.dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    @Bean
    public ItemRepository itemRepository() {
        log.info("ItemDataSourceRepository Injected");
        return new ItemDataSourceRepository(dataSource);
    }
}
