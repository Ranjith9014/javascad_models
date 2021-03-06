package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;

public class TransientCylinderElement extends AbstractWandElement {
	private final double size;

	public TransientCylinderElement(Radius radius, double size) {
		super(radius);
		this.size = size;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new Cylinder(size, lastRadius, radius);
	}
}
