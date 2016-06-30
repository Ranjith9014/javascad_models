package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords2d.Dims2d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.Ring;
import eu.printingin3d.javascad.models2d.RoundedSquare;
import eu.printingin3d.javascad.tranzitions.Union;

public class RoundedCylinderElement extends AbstractWandElement {
	private final double size;
	
	public RoundedCylinderElement(Radius radius, double size) {
		super(radius);
		this.size = size;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new Union(new Ring(radius.getRadius()-2, new RoundedSquare(new Dims2d(4, size), Radius.fromRadius(1))), 
				new Cylinder(size, radius.plusRadius(-2.0)));
	}
}
