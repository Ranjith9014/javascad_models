package eu.printingin3d.javascad.models.wand.element;

import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.basic.Angle;
import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords2d.Coords2d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.LinearExtrude;
import eu.printingin3d.javascad.models2d.Abstract2dModel;
import eu.printingin3d.javascad.models2d.Circle;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.tranzitions.Scale;
import eu.printingin3d.javascad.tranzitions.Union;

public class CelticElement extends AbstractWandElement {
	private final Radius startRadius;
	private final double length;
	private final boolean positive;
	private final Radius smallRadius;
	private final int numberOfSpirals;
	private final double smoothness;
	private final Angle spiralAngle;

	public CelticElement(Radius radius, double length, boolean positive, 
			Radius smallRadius, int numberOfSpirals, double smoothness) {
		this(radius, radius, length, positive, smallRadius, numberOfSpirals, smoothness, Angle.A360);
	}
	
	public CelticElement(Radius startRadius, Radius endRadius, double length, boolean positive, 
			Radius smallRadius, int numberOfSpirals, double smoothness, Angle spiralAngle) {
		super(endRadius);
		this.startRadius = startRadius;
		this.length = length;
		this.positive = positive;
		this.smallRadius = smallRadius;
		this.numberOfSpirals = numberOfSpirals;
		this.smoothness = smoothness;
		this.spiralAngle = spiralAngle;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		List<Abstract3dModel> spirals = new ArrayList<>();
		double a = 0.0;
		Abstract2dModel baseCircle = new Circle(smallRadius).move(Coords2d.xOnly(startRadius.getRadius()));
		double scale = positive ? radius.getRadius()/(smallRadius.getRadius()*smoothness+startRadius.getRadius()) : 
			radius.getRadius()/(startRadius.getRadius()-smallRadius.getRadius()*smoothness);
		while (a<360.0) {
			spirals.add(
					new Scale(
						new LinearExtrude(baseCircle, length, spiralAngle, scale),
						new Coords3d(1, 0.9, 1)
					).rotate(Angles3d.zOnly(a))
				);
			spirals.add(
					new Scale(
						new LinearExtrude(baseCircle, length, spiralAngle.inverse(), scale),
						new Coords3d(0.9, 1, 1)
					).rotate(Angles3d.zOnly(a))
				);
			a+= (360.0/numberOfSpirals);
		}

		if (positive) {
			spirals.add(new Cylinder(length, startRadius, radius));
			return new Union(spirals);
		}
		return new Difference(new Cylinder(length, startRadius, radius), spirals);
	}
}
