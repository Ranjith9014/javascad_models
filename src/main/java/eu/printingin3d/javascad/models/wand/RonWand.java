package eu.printingin3d.javascad.models.wand;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.wand.element.AbstractWandElement;
import eu.printingin3d.javascad.models.wand.element.CylinderElement;
import eu.printingin3d.javascad.models.wand.element.EndSphereElement;
import eu.printingin3d.javascad.models.wand.element.TransientCylinderElement;
import eu.printingin3d.javascad.utils.SaveScadFiles;

public class RonWand {
	/*
	 * 12" = 304.8mm
	 */
	private static final Radius RON_BIG_RAD = Radius.fromDiameter(13);
	private static final Radius RON_START_RAD = Radius.fromDiameter(12); 
	private static final Radius RON_SMALL_RAD = Radius.fromDiameter(11);
	private static final Radius RON_GAP_RAD = Radius.fromDiameter(10);
	private static final List<AbstractWandElement> RON_ELEMENTS = Arrays.asList(
			new CylinderElement(RON_START_RAD, 0.01),
			new TransientCylinderElement(RON_START_RAD.plusDiameter(-0.25), 2.0),
			new CylinderElement(RON_GAP_RAD, 1.0),
			// 95mm
			new CylinderElement(RON_START_RAD.plusDiameter(-0.5), 0.1),
			new TransientCylinderElement(RON_SMALL_RAD, 10.0),
			new CylinderElement(RON_SMALL_RAD, 5.0),
			new TransientCylinderElement(RON_BIG_RAD, 30.0),
			new CylinderElement(RON_BIG_RAD, 50.0),
			
			new CylinderElement(RON_GAP_RAD, 1.0),
			new CylinderElement(RON_BIG_RAD, 5.0),
			new CylinderElement(RON_GAP_RAD, 1.0),
			new CylinderElement(RON_BIG_RAD, 10.0),
			new TransientCylinderElement(RON_GAP_RAD, 150.0),
			new EndSphereElement(40)
			);

	public static void run(File root) throws IllegalValueException, IOException {
		WandMaker wandMaker = new WandMaker();
		
		new SaveScadFiles(new File(root.getAbsoluteFile()+"/wand/")).
				addModelProvider(wandMaker.createPrintableWandPieces(RON_ELEMENTS, "ron", ".print.scad")).
				saveScadFiles();

	}

	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		run(root);
	}
	
}
