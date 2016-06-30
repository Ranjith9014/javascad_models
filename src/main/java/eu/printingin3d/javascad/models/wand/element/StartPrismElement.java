package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Prism;

public class StartPrismElement extends AbstractWandElement {
	private final double length;
	private final int numberOfSides;
	
	public StartPrismElement(Radius radius, double length, int numberOfSides) {
		super(radius);
		this.length = length;
		this.numberOfSides = numberOfSides;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new Prism(length, Radius.ZERO, radius, numberOfSides);
	}
}
