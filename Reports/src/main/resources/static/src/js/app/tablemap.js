
var tablemap = tablemap ||  {},
	queueTables=[],
	incomingCallTables = [],
	
	agentModels = [],
	incomingCallsModel=[],
	Agents = [],
    IncomingCalls = [];

tablemap.queues_selected = [];

tablemap.destroyTable = function(tables){
	
	for (var id in tables) {
	    if (!tables.hasOwnProperty(id)) {
	       
	        continue;
	    }
	    console.log(tables[id]);
	    tables[id].destroy();
	}
	
}
tablemap.clearArray = function(array){
	while(array.length > 0){
		array.pop();
	}
}
tablemap.clearAll = function(){
	tablemap.destroyTable(queueTables);
	tablemap.destroyTable(incomingCallTables);
	
	tablemap.clearArray(agentModels);
	tablemap.clearArray(incomingCallsModel);
	tablemap.clearArray(Agents);
	tablemap.clearArray(IncomingCalls);
	
	$("#queues_container").empty();
	$("#incoming_calls_container").empty();
	
}
tablemap.initialize = function(){

	ko.observableArray.fn.subscribeArrayChanged = function(addCallback, deleteCallback) {
	    var previousValue = undefined;
	    this.subscribe(function(_previousValue) {
	        previousValue = _previousValue.slice(0);
	    }, undefined, 'beforeChange');
	    this.subscribe(function(latestValue) {
	        var editScript = ko.utils.compareArrays(previousValue, latestValue);
	      
	        for (var i = 0, j = editScript.length; i < j; i++) {
	            switch (editScript[i].status) {
	                case "retained":
	                    break;
	                case "deleted":
	                    if (deleteCallback)
	                        deleteCallback(editScript[i].value);
	                    break;
	                case "added":
	                    if (addCallback)
	                        addCallback(editScript[i].value);
	                    break;
	            }
	        }
	        previousValue = undefined;
	    });
	};    
   	var Agent = function(data, dt) {
	    this.id    = data.id;
	    this.agentNum  = ko.observable(data.agentNum);
	    this.agentName = ko.observable(data.agentName);
	    this.cssClass   = ko.observable(data.cssClass);
	    this.queueName =  ko.observable(data.queueName);
	    this.agent_queue = ko.computed(function() {
	        return this.agentName() + "-" + this.queueName();
	    }, this);
	    
		var that = this;
		$.each( [ 'agentName', 'agentNum', 'queueName'], function (i, prop) {

			that[ prop ].subscribe( function (val) {
				var rowIdx = dt.column( 0 ).data().indexOf( that.id );
				dt.row( rowIdx ).invalidate();
			} );
		} );
		$.each( ['cssClass'], function(i, prop){
				that[ prop ].subscribe( function (val) {
					var rowIdx = dt.column( 0 ).data().indexOf( that.id );
					var row = dt.row(rowIdx);
					var clazz = $(row.node()).attr('class');
					$(row.node()).removeClass(clazz);
					$(row.node()).addClass(that.cssClass());
					dt.row( rowIdx ).invalidate();
			} );
			
		});
	};

	var nowTime = function (){
		
		var that = new Date();
	    
		return that;
		//return ((that.getHours() < 10)?"0":"") + that.getHours() +":"+ ((that.getMinutes() < 10)?"0":"") + that.getMinutes() +":"+ ((that.getSeconds() < 10)?"0":"") + that.getSeconds();
	}

 	var IncomingCall = function(data, dt) {
 		
	    this.id    = data.id;
	    this.callTime = function(){
	    	var that = new Date();
	    	return ((that.getHours() < 10)?"0":"") + that.getHours() +":"+ ((that.getMinutes() < 10)?"0":"") + that.getMinutes() +":"+ ((that.getSeconds() < 10)?"0":"") + that.getSeconds();
	    	
	    }
	    this.connectedlinenum  = ko.observable(data.connectedlinenum);
	    this.connectedlinename = ko.observable(data.connectedlinename);
	    this.uniqueid  = ko.observable(data.uniqueid);
	    this.calleridnum  = ko.observable(data.calleridnum);
	    this.calleridname = ko.observable(data.calleridname);
	    this.cssClass   = ko.observable(data.cssClass);
	    
		var that = this;
		$.each( [ 'calleridnum', 'calleridname','connectedlinename','connectedlinenum'], function (i, prop) {

			that[ prop ].subscribe( function (val) {
				var rowIdx = dt.column( 0 ).data().indexOf( that.id );
				dt.row( rowIdx ).invalidate();
			} );
		} );
		$.each( ['cssClass'], function(i, prop){
				that[ prop ].subscribe( function (val) {
					var rowIdx = dt.column( 0 ).data().indexOf( that.id );
					var row = dt.row(rowIdx);
					var clazz = $(row.node()).attr('class');
					$(row.node()).removeClass(clazz);
					$(row.node()).addClass(that.cssClass());
					dt.row( rowIdx ).invalidate();
			} );
			
		});
	};
	
	var initializeModel = function(){
    	
		var model;
    	
    	model =  ko.mapping.fromJS( [] );
    
    	return model;
	} 
	
	var modelSubscribeArrayChanged = function(model, table, max){
		
		model.subscribeArrayChanged(
    			function ( addedItem ) {
    				table.row.add( addedItem ).draw();
    				var rowIdx = table.column( 0 ).data().indexOf( addedItem.id );
    				var row = table.row(rowIdx);
					var clazz = $(row.node()).attr('class');
					$(row.node()).removeClass(clazz);
					$(row.node()).addClass(addedItem.cssClass());
					table.row( rowIdx ).invalidate();
    				
    			},
    			function ( deletedItem ) {
    				var rowIdx = table.column( 0 ).data().indexOf( deletedItem.id );
    				table.row( rowIdx ).remove().draw();
    			}
    		); 
	}
	var koMappingFromJS = function(data, obj, table, model ){
		ko.mapping.fromJS(
				data,
				{
					key: function(data) {
						return ko.utils.unwrapObservable(data.id);        
					},
					create: function(options) {
						return new obj(options.data, table);
					}    
				},
				model
			);
	}	
	
	var fillTable = function(e,id,i){
		
		for (var j=0; j < e.detail.queues.queues[i].agents.length; j++ ){
			 
			 var agentName =  e.detail.queues.queues[i].agents[j].agentName;
			 var agentNum = e.detail.queues.queues[i].agents[j].agentNum;
			 var cssClass = e.detail.queues.queues[i].agents[j].cssClass;
			 var queueName = e.detail.queues.queues[i].queueName;
			 var maxId =  j + 1;
			 Agents[id] = new Agent({id:maxId, agentNum: agentNum, agentName: agentName, cssClass : cssClass, queueName : queueName },queueTables[id]);
			 agentModels[id].push( Agents[id] );
		 }
	}
	this.queueShowResponse = function(e){
		var select_queue =   document.getElementById('select_queue');
		for(var i=0; i < e.detail.queues.queues.length; i++){
			var id = e.detail.queues.queues[i].queueName;
  	  	//	var queues_container = document.getElementById('queues_container');
  	  		var name1 ='Queue';
 	  		$("#queues_container").append(queue_tables(id ,name1 + ': ' + id));
  	  		var did = "#table_" + id;
 			queueTables[id] = $(did).DataTable({
 				columns: [
 				          { data: 'id' },
 				          { data: 'agentNum()' , "className": "col-sm-6" },
 				          { data: 'agentName()',  "className": "col-sm-3" },
 				          { data: 'agent_queue()', "className": "text-center", "render" : function(data, type, row, meta){
  			    			return '<div class="btn-toolbar">' + 
  			    				'<button type="button" class="btn-danger btn-xs" data-toggle="tooltip" data-placement="top" title="Remove Agent from Queue"' + 
  			    				'onclick="appgui.removeAgent(\'' + data + '\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>'+
/*  			    					'<button type="button" class="btn-warning btn-xs" data-toggle="tooltip" data-placement="top" title="Pause Agent"' + 
  			    					'onclick="dwv.pauseAgent(\'' + data + '\')"><i class="fa fa-pause" aria-hidden="true"></i></button>'+
  			    					'<button type="button" class="btn-primary btn-xs" data-toggle="tooltip" data-placement="top" title="Inpause Agent"' + 
  			    					'onclick="dwv.unPauseAgent(\'' + data + '\')"><i class="fa fa-play" aria-hidden="true"></i></button>'+
*/  			    					
  			    				'</div>';	
  			    			}}
  			    		],
  			    		"paging": false,
  			 	        "info":     false,
  			 	        "searching" : false
  				});
  				 
  				 queueTables[id].column( 0 ).visible( false );
  				
  				 agentModels[id] = initializeModel();
  	    		 
  	    		 modelSubscribeArrayChanged(agentModels[id], queueTables[id] ,4);
  	      	  
  	    		 fillTable(e,id,i);
  	  	
  	    		 var name2='Incoming calls';
  	    		 
  	    		 $("#incoming_calls_container").append(incoming_calls_tables(id ,name2 + ': ' + id));
  	    		 
  	    		var cid =  "#incoming_calls_table_" + id; 
  	    		
  	    		incomingCallTables[id] = $(cid).DataTable({
  					columns: [
  			    				{ data: 'id' },
  			    				{ data: 'callTime()'},
  			    				{ data: 'connectedlinenum()' },
  			    				{ data: 'calleridnum()' }
  			    			],
  			    		    "scrollY":        "400px",
  						    "scrollCollapse": true,
  			    			"paging": false,
  			 	            "info":     false,
  			 	            "searching" : false
  				});
  	    		incomingCallsModel[id] = initializeModel();
  	    		modelSubscribeArrayChanged(incomingCallsModel[id], incomingCallTables[id] ,10);
  	    		tablemap.queues_selected.push(id);
  		}
	
	}
	
	this.queueMemberAdd = function(e){
		
		var membername = e.detail.membername;
		var queueName =  e.detail.queue;
		var cssClass="AST_DEVICE_NOT_INUSE";
	
		var agentName = membername;
		
		var agentNum = e.detail.agentname;
		
		var maxId = agentModels[queueName]().length ;
		
		var result = -1;
		for(var i=0; i < agentModels[queueName]().length; i++){
			
			if (agentName === agentModels[queueName]()[i].agentName() ){
				result = i;
				break;
			}
			
		}
		if (result == -1){
			maxId = maxId + 1;
			agentModels[queueName].push( new Agent({id:maxId, agentNum: agentNum, agentName: agentName, cssClass : cssClass, queueName : queueName },queueTables[queueName]));
		}	
		
	}
	
	this.queueMemberRemove = function(e){
		
		var membername = e.detail.membername;
		var agentName = membername;
		var agentNum = e.detail.agentname;
		var queue =  e.detail.queue;
		var id = queue;
		var result = -1;
		for(var i=0; i < agentModels[id]().length; i++){
			if (agentName === agentModels[id]()[i].agentName() ){
				result = i;
				break;
			}
		}
		
		if (result != -1){
			agentModels[id].splice(result,1);
			
		}
		
		
	}
	this.joinCall = function(e){
		var queue =  e.detail.queue;
		var id = queue;
		var cssClass="JOIN";
		
		var maxId = incomingCallsModel[id]().length ;
		
		var connectedlinenum  =e.detail.connectedlinenum;
		var connectedlinename = e.detail.connectedlinename;
		var uniqueid  =	e.detail.uniqueid;
		var calleridnum  = e.detail.calleridnum;
		var calleridname = e.detail.calleridname;
		var position = e.detail.position;
		
		if (position == '1'){
			incomingCallsModel[id].push(new IncomingCall({id:maxId, connectedlinenum: connectedlinenum, connectedlinename: connectedlinename, uniqueid : uniqueid,calleridnum : calleridnum,calleridname: calleridname, cssClass: cssClass },incomingCallTables[id]));
		}	
		
		
	}
	this.BridgeCall = function(e){
		
	
		var uniqueid1  = e.detail.uniqueid1;
		var callerid2 = e.detail.callerid2;
		var bridgestate = e.detail.bridgestate;
		
		if (bridgestate == "link"){
			for (prop in incomingCallsModel) {
				var result = -1;
				for(var i=0; i < incomingCallsModel[prop]().length; i++){
					if (uniqueid1 == incomingCallsModel[prop]()[i].uniqueid() ){
						result = i;
						break;
					}
				}
				if (result != -1){
					
					incomingCallsModel[prop]()[result].cssClass("BRIDGED");
					incomingCallsModel[prop]()[result].connectedlinenum(callerid2);
					
				}
			}
		}	
		
	}
	this.updateAgentStatus = function(e){
		
		var statusClass = ["AST_DEVICE_UNKNOWN", "AST_DEVICE_NOT_INUSE", "AST_DEVICE_INUSE","AST_DEVICE_BUSY", "AST_DEVICE_INVALID","AST_DEVICE_UNAVAILABLE","AST_DEVICE_RINGING", "AST_DEVICE_RINGINUSE","AST_DEVICE_ONHOLD"];
		var status = e.detail.status;
		
		var membername = e.detail.membername;
		var agentName = membername;
		var queue =  e.detail.queue;
		var id = queue;
		var result = -1;
		for(var i=0; i < agentModels[id]().length; i++){
		
			if (agentName == agentModels[id]()[i].agentName() ){
				result = i;
				break;
			}
		}
		if (result != -1){
			agentModels[id]()[result].cssClass(statusClass[status]);
		}
	}
}