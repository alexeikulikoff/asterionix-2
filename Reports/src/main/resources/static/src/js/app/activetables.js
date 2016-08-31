/**
 * 
 */
var activeTable = activeTable ||  {},
	queueTables = [],
	incCallsTables = [],
	recordsTotal = 10,
    statusClass = ["AST_DEVICE_UNKNOWN", "AST_DEVICE_NOT_INUSE", "AST_DEVICE_INUSE","AST_DEVICE_BUSY", "AST_DEVICE_INVALID","AST_DEVICE_UNAVAILABLE","AST_DEVICE_RINGING", "AST_DEVICE_RINGINUSE","AST_DEVICE_ONHOLD"];


activeTable.test = function(){
	console.log("activeTable alive");
}
activeTable.clearAll = function(){
	for(var i=0; i < incCallsTables.length; i++){
		if (incCallsTables[i].table != null){
			incCallsTables[i].table.destroy();
		}
		
	}
	for(var i=0; i < queueTables.length; i++){
		if (queueTables[i].table != null){
			queueTables[i].table.destroy();
		}
		
	}
}
activeTable.joinCall = function(e){
	
	Date.prototype.timeNow = function () {
	     return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +":"+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds();
	}
	var newDate = new Date();
	var queueid = e.detail.queue;
	
	for(var i=0; i < incCallsTables.length; i++){
	   if (incCallsTables[i].queue == queueid){
			var table  = incCallsTables[i].table
			
			var info = table.page.info();
		
			if (info.recordsTotal >= recordsTotal){
				table.row(0).remove().draw();
			}
			var data = {};
			data.time=newDate.timeNow();
			data.uniqueid1 = e.detail.uniqueid;
			data.callerid1 = e.detail.calleridnum;;
			data.callerid2 = $message.waitBridging ;
			data.bridgestate='join';
			table.row.add(data).draw();
		}
		
	}
}
activeTable.bridgeCall = function(e){
	for(var i=0; i < incCallsTables.length; i++){
	    var table  = incCallsTables[i].table
	    table.rows().eq(0).each( function ( index ) {
			var data = table.row( index ).data();
		
			if (data.uniqueid1 == e.detail.uniqueid1){
			
				table.cell(index,3).data(e.detail.callerid2).draw();
				var row = table.row( index);
			  
  			    $(row.node()).attr('class', e.detail.bridgestate);
  			  
			}
		});	
	   
	 }
}
activeTable.initIncCalls = function(e){
	
	$("#incoming_calls_container").empty();
	
	for(var i=0; i < e.detail.queues.queues.length; i++){
	
		var queueid = e.detail.queues.queues[i].queueName;
	
  		$("#incoming_calls_container").append(incCallsTableTemplate(queueid ,'Queue: ' + queueid));
  		
  		var tableid = "#incCallsTable_" + queueid;
  		var data = {};
  		data.time='';
  		data.uniqueid1 = '';
  		data.callerid1='';
  		data.callerid2='';
  		data.bridgestate='';
  		
  		var tb = $(tableid).DataTable({
  			    data : data,
				columns: [
				          {title   :  $label.time, data: 'time' },
				          { data: 'uniqueid1'  },
	  			    	  {title   :  $label.src,  data: 'callerid1' },
				          {title   :  $label.dst,  data: 'callerid2' },
				          
				          { data: 'bridgestate' }
			    		],
			    		paging: false,
			 	        info:     false,
			 	        searching : false,
			 	        pageLength : 3,
			 	        scrollY:        "300px",
					    scrollCollapse: true,
			 	       createdRow : function ( row, data, index ) {
			 	    	  
			 	    	   $(row).addClass(data.bridgestate);
			 	       },
			 	       rowCallback: function( row, data, index ) {
			 	    	 
			 	    	   $(row).addClass(data.bridgestate);
			 	      }
		});
  		tb.column( 1 ).visible( false );
  		tb.column( 4 ).visible( false );
  		
  		incCallsTables.push({queue : queueid, table : tb});
	}
	
}
activeTable.initQueues = function(e){
	
	$("#queues_container").empty();

	for(var i=0; i < e.detail.queues.queues.length; i++){
		var queueTableData = [];
		var queueid = e.detail.queues.queues[i].queueName;
	
  		$("#queues_container").append(queue_tables(queueid ,'Queue: ' + queueid));
  		var tableid = "#table_" + queueid;
  		
  		console.log(e.detail.queues.queues[i].agents);
  		var dataSrc = [];
  		for(var j=0; j < e.detail.queues.queues[i].agents.length; j++ ){
  			var agentName = e.detail.queues.queues[i].agents[j].agentName;
  			var agentNum = e.detail.queues.queues[i].agents[j].agentNum;
  			var cssClass = e.detail.queues.queues[i].agents[j].cssClass;
  			var action = agentName + '-' + queueid;
  			
  			dataSrc.push({agentName: agentName, agentNum: agentNum,cssClass : cssClass, action : action });
  		}
  		
  		var tb = $(tableid).DataTable({
	            data:  dataSrc,
				columns: [
				          { title   :  $label.number,  data: 'agentName' },
				          { title   :  $label.name,    data: 'agentNum' },
				          { title  :   $label.action,  data: 'action' , className: "text-center", render : function(data){
				        	   
	  			    			return '<div class="btn-toolbar">' + 
	  			    				'<button type="button" class="btn-danger btn-xs" data-toggle="tooltip" data-placement="top" title="Remove Agent from Queue"' + 
	  			    				'onclick="appgui.removeAgent(\'' + data + '\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>'+
	  			    				'</div>';	
	  			    	  }},
	  			    	  
	  			    	  { data: 'cssClass' }
			    		],
			    		"paging": false,
			 	        "info":     false,
			 	        "searching" : false,
			 	        
			 	       "createdRow" : function ( row, data, index ) {
			 	    	  
			 	    	   $(row).addClass(data.cssClass);
			 	       },
			 	       "rowCallback": function( row, data, index ) {
			 	    	 
			 	    	   $(row).addClass(data.cssClass);
			 	      }
		});
  		tb.column( 3 ).visible( false );
  		
  		queueTables.push({queue : queueid, table : tb});
	}
	
}
activeTable.removeAgent = function(e){

	var queueid = e.detail.queue;
	for(var i=0; i < queueTables.length; i++){
		console.log(queueTables[i].queue);
		if (queueTables[i].queue == queueid){
			var table  = queueTables[i].table
			table.rows().eq(0).each( function ( index ) {
				var data = table.row( index ).data();
				console.log(data);
				if (data.agentName == e.detail.membername.toUpperCase()){
					table.row( index ).remove().draw();
				}
			});	
		}
	}
}
activeTable.updateStatus = function(e){
	
	
	
	var status = parseInt(e.detail.status);
	var queueid = e.detail.queue;
	for(var i=0; i < queueTables.length; i++){
		console.log(queueTables[i].queue);
		if (queueTables[i].queue == queueid){
			var table  = queueTables[i].table
			table.rows().eq(0).each( function ( index ) {
				var data = table.row( index ).data();
				
				if (data.agentName == e.detail.membername.toUpperCase()){
					 var row = table.row( index);
					
					 $(row.node()).attr('class', statusClass[status]);
					
				}
			});	
		}
	}
}
activeTable.addAgent = function(e){
	
	var queueid = e.detail.queue;
	for(var i=0; i < queueTables.length; i++){
		if (queueTables[i].queue == queueid){
			var table  = queueTables[i].table;
			var info = table.page.info();
			var data = {};
			var agentName = e.detail.membername.toUpperCase();
			data.agentName = agentName;
			data.agentNum = e.detail.agentname;
			data.cssClass = 'AST_DEVICE_NOT_INUSE';
			data.action    =  agentName + '-' + queueid;
			//   Demo restriction //
			
			if (info.recordsTotal < 20){
				table.row.add(data).draw();
			}else{
				$("#demoInfo").removeClass("hidden");
				console.log("More than 2 Agents are not allowed in demo version.");
			}
		
		}
	}
}
activeTable.reloadQueues = function(e){

	console.log(e.detail.queue);
	var queueid = e.detail.queue;
	for(var i=0; i < queueTables.length; i++){
		console.log(queueTables[i].queue);
		if (queueTables[i].queue == queueid){
			queueTables[i].value.ajax.reload();
		}
	}
	
}