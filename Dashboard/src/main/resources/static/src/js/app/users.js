
var users = users || {},
	tableUsers = null;

(function ( $ ) {
	var rules = {
		
			text : function(text){
				 var re = /\S+/;
				 return re.test(text);	
			},
			password : function(password){
				var re = /\S+/;
				return re.test(password);	
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
	$.fn.validateUser = function() {
		 
		var options = {
			    	error_required : {
			    		firstname :  $error.enterFirstName ,
			    	
			    		password  :  $error.enterPassword
			    		
			    	},
			    	errorHandler : {
			    		firstname : function(id,message, parent){
			    			 setError(id,message, parent);
			    		},
			    	
			    		password : function(id,message, parent){
			    			 setError(id,message, parent);
			    		}
			    	
			    	},
			    	errorClear : {
			    		firstname : function(id,parent){
			    			 clearError(id,parent);
			    		},
			    		password : function(id,parent){
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
	$.fn.saveUser = function() {
		
		var headers = {},
			params = [],
			data = { params : params},
			$message = $("#save_message");
		
		headers[core.gcsrf().headerName] = core.gcsrf().token;
		
		this.find('input').each(function( index ) {
  		   var that = $(this);
		   var name = that.attr('name');
	       var value =  $(that).val();
	       data.params.push({name: name, value : value});
		});
		
		$.ajax({
			  type: "POST",
			  url: "saveUser",
			  data: JSON.stringify(data),
			  contentType : 'application/json',
			  dataType: "json",
			  headers : headers ,    	
			  success: function(e){
				  if (e.response.endsWith("Error")){
					  $("#errorUsersDiv").removeClass("hidden");
					  $("#errorUsersText").html($error.saveUser);
					 
				  }	
				  else{
					  $message.removeClass("alert-danger");
					  $message.removeClass("hidden");
					  $message.addClass("alert-success");
					  $message.html("Saved");
					  tableUsers.ajax.reload(null, false);
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

users.initialize = function(){
	
	core.test();
	
		if (tableUsers != null){
			tableUsers.destroy();
		}
		tableUsers = $('#tableUsers').DataTable({
			"ajax": {
				"url" : "users", 
	       		"type": "GET",
	            "dataSrc": "users",
	            "error": function(){
	            	console.log("data loading...");
	            	}
				},
				"columns": [
				             { title : $label.id,  "data": "id" },
				             { title : $label.firstname, "data": "firstname" },
				             { title : $label.action, "data": "id", "className": "text-center",  render: function(data){
				            	 return '<button type="button" class="btn-danger btn-xs" data-toggle="tooltip" data-placement="top" title="Delete user"' + 
				            			'onclick="users.worningDel(\'' + data +'\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>';
					        	   
				             } }
				         ],	
			'iDisplayLength': 100,
			"paging": false,
			"info":     false,
			"searching" : false 
			});		
}
users.closeUserstError = function(){
	$("#errorUsersDiv").addClass("hidden");
	$("#errorUsersText").html("");
}
users.save = function(){

	if ($("#form1").validateUser()){
		$("#form1").saveUser();
	}
}
users.close = function(){
	$("#addUserPanel").addClass("hidden");
	$("#save_message").addClass("hidden");
}
users.addUser = function(){
	$("#addUserPanel").removeClass("hidden");
}
users.worningDel = function(id){
	users.warningDiv().removeClass("hidden");
	users.warningText().html("Delete user with id: [" + id + "]?");
	users.btnDelete().attr("onclick","users.deleteUser('" + id + "')");
}
users.worningCancel = function(){
	users.warningDiv().addClass("hidden");
}

users.deleteUser = function(id){
	var headers = {};
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	var data = {name: "id", value: id};
	$.ajax({
		  type: "POST",
		  url: "deleteUser",
		  data: JSON.stringify(data),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  tableUsers.ajax.reload(null, false);
			  users.warningDiv().addClass("hidden");
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
			}
		});	
}

users.warningDiv = function(){
	
	return $("#warningDiv");
}
users.warningText = function(){
	
	return $("#warningText");
}
users.btnDelete = function(){
	
	return $("#btnDelete");
	
}
