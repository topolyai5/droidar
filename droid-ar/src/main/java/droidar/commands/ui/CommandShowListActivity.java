package droidar.commands.ui;

import droidar.gui.CustomBaseAdapter;
import droidar.gui.CustomListActivity;
import droidar.gui.ListSettings;
import droidar.system.ActivityConnector;
import droidar.system.Container;
import droidar.util.Log;
import droidar.util.Wrapper;
import android.app.Activity;
import android.widget.BaseAdapter;

import droidar.commands.Command;
import droidar.commands.undoable.UndoableCommand;

public class CommandShowListActivity extends Command {

	private Wrapper myListItemsWrapper;
	private Activity myCurrentActivity;
	private boolean closeOnCorrectClick;
	private Command myDefaultClickCommand;
	private UndoableCommand myDefaultLongClickCommand;
	private Command myOnCorrectClickCommand;
	private Command myOnCorrectLongClickCommand;
	private UndoableCommand myMenuCommands;
	private String myActivityName;

	public CommandShowListActivity(Wrapper listItemsWrapper,
			Activity currentActivity) {
		this(listItemsWrapper, currentActivity, true, null, null, null, null,
				null, null);
	}

	/**
	 * @param listItemsWrapper
	 * @param currentActivity
	 * @param closeOnCorrectClick
	 * @param defaultOnClickCommand
	 * @param defaultOnLongClickCommand
	 * @param onCorrectClickCommand
	 * @param onCorrectLongClickCommand
	 * @param menuCommands
	 * @param activityName
	 */
	public CommandShowListActivity(Wrapper listItemsWrapper,
			Activity currentActivity, boolean closeOnCorrectClick,
			Command defaultOnClickCommand,
			UndoableCommand defaultOnLongClickCommand,
			Command onCorrectClickCommand, Command onCorrectLongClickCommand,
			UndoableCommand menuCommands, String activityName) {
		myListItemsWrapper = listItemsWrapper;
		this.closeOnCorrectClick = closeOnCorrectClick;
		myCurrentActivity = currentActivity;
		myDefaultClickCommand = defaultOnClickCommand;
		myDefaultLongClickCommand = defaultOnLongClickCommand;
		myOnCorrectClickCommand = onCorrectClickCommand;
		myOnCorrectLongClickCommand = onCorrectLongClickCommand;
		myMenuCommands = menuCommands;
		myActivityName = activityName;
	}

	@Override
	public boolean execute() {
		/*
		 * create an adapter and pass it to the listactivity by using the
		 * ActivityConnector
		 */

		if (myListItemsWrapper.getObject() instanceof Container) {

			BaseAdapter adapter = new CustomBaseAdapter(
					(Container) myListItemsWrapper.getObject());

			ListSettings listSettings = new ListSettings(adapter,
					closeOnCorrectClick, myOnCorrectClickCommand,
					myOnCorrectLongClickCommand, myDefaultClickCommand,
					myDefaultLongClickCommand, myMenuCommands, myActivityName);

			ActivityConnector.getInstance().startActivity(myCurrentActivity,
					CustomListActivity.class, listSettings);

			return true;
		} else {
			Log.d("Commands",
					"No activity will be created because you did not pass a CanBeShownInList-Class of ListItems in the Wrapper!");
		}

		return false;
	}

}
