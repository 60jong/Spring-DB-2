package site._60jong.transaction.repository.log;

import site._60jong.transaction.domain.Log;

import java.util.Optional;

public interface LogRepository {

    Log save(Log logMessage);

    Optional<Log> findOne(String message);
}
