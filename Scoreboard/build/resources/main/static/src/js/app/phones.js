
var phones = {},
	tablePhones = null;

(function ( $ ) {
	var rules = {
			
			text : function(text){
				 var re = /\S+/;
				 return re.test(text);	
			}
		
	};
	 var setError = function(id,message, parent){
		 console.log(parent);
			$('#' + id).addClass('error_required')
			
			if ($("#error_phones_" + id).length == 0){
				parent.append('<p  id="error_' + id + '" class="input-group-addon error-required-message" >' + message + '</p>');	
				parent.removeClass("col-md-4");
				parent.addClass("col-md-6");
			}
	 }
	 var clearError = function(id,parent) {
			$("#" + id).removeClass('error_required')
			$("#error_phones_" + id).remove();
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
	$.fn.validatePhone = function() {
		 
		var options = {
			    	error_required : {
			    		pnumber   : $error.enterPhoneNumber,
			    		comments  : $error.enterComments
			    	
			    	},
			    	errorHandler : {
			    		pnumber : function(id,message, parent){
			    		
			    			 setError(id,message, parent);
			    			 
			    		},
			    		comments : function(id,message, parent){
			    			 setError(id,message, parent);
			    		}
			    		
			    	},
			    	errorClear : {
			    		pnumber : function(id,parent){
			    			 clearError(id,parent);
			    		},
			    		comments : function(id,parent){
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
	$.fn.savePhone = function() {
		
		var headers = {},
			params = [],
			data = { params : params},
			$message = $("#save_phone_message");
		
		headers[core.gcsrf().headerName] = core.gcsrf().token;
		
		this.find('input').each(function( index ) {
  		   var that = $(this);
  		   console.log(that);
		   var name = that.attr('name');
	       var value =  $(that).val();
	       data.params.push({name: name, value : value});
		});
		console.log(data);
		$.ajax({
			  type: "POST",
			  url: "savePhone",
			  data: JSON.stringify(data),
			  contentType : 'application/json',
			  dataType: "json",
			  headers : headers ,    	
			  success: function(e){
					console.log(e.response);
				  if (e.response.endsWith("Error")){
					  $("#errorPhonesDiv").removeClass("hidden");
					  $("#errorPhonesText").html($error.savePhone);
				  }	
				  else{
					  $message.removeClass("alert-danger");
					  $message.removeClass("hidden");
					  $message.addClass("alert-success");
					  $message.html("Saved");
					  phones.close();
					  tablePhones.ajax.reload(null, false);
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

phones.initialize = function(){
	
	
	
		if (tablePhones != null){
			tablePhones.destroy();
		}
		tablePhones = $('#tablePhones').DataTable({
			"ajax": {
				"url" : "phones", 
	       		"type": "GET",
	            "dataSrc": "phones",
	            "error": function(){
	            	console.log("data loading...");
	            	}
				},
				"columns": [
				             { title : $label.id, 		"data": "id" },
				             { title : $label.number, 	"data": "pnumber" },
				             { title : $label.comments, "data": "comments" },
				             { "data": "id", "className": "text-center",  render: function(data){
				            	 return '<button type="button" class="btn-danger btn-xs" data-toggle="tooltip" data-placement="top" title="Delete Phone"' + 
				            			'onclick="phones.worningPhoneDel(\'' + data +'\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>';
					        	   
				             } }
				         ],	
			'iDisplayLength': 100,
			"paging": false,
			"info":     false,
			"searching" : false 
			});		
}
phones.save = function(){

	if ($("#phonesForm").validatePhone()){
		$("#phonesForm").savePhone();
	}
}
phones.close = function(){
	$("#addPhonePanel").addClass("hidden");
	$("#save_phone_message").addClass("hidden");
}
phones.addPhone = function(){
	$("#addPhonePanel").removeClass("hidden");
}
phones.worningPhoneDel = function(id){
	phones.warningPhoneDiv().removeClass("hidden");
	phones.warningPhoneText().html($message.deletePhoneWithId + ": [" + id + "]?");
	phones.btnPhoneDelete().attr("onclick","phones.deletePhone('" + id + "')");
}
phones.worningPhoneCancel = function(){
	phones.warningPhoneDiv().addClass("hidden");
}

phones.deletePhone = function(id){
	var headers = {};
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	var data = {name: "id", value: id};
	$.ajax({
		  type: "POST",
		  url: "deletePhone",
		  data: JSON.stringify(data),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  tablePhones.ajax.reload(null, false);
			  phones.warningPhoneDiv().addClass("hidden");
			 
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
			}
		});	
}
phones.closePhonesError = function(){
	 $("#errorPhonesDiv").addClass("hidden");
	 $("#errorPhonesText").html("");
}
phones.warningPhoneDiv = function(){
	
	return $("#warningPhoneDiv");
}
phones.warningPhoneText = function(){
	
	return $("#warningPhoneText");
}
phones.btnPhoneDelete = function(){
	
	return $("#btnPhoneDelete");
	
}

