package pkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@SpringBootApplication
public class ReactiveChatApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReactiveChatApplication.class, args);
	}

	@Bean
	public UnicastProcessor<ChatMessage> publisher() {
		return UnicastProcessor.create();
	}

	@Bean
	public Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher) {
		return publisher.replay(30).autoConnect();
	}
}
