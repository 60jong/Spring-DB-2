package site._60jong.transaction.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.domain.Log;
import site._60jong.transaction.domain.Member;
import site._60jong.transaction.exception.MyException;
import site._60jong.transaction.repository.log.LogRepository;
import site._60jong.transaction.repository.member.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    public Member joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("=== memberRepository 호출 시작 ===");
        memberRepository.save(member);
        log.info("=== memberRepository 호출 종료 ===");

        log.info("=== logRepository 호출 시작 ===");
        logRepository.save(logMessage);
        log.info("=== logRepository 호출 종료 ===");
        return member;
    }

    @Transactional
    public Member joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("=== memberRepository 호출 시작 ===");
        memberRepository.save(member);
        log.info("=== memberRepository 호출 종료 ===");

        try {
            log.info("=== logRepository 호출 시작 ===");
            logRepository.save(logMessage);
            log.info("=== logRepository 호출 종료 ===");
        } catch (MyException me) {
            log.info("log 저장 시 예외 발생");
            log.info("정상 흐름 반환");
        }

        return member;
    }
}
