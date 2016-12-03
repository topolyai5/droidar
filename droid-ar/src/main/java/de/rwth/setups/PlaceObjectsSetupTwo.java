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
import droidar.worldData.MoveComp;
import droidar.worldData.Obj;
import droidar.worldData.SystemUpdater;
import droidar.worldData.Updateable;
import droidar.worldData.World;
import droidar.actions.Action;
import droidar.actions.ActionBufferedCameraAR;
import droidar.actions.ActionCalcRelativePos;
import droidar.actions.ActionMoveCameraBuffered;
import droidar.actions.ActionRotateCameraBuffered;
import android.app.Activity;

import droidar.commands.Command;
import droidar.components.ViewPosCalcerComp;

public class PlaceObjectsSetupTwo extends Setup {

	private GLCamera camera;
	private World world;
	private ViewPosCalcerComp viewPosCalcer;
	private Obj selectedObj;
	private MoveComp moveComp;

	@Override
	public void _a_initFieldsIfNecessary() {

		// allow the user to send error reports to the developer:
		ErrorHandler.enableEmailReports("droidar.rwth@gmail.com",
				"Error in DroidAR App");
		camera = new GLCamera(new Vec(0, 0, 15));
		world = new World(camera);
		viewPosCalcer = new ViewPosCalcerComp(camera, 150, 0.1f) {
			@Override
			public void onPositionUpdate(Updateable parent,
					Vec targetVec) {
				if (parent instanceof Obj) {
					Obj obj = (Obj) parent;
					MoveComp m = obj.getComp(MoveComp.class);
					if (m != null) {
						m.myTargetPos = targetVec;
					}
				}
			}
		};
		moveComp = new MoveComp(4);
	}

	@Override
	public void _b_addWorldsToRenderer(GL1Renderer renderer,
			GLFactory objectFactory, GeoObj currentPosition) {
		world.add(newObject());
		renderer.addRenderElement(world);
	}

	@Override
	public void _c_addActionsToEvents(EventManager eventManager,
			CustomGLSurfaceView arView, SystemUpdater updater) {
		arView.addOnTouchMoveAction(new ActionBufferedCameraAR(camera));
		Action rot = new ActionRotateCameraBuffered(camera);
		updater.addObjectToUpdateCycle(rot);
		eventManager.addOnOrientationChangedAction(rot);

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
				world.add(newObject());
				return true;
			}

		}, "Place next");

		guiSetup.setTopViewCentered();
	}

	private Obj newObject() {
		final Obj obj = new Obj();
		Color c = Color.getRandomRGBColor();
		c.alpha = 0.7f;
		MeshComponent diamond = GLFactory.getInstance().newDiamond(c);
		obj.setComp(diamond);
		setComps(obj);
		diamond.setOnClickCommand(new Command() {
			@Override
			public boolean execute() {
				setComps(obj);
				return true;
			}

		});
		return obj;
	}

	private void setComps(Obj obj) {
		if (selectedObj != null) {
			selectedObj.remove(viewPosCalcer);
			selectedObj.remove(moveComp);
		}
		obj.setComp(viewPosCalcer);
		obj.setComp(moveComp);
		selectedObj = obj;
	}
}
