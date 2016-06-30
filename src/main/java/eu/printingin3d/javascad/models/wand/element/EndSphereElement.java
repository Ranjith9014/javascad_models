package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Sphere;
import eu.printingin3d.javascad.tranzitions.Direction;
import eu.printingin3d.javascad.tranzitions.Scale;
import eu.printingin3d.javascad.tranzitions.Slicer;

public class EndSphereElement extends AbstractWandElement {
	private final double length;

	public EndSphereElement(double length) {
		super(Radius.ZERO);
		this.length = length;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new Slicer(
				new Scale(
					new Sphere(lastRadius),
					new Coords3d(1, 1, length/lastRadius.getRadius())
				),
				Direction.Z, 2, 1);
	}
}
