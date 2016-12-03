package de.rwth.setups;

import droidar.gamelogic.ActionThrowFireball;
import droidar.gamelogic.GameParticipant;
import droidar.gamelogic.Stat;
import droidar.gl.GL1Renderer;
import droidar.gl.GLFactory;
import droidar.gui.GuiSetup;
import droidar.system.DefaultARSetup;
import droidar.worldData.SystemUpdater;
import droidar.worldData.World;
import android.app.Activity;
import de.rwth.R;

public class GameDemoSetup extends DefaultARSetup {
	private GameParticipant p;
	private ActionThrowFireball e;

	public GameDemoSetup() {
		p = new GameParticipant("Player", "Karlo", R.drawable.hippopotamus64);
		p.addStat(new Stat(Stat.INTELLIGENCE, R.drawable.icon, 2));
		e = new ActionThrowFireball("Fireball");
		p.addAction(e);
	}

	@Override
	public void _d_addElementsToUpdateThread(SystemUpdater updater) {
		super._d_addElementsToUpdateThread(updater);
		updater.addObjectToUpdateCycle(p);
	}

	@Override
	public void addObjectsTo(GL1Renderer renderer, World world,
			GLFactory objectFactory) {

	}

	@Override
	public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity activity) {
		super._e2_addElementsToGuiSetup(guiSetup, activity);
		guiSetup.addViewToTop(e.getNewDefaultView(getActivity()));
	}
}
