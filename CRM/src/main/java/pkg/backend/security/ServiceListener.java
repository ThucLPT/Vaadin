package pkg.backend.security;

import org.springframework.stereotype.Component;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import pkg.ui.view.LoginView;

@Component
public class ServiceListener implements VaadinServiceInitListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(e -> e.getUI().addBeforeEnterListener(this::beforeEnter));
	}

	private void beforeEnter(BeforeEnterEvent event) {
		if (!LoginView.class.equals(event.getNavigationTarget()) && !SecurityUtils.isLoggedIn())
			event.rerouteTo(LoginView.class);
	}
}
