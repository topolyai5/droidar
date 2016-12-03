package de.rwth.setups;

import droidar.geo.GeoObj;
import droidar.gl.Color;
import droidar.gl.CustomGLSurfaceView;
import droidar.gl.GL1Renderer;
import droidar.gl.GLCamera;
import droidar.gl.GLFactory;
import droidar.gl.scenegraph.MeshComponent;
import droidar.gui.GuiSetup;
import droidar.system.ErrorHandler;
import droidar.system.EventManager;
import droidar.system.Setup;
import droidar.util.Vec;
import droidar.util.Wrapper;
import droidar.worldData.Obj;
import droidar.worldData.SystemUpdater;
import droidar.worldData.World;
import droidar.actions.Action;
import droidar.actions.ActionBufferedCameraAR;
import droidar.actions.ActionCalcRelativePos;
import droidar.actions.ActionMoveCameraBuffered;
import droidar.actions.ActionPlaceObject;
import droidar.actions.ActionRotateCameraBuffered;
import android.app.Activity;

import droidar.commands.Command;

public class PlaceObjectsSetup extends Setup {

	private GLCamera camera;
	private World world;

	private Wrapper placeObjectWrapper;

	@Override
	public void _a_initFieldsIfNecessary() {

		// allow the user to send error reports to the developer:
		ErrorHandler.enableEmailReports("droidar.rwth@gmail.com",
				"Error in DroidAR App");

		placeObjectWrapper = new Wrapper();

	}

	@Override
	public void _b_addWorldsToRenderer(GL1Renderer renderer,
			GLFactory objectFactory, GeoObj currentPosition) {
		camera = new GLCamera(new Vec(0, 0, 10));
		world = new World(camera);

		Obj placerContainer = new Obj();
		placerContainer.setComp(objectFactory.newArrow());
		world.add(placerContainer);

		placeObjectWrapper.setTo(placerContainer);

		renderer.addRenderElement(world);
	}

	@Override
	public void _c_addActionsToEvents(EventManager eventManager,
			CustomGLSurfaceView arView, SystemUpdater updater) {
		arView.addOnTouchMoveAction(new ActionBufferedCameraAR(camera));
		Action rot1 = new ActionRotateCameraBuffered(camera);
		Action rot2 = new ActionPlaceObject(camera, placeObjectWrapper, 50);

		updater.addObjectToUpdateCycle(rot1);
		updater.addObjectToUpdateCycle(rot2);

		eventManager.addOnOrientationChangedAction(rot1);
		eventManager.addOnOrientationChangedAction(rot2);
		eventManager.addOnTrackballAction(new ActionMoveCameraBuffered(camera,
				5, 25));
		eventManager.addOnLocationChangedAction(new ActionCalcRelativePos(
				world, camera));
	}

	@Override
	public void _d_addElementsToUpdateThread(SystemUpdater worldUpdater) {
		worldUpdater.addObjectToUpdateCycle(world);
	}

	@Override
	public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity context) {

		guiSetup.addButtonToTopView(new Command() {
			@Override
			public boolean execute() {
				final Obj placerContainer = newObject();
				world.add(placerContainer);
				placeObjectWrapper.setTo(placerContainer);
				return true;
			}

			private Obj newObject() {
				final Obj placerContainer = new Obj();
				Color c = Color.getRandomRGBColor();
				c.alpha = 0.7f;
				MeshComponent arrow = GLFactory.getInstance().newDiamond(c);
				arrow.setOnClickCommand(new Command() {
					@Override
					public boolean execute() {
						placeObjectWrapper.setTo(placerContainer);
						return true;
					}
				});
				placerContainer.setComp(arrow);
				return placerContainer;
			}
		}, "Place next!");

		guiSetup.setTopViewCentered();
	}

}
