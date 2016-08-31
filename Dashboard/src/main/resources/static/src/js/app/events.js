/**
 * 
 */


 var DialEvent = new CustomEvent("DialEvent",{
 		'detail': {
	              source: "",
	              connectedlinenum : "",
	              connectedlinename: "",
	              calleridnum : "",
	              calleridname: "",
    	          subevent: "",
      			  channel: "",
      			  destination :"",
      		  	  uniqueid: "",
      		  	  destuniqueid: "",
      		  	  dialstring: ""
      	  }
 });
var QueueMemberAddedEvent = new CustomEvent("QueueMemberAddedEvent",{
    	 
	  		'detail': {
	  		    source: "",
		  		connectedlinenum : "",
		        connectedlinename: "",
		        calleridnum : "",
		        calleridname: "",
		        uniqueid: "",
		        privilege : "",
		  		queue : "",
		  		location : "",
		  		membername : "",
		  		stateinterface : "",
		  		membership : "",
		  		penalty : "",
		  		callstaken : "",
		  		lastcall : "",
		  		status : "",
		  		paused : "",
		  		agentname: ""
         
      }
});


var QueueMemberStatusEvent = new CustomEvent("QueueMemberStatusEvent",{
	 
		'detail': {
		    source: "",
	  		connectedlinenum : "",
	        connectedlinename: "",
	        calleridnum : "",
	        calleridname: "",
	        uniqueid: "",
	        privilege : "",
	  		queue : "",
	  		location : "",
	  		membername : "",
	  		stateinterface : "",
	  		membership : "",
	  		penalty : "",
	  		callstaken : "",
	  		lastcall : "",
	  		status : "",
	  		paused : ""
	 
		}
});
var QueueMemberRemovedEvent = new CustomEvent("QueueMemberRemovedEvent",{
	 
		'detail': {
		    source: "",
	  		connectedlinenum : "",
	        connectedlinename: "",
	        calleridnum : "",
	        calleridname: "",
	        uniqueid: "",	    
	  		queue : "",
	  		location : "",
	  		membername : ""
		}
});  
var LoginResponse   = new CustomEvent("LoginResponse",{
	'detail': {
		message: "",
		response: ""
       
    }
});
var ErrorResponse   = new CustomEvent("ErrorResponse",{
	'detail': {
		message: "",
		response: ""
       
    }
});
var QueueShowResponse =  new CustomEvent("QueueShowResponse",{
	'detail': {
		message: "",
		response: "",
		queues: {}
       
    }
});

var AddAgentToQueueResponse =  new CustomEvent("AddAgentToQueueResponse",{
	'detail': {
		response: "",
		message: ""
    }
});

var JoinEvent = new CustomEvent("JoinEvent",{
	'detail': {
		queue : 			"",
		position :			"",
		count : 			"",	
		channel :			"",
		connectedlinenum : 	"",
		connectedlinename : "",
		uniqueid : 			"",   
		calleridnum : 		"",
		calleridname : 		""	
       
    }
});
var BridgeEvent = new CustomEvent("BridgeEvent",{
		'detail': {
			bridgestate : 	"",
			bridgetype :  	"",
			channel1 :   	"",
			channel2:		"",
			uniqueid1:		"",
			uniqueid2:		"",
			callerid1:		"",
			callerid2:		""
	       
	    }
});
var EventMap=[
  	              {key: "DialEvent",				value: DialEvent},
  	              {key: "QueueMemberAddedEvent",	value: QueueMemberAddedEvent},
  	              {key: "QueueMemberRemovedEvent",	value: QueueMemberRemovedEvent},
  	              {key: "QueueMemberStatusEvent",	value: QueueMemberStatusEvent},
  	              {key: "LoginResponse",			value: LoginResponse},
  	              {key: "QueueShowResponse",		value: QueueShowResponse},
  	              {key: "JoinEvent",				value: JoinEvent},
  	              {key: "BridgeEvent",				value: BridgeEvent},
  	              {key: "AddAgentToQueueResponse",	value: AddAgentToQueueResponse},
  	              {key: "ErrorResponse"			,	value: ErrorResponse }
  	              ]




 