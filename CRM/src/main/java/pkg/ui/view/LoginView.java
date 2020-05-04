package pkg.ui.view;

import java.util.Collections;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	private static final long serialVersionUID = 1L;
	private LoginForm form = new LoginForm();

	public LoginView() {
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		form.setAction("login");
		form.setForgotPasswordButtonVisible(false);
		add(form);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty())
			form.setError(true);
	}
}
