/**
 * 
 */
var customers = customers || {},
   customerTables;


customers.initialize = function(){
	console.log('customers');
	
	customerTables = $('#tableCustomers').DataTable({
			"ajax": {
				"url" : "customers", 
				"type": "GET",
				"dataSrc": "customers",
				"error": function(){
            		console.log("error data loading...");
            		}
				},
				"columns": [
			             { title :  $label.name, data : "name", width : "33%" ,"render" : function(data, type, row ){
			            	 return '<input class="inputExtension" id = "cust_name_'+ row.id + '"  type="text" value="' + data + '" disabled />'; 
			             } },
			             { title :  $label.number, data : "number", className : "text-center", "render" : function(data, type, row ){
			            	 return '<input class="inputExtension" id = "cust_number_'+ row.id + '"  type="text" value="' + data + '" disabled />'; 
			             }},
			             { title :  $label.queues,  data : "course", className : "text-center" },
			             { title :  $label.action, 	data : "id", className : "text-center", 
			            	 "render" : function(data){
				            	 return '<div class="btn-toolbar">' +  
				            	 '<button id = "btn_cust_delete_' + data  + '" type="button" class="btn-danger btn-xs" ' + 
				            		
				            	 	'onclick="customers.warningCustomerDelete(\'' + data +'\')">'+
				            	 	
				            	 	'<i class="fa fa-minus-circle" aria-hidden="true"></i></button>'+
				            		
				            		'<button id = "btn_cust_edit_' + data  + '" class="btn-warning btn-xs" onclick="customers.edit(\'' + data +'\')">'+
				        			
				            		'<i class="fa fa fa-pencil" aria-hidden="true"></i></button>'+
				        			
				            		'<button id="btn_cust_save_'+ data  + '" class="btn-success btn-xs hidden" onclick="customers.updateCustomer(\'' + data +'\')">'+
				        			
				        			'<i class="fa fa-floppy-o" aria-hidden="true"></i></button>'+
				        			
				        			'<button  id="btn_cust_cansel_'+ data  + '" class="btn-success btn-xs hidden" onclick="customers.canselEdit(\'' + data +'\')">'+
				        			
				        			'<i class="fa fa-ban" aria-hidden="true"></i></button>'+
				            		
				        			'</div>'
				            		
				        			;
				           	} 
			             }
			         ],	
			         'iDisplayLength': 100,
			         "info":     false
			         
			});	
}

customers.edit = function(id){
	
	var btnDelete 	= '#btn_cust_delete_'   + id;
	var btnEdit 	= '#btn_cust_edit_' 	+ id;
	var btnSave 	= '#btn_cust_save_'		+ id;
	var btnCansel 	= '#btn_cust_cansel_'	+ id;
	
	var inp_cust_name = '#cust_name_'		+ id;
	var cust_number  = '#cust_number_'		+ id;
	
	$(inp_cust_name).removeAttr('disabled');
	$(inp_cust_name).removeClass('inputExtension');
	$(inp_cust_name).addClass('inputEdit');
	
	$(cust_number).removeAttr('disabled');
	$(cust_number).removeClass('inputExtension');
	$(cust_number).addClass('inputEdit');
	
	$(btnDelete).addClass("hidden");
	$(btnEdit).addClass("hidden");
	
	$(btnSave).removeClass("hidden");
	$(btnCansel).removeClass("hidden");
}
customers.canselEdit = function(id){
	var btnDelete 	= '#btn_cust_delete_'   + id;
	var btnEdit 	= '#btn_cust_edit_' 	+ id;
	var btnSave 	= '#btn_cust_save_'		+ id;
	var btnCansel 	= '#btn_cust_cansel_'	+ id;
	
	var inp_cust_name = '#cust_name_'		+ id;
	var cust_number  = '#cust_number_'		+ id;
	
	$(inp_cust_name).attr('disabled','disabled');
	$(inp_cust_name).removeClass('inputEdit');
	$(inp_cust_name).addClass('inputExtension');
	
	$(cust_number).attr('disabled','disabled');
	$(cust_number).removeClass('inputEdit');
	$(cust_number).addClass('inputExtension');
	
	$(btnDelete).removeClass("hidden");
	$(btnEdit).removeClass("hidden");
	
	$(btnSave).addClass("hidden");
	$(btnCansel).addClass("hidden");	
}
customers.updateCustomer = function(id){
	
	var inp_cust_name = '#cust_name_'		+ id;
	var cust_number  = '#cust_number_'		+ id;
	var headers = {};

	if ($(inp_cust_name).val().length==0){
		$(inp_cust_name).addClass('error_required');
		return;
	}else{
		$(inp_cust_name).removeClass('error_required');
	}
	if ($(cust_number).val().length==0){
		$(cust_number).addClass('error_required');
		return;
	}else{
		$(cust_number).removeClass('error_required');
	}
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	
	var cunstomer = {id: id, name : $(inp_cust_name).val(), number : $(cust_number).val(),course : ""  };

	$.ajax({
		  type: "POST",
		  url: "updateCustomer",
		  data: JSON.stringify(cunstomer),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  
			  if (e.response == "Error"){
				  customers.errorCustomerDiv().removeClass("hidden");
				  customers.errorCustomerText().text($error.saveCustomer);
				  setTimeout( function() { customers.errorCustomerDiv().addClass('hidden'); }, 3000);
			  }
			  if (e.response == "Saved"){
				  customers.customer_save_done().parent().removeClass('hidden');
				  customers.customer_save_done().text($message.customerSaved);
				  setTimeout( function() { customers.customer_save_done().parent().addClass('hidden'); }, 3000);
				  customerTables.ajax.reload();
			  }
			  
			  console.log(e)
		  },
		  error : function(e) {
			  customers.errorCustomerDiv().removeClass("hidden");
			  customers.errorCustomerText().text($error.saveCustomer);
			  setTimeout( function() { customers.errorCustomerDiv().addClass('hidden'); }, 3000);
			}
		});	
		
}
customers.close = function(){
	
	customers.addCustomerPanel().addClass("hidden");
	
}
customers.warningCustomerDelDiv = function(){
	
	return $('#warningCustomerDelDiv');
	
}
customers.btnCustomerDelete = function(){
	
	return $('#btnCustomerDelete');
	
}
customers.warningCustomerDelText = function(){
	
	return $('#warningCustomerDelText');
	
}

customers.customer_save_done = function(){
	
	return $('#customer_save_done');
	
}
customers.customer_save_error = function(){
	
	return $('#customer_save_error');
	
}
customers.worningCancel = function(){
	
	customers.warningCustomerDelDiv().addClass('hidden');
	
}
customers.addCustomerPanel = function(){
	
	return $('#addCustomerPanel');
	
}
customers.selectQueueCustomers = function(){
	
	return $('#selectQueueCustomers');
	
}

customers.errorCustomerDiv = function(){
	
	return $('#errorCustomerDiv');
	
}
customers.errorCustomerText = function(){
	
	return $('#errorCustomerText');
	
}

customers.save = function(){
	
	var name = $("#new_name"), number = $("#new_number"),
		headers = {};

	var course = $("#selectQueueCustomers :selected").text();

	if (name.val().length==0){
		name.addClass('error_required');
		return;
	}else{
		name.removeClass('error_required');
	}
	if (number.val().length==0){
		number.addClass('error_required');
		return;
	}else{
		number.removeClass('error_required');
	}
	headers[core.gcsrf().headerName] = core.gcsrf().token;
   
	var cunstomer = {name : name.val(), number : number.val(),course : course  };
	
	$.ajax({
		  type: "POST",
		  url: "saveCustomer",
		  data: JSON.stringify(cunstomer),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  
			  if (e.response == "Error"){
				  customers.errorCustomerDiv().removeClass("hidden");
				  customers.errorCustomerText().text($error.saveCustomer);
				  setTimeout( function() { customers.errorCustomerDiv().addClass('hidden'); }, 3000);
			  }
			  if (e.response == "Saved"){
				  customers.customer_save_done().parent().removeClass('hidden');
				  customers.customer_save_done().text($message.customerSaved);
				  setTimeout( function() { customers.customer_save_done().parent().addClass('hidden'); }, 3000);
				  customerTables.ajax.reload();
			  }
			  
			  console.log(e)
		  },
		  error : function(e) {
			  customers.errorCustomerDiv().removeClass("hidden");
			  customers.errorCustomerText().text($error.saveCustomer);
			  setTimeout( function() { customers.errorCustomerDiv().addClass('hidden'); }, 3000);
			}
		});	
}
customers.addCustomer = function(){
	
	customers.addCustomerPanel().removeClass("hidden");
	$.ajax({
		  type: "GET",
		  url: "queues",
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			 customers.selectQueueCustomers().empty();
			 for(var i=0; i< e.courses.length; i++){  
				 var option1 = document.createElement("option");
				 option1.setAttribute("value", e.courses[i].coursename);
				 option1.innerHTML = e.courses[i].coursename;
				 customers.selectQueueCustomers().append(option1);
			 }
		  },
		  error : function(e) {
		
				console.log("ERROR: ", e);
			}
		});	
	
	
}
customers.deleteCustomer = function(id){
	
	var headers = {};
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	
	var data = {name: "id", value: id};
	$.ajax({
		  type: "POST",
		  url: "deleteCustomer",
		  data: JSON.stringify(data),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
				
			  customers.warningCustomerDelDiv().addClass('hidden');
				 
				 if (e.response == 'Deleted'){
					
					 customers.customer_save_done().parent().removeClass('hidden');
					  
					 customers.customer_save_done().text('Agent has been deleted');

					  setTimeout( function() { customers.customer_save_done().parent().addClass('hidden'); }, 3000);
					  
					  customerTables.ajax.reload();
				 }
				 if (e.response == 'Error'){
					 customers.customer_save_error.parent().removeClass('hidden');
					  
					 customers.customer_save_error.text('Error deleting Agent!');
					  
					  setTimeout( function() { customers.customer_save_error.parent().addClass('hidden');  }, 3000);
					 
				 }
			  },
			  error : function(e) {
				  
				  customers.warningCustomerDelDiv().addClass('hidden');
				  
				  customers.customer_save_error().parent().removeClass('hidden');
				  
				  customers.customer_save_error().text('Error deleting Agent!');
				  
				  setTimeout( function() { customers.customer_save_error().parent().addClass('hidden');  }, 3000);
				}		
		});		
}
customers.warningCustomerDelete = function(id){
	var url ='getCustomer?id=' + id;
	$.ajax({
		  type: "GET",
		  url: url,
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			  customers.warningCustomerDelDiv().removeClass("hidden");
			  customers.warningCustomerDelText().html($message.deleteCustomer  +" [" + e.name + "]?");
			  customers.btnCustomerDelete().attr("onclick","customers.deleteCustomer('" + id + "')");
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
			}
		});	
}