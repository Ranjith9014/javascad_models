package eu.printingin3d.javascad.models.printer;

import java.io.File;
import java.io.IOException;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.enums.AlignType;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.Extendable3dModel;
import eu.printingin3d.javascad.models.legobrick.LegoBrick;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.tranzitions.Union;
import eu.printingin3d.javascad.utils.SaveScadFiles;

public class RollBearingHolder extends Extendable3dModel {
	private static final Radius BEARING_DIAMETER = Radius.fromDiameter(22.3);
	private static final double BEARING_DEPTH = 5.0;
	private static final Radius INSIDE_DIAMETER = Radius.fromDiameter(18.0);
	private static final double SKIRT_WIDTH = 3.0;
	private static final double SKIRT_THICKNESS = 3.0;
	private static final double INSIDE_MARGIN = 3.0;
	private static final Radius ROLL_INSIDE_DIAMETER = Radius.fromDiameter(54.5);
	
	public RollBearingHolder() {
		Cylinder base = new Cylinder(BEARING_DEPTH+INSIDE_MARGIN, ROLL_INSIDE_DIAMETER);
		
		this.baseModel = new Difference(
				new Union(base,
						new Cylinder(SKIRT_THICKNESS, ROLL_INSIDE_DIAMETER.plusRadius(SKIRT_WIDTH))
							.align(new Side(AlignType.CENTER, AlignType.CENTER, AlignType.MIN_IN), base)), 
				new Cylinder(BEARING_DEPTH, BEARING_DIAMETER)
					.align(new Side(AlignType.CENTER, AlignType.CENTER, AlignType.MIN_IN), base)
					.move(Coords3d.zOnly(-0.005)),
				new Cylinder(BEARING_DEPTH+INSIDE_MARGIN+1.0, INSIDE_DIAMETER)
					.align(Side.CENTER, base));
	}
	
	public static void run(File root) throws IllegalValueException, IOException {
		new SaveScadFiles(root).
				addModel("roll_bearing_holder.scad", new LegoBrick(2, 4)).
				saveScadFiles();
	}
	
	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		run(root);
	}
}
