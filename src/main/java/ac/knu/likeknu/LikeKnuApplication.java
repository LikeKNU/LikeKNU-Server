package ac.knu.likeknu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LikeKnuApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeKnuApplication.class, args);
	}

}
