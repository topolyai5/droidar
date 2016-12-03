package droidar.listeners;

import droidar.listeners.eventManagerListeners.CamRotationVecUpdateListener;
import droidar.listeners.eventManagerListeners.LocationEventListener;
import droidar.listeners.eventManagerListeners.OrientationChangedListener;
import droidar.listeners.eventManagerListeners.TouchMoveListener;
import droidar.listeners.eventManagerListeners.TrackBallEventListener;

@Deprecated
public interface EventListener extends LocationEventListener,
        OrientationChangedListener, TouchMoveListener, TrackBallEventListener,
        CamRotationVecUpdateListener {

}