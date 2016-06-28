package eu.printingin3d.eu.javascad.models;

import java.io.File;
import java.io.IOException;

import eu.printingin3d.eu.javascad.models.cakeholder.CakeHolder;
import eu.printingin3d.eu.javascad.models.legobrick.LegoBrick;
import eu.printingin3d.javascad.exceptions.IllegalValueException;

public class Main {
	
	public static void main(String[] args) throws IllegalValueException, IOException {
		File root = new File("c:/temp");
		if (args.length>0) root = new File(args[0]);
		
		LegoBrick.run(root);
		CakeHolder.run(root);
	}

}
