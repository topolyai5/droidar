package de.rwth.setups;

import droidar.geo.GeoObj;
import droidar.gl.GL1Renderer;
import droidar.gl.GLFactory;
import droidar.system.DefaultARSetup;
import droidar.util.Vec;
import droidar.worldData.World;

public class GeoPosTestSetup extends DefaultARSetup {

	@Override
	public void addObjectsTo(GL1Renderer renderer, World world,
			GLFactory objectFactory) {
		GeoObj o = new GeoObj(50.779058, 6.060429);
		o.setComp(GLFactory.getInstance().newSolarSystem(new Vec()));
		world.add(o);
	}

}
