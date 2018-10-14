package doryan_bessiere.jbulma.fr.elements.navbar;

import doryan_bessiere.jwebapi.fr.engine.nodes.children.hr.hr;

public class navbar_item_divider extends navbar_item {

	public navbar_item_divider() {
		this.navbar_item = (hr) new hr().applyClass("navbar-divider");
	}
}
