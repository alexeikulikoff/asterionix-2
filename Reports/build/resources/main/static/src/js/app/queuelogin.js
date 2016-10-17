

var queuelogin = {},
	tableQueueLogin = null,
	tableQDR = null,
	qplayer=null;

queuelogin.dtpicker1 = {};
queuelogin.dtpicker2 = {};

queuelogin.date1 = null;
queuelogin.date2 = null;

(function ( $ ) {
	
}( jQuery ));


queuelogin.createPlayer = function(data){
	
	if(qplayer != null){
		
		$("#qdr_player").jPlayer( "destroy" );
	}
	var sound_file = data.split("-")[0] + "." + data.split("-")[1]+".mp3";
	
	var curURL = window.location.href;
	var ind = curURL.lastIndexOf("/");
	var ur = curURL.substring(0,ind+1);
	//var sound_url = ur + 'sounds/' +  sound_file;
	
	var sound_url = "http://asterisk.sound.host/sounds_mp3/" + sound_file;
	
	var stopId = "#qstop-" +sound_file;
	var playId = "#qplay-" +sound_file;
	
	qplayer = $("#qdr_player").jPlayer({
		 errorAlerts: true,
          ready: function () {
            $(this).jPlayer("setMedia", {
              mp3: sound_url
            	  
            }).jPlayer("play");
          },
          error: function (event) {
              $("#qsound_error-" + data).removeClass("hidden");
          	  $("#qplay-" + data).addClass("hidden");
			  $("#qstop-" + data).addClass("hidden");
              
              console.log(event.jPlayer.error.type);
          },
          swfPath: "/js",
          supplied: "mp3",
	          cssSelectorAncestor: "",
	          cssSelector: {
//	        	play: playId,
	            stop: stopId
	          }
      });
	
	  $("#qdownload-" + data).removeClass("hidden");
 
}	

queuelogin.playSound = function(data){
	
	
	$("#qplay-" + data).addClass("hidden");
	$("#qstop-" + data).removeClass("hidden");
	
	if(qplayer != null){
		$("#qdr_player").jPlayer( "destroy" );
	}
	tableQDR.rows().eq(0).each( function ( index ) {
		var row = tableQDR.row( index );
		var tr = row.node();
	
		if ($(row.node().childNodes[6].childNodes[0]).attr('id') != "qplay-" + data){
			$(row.node().childNodes[6].childNodes[0]).removeClass("hidden");
			$(row.node().childNodes[6].childNodes[1].childNodes[1]).addClass("hidden");
			$(row.node().childNodes[6].childNodes[1].childNodes[2]).addClass("hidden");
		}
		
	});
	queuelogin.createPlayer(data);
}
queuelogin.stopSound = function(data){
	$("#qplay-" + data).removeClass("hidden");
	$("#qstop-" + data).addClass("hidden");
	$("#qdownload-" + data).addClass("hidden");
	
	if(qplayer != null){
		$("#qdr_player").jPlayer( "destroy" );
	}
}
queuelogin.downloadSound = function(filename) {
	var curURL = window.location.href;
	var ind = curURL.lastIndexOf("/");
	var ur = curURL.substring(0,ind+1);
	
	var url = ur + 'sounds/'+  filename + '.mp3';
	

    var pom = document.createElement('a');
 
    pom.setAttribute('href', url);
    pom.setAttribute('download', 'sound_' + filename+'.mp3');

    if (document.createEvent) {
        var event = document.createEvent('MouseEvents');
        event.initEvent('click', true, true);
        pom.dispatchEvent(event);
    }
    else {
        pom.click();
    }
}

queuelogin.getCurrentDate = function(){
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

queuelogin.initialize = function(){
	
	$("#closeQDR").hover(function() {
	    $(this).css('cursor','pointer');
	}, function() {
	    $(this).css('cursor','auto');
	});
	$("#closeQGR").hover(function() {
	    $(this).css('cursor','pointer');
	}, function() {
	    $(this).css('cursor','auto');
	});
	
	queuelogin.dtpicker1 = jQuery('#picker1').datetimepicker({
		 lang:'ru',
			timepicker:false,
			 format:'d.m.Y',
			  onChangeDateTime:function(dp,$input){
				  queuelogin.date1 = $input.val();
		      }					 
	});
	
	queuelogin.dtpicker2 = jQuery('#picker2').datetimepicker({
		 lang:'ru',
		  timepicker:false,
		  format:'d.m.Y',
		  onChangeDateTime:function(dp,$input){
			  queuelogin.date2 = $input.val();
	     }
	});


	
	queuelogin.picker1().val(queuelogin.getCurrentDate());
	queuelogin.picker2().val(queuelogin.getCurrentDate());
	queuelogin.getAgents();
	queuelogin.getCourse();

}
queuelogin.getAgents = function(){
	
	queuelogin.selectAgent().empty();
	$.ajax({
		  type: "GET",
		  url: "agents",
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			for(var i=0; i< e.agents.length; i++){
				var option2 = document.createElement("option");
				option2.setAttribute("value",e.agents[i].id );
				option2.innerHTML = e.agents[i].name;
				queuelogin.selectAgent().append(option2);
			}
			 
		  },
		  error : function(e) {
		
				console.log("ERROR: ", e);
			}
		});	

	console.log("get agents");
}
queuelogin.getCourse = function(){
	
	queuelogin.selectCourse().empty();
	$.ajax({
		  type: "GET",
		  url: "queues",
		  contentType : 'application/json',
		  dataType: "json",
		  success: function(e){
			for(var i=0; i< e.courses.length; i++){
				var option2 = document.createElement("option");
				option2.setAttribute("value",e.courses[i].coursename );
				option2.innerHTML = e.courses[i].coursename;
				queuelogin.selectCourse().append(option2);
			}
			 
		  },
		  error : function(e) {
		
				console.log("ERROR: ", e);
			}
		});	

	
}
queuelogin.hideQDRPanel = function(){
	
	$("#QDRPanel").addClass("hidden");
}
queuelogin.hideQGRPanel = function(){
	
	$("#QGRPanel").addClass("hidden");
}
queuelogin.loginReport = function(page){
	
	core.showWaitDialog(true);
	
	var headers = {},
		data = { actiontime1 : core.dataToMysql(queuelogin.picker1().val()),
				 actiontime2 : core.dataToMysql(queuelogin.picker2().val()),
				 agentid 	 : queuelogin.selectAgent().val(),
				 pagesize    : queuelogin.pageSize().val(),
				 coursename    : queuelogin.selectCourse().val()
				};
	
	if (tableQueueLogin != null){
		tableQueueLogin.destroy();
	}
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	var dataSrc = {};
	
	$.ajax({
		  type: "POST",
		  url: "report-queuelogin",
		  data: JSON.stringify(data),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  
			  $("#QGRPanel").removeClass("hidden");
		
			  var dataSet = [];
			  for(var i=0; i < e.records.length; i++){
					 var line = [];
					 line.push( e.records[i].theDate);
					 line.push( e.records[i].loginTime);
					 line.push( e.records[i].logoffTime);
					 line.push( e.records[i].phoneNumber);
					 line.push( e.records[i].callCount);
					 line.push( e.records[i].args);
					 dataSet.push(line);
			  }	 
			  tableQueueLogin = $('#tableQueueLogin')
				.on('draw.dt', function(){
						core.showWaitDialog(false);
			  })
			  .DataTable({
				 	data : dataSet,
				 	columns : [
				               { title :  $label.date	,     className: "text-center" },
					           { title :  $label.enter	,     className: "text-center" },
					           { title :  $label.exit	,	  className: "text-center" },
					           { title :  $label.phone	, 	  className: "text-center" },
					           { title :  $label.count	, 	  className: "text-center" },
					           { title :  $label.action	, 	  className: "text-center", render : function(data){
					        	   return  '<button type="button" class="btn-success btn-xs" onclick="queuelogin.reportQDR(' + data +')" >Show</button>';
					           } },
					         ],	
					paging: false,
					info:     false,
					searching : false,
				    scrollY:        "400px",
				    scrollCollapse: true,
			});
	    
		  },
		  error : function(e) {
			
				console.log("ERROR: ", e);
			}
		});	
	
}

queuelogin.reportQDR = function(theDate, loginTime, logoffTime, phoneNumber, page, pagesize, coursename){

	
		event='CONNECT',
		query = { 
			     time1    : loginTime, 
			     time2    : logoffTime, 
			     agent    : phoneNumber,
			     event    : event, 
			     page     : page,
			     pagesize : pagesize,
			     coursename : coursename},
		
		headers = {};
	
	core.showWaitDialog(true);
	
	if (tableQDR != null){
		tableQDR.destroy();
	}
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	$.ajax({
		  type: "POST",
		  url: "report-qdr",
		  data: JSON.stringify(query),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			
			  $("#QDRPanel").removeClass("hidden");
			 
			  var dataSet = [];
			  for(var i=0; i < e.records.length; i++){
					 var line = [];
					 line.push( e.records[i].id);
					 line.push( e.records[i].date);
					 line.push( e.records[i].time);
					 line.push( e.records[i].src);
					 line.push( e.records[i].dst);
					 line.push( e.records[i].duration);
					 line.push( e.records[i].uniqueid);
					 dataSet.push(line);
			  }	 
			
			  tableQDR = $('#tableQDR')
				.on('draw.dt', function(){
						core.showWaitDialog(false);
			  })
				.DataTable({
				 	data : dataSet,
				 	columns : [
				               { title	: 	$label.id  ,    className: "text-center" },
					           { title	:   $label.date	,   className: "text-center",render : function(data){
					        	    var d1 = data.split(" ");
		                            var d2 = d1[0].split("-"); 
		                        	return d2[2] + "." + d2[1] + "." + d2[0];
					           } },
					           { title: $label.time,	 className: "text-center" , render: function(data){
					        	    var d1 = data.split(" ");
		                        	return d1[1].split("\.")[0];
					           }},
					           { title : $label.src, 	 	 className: "text-center" },
					           { title : $label.dst, 	 	 className: "text-center" },
					           { title : $label.duration,	 className: "text-center" , render : function(data){
					        	   return core.secondsToDate(data);
					           }},
					           { title : $label.action,		 className: "text-center", render: function(data){
					        	   
					        	   //return '<button type="button" class="btn-success btn-xs" onclick="appgui.playSound(' + data +')" >Show</button>';
					        	   var data1 = data.split("\.")[0] + "-" + data.split("\.")[1]; 
					        	   
					        	return '<button  id="qplay-' + data1 + '" type="button" class="btn btn-success btn-xs" onclick="queuelogin.playSound(\'' + data1 +  '\')"><i class="fa fa-play"></i></button>' + 
		                        	'<div class="btn-toolbar btn-toolbar-sound"> '+
		                        	'<button id="qstop-'+ data1 + '" class="btn btn-danger btn-xs hidden" onclick="queuelogin.stopSound(\'' + data1 + '\')" ><i class="fa fa-stop"></i></button>' +
		                        	'<button id="qdownload-'+ data1 + '" class="btn btn-success btn-xs hidden" onclick="queuelogin.downloadSound(\'' + data + '\')" ><i class="fa fa-download"></i></button>' +
		                        	'</div>'+
		                        	'<div id="qsound_error-' + data1 + '" class="alert alert-danger hidden" role="alert"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>'+
	  								'<span class="sr-only">Error:</span>No sound</div>';
					        	   
					        	   
					           } },
					         ],	
				
				    scrollY:        "600px",
				    scrollCollapse: true,
					paging: false,
					info:     false,
					searching : false 
			});
				$("#pageTab1").empty();
				console.log(e);
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
						    // src, dst, dsp, time1, time2, page
						    'html':'<a  href="#" aria-controls="tableCDR" onclick="queuelogin.reportQDR(\'' + theDate +'\',\'' +  loginTime + '\',\'' + logoffTime + '\',\''  + phoneNumber +  '\',\'' + e.tabs[k].p + '\',\'' + pagesize +  '\',\'' + coursename  +  '\')" >' + e.tabs[k].caption + '</a>'
						}).appendTo('#pageTab1');
				} 
			

		 },
		  error : function(e) {
				console.log("ERROR: ", e);
				
		}
	});
	
	
}
queuelogin.picker1 = function(){
	return $("#picker1");
}

queuelogin.picker2 = function(){
	return $("#picker2");
}
queuelogin.selectAgent = function(){
	
	return $("#selectAgent");
	
}
queuelogin.selectCourse = function(){
	
	return $("#selectCourse");
	
}
queuelogin.pageSize = function(){
	return $("#pageSize");
}

