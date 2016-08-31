package com.asterionix.agi;

public interface SocketHandler extends Runnable{
	void OnAddAgentToQueue();
	void OnRemoveAgentToQueue();
}
