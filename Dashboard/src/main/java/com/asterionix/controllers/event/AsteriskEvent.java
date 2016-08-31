package com.asterionix.controllers.event;

public interface AsteriskEvent {

	void onEvent(Object source, AsteriskEvent ev);
}
