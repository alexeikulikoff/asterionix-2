package com.asterionix.controllers.dashboard;

import com.asterionix.controllers.event.BridgeEvent;
import com.asterionix.controllers.event.DialEvent;
import com.asterionix.controllers.event.JoinEvent;
import com.asterionix.controllers.event.QueueMemberAddedEvent;
import com.asterionix.controllers.event.QueueMemberRemovedEvent;
import com.asterionix.controllers.event.QueueMemberStatusEvent;
import com.asterionix.controllers.response.AddAgentToQueueResponse;
import com.asterionix.controllers.response.AsteriskResponse;

public interface DashBoard {
	
	void OnAgentEnterQueue();
	
	void OnQueueMemberAddedEvent(QueueMemberAddedEvent event);
	
	void OnQueueMemberRemovedEvent(QueueMemberRemovedEvent event);
	
	void OnDialEvent(DialEvent event);
	
	void OnLoginResponse(AsteriskResponse response);
	
	void OnLogOffResponse(AsteriskResponse response);
	
	void OnErrorResponse(AsteriskResponse response);
	
	void OnQueueShowResponse(AsteriskResponse response);
	
	void OnQueueMemberStatusEvent(QueueMemberStatusEvent event);
	
	void OnJoinEvent(JoinEvent event);
	
	void OnBridgeEvent(BridgeEvent event);
	
	void OnAddAgentToQueueResponse(AsteriskResponse response);
	
	void OnRemoveAgentFromQueueResponse(AsteriskResponse response);
	
}
