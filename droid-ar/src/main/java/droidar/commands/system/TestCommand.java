package droidar.commands.system;

import droidar.util.Log;

import droidar.commands.undoable.UndoableCommand;

public class TestCommand extends UndoableCommand {

	private int x = 0;

	@Override
	public boolean override_do() {
		x++;
		Log.out("Test Command do. Count: " + x);
		return true;
	}

	@Override
	public boolean override_undo() {
		x--;
		Log.out("Test Command undo. Count: " + x);
		return true;
	}

}
