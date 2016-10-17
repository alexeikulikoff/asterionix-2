var reports = reports || {},
	tableCDR = null,
	tableConsolidate = null,
	player = null;

var dataToMysql = function(data){
	var str = data.split(".");
	return str[2]+ "-" + str[1] + "-" + str[0];
}
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

reports.dtpicker_from = {};
reports.dtpicker_to = {};

reports.setHeader = function(d){
	
	
	if (d == 'sub01') { $("#report_header").text('Reports: CDR')}
	if (d == 'sub02') { $("#report_header").text('Reports: Queue')}
	if (d == 'sub03') { $("#report_header").text('Reports: Search')}
	if (d == 'sub04') { $("#report_header").text('Settings: Agents')}
	if (d == 'sub05') { $("#report_header").text('Settings: Phones')}
	if (d == 'sub06') { $("#report_header").text('Settings: Queues')}
	if (d == 'sub07') { $("#report_header").text('Settings: Users')}
	if (d == 'sub08') { $("#report_header").text('Settings: Customers')}
	
	
}
reports.logout = function(){
	
	location.href = "/reports/login?logout";
	
}
reports.hideCGRPanel = function(){
	
	$("#CGRPanel").addClass("hidden");
	
}
reports.hideCDRPanel = function(){
	
	$("#CDRPanel").addClass("hidden");
	
}


reports.initialize = function(){
	
	console.log("init cdr report");

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
	$("ul.nav-second-level a").click(function (e) {
		
		  e.preventDefault();
		  console.log(e);
		  $(this).tab('show');
	});
	
	
    $('#menu').metisMenu();
   
	
	
	reports.dtpicker_from = jQuery('#picker_from').datetimepicker({
		 lang:'ru',
			timepicker:false,
			 format:'d.m.Y',
			  onChangeDateTime:function(dp,$input){
				  reports.date_from = $input.val();
		      }					 
	});

	reports.dtpicker_to = jQuery('#picker_to').datetimepicker({
		 lang:'ru',
		  timepicker:false,
		  format:'d.m.Y',
		  onChangeDateTime:function(dp,$input){
			  reports.date_to = $input.val();
	     }
	});

	$("#picker_from").val(getCurrentDate());
	$("#picker_to").val(getCurrentDate());

}
reports.reportCDR = function(src, dst, dsp, time1, time2, page, pagesize){
	
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
	core.showWaitDialog(true);
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
						core.showWaitDialog(false);
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
					        	 
					        		return '<button  id="play-' + data1 + '" type="button" class="btn btn-success btn-xs" onclick="reports.playSound(\'' + data1 +  '\')"><i class="fa fa-play"></i></button>' + 
		                        	 '<div class="btn-toolbar btn-toolbar-sound"> '+
		                        	'<button id="stop-'+ data1 + '" class="btn btn-danger btn-xs hidden" onclick="reports.stopSound(\'' + data1 + '\')" ><i class="fa fa-stop"></i></button>' +
		                        	 '<button id="download-'+ data1 + '" class="btn btn-success btn-xs hidden" onclick="reports.downloadSound(\'' + data + '\')" ><i class="fa fa-download"></i></button>' +
		                        	'</div>'+
		                        	 '<div id="sound_error-' + data1 + '" class="alert alert-danger hidden" role="alert"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>'+
	  								 '<span class="sr-only">Error:</span>No sound</div>';
					        	   
					           } },
					         ],	
				
				    scrollY:        "800px",
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
					    'html':'<a  href="#" aria-controls="tableCDR" onclick="reports.reportCDR(\'' + src +'\',\'' +  dst + '\',\'' + dsp + '\',\''  + time1 + '\',\'' + time2 + '\',\'' + e.tabs[k].p  + '\',\'' + pagesize +  '\')" >' + e.tabs[k].caption + '</a>'
					}).appendTo('#pageTab');
			} 
		  },
		  error : function(e) {
				console.log("ERROR: ", e);
				
		}
	});
	
	
}
reports.pagesizeCDR = function(){
	
	return $("#pagesizeCDR");
}
reports.reportCGR = function(page){

	core.showWaitDialog(true);
	
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
				pagesize: reports.pagesizeCDR().val() 
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
				core.showWaitDialog(false);
			})
			.DataTable({
			 	data : dataSet,
			 	columns : [
				             { title	: $label.disposition, 	className: "text-left" },
				             { title	: $label.count, 		className: "text-center" },
				             { title	: $label.action,		className: "text-center", render : function(data){
				            	 return '<button type="button" class="btn-success btn-xs" onclick="reports.reportCDR(' + data +')" >Show</button>';
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
reports.createPlayer = function(data){
	
	if(player != null){
		
		$("#cdr_player").jPlayer( "destroy" );
	}
	var sound_file = data.split("-")[0] + "." + data.split("-")[1]+".mp3";
	
	var curURL = window.location.href;
	var ind = curURL.lastIndexOf("/");
	var ur = curURL.substring(0,ind+1);
	//var sound_url = ur + 'sounds/' + sound_file;
	
	var sound_url = "http://asterisk.sound.host/sounds_mp3/" + sound_file;
		
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

reports.playSound = function(data){
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
	reports.createPlayer(data);
}
reports.stopSound = function(data){
	
	$("#play-" + data).removeClass("hidden");
	$("#stop-" + data).addClass("hidden");
	$("#download-" + data).addClass("hidden");
	
	if(player != null){
		$("#cdr_player").jPlayer( "destroy" );
	}
}
reports.downloadSound = function(filename) {
	
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


