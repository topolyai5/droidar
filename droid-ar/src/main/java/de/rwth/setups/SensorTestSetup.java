package de.rwth.setups;

import droidar.geo.GeoObj;
import droidar.gl.Color;
import droidar.gl.CustomGLSurfaceView;
import droidar.gl.GL1Renderer;
import droidar.gl.GLCamera;
import droidar.gl.GLFactory;
import droidar.gl.animations.AnimationRotate;
import droidar.gl.scenegraph.MeshComponent;
import droidar.gl.scenegraph.Shape;
import droidar.gui.GuiSetup;
import droidar.system.ErrorHandler;
import droidar.system.EventManager;
import droidar.system.Setup;
import droidar.util.Vec;
import droidar.worldData.SystemUpdater;
import droidar.worldData.World;
import droidar.actions.Action;
import droidar.actions.ActionBufferedCameraAR;
import droidar.actions.ActionMoveCameraBuffered;
import droidar.actions.ActionRotateCameraBuffered;
import droidar.actions.ActionRotateCameraBuffered3;
import droidar.actions.ActionRotateCameraBuffered4;
import droidar.actions.ActionRotateCameraBufferedDebug;
import droidar.actions.ActionRotateCameraBufferedDirect;
import droidar.actions.ActionRotateCameraUnbuffered;
import droidar.actions.ActionRotateCameraUnbuffered2;
import droidar.actions.ActionUseCameraAngles2;
import android.app.Activity;

import droidar.commands.Command;

public class SensorTestSetup extends Setup {

	private GLCamera camera;
	private World world;
	private Action rotActionB1;
	private Action rotActionB3;
	private Action rotActionB4;
	private Action rotActionDebug;
	private Action rotActionUnB;
	private Action rotActionUnB2;
	private Action rotActionB2;

	@Override
	public void _a_initFieldsIfNecessary() {
		// allow the user to send error reports to the developer:
		ErrorHandler.enableEmailReports("droidar.rwth@gmail.com",
				"Error in DroidAR App");

		/*
		 * the following are just example rotate droidar.actions, take a look at the
		 * implementation to see how to create own CameraBuffered droidar.actions
		 */

		camera = new GLCamera();
		rotActionB1 = new ActionRotateCameraBuffered(camera);
		rotActionB2 = new ActionRotateCameraBufferedDirect(camera);
		rotActionB3 = new ActionRotateCameraBuffered3(camera);
		rotActionB4 = new ActionRotateCameraBuffered4(camera);
		new ActionRotateCameraBufferedDebug(camera);
		rotActionUnB = new ActionRotateCameraUnbuffered(camera);
		rotActionUnB2 = new ActionRotateCameraUnbuffered2(camera);

	}

	@Override
	public void _b_addWorldsToRenderer(GL1Renderer renderer,
			GLFactory objectFactory, GeoObj currentPosition) {

		world = new World(camera);

		MeshComponent compasrose = new Shape();

		MeshComponent middle = objectFactory.newDiamond(Color.green());
		middle.setPosition(new Vec(0, 0, -2.8f));
		middle.addChild(new AnimationRotate(40, new Vec(0, 0, 1)));
		compasrose.addChild(middle);

		int smallDistance = 10;
		int longDistance = 60;

		MeshComponent north = objectFactory.newDiamond(Color.redTransparent());
		north.setPosition(new Vec(0, smallDistance, 0));

		MeshComponent north2 = objectFactory.newDiamond(Color.red());
		north2.setPosition(new Vec(0, longDistance, 0));

		MeshComponent east = objectFactory.newDiamond(Color.blueTransparent());
		east.setPosition(new Vec(smallDistance, 0, 0));

		MeshComponent east2 = objectFactory.newDiamond(Color.blue());
		east2.setPosition(new Vec(longDistance, 0, 0));

		MeshComponent south = objectFactory.newDiamond(Color.blueTransparent());
		south.setPosition(new Vec(0, -smallDistance, 0));

		MeshComponent south2 = objectFactory.newDiamond(Color.blue());
		south2.setPosition(new Vec(0, -longDistance, 0));

		MeshComponent west = objectFactory.newDiamond(Color.blueTransparent());
		west.setPosition(new Vec(-smallDistance, 0, 0));

		MeshComponent west2 = objectFactory.newDiamond(Color.blue());
		west2.setPosition(new Vec(-longDistance, 0, 0));

		compasrose.addChild(north2);
		compasrose.addChild(north);
		compasrose.addChild(east2);
		compasrose.addChild(east);
		compasrose.addChild(south2);
		compasrose.addChild(south);
		compasrose.addChild(west2);
		compasrose.addChild(west);

		currentPosition.setComp(compasrose);
		world.add(currentPosition);

		renderer.addRenderElement(world);

	}

	@Override
	public void _c_addActionsToEvents(EventManager eventManager,
			CustomGLSurfaceView arView, SystemUpdater updater) {
		arView.addOnTouchMoveAction(new ActionBufferedCameraAR(camera));
		eventManager.addOnOrientationChangedAction(rotActionB1);
		eventManager.addOnTrackballAction(new ActionMoveCameraBuffered(camera,
				5, 25));

		eventManager
				.addOnOrientationChangedAction(new ActionUseCameraAngles2() {

					@Override
					public void onAnglesUpdated(float pitch, float roll,
							float compassAzimuth) {
						/*
						 * the angles could be used in some way here..
						 */
					}
				});

	}

	@Override
	public void _d_addElementsToUpdateThread(SystemUpdater worldUpdater) {
		worldUpdater.addObjectToUpdateCycle(world);
		worldUpdater.addObjectToUpdateCycle(rotActionB1);
		worldUpdater.addObjectToUpdateCycle(rotActionB3);
		worldUpdater.addObjectToUpdateCycle(rotActionB4);
		worldUpdater.addObjectToUpdateCycle(rotActionDebug);
		worldUpdater.addObjectToUpdateCycle(rotActionUnB);
		worldUpdater.addObjectToUpdateCycle(rotActionUnB2);
	}

	@Override
	public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity activity) {
		guiSetup.addButtonToBottomView(new myRotateAction(rotActionB1),
				"Camera Buffered 1");
		guiSetup.addButtonToBottomView(new myRotateAction(rotActionB2),
				"Camera Buffered 2");
		guiSetup.addButtonToBottomView(new myRotateAction(rotActionB3),
				"Camera Buffered 3");
		guiSetup.addButtonToBottomView(new myRotateAction(rotActionB4),
				"Camera Buffered 4");
		guiSetup.addButtonToBottomView(new myRotateAction(rotActionUnB),
				"Camera Unbuffered 1");
		guiSetup.addButtonToBottomView(new myRotateAction(rotActionUnB2),
				"Camera Unbuffered 2");
	}

	private class myRotateAction extends Command {

		private Action myAction;

		public myRotateAction(Action a) {
			myAction = a;
		}

		@Override
		public boolean execute() {
			EventManager.getInstance().getOnOrientationChangedAction().clear();
			EventManager.getInstance().getOnOrientationChangedAction()
					.add(myAction);
			return true;
		}

	}

}
