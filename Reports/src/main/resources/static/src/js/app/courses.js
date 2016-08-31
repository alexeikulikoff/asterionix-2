
var course = {},
	tableQueues = null;

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
				parent.removeClass("col-md-6");
				parent.addClass("col-md-8");
			}
	 }
	 var clearError = function(id,parent) {
			$("#" + id).removeClass('error_required')
			$("#error_" + id).remove();
			parent.removeClass("col-md-8");
			parent.addClass("col-md-6");
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
	$.fn.validateCourse = function() {
		 
		var options = {
			    	error_required : {
			    		coursename : $message.enterQueueName
			    		
			    	},
			    	errorHandler : {
			    		coursename : function(id,message, parent){
			    			 setError(id,message, parent);
			    		}
			    	
			    	},
			    	errorClear : {
			    		coursename : function(id,parent){
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
	$.fn.saveCourse = function() {
		
		var headers = {},
			params = [],
			data = { params : params},
			$message = $("#queue_save_message");
		
		headers[core.gcsrf().headerName] = core.gcsrf().token;
		
		this.find('input').each(function( index ) {
  		   var that = $(this);
		   var name = that.attr('name');
	       var value =  $(that).val();
	       data.params.push({name: name, value : value});
		});
		
		console.log(data);
		
		$.ajax({
			  type: "POST",
			  url: "saveCourses",
			  data: JSON.stringify(data),
			  contentType : 'application/json',
			  dataType: "json",
			  headers : headers ,    	
			  success: function(e){
				  console.log(e);
				  if (e.response.endsWith("Error")){
					  $("#errorCoursesDiv").removeClass("hidden");
					  $("#errorCoursesText").html($error.saveQueue);
					  
					 
				  }	else{
					  $message.removeClass("alert-danger");
					  $message.removeClass("hidden");
					  $message.addClass("alert-success");
					  $message.html(e.response);
					  tableQueues.ajax.reload(null, false); 
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


course.initialize = function(){
	
		if (tableQueues != null){
			tableQueues.destroy();
		}
		tableQueues = $('#tableQueues').DataTable({
			"ajax": {
				"url" : "queues", 
	       		"type": "GET",
	            "dataSrc": "courses",
	            "error": function(){
	            
	            	console.log("data loading...");
	            	}
				},
				"columns": [
				             { title :  $label.id, 			data 	: "id" },
				             { title :  $label.queuename, 	data	: "coursename" },
				             { title :  $label.weight,   	data	: "weight", className : "text-center" },
				             
				             { title : 	$label.action, 		data	: "id", "className" : "text-center", "render" : function(data){
				            	 return '<button type="button" class="btn-danger btn-xs" data-toggle="tooltip" data-placement="top" title="Delete Queue Name"' + 
				            		'onclick="course.warningCourseDelete(\'' + data +'\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>'
				            		
				            		
				            		;
				             }  }
				         ],	
			'iDisplayLength': 100,
			"paging": false,
			"info":     false,
			"searching" : false 
			});
		tableQueues.column( 0 ).visible( false );
		
}

course.warningCourseDelete = function(id){
	
	course.warningCoursesDel().removeClass("hidden");
	course.warningCoursesDelText().removeClass("hidden");
	course.warningCoursesDelText().html("Delete Queue  with id: [" + id + "] ?");
	course.btnCoursesDel().attr("onclick","course.deleteCourse('" + id + "')");
}
course.worningCourseCancel = function(){
	course.warningCoursesDel().addClass("hidden");
}
course.deleteCourse = function(id){
	var headers = {};
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	var data = {name: "id", value: id};
	$.ajax({
		  type: "POST",
		  url: "deleteCourse",
		  data: JSON.stringify(data),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  tableQueues.ajax.reload(null, false);
			  course.warningCoursesDel().addClass("hidden");
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
			}
		});	
}
course.coursesCourseError = function(){
	 
	$("#errorCoursesDiv").addClass("hidden");
	$("#errorCoursesText").html("");
	
}
course.save = function(){
	if ($("#formQueueName").validateCourse()){
		
		$("#formQueueName").saveCourse();
	}
	
}
course.close = function(){

	course.addQueuePanel().addClass("hidden");
	course.queue_save_message().addClass("hidden");
}

course.addQueueName = function(){
	
	course.addQueuePanel().removeClass("hidden");
}
course.addQueuePanel = function(){
	
	return $("#addQueuePanel");
}
course.queue_save_message = function(){
	
	return $("#queue_save_message");
}
course.warningCoursesDel = function(){
	
	return $("#warningCoursesDel");
}
course.warningCoursesDelText = function(){
	
	return $("#warningCoursesDelText");
	
}
course.btnCoursesDel = function(){
	
	return $("#btnCoursesDel");
}


