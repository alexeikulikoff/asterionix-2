
var appgui = appgui || {} ,
	tableAgents = null, 
	tableCDR = null,
	tableConsolidate = null, 
	
	player=null,
	queues = [];

var dataToMysql = function(data){
	var str = data.split(".");
	return str[2]+ "-" + str[1] + "-" + str[0];
}

appgui.stompClient = null;
appgui.dtpicker_from = {};
appgui.dtpicker_to = {};

appgui.date_from = null;
appgui.date_to = null;

appgui.showWaitDialog = function(show){
	
	if (show) {
		$("#wait_dialog").removeClass("hidden");
		
	}else{
		$("#wait_dialog").addClass("hidden");
	}
	
	
}
appgui.start = function(){
	
	
	appgui.connect();
	setTimeout(
			  function() 
			  {
				  appgui.onLogin();
				  setTimeout(
						  function() 
						  {
							 // tablemap.initialize();
							  appgui.onShowQueue();
							  $("#btnStop").removeClass("disabled");
							  $("#btnStart").addClass("disabled");
							  $("#errorConnection").addClass("hidden");
							  
						  }, 2000);
				  
			  }, 2000);
}
appgui.relogin = function(){
	
	if (appgui.stompClient != null) {
	
	//	appgui.stompClient.send("/asterionix/messages", {}, JSON.stringify({ 'name': "LOGOFF" })); // !!! to stop thread and close socket
		appgui.stompClient.disconnect();
    }
}
appgui.stop = function(){
	appgui.onDisconnect();
	
	$("#btnStop").addClass("disabled");
	$("#btnStart").removeClass("disabled");
}
appgui.logout = function(){
	appgui.stop();
	location.href = "/asterionix-reports/login?logout";
}


appgui.createPlayer = function(data){
	
	if(player != null){
		
		$("#cdr_player").jPlayer( "destroy" );
	}
	var sound_file = data.split("-")[0] + "." + data.split("-")[1]+".mp3";
	
	var curURL = window.location.href;
	var ind = curURL.lastIndexOf("/");
	var ur = curURL.substring(0,ind+1);
	var sound_url = ur + 'sounds/' + sound_file;
	
	var stopId = "#stop-" +sound_file;
	var playId = "#play-" +sound_file;
	
	player = $("#cdr_player").jPlayer({
		 errorAlerts: true,
          ready: function () {
            $(this).jPlayer("setMedia", {
              mp3: sound_url
            	  
            }).jPlayer("play");
          },
          error: function (event) {
              $("#sound_error-" + data).removeClass("hidden");
          	  $("#play-" + data).addClass("hidden");
			  $("#stop-" + data).addClass("hidden");
              
             // console.log(event.jPlayer.error.type);
          },
          swfPath: "/js",
          supplied: "mp3",
	          cssSelectorAncestor: "",
	          cssSelector: {
//	        	play: playId,
	            stop: stopId
	          }
      });
	
	  $("#download-" + data).removeClass("hidden");
 
}	

appgui.playSound = function(data){
	//console.log(data);
	
	$("#play-" + data).addClass("hidden");
	$("#stop-" + data).removeClass("hidden");
	
	if(player != null){
		$("#cdr_player").jPlayer( "destroy" );
	}
	tableCDR.rows().eq(0).each( function ( index ) {
		var row = tableCDR.row( index );
		var tr = row.node();
	//	console.log(tr);
		if ($(row.node().childNodes[6].childNodes[0]).attr('id') != "play-" + data){
			$(row.node().childNodes[6].childNodes[0]).removeClass("hidden");
			$(row.node().childNodes[6].childNodes[1].childNodes[1]).addClass("hidden");
			$(row.node().childNodes[6].childNodes[1].childNodes[2]).addClass("hidden");
		}
		
	});
	appgui.createPlayer(data);
}
appgui.stopSound = function(data){
	
	$("#play-" + data).removeClass("hidden");
	$("#stop-" + data).addClass("hidden");
	$("#download-" + data).addClass("hidden");
	
	if(player != null){
		$("#cdr_player").jPlayer( "destroy" );
	}
}
appgui.downloadSound = function(filename) {
	
	var curURL = window.location.href;
	var ind = curURL.lastIndexOf("/");
	var ur = curURL.substring(0,ind+1);
	
	var url = ur + 'sounds/' +  filename + '.mp3';

    var pom = document.createElement('a');
 //   pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(url));
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
appgui.testQuery = function(){
	
	var cdrquery = {src: "", dst: "", dsp: "ANSWERED", time1: "2016-04-21", time2 : "2016-04-22",  page: "1", pageSize: "10"},
	
		headers = {};
	
	//console.log("test Query");
	//console.log(JSON.stringify(cdrquery));
	
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	
	$.ajax({
		  type: "POST",
		  url: "test-report",
		  data: JSON.stringify(cdrquery),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			 //console.log(e);
		  }, 
		  error : function(e) {
					console.log("ERROR: ", e);
					
		 }
	});	  
	
}

appgui.hideCGRPanel = function(){
	
	$("#CGRPanel").addClass("hidden");
	
}
appgui.hideCDRPanel = function(){
	
	$("#CDRPanel").addClass("hidden");
	
}
appgui.reportCDR = function(src, dst, dsp, time1, time2, page, pagesize){
	
	var query = { 
				src			: src, 
				dst			: dst, 
				dsp			: dsp, 
				time1		: time1 , 
				time2		: time2, 
				page		: page, 
				pagesize	: pagesize
				},
	headers = {};
	appgui.showWaitDialog(true);
	if (tableCDR != null){
		tableCDR.destroy();
	}
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	
	$.ajax({
		  type: "POST",
		  url: "report-cdr",
		  data: JSON.stringify(query),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			 
			  $("#CDRPanel").removeClass("hidden");
			 
			
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
			
			  tableCDR = $('#tableCDR')
				.on('draw.dt', function(){
						appgui.showWaitDialog(false);
			  })
				.DataTable({
				 	data : dataSet,
				 	columns : [
				               { title	: $label.id, 	 	className: "text-left" },
					           { title	: $label.date,   	className: "text-center",render : function(data){
					        	    var d1 = data.split(" ");
		                            var d2 = d1[0].split("-"); 
		                        	return d2[2] + "." + d2[1] + "." + d2[0];
					           } },
					           { title	: $label.time,	  	className: "text-center" , render: function(data){
					        	    var d1 = data.split(" ");
		                        	return d1[1].split("\.")[0];
					           }},
					           { title	: $label.src, 	 	 	className: "text-center" },
					           { title	: $label.dst, 	 	 	className: "text-center" },
					           { title	: $label.duration, 		className: "text-center" , render : function(data){
					        	   return core.secondsToDate(data);
					           } },
					           { title	: $label.action,		className: "text-center", render: function(data){
					        	  
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
				
				    scrollY:        "400px",
				    scrollCollapse: true,
					paging: false,
					info:     false,
					searching : false 
			});
		
			$("#pageTab").empty();
			
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
					    'html':'<a  href="#" aria-controls="tableCDR" onclick="appgui.reportCDR(\'' + src +'\',\'' +  dst + '\',\'' + dsp + '\',\''  + time1 + '\',\'' + time2 + '\',\'' + e.tabs[k].p  + '\',\'' + pagesize +  '\')" >' + e.tabs[k].caption + '</a>'
					}).appendTo('#pageTab');
			} 
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
				
		}
	});
	
	
}
appgui.reportCGR = function(page){

	appgui.showWaitDialog(true);
	
	if (tableConsolidate != null){
		tableConsolidate.destroy();
	}
	var src = $("#src").val();
	var dst = $("#dst").val();
	
	
	var time1 = $("#picker_from").val();
	var time2 = $("#picker_to").val();
	
	var pageSize = 10;
	
	var query = { 
				src		: src, 
				dst		: dst, 
				time1	: dataToMysql(time1) , 
				time2	: dataToMysql(time2),
				pagesize: appgui.pagesizeCDR().val() 
				};
	
	
	
	var headers = {};
	
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	$.ajax({
		  type: "POST",
		  url: "report-cgr",
		  data: JSON.stringify(query),
		  contentType : 'application/json',
		  dataType: "json",
		  headers : headers ,    	
		  success: function(e){
			  
			 $("#CGRPanel").removeClass("hidden");
			  
			 var dataSet = [];
			 for(var i=0; i < e.records.length; i++){
				 var line = [];
				 line.push( e.records[i].disposition);
				 line.push( e.records[i].value);
				 line.push( e.records[i].action);
				 dataSet.push(line);
			 }
		
			tableConsolidate = $('#tableConsolidate')
			.on('draw.dt', function(){
				appgui.showWaitDialog(false);
			})
			.DataTable({
			 	data : dataSet,
			 	columns : [
				             { title	: $label.disposition, 	className: "text-left" },
				             { title	: $label.count, 		className: "text-center" },
				             { title	: $label.action,		className: "text-center", render : function(data){
				            	 return '<button type="button" class="btn-success btn-xs" onclick="appgui.reportCDR(' + data +')" >Show</button>';
				             } }
				            
				         ],	
				iDisplayLength: 100,
				paging: false,
				info:     false,
				searching : false 
				});
		 },
		 error : function(e) {
				console.log("ERROR: ", e);
				
			}
		});	

}
appgui.removeAgent = function(data){
	
	var agent = data.split("-")[0];
	var queue = data.split("-")[1];
	
	queueRemoveWarning(queue,agent);

	
}
appgui.removeAgentComplete = function(agent,queue){
	
	appgui.stompClient.send("/messages", {}, JSON.stringify({ 'name': "REMOVE_AGENT", "param1" : agent, "param2" : queue }));
	
	var idToRemove = '#queueRemoveDiv-' + queue; 

	$(idToRemove).remove();
	
}
appgui.queueRemoveCancel = function(queue){
	
	var idToRemove = '#queueRemoveDiv-' + queue; 

	$(idToRemove).remove();
	
}
appgui.showWarningAddToQueue = function(data){
	
	$('#add_to_queue').removeClass("hidden");
	$('#add_to_queue').empty();
	

	
	var div0 = document.getElementById('add_to_queue'); 
	div0.setAttribute("class","input-group alert alert-info");
	
	var div1 = document.createElement("div");
	div1.setAttribute("class","btn-toolbar");
	div1.setAttribute("role","toolbar" );
	
	var div2 = document.createElement("div");
	div2.setAttribute("class","btn-group btn-toolbar-margin ");
	div2.setAttribute("role","group" );
	
	var div3 = document.createElement("div");
	div3.setAttribute("class","input-group ");
	
	var span3 = document.createElement("span");
	span3.setAttribute("class","input-group-addon no-border-right");
	span3.innerHTML = data;
	
	var select = document.createElement("select");
	select.setAttribute("class","form-control no-border-right no-border-left");
	select.setAttribute("id","select_queue");
	
	for(var i=0; i <  tablemap.queues_selected.length; i++){
		var option1 = document.createElement("option");
		option1.setAttribute("value", tablemap.queues_selected[i]);
		option1.innerHTML = tablemap.queues_selected[i];
		select.appendChild(option1);
	}
	
	var div4 = document.createElement("div");
	div4.setAttribute("class","input-group btn-group  btn-toolbar button_center");
	div4.setAttribute("role","group" );
	
	var div5 = document.createElement("div");
	div5.setAttribute("class","input-group btn-group  btn-toolbar button_center");
	div5.setAttribute("role","group" );
	
	var button0 = document.createElement("button");
	button0.setAttribute("class","btn-primary btn-xs");
	button0.setAttribute("type","button" );
	button0.setAttribute("onclick","appgui.addAgentToQueue(\'" + data + "\')");
	
	var spanAdd = document.createElement("span");
	spanAdd.innerHTML = 'Add';
	button0.appendChild(spanAdd);
	
	var button1 = document.createElement("button");
	button1.setAttribute("class","btn-primary btn-xs");
	button1.setAttribute("type","button" );
	button1.setAttribute("onclick","appgui.close_add_to_queue()");
	
	var spanCncel = document.createElement("span");
	spanCncel.innerHTML = 'Cancel';
	button1.appendChild(spanCncel);
	
	div3.appendChild(span3);
	div3.appendChild(select);

	div4.appendChild(button0);
	div5.appendChild(button1);
	
	div2.appendChild(div3);
	
	div1.appendChild(div2);
	div1.appendChild(div4);
	div1.appendChild(div5);
	
	div0.appendChild(div1);
	
}
var showInfo = function(text){
	

}
appgui.addAgentToQueue = function(agent, queue){
	
	var queue = $("#select_queue :selected").text(),
		AgentQueue = {name: agent, queue:queue},
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
			 
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
				
			}
		});	
}

appgui.pagesizeCDR = function(){
	
	return $("#pagesizeCDR");
}

appgui.onDisconnect = function(){
    
	if (appgui.stompClient != null) {
		appgui.stompClient.send("/messages", {}, JSON.stringify({ 'name': "STOP" })); // !!! to stop thread and close socket
		appgui.stompClient.disconnect();
    }
  
}
appgui.onLogin = function(){
	 
	console.log('OnLogin');
	appgui.stompClient.send("/messages", {}, JSON.stringify({ 'name': "LOGIN" }));
    
	appgui.showWaitDialog(true);
	
}
appgui.onShowQueue = function(){
	
	appgui.stompClient.send("/messages", {}, JSON.stringify({ 'name': "QUEUE_SHOW" }));
	
	console.log("onShowQueue");
}
appgui.onLogOff = function(){

	appgui.stompClient.send("/messages", {}, JSON.stringify({ 'name': "LOGOFF" }));
}

appgui.addEventListeners = function(){
 	
	var onDialEvent = function(t){
 	//	console.log(t.detail.callerIdNum);
  	}
	var onLoginResponse = function(e){
  		
		console.log(e);
  		if (e.detail.response == "success"){
  			appgui.showWaitDialog(false);
  		}
  	}
	var onQueueShowResponse = function(e){
	
		activeTable.initQueues(e);
		activeTable.initIncCalls(e);
	}
	
	var onQueueMemberAddedEvent = function(e){
	
		activeTable.addAgent(e);
		
	} 
	var onQueueMemberRemovedEvent = function(e){
	
		activeTable.removeAgent(e);
		
	}
	var onQueueMemberStatusEvent = function(e){
		
		activeTable.updateStatus(e);
	
	}
	var onJoinEvent = function(e){
		
		
		activeTable.joinCall(e);
		
	}
	var onBridgeEvent = function(e){
		
		
		activeTable.bridgeCall(e);
	}
	var onErrorResponse = function(){
		
		appgui.showWaitDialog(false);
		
		$("#errorConnection").removeClass("hidden");
		
		$("#btnStop").addClass("disabled");
		$("#btnStart").removeClass("disabled");
	}
	
    document.body.addEventListener("DialEvent", 				onDialEvent, false);
	document.body.addEventListener("LoginResponse", 			onLoginResponse, false);
	document.body.addEventListener("ErrorResponse", 			onErrorResponse, false);
	document.body.addEventListener("QueueShowResponse", 		onQueueShowResponse, false);
	document.body.addEventListener("QueueMemberAddedEvent", 	onQueueMemberAddedEvent, false);
	document.body.addEventListener("QueueMemberRemovedEvent", 	onQueueMemberRemovedEvent, false);
	document.body.addEventListener("QueueMemberStatusEvent", 	onQueueMemberStatusEvent, false);
	document.body.addEventListener("JoinEvent", 				onJoinEvent, false);
	document.body.addEventListener("BridgeEvent", 				onBridgeEvent, false);
		
}

appgui.connect = function(){
	
	var getObject = function(name){
  		var result= null;
  		for(var i=0; i < EventMap.length; i++){
  			if (name == EventMap[i].key){
  				result = EventMap[i].value;
  			}
  		}
  		return result;
  	}
	var headers = {};
	
	headers[core.gcsrf().headerName] = core.gcsrf().token;
	
	var socket = new SockJS('/asterionix-reports/messages');
	appgui.stompClient = Stomp.over(socket);            
	appgui.stompClient.connect(headers, function(frame) {
		appgui.stompClient.subscribe('/topic/greetings', function(greeting){
	      var event = JSON.parse(greeting.body);
	      var newEvent = getObject(JSON.parse(greeting.body).source);
	  
	      if (newEvent != null){
	        Object.getOwnPropertyNames(event).forEach(function(val, idx, array){
	          Object.defineProperty(newEvent.detail,val,{ value : event[val]});
	        });
	        document.body.dispatchEvent(newEvent);
	      }
	      
	    });
		appgui.stompClient.subscribe('/topic/connect', function(data){
	    	console.log('/topic/connect');
	    	
	    });
		appgui.stompClient.subscribe('/topic/disconnect', function(data){
	    	console.log('/topic/disonnect');
	    	
	    });
    });
}
appgui.createGUI = function(){
	
	var initTabs  = function(){
		
		appgui.addEventListeners();
		
		$("#arrowL").hover(function() {
		    $(this).css('cursor','pointer');
		}, function() {
		    $(this).css('cursor','auto');
		});
		
		
		
		$("ul.nav-tabs a").click(function (e) {
			  e.preventDefault();
			
			  if ($(this).attr('href') == '#sub12'){
				  queuelogin.getAgents();
				  queuelogin.getCourse();
			  }
			  $(this).tab('show');
		});
		
		appgui.dtpicker_from = jQuery('#picker_from').datetimepicker({
			 lang:'ru',
				timepicker:false,
				 format:'d.m.Y',
				  onChangeDateTime:function(dp,$input){
					  appgui.date_from = $input.val();
			      }					 
		});
		
		appgui.dtpicker_to = jQuery('#picker_to').datetimepicker({
			 lang:'ru',
			  timepicker:false,
			  format:'d.m.Y',
			  onChangeDateTime:function(dp,$input){
				  appgui.date_to = $input.val();
		      }
		});
		var getCurrentDate = function(){
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
		$("#picker_from").val(getCurrentDate());
		$("#picker_to").val(getCurrentDate());
		
// **** TEST MODE *************************** ///		
		//$("#picker_from").val("04.05.2016");
		//$("#picker_to").val("06.05.2016");
	}
	this.initialize = function(){
		
		$("#closeCGR").hover(function() {
		    $(this).css('cursor','pointer');
		}, function() {
		    $(this).css('cursor','auto');
		});
		$("#closeCDR").hover(function() {
		    $(this).css('cursor','pointer');
		}, function() {
		    $(this).css('cursor','auto');
		});
		
	//	tablemap.initialize();
		
		//addEventListeners();
		
//		appgui.onDisconnect();
	
		initTabs();
	}

}