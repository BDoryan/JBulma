package doryan_bessiere.jbulma.fr.elements;

import doryan_bessiere.jwebapi.fr.engine.nodes.Node;

public abstract class BulmaNode {
	
	public Node node;
	
	public BulmaNode(Node node) {
		this.node = node;
	}
	
	public abstract Node getMain();
}
