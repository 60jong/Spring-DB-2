package hello.springtx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringtxApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		AppConfig.MemberInstanceService memberInstanceService = context.getBean("memberInstanceService", AppConfig.MemberInstanceService.class);

		System.out.println(memberInstanceService);
		memberInstanceService = context.getBean("memberInstanceService", AppConfig.MemberInstanceService.class);
		System.out.println(memberInstanceService);
	}

}
