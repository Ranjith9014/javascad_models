package eu.printingin3d.javascad.models.wand.element;

import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.tranzitions.Direction;
import eu.printingin3d.javascad.tranzitions.Slicer;
import eu.printingin3d.javascad.tranzitions.Union;

public class SlicedElement extends AbstractWandElement {
	private final double size;

	public SlicedElement(Radius newRadius, double size) {
		super(newRadius);
		this.size = size;
	}

	@Override
	public Abstract3dModel getModel(Radius lastRadius) {
		Abstract3dModel current;
		List<Abstract3dModel> subs = new ArrayList<>();
		
		for (int i=0;i<4;i++) {
			subs.add(
					new Slicer(new Cylinder(size, lastRadius.plusRadius(1.0), radius.plusRadius(1.0)), Direction.X, 0.47, 0.47)
							.rotate(new Angles3d(0, 0, i*180.0/4+22.5))
					);
		}
		
		current = new Union(new Cylinder(size, lastRadius.mul(0.85), radius.mul(0.85)),
				new Difference(new Cylinder(size, lastRadius, radius), subs)
				);
		return current;
	}

}
