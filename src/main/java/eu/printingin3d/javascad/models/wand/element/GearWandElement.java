package eu.printingin3d.javascad.models.wand.element;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.tranzitions.Union;

public class GearWandElement extends AbstractWandElement {
	private static final double LAYER_THICKNESS = 0.1;
	
	private final double length;
	private final Radius baseRadius;

	public GearWandElement(Radius radius, Radius baseRadius, double length) {
		super(radius);
		this.length = length;
		this.baseRadius = baseRadius;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		Abstract3dModel result = new Union();
		
		double z = 0;
		do {
			Radius x1 = calculateX(z);
			z+=LAYER_THICKNESS;
			if (z>length) {
				z = length;
			}
			Radius x2 = calculateX(z);
			
			result = result.addModelTo(Side.TOP_OUT, new Cylinder(LAYER_THICKNESS, x1, x2));
		} while (z<length);
		
		
		return result;
	}

	//sin ^2((x*0.5+0.25)*pi)-0.5
	private Radius calculateX(double z) {
		double x = Math.sin(((z/length)*0.5+0.25)*Math.PI);
		return Radius.fromRadius((x-0.5)*2*(radius.getRadius()-baseRadius.getRadius())+baseRadius.getRadius());
	}

}
