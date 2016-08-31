package com.asterionix.events;

public interface AsteriskEvent {

	void onEvent(Object source, AsteriskEvent ev);
}
