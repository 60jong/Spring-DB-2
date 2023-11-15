package site._60jong.transaction.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired PlatformTransactionManager transactionManager;

    @Test
    void basic_tx() {
        log.info("트랜잭션 시작");
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        transactionManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void basic_tx_rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        transactionManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    @Test
    void double_commit() {
        log.info("트랜잭션1 시작");
        TransactionStatus ts1 = transactionManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션1 커밋 시작");
        transactionManager.commit(ts1);
        log.info("트랜잭션1 커밋 완료");

        log.info("트랜잭션2 시작");
        TransactionStatus ts2 = transactionManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션2 커밋 시작");
        transactionManager.commit(ts2);
        log.info("트랜잭션2 커밋 완료");

    }

    @Test
    void double_commit_rollback() {
        log.info("트랜잭션1 시작");
        TransactionStatus ts1 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋 시작");
        transactionManager.commit(ts1);
        log.info("트랜잭션1 커밋 완료");


        log.info("트랜잭션2 시작");
        TransactionStatus ts2 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 롤백 시작");
        transactionManager.rollback(ts2);
        log.info("트랜잭션2 롤백 완료");

    }

    @Test
    void inner_commit() {
        log.info("트랜잭션1 시작");
        TransactionStatus ts1 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 new Transaction : {}", ts1.isNewTransaction());

        log.info("트랜잭션2 시작");
        TransactionStatus ts2 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 new Transaction : {}", ts2.isNewTransaction());

        log.info("트랜잭션2 커밋 시작");
        transactionManager.commit(ts2);
        log.info("트랜잭션2 커밋 완료");

        log.info("트랜잭션1 커밋 시작");
        transactionManager.commit(ts1);
        log.info("트랜잭션1 커밋 완료");

    }

    @Test
    void inner_rollback() {
        log.info("트랜잭션1 시작");
        TransactionStatus ts1 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 new Transaction : {}", ts1.isNewTransaction());

        log.info("트랜잭션2 시작");
        TransactionStatus ts2 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 new Transaction : {}", ts2.isNewTransaction());

        log.info("트랜잭션2 롤백 시작");
        transactionManager.rollback(ts2);
        log.info("트랜잭션2 롤백 완료");

        log.info("트랜잭션1 커밋 시작");
        assertThatThrownBy(() -> transactionManager.commit(ts1)).isInstanceOf(UnexpectedRollbackException.class);
        log.info("트랜잭션1 커밋 완료");

    }

    @Test
    void outer_rollback_inner_commit() {
        log.info("트랜잭션1 시작");
        TransactionStatus ts1 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 new Transaction : {}", ts1.isNewTransaction());

        log.info("트랜잭션2 시작");
        TransactionStatus ts2 = transactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 new Transaction : {}", ts2.isNewTransaction());

        log.info("트랜잭션2 커밋 시작");
        transactionManager.commit(ts2);
        log.info("트랜잭션2 커밋 완료");

        log.info("트랜잭션1 롤백 시작");
        transactionManager.rollback(ts1);
        log.info("트랜잭션1 롤백 완료");

    }

    @Test
    void inner_rollback_requires_new() {
        DefaultTransactionAttribute txAttribute = new DefaultTransactionAttribute();

        log.info("트랜잭션1 시작");
        TransactionStatus ts1 = transactionManager.getTransaction(txAttribute);
        log.info("트랜잭션1 new Transaction : {}", ts1.isNewTransaction());

        log.info("트랜잭션2 시작");
        txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus ts2 = transactionManager.getTransaction(txAttribute);
        log.info("트랜잭션2 new Transaction : {}", ts2.isNewTransaction());

        log.info("트랜잭션2 롤백 시작");
        transactionManager.rollback(ts2);
        log.info("트랜잭션2 롤백 완료");

        log.info("트랜잭션1 커밋 시작");
        assertThatThrownBy(() -> transactionManager.commit(ts1)).isInstanceOf(UnexpectedRollbackException.class);
        log.info("트랜잭션1 커밋 완료");


    }
    @TestConfiguration
    static class TestConfig {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }
}
