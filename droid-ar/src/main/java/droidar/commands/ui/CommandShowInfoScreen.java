package droidar.commands.ui;

import droidar.gui.simpleUI.EditItem;
import droidar.gui.simpleUI.SimpleUIv1;
import android.app.Activity;

import droidar.commands.Command;

public class CommandShowInfoScreen extends Command {

	private Activity myCurrentActivity;
	private EditItem myObjectToDisplay;
	private Object myOptionalMessage;

	public CommandShowInfoScreen(Activity currentActivity, EditItem objectToEdit) {
		myCurrentActivity = currentActivity;
		myObjectToDisplay = objectToEdit;
	}

	public CommandShowInfoScreen(Activity currentActivity,
			EditItem objectToEdit, Object optionalMessage) {
		myCurrentActivity = currentActivity;
		myObjectToDisplay = objectToEdit;
		myOptionalMessage = optionalMessage;
	}

	@Override
	public boolean execute() {
		SimpleUIv1.showInfoScreen(myCurrentActivity, myObjectToDisplay,
				myOptionalMessage);
		return true;
	}
}
