package pkg;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Route("")
@PageTitle("Reactive Chat")
@Push
public class MainView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private String username;
	@Autowired
	private UnicastProcessor<ChatMessage> publisher;
	@Autowired
	private Flux<ChatMessage> messages;

	public MainView() {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		add(new H1("Reactive Chat"), askUsername());
	}

	private Component askUsername() {
		HorizontalLayout layout = new HorizontalLayout();

		TextField textField = new TextField(null, "Username");
		textField.focus();

		Button button = new Button("Start", click -> {
			if (textField.getValue().trim().length() > 0) {
				username = textField.getValue().trim();
				remove(layout);
				displayChat();
			}
		});
		button.addClickShortcut(Key.ENTER);
		button.setThemeName("primary");

		layout.add(textField, button);
		return layout;
	}

	private void displayChat() {
		Div div = new Div();
		div.getStyle().set("overflow-y", "auto");
		div.setWidth("80%");
		add(div, chatSection());
		expand(div);
		messages.subscribe(message -> {
			getUI().ifPresent(ui -> ui.access(() -> {
				div.add(new Paragraph(message.getFrom() + ": " + message.getMessage()));
				div.getChildren().reduce((first, second) -> second).get().getElement().callJsFunction("scrollIntoView");
			}));
		});
	}

	private Component chatSection() {
		TextField textField = new TextField();
		textField.focus();

		Button button = new Button("Send", click -> {
			if (textField.getValue().trim().length() > 0) {
				publisher.onNext(new ChatMessage(username, textField.getValue().trim()));
				textField.clear();
				textField.focus();
			}
		});
		button.addClickShortcut(Key.ENTER);
		button.setThemeName("primary");

		HorizontalLayout layout = new HorizontalLayout(textField, button);
		layout.expand(textField);
		layout.setWidth("80%");
		return layout;
	}
}
