package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired TestService testService;

    @Test
    void tx_call_internal() {
        testService.external();
    }

    @Test
    void testService_is_proxy() {
        assertThat(AopUtils.isAopProxy(testService)).isTrue();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TestService testService() {
            return new TestService();
        }
    }

    static class TestService {

        public void external() {
            log.info("call external");
            internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            log.info("Tx active : {}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("Tx readOnly : {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        }
    }
}
