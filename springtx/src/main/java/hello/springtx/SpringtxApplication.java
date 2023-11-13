package hello.springtx;

import hello.springtx.config.repository.jdbc.DataSourceConfig;
import hello.springtx.config.repository.jpa.JpaConfig;
import hello.springtx.config.service.TxAopConfig;
import hello.springtx.config.service.TxManagerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(MemoryConfig.class)
//@Import(DriverManagerConfig.class)
//@Import(DataSourceConfig.class)
//@Import({JpaConfig.class, TxManagerConfig.class})
@Import({JpaConfig.class, TxAopConfig.class})
@SpringBootApplication(scanBasePackages = "hello.springtx.web")
public class SpringtxApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringtxApplication.class, args);
    }
}
