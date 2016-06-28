package eu.printingin3d.eu.javascad.models.cakeholder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.printingin3d.javascad.basic.Angle;
import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords2d.Dims2d;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.Extendable3dModel;
import eu.printingin3d.javascad.models.Ring;
import eu.printingin3d.javascad.models2d.Square;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.tranzitions.Union;
import eu.printingin3d.javascad.utils.SaveScadFiles;
import eu.printingin3d.javascad.utils.UsefulConsts;

public class CakeHolder extends Extendable3dModel {
	private static final Radius SECTION1_D = Radius.fromDiameter(80); 
	private static final Radius SECTION2_D = Radius.fromDiameter(130);
	private static final Radius RING1_D = Radius.fromDiameter(140);
	private static final Radius RING2_D = Radius.fromDiameter(150);
	
	private static final Radius OUTER_DIAMETER = Radius.fromDiameter(160);
	
	private static final Radius BIG_HOLE_DIAMETER = Radius.fromDiameter(30);

	private static final Radius SMALL_HOLE_DIAMETER = Radius.fromDiameter(7);
	private static final double SMALL_HOLE_DIST_FROM_ORIG = 30;
	
	private static final int NUMBER_OF_BIG_RINGS = 12;
	
	private static final double THICKNESS = 7.5;
	private static final double THICKNESS_THIN = 4;
	
	
	public CakeHolder() {
		Abstract3dModel _base = new Cylinder(THICKNESS, OUTER_DIAMETER);
		
		Abstract3dModel ring = createMiddleRing();
		
		Abstract3dModel base = new Union(
				createOuterRing().align(Side.BOTTOM_IN, _base),
				ring.align(Side.BOTTOM_IN, _base),
				new Cylinder(THICKNESS, SECTION1_D)
			);
		
		Abstract3dModel bigHole = new Cylinder(THICKNESS+1, BIG_HOLE_DIAMETER);
		Abstract3dModel smallHole =
			trice(new Cylinder(THICKNESS+1, SMALL_HOLE_DIAMETER), SMALL_HOLE_DIAMETER.getDiameter()*0.43)
					.move(Coords3d.yOnly(-SMALL_HOLE_DIST_FROM_ORIG));
		
		this.baseModel = new Difference(base,
				trice(bigHole, BIG_HOLE_DIAMETER.getRadius()+1),
				smallHole,
				smallHole.rotate(new Angles3d(0, 0, +120)),
				smallHole.rotate(new Angles3d(0, 0, -120))
			);
	}

	private Abstract3dModel createOuterRing() {
		List<Coords3d> moves = new ArrayList<>();
		
		Angle slice = Angle.A360.divide(24);
		
		for (int i=0;i<24;i++) {
			moves.add(RING1_D.toCoordinate(slice.mul(i)).withZ(0));
			moves.add(RING2_D.toCoordinate(slice.mul(i+0.5)).withZ(0));
		}
		
		Abstract3dModel ringBase = new Cylinder(THICKNESS, OUTER_DIAMETER);
		return new Union( 
			new Difference(
				ringBase,
				new Cylinder(THICKNESS+1, SECTION2_D),
				new Ring(RING1_D.getRadius(), new Square(new Dims2d(2, 2))).align(Side.TOP_IN, ringBase).move(Coords3d.zOnly(0.1)),
				new Ring(RING2_D.getRadius(), new Square(new Dims2d(2, 2))).align(Side.TOP_IN, ringBase).move(Coords3d.zOnly(0.1))
			),
			new Cylinder(THICKNESS, Radius.fromRadius(1.1)).moves(moves)
			);
	}

	private Abstract3dModel createMiddleRing() {
		Radius offset = SECTION1_D.add(SECTION2_D).divide(2);
		
		Angle slice = Angle.A360.divide(NUMBER_OF_BIG_RINGS);
		
		List<Coords3d> moves = new ArrayList<>();
		for (int i=0;i<NUMBER_OF_BIG_RINGS;i++) {
			moves.add(offset.toCoordinate(slice.mul(i)).withZ(0));
		}
		
		return new Ring((SECTION2_D.getDiameter()-SECTION1_D.getDiameter())/4, new Square(new Dims2d(THICKNESS_THIN, THICKNESS_THIN)))
				.moves(moves);
	}
	
	private static Abstract3dModel trice(Abstract3dModel orig, double dist) {
		return orig.moves(Arrays.asList(
					new Coords3d(+dist, -dist/2, 0),
					new Coords3d(-dist, -dist/2, 0),
					Coords3d.yOnly(dist*(UsefulConsts.SQRT_3-0.5))
				));
	}

	public static void run(File root) throws IOException {
		new SaveScadFiles(root).
				addModel("cakeholder.scad", new CakeHolder()).
				saveScadFiles();
	}

	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		run(root);
	}
}
