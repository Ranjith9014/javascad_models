package eu.printingin3d.javascad.models;

import java.io.File;
import java.io.IOException;

import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.cakeholder.CakeHolder;
import eu.printingin3d.javascad.models.legobrick.LegoBrick;
import eu.printingin3d.javascad.models.printer.MaxFilamentHolder;
import eu.printingin3d.javascad.models.printer.RollBearingHolder;
import eu.printingin3d.javascad.models.wand.RonWand;

public class Main {
	
	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		LegoBrick.run(root);
		CakeHolder.run(root);
		RonWand.run(root);
		
		MaxFilamentHolder.run(root);
		RollBearingHolder.run(root);
	}

}
