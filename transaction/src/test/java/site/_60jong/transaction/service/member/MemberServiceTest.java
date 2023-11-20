package site._60jong.transaction.service.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.repository.log.LogRepository;
import site._60jong.transaction.repository.member.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * MemberService     Transaction - OFF
     * MemberRepository  Transaction - ON
     * LogRepository     Transaction - ON
     */
    @Test
    void outerTxOff_success() {
        // given
        String username = "username";

        // when
        memberService.joinV1(username);

        // then
        assertAll(
                () -> assertThat(memberRepository.findByName(username)).isPresent(),
                () -> assertThat(logRepository.findOne(username)).isPresent()
        );
    }

    @Test
    void outerTxOff_exception() {
        // given
        String username = "exception";

        // when
        memberService.joinV2(username);

        // then
        assertAll(
                () -> assertThat(memberRepository.findByName(username)).isPresent(),
                () -> assertThat(logRepository.findOne(username)).isEmpty()
        );
    }
}