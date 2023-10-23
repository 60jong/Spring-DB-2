package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TxLevelTest {

    @Autowired
    TestService testService;

    @Test
    void txLevel() {
        testService.funcTx();
        testService.funcNonTx();
    }

    @TestConfiguration
    static class TxLevelConfig {
        @Bean
        TestService testService() {
            return new TestServiceImpl();
        }
    }

    @Transactional(readOnly = true)
    interface TestService {

        void funcTx();
        void funcNonTx();
    }

    @Slf4j
    static class TestServiceImpl implements TestService {

        @Transactional
        @Override
        public void funcTx() {
            log.info("funcTx start");

            printTxInfo();

            log.info("funcTx end");
        }

        @Override
        public void funcNonTx() {
            log.info("funcNonTx start");

            printTxInfo();

            log.info("funcNonTx end");
        }

        private void printTxInfo() {
            log.info("Tx active : {}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("Tx readOnly : {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        }
    }
}
