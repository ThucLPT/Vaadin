package pkg.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

import pkg.ui.view.DashboardView;
import pkg.ui.view.ListView;

@PWA(name = "Customer Relationship Management", shortName = "CRM", enableInstallPrompt = false)
public class MainLayout extends AppLayout {
	private static final long serialVersionUID = 1L;

	public MainLayout() {
		setDrawerOpened(false);
		H4 h4 = new H4("Customer Relationship Management");
		HorizontalLayout layout = new HorizontalLayout(new DrawerToggle(), h4, new Anchor("/logout", "Log out"));
		layout.setWidth("99%");
		layout.expand(h4);
		layout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
		addToNavbar(layout);
		addToDrawer(new VerticalLayout(new RouterLink("List", ListView.class), new RouterLink("Dashboard", DashboardView.class)));
	}
}
