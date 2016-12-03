package droidar.commands.system;

import droidar.commands.Command;
import droidar.commands.undoable.CommandProcessor;

public class UndoCommand extends Command {

	@Override
	public boolean execute() {
		return CommandProcessor.getInstance().undo();
	}

}
