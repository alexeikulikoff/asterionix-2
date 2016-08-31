

var core = core || {};

core.config = {};

core.test = function(){
	console.log("core test");
}
core.gcsrf = function(){
	var self = this;
/*	self.csrfToken = ko.computed(function() {
		return JSON.parse($.ajax({
			type: 'GET',
			url: 'csrf',
			dataType: 'json',
			success: function() { },
			data: {},
			async: false
		}).responseText);
	}, this);*/
	self.csrfToken = function(){
		return 1;
	}
	return self.csrfToken();
}
core.dataToMysql = function(data){
	var str = data.split(".");
	return str[2]+ "-" + str[1] + "-" + str[0];
}
core.secondsToDate = function(data){
	var minutes, seconds ,hours;
	var d1 = parseInt(data);
    if (d1 >= 60) {
    	minutes = Math.floor(d1/60);
    	if (minutes >=60){
         	hours = Math.floor(minutes/60);
         	minutes = minutes - hours * 60;  
         	seconds = d1  - hours * 3600 - minutes * 60;
         }
    	else{
    		hours = "0";
    		seconds = d1 - minutes * 60;
    	}
    }
    else{
    	minutes="0";
    	hours = "0";
    	seconds = d1;
    }
    if (seconds < 10){
		seconds = "0" + seconds;
	}
    if (minutes < 10){
		minutes = "0" + minutes;
	}
    if (hours < 10){
    	hours = "0" + hours;
	}
  return hours + ":" + minutes + ":" + seconds;
}
core.closeError = function(data){
	
	
}



