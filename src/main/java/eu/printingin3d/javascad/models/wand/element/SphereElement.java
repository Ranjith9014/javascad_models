package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Boundaries3d;
import eu.printingin3d.javascad.coords.Boundary;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.BoundedModel;
import eu.printingin3d.javascad.models.Sphere;

public class SphereElement extends AbstractWandElement {
	private final Radius size;
	
	public SphereElement(Radius radius, Radius size) {
		super(radius);
		this.size = size;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		return new BoundedModel(new Sphere(size), new Boundaries3d(
				Boundary.createSymmetricBoundary(size.getRadius()), 
				Boundary.createSymmetricBoundary(size.getRadius()), 
				Boundary.createSymmetricBoundary(size.getRadius()*0.8)
			));
	}
}
