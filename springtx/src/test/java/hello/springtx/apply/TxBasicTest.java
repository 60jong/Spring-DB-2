package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TxBasicTest {

    @Test
    @Transactional
    void tx() {
        boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
        assertThat(txActive).isTrue();
    }

    @Test
    void nonTx() {
        boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
        assertThat(txActive).isFalse();
    }
}
