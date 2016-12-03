package de.rwth.setups;

import droidar.gl.GL1Renderer;
import droidar.gl.GLFactory;
import droidar.gl.GLText;
import droidar.gl.scenegraph.MeshComponent;
import droidar.gui.GuiSetup;

import java.util.HashMap;

import droidar.system.DefaultARSetup;
import droidar.worldData.Obj;
import droidar.worldData.World;
import android.app.Activity;

import droidar.commands.Command;

public class FastChangingTextSetup extends DefaultARSetup {

	HashMap<String, MeshComponent> textMap = new HashMap<String, MeshComponent>();
	private GLText text;

	@Override
	public void addObjectsTo(GL1Renderer renderer, World world,
			GLFactory objectFactory) {

		text = new GLText("11223344swrvgweln@@@@", myTargetActivity, textMap,
				getCamera());

		Obj o = new Obj();
		o.setComp(text);
		world.add(o);
	}

	@Override
	public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity activity) {
		super._e2_addElementsToGuiSetup(guiSetup, activity);
		guiSetup.addSearchbarToView(guiSetup.getBottomView(), new Command() {

			@Override
			public boolean execute() {
				return false;
			}

			@Override
			public boolean execute(Object transfairObject) {
				if (transfairObject instanceof String) {
					String s = (String) transfairObject;
					if (text != null)
						text.changeTextTo(s);
				}
				return true;
			}
		}, "Enter text");
	}

}
