

var quest = quest || {} ,
	tableCDRFind = null,
	qplayer=null;

quest.sPicker1 = {};
quest.sPicker2 = {};

quest.date1 = null;
quest.date2 = null;



quest.getCurrentDate = function(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10) {
	    dd='0'+dd
	} 
	if(mm<10) {
	    mm='0'+mm
	} 
	today = dd + '.'+ mm + '.' + yyyy;
	return today;
}

quest.startfind = function(page){
	
	
	var time1 = $("#searchPicker1").val();
	var time2 = $("#searchPicker2").val();
	var pagesize  = quest.pagesize().val();
	var src = quest.searchText().val();
	console.log(pagesize);
	quest.find(src,'','',time1, time2 ,page, pagesize );
	
}
var format  = function ( d ) {
	var str='<table class="table table-bordered" >'+
			'<thead><tr class="text-center-small"><td>Time</td><td>Agent</td><td>Peer</td><td>Event</td><td>Queue</td></tr></thead>';
	for (var i=0; i < d.childSet.length; i++){
		str = str + '<tr class="text-center-small"><td>' + d.childSet[i].time.split(" ")[1].split("\.")[0] + '</td><td>' + d.childSet[i].name + '</td><td>' + d.childSet[i].agent + '</td><td>' + d.childSet[i].event + '</td><td>' + d.childSet[i].queuename + '</td></tr>';
	}
	str = str + '</table>';
    return str;
}

quest.hideSearchPanel = function(){
	
	$("#searchPanel").addClass("hidden");
	
}

quest.find = function(src, dst, dsp, time1, time2, page, pagesize){
	
		var query = { 
					src			: src, 
					dst			: dst, 
					dsp			: dsp, 
					time1		: core.dataToMysql(time1) , 
					time2		: core.dataToMysql(time2), 
					page		: page, 
					pagesize	: pagesize
					},
		headers = {};
		core.showWaitDialog(true);
		if (tableCDRFind != null){
			tableCDRFind.destroy();
		}
		headers[core.gcsrf().headerName] = core.gcsrf().token;
		
		
		$.ajax({
			  type: "POST",
			  url: "report-find",
			  data: JSON.stringify(query),
			  contentType : 'application/json',
			  dataType: "json",
			  headers : headers ,    	
			  success: function(e){
				  
				  $("#searchPanel").removeClass("hidden");
				  
				  $("#tableFindDiv").empty();
			
				  var tb = $('<table/>', {
					    'id':	'tableCDRFind',
					    'class': 'table table-bordered'
					}).appendTo("#tableFindDiv");
					
				  
				  var dataSet = [];
				
				  for(var i=0; i < e.records.length; i++){
					 
					  var childSet = [];
					  
					  if (e.records[i].queueLogFindRecords != null){
						  var obj = e.records[i].queueLogFindRecords.records;
						  for(var j=0; j < obj.length; j++){
							  childSet.push({time: obj[j].time, name : obj[j].name,  agent : obj[j].agent, event : obj[j].event, queuename : obj[j].queuename });
						  } 
					  }
					
					  dataSet.push({
						//  	DT_RowId	: "tr-" + i,
						   // id			: i+1,
						  	date 		: e.records[i].date, 
						  	time		: e.records[i].time, 
						  	src			: e.records[i].src,
						  	dst			: e.records[i].dst,
						  	duration 	: e.records[i].duration,
						  	uniqueid	: e.records[i].uniqueid,
						  	disposition : e.records[i].disposition,
						  	childSet    : childSet
						  	}); 
				  }	 
				 
				  tableCDRFind = $('#tableCDRFind')
				  	.on('click', 'td.details-control', function(){
				  		tableCDRFind.off('click');
					  console.log("clicked");
					  var tr = $(this).closest('tr');
					  var row = tableCDRFind.row( tr );
					  if ( row.child.isShown() ) {
				            row.child.hide();
				            tr.removeClass('shown');
				        }
				        else {
				            row.child( format(row.data()) ).show();
				            tr.addClass('shown');
				        }
					  
					  
				  })
				  .on('draw.dt', function(){
							core.showWaitDialog(false);
				  })
				  .DataTable({
					 	data : dataSet,
					 	columns : [
					              
					                {
						                "className":      "details-control",
						                "orderable":      false,
						                "data":           null,
						                "defaultContent": '',
						                "width": "10%"
						            },
						        //   {title	 : "id", 	data : "id"}, 
						           { title	 : $label.date, 	data : "date",  className: "text-center",render : function(data){
						        	    var d1 = data.split(" ");
			                            var d2 = d1[0].split("-"); 
			                        	return d2[2] + "." + d2[1] + "." + d2[0];
						           } },
						           { title  : $label.time,	 data: "time",  className: "text-center" , render: function(data){
						        	    var d1 = data.split(" ");
			                        	return d1[1].split("\.")[0];
						           }},
						           { title  : $label.src, 			data	: "src",	 		className: "text-center" },
						           { title  : $label.dst, 			data	: "dst",  			className: "text-center" },
						           { title  : $label.duration,	 	data 	: "duration", 		className: "text-center" ,render : function(data){
						        	   return core.secondsToDate(data);
						           } },
						           { title  : $label.disposition, 	data 	: "disposition", 	className: "text-center" },
						           { title  : $label.action, 		data	: "uniqueid", 		className: "text-center", render: function(data){
						        	  
						        	   var data1 = data.split("\.")[0] + "-" + data.split("\.")[1]; 
						        	   
						        	 //  return '<button type="button" class="btn-success btn-xs" onclick="appgui.playSound(' + data +')" >Show</button>';
						        	 
						        		return '<button  id="play-' + data1 + '" type="button" class="btn btn-success btn-xs" onclick="appgui.playSound(\'' + data1 +  '\')"><i class="fa fa-play"></i></button>' + 
			                        	 '<div class="btn-toolbar btn-toolbar-sound"> '+
			                        	'<button id="stop-'+ data1 + '" class="btn btn-danger btn-xs hidden" onclick="appgui.stopSound(\'' + data1 + '\')" ><i class="fa fa-stop"></i></button>' +
			                        	 '<button id="download-'+ data1 + '" class="btn btn-success btn-xs hidden" onclick="appgui.downloadSound(\'' + data + '\')" ><i class="fa fa-download"></i></button>' +
			                        	'</div>'+
			                        	 '<div id="sound_error-' + data1 + '" class="alert alert-danger hidden" role="alert"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>'+
		  								 '<span class="sr-only">Error:</span>No sound</div>';
						        	   
						           } },
						         ],	
						order: [[1, 'asc']],
					    scrollY:        "600px",
					    scrollCollapse: false,
						paging: false,
						info:     false,
						searching : false 
				});
				$('#tableCDRFindTab').empty();
				for(var k=0; k < e.tabs.length; k++){
						if (e.tabs[k].caption == "Next"){
						//	e.pageTab[k].caption = button_next;
						}
						if (e.tabs[k].caption == "Previous"){
						//	data[0][k].caption = button_previous;
						}
					var a = $('<li/>', {
						    'id':'page-' + e.tabs[k].p,
						    'class': e.tabs[k].cssClass,
						    'html':'<a  href="#" aria-controls="tableCDR" onclick="quest.find(\'' + src +'\',\'' +  dst + '\',\'' + dsp + '\',\''  + time1 + '\',\'' + time2 + '\',\'' + e.tabs[k].p  + '\',\'' + pagesize +  '\')" >' + e.tabs[k].caption + '</a>'
						}).appendTo('#tableCDRFindTab');
				}
			  },
			  error : function(e) {
					console.log("ERROR: ", e);
			}
		});
		
}
quest.initialize = function(){
	
	$("#closeSearch").hover(function() {
	    $(this).css('cursor','pointer');
	}, function() {
	    $(this).css('cursor','auto');
	});

	
	quest.sPicker1 = jQuery('#searchPicker1').datetimepicker({
		 lang:'ru',
			timepicker:false,
			 format:'d.m.Y',
			  onChangeDateTime:function(dp,$input){
				  quest.date1 = $input.val();
		      }					 
	});
	
	quest.sPicker2 = jQuery('#searchPicker2').datetimepicker({
		 lang:'ru',
		  timepicker:false,
		  format:'d.m.Y',
		  onChangeDateTime:function(dp,$input){
			  quest.date2 = $input.val();
	     }
	});
	
	quest.searchPicker1().val(quest.getCurrentDate());
	quest.searchPicker2().val(quest.getCurrentDate());

}
quest.searchText = function(){
	return $("#searchText");
}
quest.searchPicker1 = function(){
	
	return $("#searchPicker1");
	
}

quest.searchPicker2 = function(){
	
	return $("#searchPicker2");
	
}


quest.pagesize = function(){
	
	return $("#questpagesize");
}

