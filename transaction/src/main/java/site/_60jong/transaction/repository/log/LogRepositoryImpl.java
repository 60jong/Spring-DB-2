package site._60jong.transaction.repository.log;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.domain.Log;
import site._60jong.transaction.exception.MyException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class LogRepositoryImpl implements LogRepository {

    private final EntityManager em;

    @Transactional
    @Override
    public Log save(Log logMessage) {
        if (logMessage.getMessage().contains("exception")) {
            throw new MyException("로그 저장 예외");
        }

        em.persist(logMessage);
        log.info("Log : {}", logMessage.getMessage());
        return logMessage;
    }

    @Override
    public Optional<Log> findOne(String message) {
        return em.createQuery("select l from Log l where l.message = :message", Log.class)
                .setParameter("message", message)
                .getResultStream()
                .findAny();
    }
}
