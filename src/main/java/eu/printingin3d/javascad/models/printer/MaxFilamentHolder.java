package eu.printingin3d.javascad.models.printer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.Extendable3dModel;
import eu.printingin3d.javascad.models.legobrick.LegoBrick;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.tranzitions.Union;
import eu.printingin3d.javascad.utils.SaveScadFiles;

public class MaxFilamentHolder extends Extendable3dModel {
	private static final double BASE_LENGTH    = 50;
	private static final double BASE_WIDTH     = 10;
	private static final double BASE_THICKNESS =  8;
	
	private static final Radius BOLT_HOLE_DIAM =  Radius.fromDiameter(5);
	private static final double BOLT_DIST      = 17;
	
	private static final Radius AXIS_DIAM      =  Radius.fromDiameter(8);
	private static final Radius AXIS_FRAME_DIAM= Radius.fromDiameter(18);
	
	public MaxFilamentHolder() {
		Cube base = new Cube(new Dims3d(BASE_LENGTH, BASE_WIDTH, BASE_THICKNESS));
		Abstract3dModel axisHolder = new Cylinder(BASE_WIDTH, AXIS_FRAME_DIAM)
			.rotate(Angles3d.ROTATE_MINUS_X)
			.align(Side.BOTTOM_IN, base);
		this.baseModel = new Difference(
				new Union(base, axisHolder),
				new Cylinder(BASE_THICKNESS+1, BOLT_HOLE_DIAM)
						.moves(Arrays.asList(Coords3d.xOnly(-BOLT_DIST), Coords3d.xOnly(+BOLT_DIST))),
				new Cylinder(BASE_WIDTH+1, AXIS_DIAM)
					.rotate(Angles3d.ROTATE_MINUS_X)
					.align(Side.CENTER, axisHolder)
		);
	}
	
	public static void run(File root) throws IllegalValueException, IOException {
		new SaveScadFiles(root).
				addModel("max_filament_holder.scad", new LegoBrick(2, 4)).
				saveScadFiles();
	}
	
	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		run(root);
	}
}
