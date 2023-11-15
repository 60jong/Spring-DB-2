package site._60jong.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import site._60jong.transaction.config.repository.jpa.JpaConfig;
import site._60jong.transaction.config.service.TxAopConfig;

//@Import(MemoryConfig.class)
//@Import(DriverManagerConfig.class)
//@Import(DataSourceConfig.class)
//@Import({JpaConfig.class, TxManagerConfig.class})
@Import({JpaConfig.class, TxAopConfig.class})
@SpringBootApplication(scanBasePackages = "site._60jong.transaction.web")
public class TransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

}
