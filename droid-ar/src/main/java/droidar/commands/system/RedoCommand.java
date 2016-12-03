package droidar.commands.system;

import droidar.commands.Command;
import droidar.commands.undoable.CommandProcessor;

public class RedoCommand extends Command {

	@Override
	public boolean execute() {
		return CommandProcessor.getInstance().redo();
	}

}
