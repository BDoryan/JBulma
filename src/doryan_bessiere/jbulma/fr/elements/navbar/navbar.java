package doryan_bessiere.jbulma.fr.elements.navbar;

import doryan_bessiere.jbulma.fr.elements.BulmaNode;
import doryan_bessiere.jwebapi.fr.engine.css.MasterCSS;
import doryan_bessiere.jwebapi.fr.engine.nodes.Node;
import doryan_bessiere.jwebapi.fr.engine.nodes.children.img.img;
import doryan_bessiere.jwebapi.fr.engine.nodes.children.span.span;
import doryan_bessiere.jwebapi.fr.engine.nodes.parent.a.a;
import doryan_bessiere.jwebapi.fr.engine.nodes.parent.div.div;

public class navbar extends BulmaNode {

	public MasterCSS masterCSS;

	public div div_navbar;
	public div div_navbar_band;

	public div div_navbar_menu;
	
	public div div_navbar_start;
	public div div_navbar_end;

	public a img_a;

	public String navbar_name;
	
	public navbar(String navbar_name) {
		super(new div("navbar"));
		
		this.navbar_name = navbar_name;

		this.div_navbar = (div) this.node;

		this.div_navbar.addVariable("role", "navigation");
		this.div_navbar.addVariable("aria-label", "main navigation");

		this.div_navbar_band = new div("navbar-brand");
		
		this.div_navbar_menu = (div) new div("navbar-menu").addVariable("id", this.navbar_name);

		this.div_navbar_start = new div("navbar-start");
		this.div_navbar_end = new div("navbar-end");

		this.div_navbar_menu.addChildren(div_navbar_start); 
		this.div_navbar_menu.addChildren(div_navbar_end); 

		this.div_navbar.addChildren(this.div_navbar_band);
		this.div_navbar.addChildren(this.div_navbar_menu);
	}
	
	public navbar_item addStringItem(String text, String href, NavbarItemLocation location) {
		navbar_item item = new navbar_item_text(text, href, location);
		if(location == NavbarItemLocation.LEFT) {
			div_navbar_start.addChildren(item.getNavbarItem());
		} else {
			div_navbar_end.addChildren(item.getNavbarItem());
		}
		
		return item;
	}
	
	public navbar_dropdown addDropdownItem(String text, boolean hoverable, NavbarItemLocation location) {
		navbar_dropdown navbar_dropdown = new navbar_dropdown(text, hoverable, location);

		if(location == NavbarItemLocation.LEFT) {
			div_navbar_start.addChildren(navbar_dropdown.getNavbarDropdown());
		} else {
			div_navbar_end.addChildren(navbar_dropdown.getNavbarDropdown());
		}
		
		return navbar_dropdown;
	}
	
	public void setImage(img img, String href) { 
		if (img == null) {
			this.div_navbar_band.removeChildren(this.img_a);
			this.img_a = null;
			return;
		}
		this.img_a = new a(href, null, null, null, img);
		this.img_a.applyClass("navbar-item");
		this.div_navbar_band.addChildren(this.img_a); 
	}

	@Override
	public Node getMain() {
		span span = (span) new span("").addVariable("aria-hidden", "true");
		a a_navbar_burger = new a(null, null, null, null, span, span, span);
		a_navbar_burger.addVariable("role", "button").addVariable("aria-label", "menu").addVariable("aria-expanded", "false").addVariable("data-target", navbar_name);
		a_navbar_burger.applyClass("navbar-burger burger");
		
		this.div_navbar_band.addChildren(a_navbar_burger);
		
		this.div_navbar.compile();
		return this.div_navbar;
	}
}
