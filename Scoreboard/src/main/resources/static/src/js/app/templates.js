/**
 * 
 */

var queue_tables = function(id, name) {
	
	return '<div id=queuesPanel-' + id + '  class="panel"><div class="panel-heading" >'+
	'<h4 class="panel-title"><i class="fa fa-headphones fa-fw"></i><span>'+ name + '</span></h4>'+
	'</div><div class="panel-body "><table id="table_'+ id + '" class="table table-bordered " ></table>'+
	'</div></div>';
	
}
var queueRemoveWarning = function(queue, agent){
	
	var id  = '#queuesPanel-'+ queue;
	var id2 = '#queueRemoveDiv-' + queue;
	if ($(id2).length == 0) {
		$('<div id="queueRemoveDiv-' + queue + '"  class="panel panel-default dashboard-panel">'+
			  '<div class="panel-body">'+
	             '<div class="input-group col-md-12 ">'+		
		            ' <div class="alert alert-warning alert-dismissible text-center" role="alert">'+
		                '<i class="fa  fa-exclamation-triangle" aria-hidden="true"></i>'+
		                '<span><strong>Warning!&nbsp;</strong><span id="warningAgentDelText">' + $message.removeAgent  + ':&nbsp;' + agent + '?</span></span>'+
		            '<div>'+
		         '</div>'+
		         '<div class="input-group col-md-6 col-md-offset-4">'+
		  	         '<button id="btnQueueDelete" type="button" class="btn-xs btn-primary btn-margin-left" data-toggle="tooltip" data-placement="top" title="Yes" onclick="appgui.removeAgentComplete(\'' + agent + '\',\'' + queue +  '\')"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>'+
			         '<button type="button" class="btn-xs btn-primary btn-margin-left" data-toggle="tooltip" data-placement="top" title="No"  onclick="appgui.queueRemoveCancel(\'' + queue + '\')"><i class="fa fa-ban" aria-hidden="true"></i></button>'+
		          '</div>'+
		       '</div>'+
		    '</div>'+
		'</div>').appendTo(id);
	}
	
/*
	
	$('<div id="queueRemoveDiv-' + queue +  '" class=" col-md-8" role="alert">'+
	  '<div class="input-group col-md-12 ">'+
		 '<div class="alert alert-warning alert-dismissible text-center" role="alert">'+
		   '<i class="fa  fa-exclamation-triangle" aria-hidden="true"></i>'+
		   '<span><strong>Warning!&nbsp;</strong><span >' + agent + '</span></span>'+
		'</div></div><div class="input-group col-md-6 col-md-offset-4">'+
		' <button id="btnAgentDelete" type="button" class="btn btn-primary" >Delete</button>'+
		'<button type="button" class="btn btn-primary"  onclick="appgui.queueRemoveCancel()" >Cancel</button>'+
	  '</div><br/></div>').appendTo(id);	*/
	
}
var incCallsTableTemplate = function(id, name) {
	
	return '<div class="panel"><div class="panel-heading" >'+
	'<h4 class="panel-title"><i class="fa fa-headphones fa-fw"></i><span>'+ name + '</span></h4>'+
	'</div><div class="panel-body"><table id="incCallsTable_'+ id + '" class="table table-striped table-bordered table-hover" ></table>'+
	'</div></div>';
	
}


