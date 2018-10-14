package doryan_bessiere.jbulma.fr.elements.navbar;

import java.util.ArrayList;

import doryan_bessiere.jwebapi.fr.engine.nodes.parent.a.a;
import doryan_bessiere.jwebapi.fr.engine.nodes.parent.div.div;

public class navbar_dropdown {
	
	public ArrayList<navbar_item> items = new ArrayList<>();
	public NavbarItemLocation location;

	public div navbar_dropdown_item;
	public a navbar_text;

	public div navbar_dropdown;
	
	public navbar_dropdown(String text, boolean hoverable, NavbarItemLocation location) {
		this.location = location;
		
		this.navbar_dropdown_item = new div("navbar-item has-dropdown");
		if(hoverable) {
			this.navbar_dropdown_item.classUtils.add("is-hoverable");	
		}
		
		this.navbar_text = (a) new a(null, null, null, text).applyClass("navbar-link");
		navbar_dropdown_item.addChildren(this.navbar_text);
		
		this.navbar_dropdown = new div("navbar-dropdown");
		navbar_dropdown_item.addChildren(this.navbar_dropdown);
	}
	
	public void add(navbar_item item) {
		items.add(item);
		this.navbar_dropdown.addChildren(item.getNavbarItem());
	}
	
	public void remove(navbar_item item) {
		items.remove(item);
		this.navbar_dropdown.removeChildren(item.getNavbarItem());
	}
	
	public div getNavbarDropdown() {
		return this.navbar_dropdown_item;
	}
}
