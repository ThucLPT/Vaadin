package pkg;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ChatMessage {
	private String from, message;
	private LocalDateTime dateTime;

	public ChatMessage(String from, String message) {
		this.from = from;
		this.message = message;
		this.dateTime = LocalDateTime.now();
	}
}
