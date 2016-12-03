package de.rwth.setups;

import droidar.geo.GeoObj;
import droidar.gl.GL1Renderer;
import droidar.gl.GLFactory;
import droidar.gui.GuiSetup;
import droidar.gui.RadarView;
import droidar.system.DefaultARSetup;
import droidar.util.Vec;
import droidar.worldData.SystemUpdater;
import droidar.worldData.World;
import android.app.Activity;
import android.util.Log;

import droidar.commands.Command;
import droidar.commands.ui.CommandShowToast;
import droidar.components.SimpleTooFarAwayComp;

public class FarAwayPOIScenarioSetup extends DefaultARSetup {

	private String LOG_TAG = "FarAwayPOIScenarioSetup";
	private RadarView radar;

	@Override
	public void _a_initFieldsIfNecessary() {
		super._a_initFieldsIfNecessary();
		radar = new RadarView(getActivity(), (int) (getScreenWidth() / 3),
				getCamera(), getWorld().getAllItems());
	}

	@Override
	public void addObjectsTo(GL1Renderer renderer, World world,
			final GLFactory objectFactory) {
		GeoObj o = new GeoObj();
		o.setComp(objectFactory.newCube());
		// place 20 meters north of the user:
		o.setVirtualPosition(new Vec(0, 20, 0));
		o.setComp(new SimpleTooFarAwayComp(30, getCamera(), getActivity()));
		world.add(o);
	}

	@Override
	public void _d_addElementsToUpdateThread(SystemUpdater updater) {
		super._d_addElementsToUpdateThread(updater);
		updater.addObjectToUpdateCycle(radar);
	}

	@Override
	public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity activity) {
		super._e2_addElementsToGuiSetup(guiSetup, activity);
		guiSetup.addViewToTop(radar);
		guiSetup.addButtonToBottomView(new Command() {

			@Override
			public boolean execute() {
				// float[] rayPos = new float[4];
				// float[] rayDir = new float[4];
				CommandShowToast.show(getActivity(), "altitude="
						+ getCamera().getGPSPositionVec().z);

				return true;
			}
		}, "Show altitude");

		guiSetup.addButtonToBottomView(new Command() {

			@Override
			public boolean execute() {
				Vec pos = getCamera().getGPSPositionVec();
				Log.d(LOG_TAG, "Placing object at " + pos);

				final GeoObj o = new GeoObj(pos.y, pos.x, pos.z);
				o.setComp(GLFactory.getInstance().newArrow());
				o.setComp(new SimpleTooFarAwayComp(30, getCamera(),
						getActivity()));
				o.setOnClickCommand(new Command() {

					@Override
					public boolean execute() {
						CommandShowToast.show(getActivity(), "o.getAltitude()="
								+ o.getAltitude());
						return true;
					}
				});
				Log.d(LOG_TAG, "virtual pos=" + o.getVirtualPosition());
				Log.d(LOG_TAG, "cam pos=" + getCamera().getPosition());
				getWorld().add(o);
				return true;
			}
		}, "Place GeoObj");
	}
}
