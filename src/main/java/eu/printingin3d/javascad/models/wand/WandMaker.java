package eu.printingin3d.javascad.models.wand;

import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.basic.Radius;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.enums.Side;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.wand.element.AbstractWandElement;
import eu.printingin3d.javascad.tranzitions.Direction;
import eu.printingin3d.javascad.tranzitions.Slicer;
import eu.printingin3d.javascad.tranzitions.Union;
import eu.printingin3d.javascad.utils.IModelProvider;
import eu.printingin3d.javascad.utils.ModelWithPath;


public class WandMaker {
	public Abstract3dModel createWand(List<AbstractWandElement> elements) {
		List<Abstract3dModel> parts = new ArrayList<>();
		
		Radius lastRadius = Radius.ZERO;
		Abstract3dModel last = null;
		for (AbstractWandElement element : elements) {
			Abstract3dModel current = element.getModel(lastRadius);
			lastRadius = element.getNewRadius();

			if (current!=null) {
				if (last != null) {
					current = current.align(Side.TOP_OUT, last);
				}
				last = current;
				
				parts.add(current);
			}
		}
		
		return new Union(parts);
	}
	
	public IModelProvider createPrintableWandPieces(List<AbstractWandElement> elements, String fileNamePrefix, String fileNamePostfix) {
		final Abstract3dModel wand = createWand(elements).rotate(Angles3d.ROTATE_MINUS_Y);
		double wandLength = wand.getBoundaries().getX().getSize();
		int pieces = (int)(Math.ceil(wandLength/160.0));
		
		double part1 = 0.0;
		double piecesLength = (wandLength / pieces + 10.0)/wandLength;
		double part2 = 1.0 - piecesLength;
		
		final List<ModelWithPath> models = new ArrayList<>();
		
		Abstract3dModel side1 = new Slicer(wand, Direction.Z, 2, 0).rotate(Angles3d.xOnly(180));
		models.add(new ModelWithPath(side1, fileNamePrefix+".side1"+fileNamePostfix));
		Abstract3dModel side2 = new Slicer(wand, Direction.Z, 2, 1);
		models.add(new ModelWithPath(side2, fileNamePrefix+".side2"+fileNamePostfix));
		
		int i = 1;
		while (part2>0.0) {
			Abstract3dModel piece = new Slicer(side1, Direction.X, part1, part2);
			models.add(new ModelWithPath(piece, fileNamePrefix+".side1.part"+i+fileNamePostfix));
			
			piece = new Slicer(side2, Direction.X, part2, part1);
			models.add(new ModelWithPath(piece, fileNamePrefix+".side2.part"+i+fileNamePostfix));
			
			part1+= piecesLength;
			part2-= piecesLength;
			i++;
		}

		Abstract3dModel piece = new Slicer(side1, Direction.X, part1, 0.0);
		models.add(new ModelWithPath(piece, fileNamePrefix+".side1.part"+i+fileNamePostfix));
		
		piece = new Slicer(side2, Direction.X, 0.0, part1);
		models.add(new ModelWithPath(piece, fileNamePrefix+".side2.part"+i+fileNamePostfix));
		
		models.add(new ModelWithPath(wand, fileNamePrefix+".scad"));
		
		return new IModelProvider() {
			
			@Override
			public List<ModelWithPath> getModelsAndPaths() {
				return models;
			}
			
			@Override
			public Abstract3dModel getAssembledModel() {
				return wand;
			}
		};
	}
}
