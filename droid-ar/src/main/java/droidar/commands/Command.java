package droidar.commands;

import droidar.gui.ListItem;
import droidar.gui.MetaInfos;
import droidar.listeners.ItemGuiListener;
import droidar.util.Log;
import droidar.worldData.HasInfosInterface;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO name this Command ?
 * 
 * @author Spobo
 * 
 */
public abstract class Command implements ListItem, HasInfosInterface {

	private MetaInfos myInfoObj;
	private ItemGuiListener myItemGuiListener;

	/**
	 * @param transfairObject
	 * @return true if command was successful. if the transfairObject isn't used
	 *         the default execute()-method will be executed
	 */
	public boolean execute(Object transfairObject) {
		/*
		 * on default the transfairObject isnt used, this way only command which
		 * need the transfairobject will have to override this method
		 */
		return execute();
	}

	public abstract boolean execute();

	@Override
	public View getMyListItemView(View viewToUseIfNotNull, ViewGroup parentView) {
		if (myItemGuiListener != null)
			return myItemGuiListener.requestItemView(viewToUseIfNotNull,
					parentView);
		Log.d("GUI", "    -> Loading default view for " + this.getClass());
		return getInfoObject().getDefaultListItemView(viewToUseIfNotNull,
				parentView);
	}

	/**
	 * TODO remove or refactor, this is never used
	 * 
	 * @param myItemGuiListener
	 */
	public void setMyItemGuiListener(ItemGuiListener myItemGuiListener) {
		this.myItemGuiListener = myItemGuiListener;
	}

	@Override
	public Command getListClickCommand() {
		return this;
	}

	@Override
	public Command getListLongClickCommand() {
		return null;
	}

	@Override
	public MetaInfos getInfoObject() {
		if (myInfoObj == null)
			myInfoObj = new MetaInfos(this);
		return myInfoObj;
	}

	@Override
	public boolean HasInfoObject() {
		if (myInfoObj != null)
			return true;
		return false;
	}

}