package pkg;

import java.util.*;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Snack Order")
public class MainView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private Grid<Order> grid = new Grid<>(Order.class);
	private List<Order> list = new ArrayList<>();

	private TextField name = new TextField("Name");
	private IntegerField quantity = new IntegerField("Quantity");
	private Select<String> snack = new Select<>();

	public MainView() {
		add(new H1("Snack Order"), form(), grid);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

	private Component form() {
		Map<String, List<String>> snacks = new HashMap<>();
		snacks.put("Fruit", Arrays.asList("Banana", "Apple", "Orange", "Avocado"));
		snacks.put("Candy", Arrays.asList("Chocolate", "Gummy", "Granola"));
		snacks.put("Drink", Arrays.asList("Soda", "Water", "Coffee", "Tea"));

		snack.setLabel("Snack");
		snack.setEnabled(false);

		Select<String> typeSelect = new Select<>();
		typeSelect.setLabel("Type");
		typeSelect.setItems(snacks.keySet());
		typeSelect.addValueChangeListener(e -> {
			String type = e.getValue();
			if (!type.isEmpty()) {
				snack.setEnabled(true);
				snack.setItems(snacks.get(type));
			} else
				snack.setEnabled(false);
		});

		Binder<Order> binder = new BeanValidationBinder<>(Order.class);
		binder.bindInstanceFields(this);

		Button orderBtn = new Button("Order", click -> {
			if (binder.validate().isOk()) {
				Order order = new Order();
				binder.writeBeanIfValid(order);
				addOrder(order);
				binder.readBean(new Order());
				typeSelect.setValue("");
			}
		});
		orderBtn.setThemeName("primary");

		Button toggleBtn = new Button("Toggle dark theme", click -> {
			ThemeList list = UI.getCurrent().getElement().getThemeList();
			if (list.contains("dark"))
				list.remove("dark");
			else
				list.add("dark");
		});

		HorizontalLayout formLayout = new HorizontalLayout(name, quantity, typeSelect, snack, orderBtn, toggleBtn);
		formLayout.setAlignItems(Alignment.BASELINE);
		return formLayout;
	}

	private void addOrder(Order order) {
		list.add(order);
		grid.setItems(list);
	}
}
