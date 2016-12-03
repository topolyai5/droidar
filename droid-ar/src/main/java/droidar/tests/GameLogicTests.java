package droidar.tests;

import droidar.gamelogic.ActionList;
import droidar.gamelogic.ActionThrowFireball;
import droidar.gamelogic.Booster;
import droidar.gamelogic.BoosterList;
import droidar.gamelogic.GameParticipant;
import droidar.gamelogic.Stat;
import droidar.gamelogic.StatList;
import de.rwth.R;

public class GameLogicTests extends SimpleTesting {

	@Override
	public void run() throws Exception {
		newPlayerTest();
	}

	private void newPlayerTest() throws Exception {
		GameParticipant p = new GameParticipant("Player", "P A", 0);
		GameParticipant enemy = new GameParticipant("Player", "P Enemy", 0);

		StatList stats = p.getStatList();
		assertNotNull(stats);
		assertTrue(stats.add(new Stat(Stat.MAX_HP, R.drawable.icon, 20)));
		stats.add(new Stat(Stat.INTELLIGENCE, R.drawable.icon, 20));
		assertTrue(stats.get(Stat.MAX_HP).addBooster(
				new Booster(Booster.MAX_HP_PLUS_15, R.drawable.elephant64, 15)));

		BoosterList boosterList = new BoosterList();
		boosterList.add(new Booster(Booster.MAX_HP_MINUS_10, Stat.MAX_HP,
				R.drawable.hippopotamus64, -10));
		assertTrue(stats.applyBoosterList(boosterList));

		assertTrue(stats.get(Stat.MAX_HP).getValue() == 25);

		ActionList actions = p.getActionList();
		actions.add(new ActionThrowFireball(ActionThrowFireball.FIREBALL_ACTION));

		enemy.getStatList().add(new Stat(Stat.HP, R.drawable.elephant64, 100));
		enemy.getStatList().add(
				new Stat(Stat.FIRE_RESISTANCE, R.drawable.elephant64, 5));

		assertTrue(p.doAction(ActionThrowFireball.FIREBALL_ACTION, enemy)
				.actionCorrectlyExecuted());

		// when target null it should not crash!
		assertFalse(p.doAction(ActionThrowFireball.FIREBALL_ACTION, null)
				.actionCorrectlyExecuted());

	}

}
