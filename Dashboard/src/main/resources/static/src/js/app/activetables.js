/**
 * 
 */
var activeTable = activeTable ||  {},
	queueTables = [],
	incCallsTables = [],
	bridgedCallsTables,
	recordsTotal = 20,
	totalCalls = 0,
	answeredCalls =0,
	unansweredCalls = 0,
	callbackCalls = 0,
	maxCallCounter = 99999,
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
	
	totalCalls =  parseInt($("#total-calls").text());
	if (totalCalls >= maxCallCounter){
		totalCalls = 0;
	}
	totalCalls = totalCalls + 1;
	$("#total-calls").text(totalCalls);
	
	unansweredCalls =  parseInt($("#unanswered-calls").text());
	if (unansweredCalls >= maxCallCounter){
		unansweredCalls = 0;
	}
	unansweredCalls = unansweredCalls + 1;
	$("#unanswered-calls").text(unansweredCalls);
	
	
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
			data.callback = 'no';
			table.row.add(data).draw();
			//table.row(3).remove().draw();
		}
		
	}
}
activeTable.bridgeCall = function(e){
	
	
	Date.prototype.timeNow = function () {
	     return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +":"+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds();
	}
	var newDate = new Date();
	
	if (e.detail.bridgestate == 'link'){
	
		var queueid = e.detail.queue;
	 	var data = {};
	 	data.time=newDate.timeNow();
	 	data.uniqueid1 = e.detail.uniqueid1;
	 	data.callerid1 = e.detail.callerid1;
	 	data.callerid2 = e.detail.callerid2 ;
	 	data.bridgestate = e.detail.bridgestate;
	 	
		var info = bridgedCallsTables.page.info();
		
		if (info.recordsTotal >= recordsTotal){
			bridgedCallsTables.row(0).remove().draw();
		}
	 	
	 	bridgedCallsTables.row.add(data).draw();
	}
 	if (e.detail.bridgestate == 'unlink'){
 		bridgedCallsTables.rows().eq(0).each( function ( index ) {
 			var data = bridgedCallsTables.row( index ).data();
 			if (data.uniqueid1 == e.detail.uniqueid1){
 				var idata = {};
 				idata.time		=	newDate.timeNow();
 				idata.uniqueid1 =	data.uniqueid1;
 				idata.callerid1 = 	data.callerid1;
 				idata.callerid2 = 	data.callerid2 ;
 				idata.bridgestate = e.detail.bridgestate;
 				bridgedCallsTables.row( index ).data(idata).draw();
 			}
 	 	});
 		for(var i=0; i < incCallsTables.length; i++){
 			var table = incCallsTables[i].table;
 			
 			table.rows().eq(0).each( function ( index ) {
 				var data = table.row( index ).data();
 				
 			
 				if (data.callerid1 == e.detail.callerid1){
 					table.row( index ).remove().draw();
 				}
 			});
 		}
 		answeredCalls =  parseInt($("#answered-calls").text());
 		if (answeredCalls >= maxCallCounter){
 			answeredCalls = 0;
 		}
 		answeredCalls = answeredCalls + 1;
 		$("#answered-calls").text(answeredCalls);
 		
 		unansweredCalls =  parseInt($("#unanswered-calls").text());
 		
 		unansweredCalls = unansweredCalls - 1;
 		$("#unanswered-calls").text(unansweredCalls);
 		
 		
 	}
}
activeTable.callBack = function(callerid2){
	  
	$("#wait_dialog").removeClass('hidden');
	
	setTimeout( function() {   $("#wait_dialog").addClass('hidden');  }, 3000);
	
	Date.prototype.timeNow = function () {
	     return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +":"+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds();
	}
	var newDate = new Date();  
	
	  callbackCalls =  parseInt($("#callback-calls").text());
	  if (callbackCalls >= maxCallCounter){
		  callbackCalls = 0;
	  }	
	  callbackCalls = callbackCalls + 1;
	  $("#callback-calls").text(callbackCalls);

	  var headers = {};
	  var callerid1 = $("#selectCallBackPhone :selected").text();
	  var callback = {callerid1: callerid1, callerid2 : callerid2 };
	 
	  console.log(callback);
	  
	  headers[core.gcsrf().headerName] = core.gcsrf().token;
	  
	  $.ajax({
		  type: "POST",
		  url: "callback",
		  data: JSON.stringify(callback),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  console.log(e);
			  
				for(var i=0; i < incCallsTables.length; i++){
		 			var table = incCallsTables[i].table;
		 			
		 			table.rows().eq(0).each( function ( index ) {
		 				var data = table.row( index ).data();
		 				console.log(data.callerid2);
		 				console.log(data.callerid1);
		 				console.log(callerid2);
		 				if (data.callerid1 == callerid2){
		 					
		 					var idata = {};
		 	 				idata.time		=	newDate.timeNow();
		 	 				idata.uniqueid1 =	data.uniqueid1;
		 	 				idata.callerid1 = 	data.callerid1;
		 	 				idata.callerid2 = 	$label.callback ;
		 	 				idata.bridgestate = data.bridgestate;
		 	 				idata.callback = 'yes';
		 					
		 	 				table.row( index ).data(idata).draw();
		 					
		 				}
		 			});
		 		}
			  
			  
		  },
		  error : function(e) {
			  console.log(e);
			}
		});		
	
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
  		data.callback = 'no';
  		
  		var tb = $(tableid).DataTable({
  			    data : data,
				columns: [
				          {title   :  $label.time, data: 'time' },
				          { data: 'uniqueid1'  },
	  			    	  {title   :  $label.src,  data: 'callerid1' },
				          {title   :  $label.dst,  data: 'callerid2' },
				          
				          { data: 'bridgestate' },
				          {title: 'Action',	className: "text-center", render: function(data, type, row){
				        	  return '<div class="btn-toolbar">' + 
  			    				'<button type="button" class="btn-success btn-xs" ' + 
  			    				'onclick="activeTable.callBack(\'' + row.callerid1 + '\')"><i class="fa fa-phone" aria-hidden="true"></i></button>'+
  			    				'</div>';	
				          }},
				          { title   :  "callback",  data: 'callback' }
				          
			    		],
			    		paging: false,
			 	        info:     false,
			 	        searching : false,
			 	        pageLength : 3,
			 	        scrollY:        "800px",
			 	        order: [[ 0, "desc" ]],
					    scrollCollapse: true,
					    language: {
					        emptyTable:     " "
					    },
			 	       createdRow : function ( row, data, index ) {

			 	    	   if (data.callback == 'yes'){
			 	    		  $(row).addClass('callback');
			 	    	   }
			 	    	   else{
			 	    		  $(row).addClass(data.bridgestate);
			 	    	   }
			 	       },
			 	       rowCallback: function( row, data, index ) {
			 	    	  
			 	    	   if (data.callback == 'yes'){
			 	    		  $(row).addClass('callback');
			 	    	   }
			 	    	   else{
			 	    		  $(row).addClass(data.bridgestate);
			 	    	   }
			 	    	 
			 	    	  
			 	      }
		});
  		tb.column( 1 ).visible( false );
  		tb.column( 4 ).visible( false );
  		tb.column( 6 ).visible( false );
  		
  		incCallsTables.push({queue : queueid, table : tb});
	}
	
}
activeTable.initBridgedCalls = function(e){
	
  		var tableid = "#bridgedTable";
  		
  		var data = {};
  		data.time='';
  		data.uniqueid1 = '';
  		data.callerid1='';
  		data.callerid2='';
  		data.bridgestate='';
  		
  		
  		bridgedCallsTables = $(tableid).DataTable({
  			    data : data,
				columns: [
				          {title: $label.time,       data: 'time' },
				          {title: 'uniqueid1',  data: 'uniqueid1'  },
	  			    	  {title: $label.src,  data: 'callerid1' },
				          {title: $label.dst,  data: 'callerid2' },
				          {title: 'bridgestate', data: 'bridgestate' }
				         
				         
			    		],
			    		
			    		paging: false,
			 	        info:     false,
			 	        searching : false,
			 	        pageLength : 3,
			 	        scrollY:        "800px",
			 	        order: [[ 0, "desc" ]],
					    scrollCollapse: true,
					    language: {
					        emptyTable:     " "
					    },
			 	       createdRow : function ( row, data, index ) {
			 	    	  
			 	    	   $(row).addClass(data.bridgestate);
			 	       },
			 	       rowCallback: function( row, data, index ) {
			 	    	 
			 	    	   $(row).addClass(data.bridgestate);
			 	      }
		});
  		bridgedCallsTables.column( 1 ).visible( false );
  		bridgedCallsTables.column( 4 ).visible( false );
  		
  		
}

activeTable.initQueues = function(e){
	
	activeTable.getPhones();
	
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
				          { title   :  $label.number,  data: 'agentName', className : "col-xs-3" },
				          { title   :  $label.name,    data: 'agentNum' , className : "col-xs-6"},
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

activeTable.selectCallBackPhone = function(){
	return $("#selectCallBackPhone");
}
activeTable.getPhones = function(){
	$.ajax({
		  type: "GET",
		  url: "phones",
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			 activeTable.selectCallBackPhone().empty();
			 for(var i=0; i< e.phones.length; i++){  
				 var option1 = document.createElement("option");
				 option1.setAttribute("value", e.phones[i].pnumber);
				 option1.innerHTML = e.phones[i].pnumber;
				 activeTable.selectCallBackPhone().append(option1);
			 }
		  },
		  error : function(e) {
		
				console.log("ERROR: ", e);
			}
		});	
	
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