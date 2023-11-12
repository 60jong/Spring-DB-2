package hello.springtx;

import hello.springtx.config.JdbcConfig;
import hello.springtx.config.MemoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MemoryConfig.class)
//@Import(JdbcConfig.class)
@SpringBootApplication(scanBasePackages = "hello.springtx.web")
public class SpringtxApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringtxApplication.class, args);
    }
}
