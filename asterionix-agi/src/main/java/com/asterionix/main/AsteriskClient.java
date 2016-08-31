package com.asterionix.main;

import com.asterionix.events.QueueMemberAddedEvent;
import com.asterionix.events.QueueMemberRemovedEvent;
import com.asterionix.events.QueueMemberStatusEvent;
import com.asterionix.response.AsteriskResponse;

public interface AsteriskClient {

	void OnLoginResponse(AsteriskResponse response);
	
	void OnLogOffResponse(AsteriskResponse response);
	
	void OnQueueMemberAddedEvent(QueueMemberAddedEvent event);
	
	void OnQueueMemberRemovedEvent(QueueMemberRemovedEvent event);
	
	void OnQueueMemberStatusEvent(QueueMemberStatusEvent event);
	
	void OnQueueShowResponse(AsteriskResponse response);
	
	void OnOriginateResponse(AsteriskResponse response);
	
	void OnAddAgentToQueueResponse(AsteriskResponse response);
	
	void OnRemoveAgentFromQueueResponse(AsteriskResponse response);
	
	void OnErrorResponse(AsteriskResponse response);
	
}
