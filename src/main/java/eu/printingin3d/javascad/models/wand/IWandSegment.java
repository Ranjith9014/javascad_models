package eu.printingin3d.javascad.models.wand;

import eu.printingin3d.javascad.models.Abstract3dModel;

public interface IWandSegment {
	Abstract3dModel createSegment();
	double length();
}
