package hello.springtx.config.repository.jdbc;

import hello.springtx.repository.ItemRepository;
import hello.springtx.repository.jdbc.ItemDataSourceRepository;
import hello.springtx.service.ItemService;
import hello.springtx.service.ItemDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static hello.springtx.config.repository.jdbc.JdbcConnectionConst.*;

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
