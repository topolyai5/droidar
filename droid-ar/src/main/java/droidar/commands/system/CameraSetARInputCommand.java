package droidar.commands.system;

import droidar.gl.GLCamera;

import droidar.commands.undoable.UndoableCommand;

public class CameraSetARInputCommand extends UndoableCommand {

	private GLCamera myTargetCamera;
	private boolean valueToSet;
	private boolean backupValue;

	public CameraSetARInputCommand(GLCamera targetCamera, boolean valueToSet) {
		myTargetCamera = targetCamera;
		this.valueToSet = valueToSet;
	}

	@Override
	public boolean override_do() {
		backupValue = myTargetCamera.isSensorInputEnabled();
		myTargetCamera.setSensorInputEnabled(valueToSet);
		myTargetCamera.resetBufferedAngle(); // TODO do this here?
		return true;
	}

	@Override
	public boolean override_undo() {
		myTargetCamera.setSensorInputEnabled(backupValue);
		return true;
	}

}
