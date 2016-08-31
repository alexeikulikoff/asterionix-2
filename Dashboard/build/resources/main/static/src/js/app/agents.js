
var agents = agents || {},

	tableAgents = null;



(function ( $ ) {
	var rules = {
			text : function(text){
				 var re = /\S+/;
				 return re.test(text);	
			}
	};
	 var setError = function(id,message, parent){
			$('#' + id).addClass('error_required')
			if ($("#error_" + id).length == 0){
				parent.append('<p  id="error_' + id + '" class="input-group-addon error-required-message" >' + message + '</p>');	
				parent.removeClass("col-md-4");
				parent.addClass("col-md-6");
			}
	 }
	 var clearError = function(id,parent) {
			$("#" + id).removeClass('error_required')
			$("#error_" + id).remove();
			parent.removeClass("col-md-6");
			parent.addClass("col-md-4");
	 }
	var fields_required = [];
	var init = function(_this){
		while(fields_required.length > 0) {
			fields_required.pop();
		}
		_this.find('input').each(function( index ) {
			 var that = $(this);
			
			  var name = that.attr('name');
	          var type = that.attr('type');
	    	  var value =  $(that).val();
	          if (that.attr('required')=='required'){
	        	  fields_required.push({name : name,  type : type, value : value,});
	          }	
		});
	}
	$.fn.validate = function() {
		 
		var options = {
			    	error_required : {
			    		name : $error.enterAgentName
			    		
			    	},
			    	errorHandler : {
			    		name : function(id,message, parent){
			    			 setError(id,message, parent);
			    		}
			    	},
			    	errorClear : {
			    		name : function(id,parent){
			    			 clearError(id,parent);
			    		}
			    	}
			    };
		
		  init(this);
		  var errors = [] ;
		  for (var i=0; i < fields_required.length; i++ ){
			  var id =  fields_required[i].name;
			  var arg = $("#" + id).val();
			  var parent = $("#" + id).parent();
			  if (rules[fields_required[i].type](arg)){
				 options.errorClear[fields_required[i].name](id,parent); 
			  }
			  else{
				  options.errorHandler[fields_required[i].name](id,options.error_required[fields_required[i].name], parent );
				  errors.push(1);
			  }
	        }
		  
		  return errors.length > 0 ? false : true ;
	}
	$.fn.save = function() {
		
		var headers = {},
			params = [],
			data = { params : params},
			$message = $("#agents_save_message");
		
		headers[core.gcsrf().headerName] = core.gcsrf().token;
		
		this.find('input').each(function( index ) {
  		   var that = $(this);
		   var name = that.attr('name');
	       var value =  $(that).val();
	       data.params.push({name: name, value : value});
		});
		
		$.ajax({
			  type: "POST",
			  url: "saveAgent",
			  data: JSON.stringify(data),
			  contentType : 'application/json',
			  dataType: "json",
			  headers : headers ,    	
			  success: function(e){
				  if (e.response.endsWith("Error")){
					  $("#errorAgentsDiv").removeClass("hidden");
					  $("#errorAgentText").html($error.saveAgent);
					
				  }	else{
					  $message.removeClass("alert-danger");
					  $message.removeClass("hidden");
					  $message.addClass("alert-success");
					  $message.html("Saved");
					  tableAgents.ajax.reload(null, false); 
				  }
			  },
			  error : function(e) {
				  $message.removeClass("alert-success");
				  $message.removeClass("hidden");
				  $message.addClass("alert-danger");
				  $message.html("fail");
					console.log("ERROR: ", e);
				}
			});	
	}
}( jQuery ));

agents.hadnleAgentButton = function(data, btnAddToQueue ){
	return '<div class="btn-toolbar">' + 
	'<button type="button" class="btn-success btn-xs" data-toggle="tooltip" data-placement="top" title="Add Agent to Queue"' + 
	'onclick="agents.warningQueue(\'' + data +'\')"><i class="fa fa-plus-circle" aria-hidden="true"></i></button>'+
	'<button type="button" class="btn-danger btn-xs"  data-toggle="tooltip" data-placement="top" title="Delete Agent"'+
	'onclick="agents.warningDel(\'' + data +'\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>' + 
    '</div>';	
}

agents.initialize = function(){
	
		if (tableAgents !=  null){
			tableAgents.destroy();
		}
		tableAgents = $('#tableAgents').DataTable({
			"ajax": {
				"url" : "agents", 
	       		"type": "GET",
	            "dataSrc": "agents",
	            "error": function(){
	            
	            	console.log("data loading...");
	            	}
				},
				"columns": [
				             { title  : $label.id,   	data: "id" },
				             { title  : $label.name,    data: "name" },
				             {  title : $label.action,  data: "id", "className" : "text-center",  "render" : function( data, type, row, meta){
				            	
				            	 return agents.hadnleAgentButton(data,"Button");
				             }}
				        
				         ],	
			'iDisplayLength': 100,
			"paging": false,
			"info":     false,
			"searching" : false 
			});
}
agents.save = function(){

	if ($("#formAgent").validate()){
		$("#formAgent").save();
	}
}
agents.close = function(){
	$("#addAgentPanel").addClass("hidden");
	$("#agents_save_message").addClass("hidden");
}
agents.warningCancel = function(){
	agents.warningAgentAddQueueDiv().addClass("hidden");
}
agents.addAgent = function(){
	
	$("#addAgentPanel").removeClass("hidden");
}
agents.warningQueue = function(id){
	agents.warningAgentAddQueueDiv().removeClass("hidden");
	agents.warningAgentAddQueueText().html($message.addAgentWithId + ": [" + id + "] "  + 	$message.toQueue );
	
	$.ajax({
		  type: "GET",
		  url: "queues",
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			 agents.selectQueue().empty();
			 for(var i=0; i< e.courses.length; i++){  
				 var option1 = document.createElement("option");
				 option1.setAttribute("value", e.courses[i].coursename);
				 option1.innerHTML = e.courses[i].coursename;
 			 	 agents.selectQueue().append(option1);
			 }
		  },
		  error : function(e) {
		
				console.log("ERROR: ", e);
			}
		});	
	
	
	$.ajax({
		  type: "GET",
		  url: "phones",
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			agents.selectPhone().empty();
			for(var i=0; i< e.phones.length; i++){
				var option2 = document.createElement("option");
				option2.setAttribute("value",e.phones[i].id );
				option2.innerHTML = e.phones[i].pnumber;
				agents.selectPhone().append(option2);
				console.log(e.phones[i]);
			}
			 
		  },
		  error : function(e) {
		
				console.log("ERROR: ", e);
			}
		});	
	
	
	
	agents.btnAgentQueue().attr("onclick","agents.addAgentToQueue('" + id + "')");
}
agents.addAgentToQueue = function(id){
	
//	var queue = $("#selectQueue :selected").text();
	var pnumber = $("#selectPhone :selected").text();
	
	var queue = $("#selectQueue").val();
	console.log(queue);
	
	var	AgentQueue = {id: id, queue: queue, pnumber : pnumber },
		headers = {};
	
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	
	$.ajax({
		  type: "POST",
		  url: "addToQueue",
		  data: JSON.stringify(AgentQueue),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  console.log("success ajax post");
			  agents.warningCancel();
			  
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
				
			}
		});
	
	console.log(AgentQueue);
}
agents.selectPhone = function(){
	return $("#selectPhone");
}
agents.warningAgentAddQueueText = function(){
	return $("#warningAgentAddQueueText");
}
agents.warningDel = function(id){
	agents.warningDiv().removeClass("hidden");
	agents.warningText().html($message.deleteAgentWithId +" [" + id + "]?");
	agents.btnDelete().attr("onclick","agents.deleteAgent('" + id + "')");
}
agents.worningCancel = function(){
	agents.warningDiv().addClass("hidden");
}

agents.deleteAgent = function(id){
	var headers = {};
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	var data = {name: "id", value: id};
	$.ajax({
		  type: "POST",
		  url: "deleteAgent",
		  data: JSON.stringify(data),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  tableAgents.ajax.reload(null, false);
			  agents.warningDiv().addClass("hidden");
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
			}
		});	
}

agents.closeAgentError = function(){
	$("#errorAgentsDiv").addClass("hidden");
	$("#errorAgentText").html("");
}
agents.warningDiv = function(){
	
	return $("#warningAgentDelDiv");
}
agents.warningText = function(){
	
	return $("#warningAgentDelText");
}
agents.btnDelete = function(){
	
	return $("#btnAgentDelete");
	
}
agents.warningAgentAddQueueDiv = function(){
	
	return $("#warningAgentAddQueueDiv");
	
}
agents.selectQueue = function(){
	return $("#selectQueue");
}
agents.btnAgentQueue = function(){
	
	return $("#btnAgentQueue");
	
}

