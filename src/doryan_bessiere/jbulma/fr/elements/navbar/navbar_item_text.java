package doryan_bessiere.jbulma.fr.elements.navbar;

import doryan_bessiere.jwebapi.fr.engine.nodes.parent.a.a;

public class navbar_item_text extends navbar_item {

	public navbar_item_text(String text, String href, NavbarItemLocation location) {
		this.navbar_item = (a) new a(href, null, null, text).applyClass("navbar-item");
	}
}
