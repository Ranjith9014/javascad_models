package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;


public class EndConeElement extends AbstractWandElement {

	public EndConeElement() {
		super(Radius.ZERO);
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new Cylinder(3.0, lastRadius, Radius.ZERO);
	}
}
