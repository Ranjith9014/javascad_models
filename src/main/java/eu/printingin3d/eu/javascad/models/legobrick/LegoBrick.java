package eu.printingin3d.eu.javascad.models.legobrick;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.models.Cylinder;
import eu.printingin3d.javascad.models.Extendable3dModel;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.utils.SaveScadFiles;

public class LegoBrick extends Extendable3dModel {
	private static final double ONE_SEGMENT_WIDTH = 8.0;
	private static final double HEIGHT = 9.6;
	private static final double HORIZONTAL_GAP = 2*0.1;
	private static final double WALL_THICKNESS = 1.5;
	private static final Radius AXLE_INNER_DIAMETER = Radius.fromDiameter(4.75);
	private static final Radius AXLE_OUTER_DIAMETER = Radius.fromDiameter(6.51);
	private static final Radius AXLE_ONE_DIAMETER = Radius.fromDiameter(3.0);
	private static final Radius KNOB_DIAMETER = Radius.fromDiameter(4.85);
	private static final double KNOB_HEIGTH = 1.8;

	protected LegoBrick() {
		// to make submodel works, which is used by move / rotate etc method in Abstract3dModel
	}
	
	public LegoBrick(int xSize, int ySize) {
		Abstract3dModel base = new Difference(
						new Cube(new Dims3d(ONE_SEGMENT_WIDTH*xSize-HORIZONTAL_GAP, ONE_SEGMENT_WIDTH*ySize-HORIZONTAL_GAP, HEIGHT)),
						new Cube(new Dims3d(ONE_SEGMENT_WIDTH*xSize-HORIZONTAL_GAP-WALL_THICKNESS*2, ONE_SEGMENT_WIDTH*ySize-HORIZONTAL_GAP-WALL_THICKNESS*2, HEIGHT-WALL_THICKNESS)).move(Coords3d.zOnly(-WALL_THICKNESS))						
				);
		this.baseModel = base
				.addModel(addAxles(xSize, ySize))
				.addModel(getKnobs(base, xSize, ySize));
	}

	private static Abstract3dModel getKnobs(Abstract3dModel base, int xSize, int ySize) {
		List<Coords3d> moves = new ArrayList<>();
		for (int x=0;x<xSize;x++) {
			for (int y=0;y<ySize;y++) {
				moves.add(new Coords3d((x-(xSize-1.0)/2.0)*ONE_SEGMENT_WIDTH, (y-(ySize-1.0)/2.0)*ONE_SEGMENT_WIDTH, 0.0));
			}
		}
		return getKnob(base).moves(moves);
	}
	
	private static Abstract3dModel addAxles(int xSize, int ySize) {
		List<Coords3d> moves = new ArrayList<>();
		if (xSize==1) {
			for (int y=0;y<ySize-1;y++) {
				moves.add(Coords3d.yOnly((y-(ySize-2.0)/2.0)*ONE_SEGMENT_WIDTH));
			}
			return getAxleOne().moves(moves);
		}
		else if (ySize==1) {
			for (int x=0;x<xSize-1;x++) {
				moves.add(Coords3d.xOnly((x-(xSize-2.0)/2.0)*ONE_SEGMENT_WIDTH));
			}
			return getAxleOne().moves(moves);
		}
		else {		
			for (int x=0;x<xSize-1;x++) {
				for (int y=0;y<ySize-1;y++) {
					moves.add(new Coords3d((x-(xSize-2.0)/2.0)*ONE_SEGMENT_WIDTH, (y-(ySize-2.0)/2.0)*ONE_SEGMENT_WIDTH, 0.0));
				}
			}
			return getAxle().moves(moves);
		}
	}
	
	private static Abstract3dModel getKnob(Abstract3dModel base) {
		return new Cylinder(KNOB_HEIGTH, KNOB_DIAMETER).align(Side.TOP_OUT, base);
	}
	
	private static Abstract3dModel getAxle() {
		return new Difference(
				new Cylinder(HEIGHT, AXLE_OUTER_DIAMETER),
				new Cylinder(HEIGHT, AXLE_INNER_DIAMETER).move(Coords3d.zOnly(-0.01))
		);
	}
	
	private static Abstract3dModel getAxleOne() {
		return new Cylinder(HEIGHT, AXLE_ONE_DIAMETER);
	}
	
	public static void run(File root) throws IllegalValueException, IOException {
		new SaveScadFiles(root).
				addModel("lego_brick.scad", new LegoBrick(2, 6)).
				saveScadFiles();
	}
	
	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		run(root);
	}

}
