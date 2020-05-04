package pkg.ui.view;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pkg.backend.PersonService;
import pkg.ui.MainLayout;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
public class DashboardView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private PersonService personService;

	public DashboardView(PersonService personService) {
		this.personService = personService;
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		add(new H1(personService.count() + " contacts"), chart());
	}

	private Chart chart() {
		Chart chart = new Chart(ChartType.PIE);
		DataSeries series = new DataSeries();
		personService.getStats().forEach((status, number) -> series.add(new DataSeriesItem(status, number)));
		chart.getConfiguration().setSeries(series);
		return chart;
	}
}
