package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootTest
public class InitTxTest {

    @Autowired InitService initService;

    @Test
    void tx_in_postconstruct() {
    }

    @TestConfiguration
    static class InitTestConfig {
        @Bean
        public InitService initService() {
            return new InitService();
        }
    }

    static class InitService {

        @PostConstruct
        public void init() {
            log.info("init method start");
            printTxInfo();
            log.info("init method end");
        }

        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void init2() {
            log.info("init2 method start");
            printTxInfo();
            log.info("init2 method end");
        }

        private void printTxInfo() {
            log.info("Tx active : {}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("Tx readOnly : {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        }
    }
}
