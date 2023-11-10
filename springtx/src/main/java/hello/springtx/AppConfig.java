package hello.springtx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class AppConfig {

    @Bean
    public MemberInstanceService memberInstanceService() {
        MemberInstanceService instanceService = new MemberInstanceService();

        System.out.println(instanceService);
        return instanceService;
    }

    @Bean
    public MemberService memberService() {
        MemberInstanceService instanceService = memberInstanceService();
        System.out.println(instanceService);
        return new MemberService(instanceService);
    }

    static class MemberInstanceService {
    }

    @RequiredArgsConstructor
    static class MemberService {
        final MemberInstanceService memberInstanceService;
    }
}
