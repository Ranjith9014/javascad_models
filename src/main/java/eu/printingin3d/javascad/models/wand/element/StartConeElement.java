package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;

public class StartConeElement extends AbstractWandElement {
	private final double length;
	
	public StartConeElement(Radius radius, double length) {
		super(radius);
		this.length = length;
	}
	
	public StartConeElement() {
		this(Radius.fromRadius(6.0), 3.0);
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new Cylinder(length, Radius.ZERO, radius);
	}
}
