package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.models.Abstract3dModel;

public abstract class AbstractWandElement {
	protected final Radius radius;
	
	protected AbstractWandElement(Radius radius) {
		this.radius = radius;
	}

	public Radius getNewRadius() {
		return radius;
	}
	
	public abstract Abstract3dModel getModel(Radius lastRadius);
}
