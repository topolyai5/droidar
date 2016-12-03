package de.rwth.setups;

import droidar.geo.GeoObj;
import droidar.gl.GL1Renderer;
import droidar.gl.GLFactory;
import droidar.gl.scenegraph.Shape;
import droidar.system.DefaultARSetup;
import droidar.util.Vec;
import droidar.worldData.World;

public class TestSetup extends DefaultARSetup {

	@Override
	public void addObjectsTo(GL1Renderer renderer, World world,
			GLFactory objectFactory) {
		GeoObj o = new GeoObj(50.77854197, 6.06048614);
		Shape s = objectFactory.newSquare(null);
		s.setScale(new Vec(0.5f, 0.5f, 0.5f));
		o.setComp(s);
		world.add(o);
	}

}
