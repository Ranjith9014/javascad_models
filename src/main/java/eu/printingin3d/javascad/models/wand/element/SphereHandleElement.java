package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.Sphere;
import eu.printingin3d.javascad.tranzitions.Scale;

public class SphereHandleElement extends AbstractWandElement {
	private final double length;
	private final int numberOfSpikes = 8;
	private final Radius spikeRadius = Radius.fromRadius(0.5);

	public SphereHandleElement(Radius radius, double length) {
		super(radius);
		this.length = length;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		Abstract3dModel base = new Cube(length);
		double x = Math.sqrt(radius.getRadius()*radius.getRadius() - lastRadius.getRadius()*lastRadius.getRadius());

		double ratio = (length*0.5)/x;
		Abstract3dModel result = new Scale(new Sphere(radius), new Coords3d(1, 1, ratio))
				.subtractModel(new Cube(new Dims3d(radius.getDiameter()*2, radius.getDiameter()*2, ratio*radius.getDiameter())).align(Side.TOP_OUT_CENTER, base))
				.subtractModel(new Cube(new Dims3d(radius.getDiameter()*2, radius.getDiameter()*2, ratio*radius.getDiameter())).align(Side.BOTTOM_OUT_CENTER, base));

		double delta = 0.0;
		for (double z = 0; z<length*0.5; z+=2) {
			double s = Math.sqrt(radius.getRadius()*radius.getRadius()-(z/ratio)*(z/ratio))*2;
			Cylinder spike = new Cylinder(s+1, spikeRadius);
			for (int i=0;i<numberOfSpikes;i++) {
				result = result.addModel(spike.rotate(new Angles3d(90, 0, 360.0*(i+delta)/numberOfSpikes)).moves(Coords3d.zOnly(z).createVariances()));
			}
			
			delta = 0.5-delta;
		}			
		
		return result;
	}

}
