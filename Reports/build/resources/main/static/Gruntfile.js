/*global module:false*/
module.exports = function(grunt) {

	grunt.initConfig({
		concat_css: {
		    options: {},
		    all: {
		        src: ['src/css/bootstrap.min.css', 
		              'src/css/starter-template.css',
		              'src/css/jquery.datetimepicker.css', 
		              'src/css/metisMenu.min.css', 
		              'src/css/sb-admin.css',
		              'src/css/starter-template.css'],
		              
		        dest: "dist/css/cdr-styles.css"
		      },
		  },
        concat: {
            "options": { "separator": ";" },
             dist1: {
            	"src" : [   "src/js/jquery/jquery.min.js",
            	            "src/js/jquery/jquery.jplayer.min.js",
            	            "src/js/jquery/jquery.dataTables.min.js",
            	            "src/js/jquery/jquery.datetimepicker.js",
            	            "src/js/jquery/jquery.fileDownload.js",
            	            "src/js/bootstrap/js/bootstrap.min.js",
            	            "src/js/bootstrap/js/dataTables.bootstrap.js",
            	            "src/js/sockjs/sockjs.js",
            	            "src/js/stomp/lib/stomp.js",
            	            "src/js/knockout/knockout.js",
            	            "src/js/knockout/knockout.mapping-latest.js",
            	          	"src/js/app/activetables.js",
							"src/js/app/agents.js",
							"src/js/app/app.js",
							"src/js/app/appgui.js",
							"src/js/app/applauncher.js",
							"src/js/app/core.js",
							"src/js/app/courses.js",
							"src/js/app/events.js",
							"src/js/app/phones.js",
							"src/js/app/quests.js",
							"src/js/app/queuelogin.js",
							"src/js/app/tablemap.js",
							"src/js/app/templates.js",
							"src/js/app/test.js",
							"src/js/app/users.js"
							],	
                dest: "dist/js/asterionix.js"
            },
            dist2 : {
            	"src" : [   "src/js/jquery/jquery.min.js",
            	            "src/js/jquery/metisMenu.min.js",
            	            "src/js/jquery/jquery.jplayer.min.js",
            	            "src/js/jquery/jquery.dataTables.min.js",
            	            "src/js/jquery/jquery.datetimepicker.js",
            	            "src/js/jquery/jquery.fileDownload.js",
            	            "src/js/bootstrap/js/bootstrap.min.js",
            	            "src/js/bootstrap/js/dataTables.bootstrap.js",
            	            "src/js/knockout/knockout.js",
            	            "src/js/knockout/knockout.mapping-latest.js",
            	        	"src/js/app/core.js",
            	            "src/js/reports/report.js",
            	            "src/js/reports/reportApp.js",
            	            "src/js/app/queuelogin.js",
            	            "src/js/app/quests.js",
            	        	"src/js/app/agents.js",
            	        	"src/js/app/phones.js",
            	        	"src/js/app/courses.js",
            	        	"src/js/app/users.js",
            	        	"src/js/app/customers.js"
            	           ],
            	 dest: "dist/js/reportViewer.js"        
            }
        },
    
        uglify: {
        	options: {
        		mangle: false
        		
        	},
        js: {
          files: {
        	
        	  "dist/js/asterionix.min.js" : ["dist/js/asterionix.js"],
        	  "dist/js/reportViewer.min.js" : ["dist/js/reportViewer.js"]
        	  
            }
         }
       }
       
    });
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-concat-css');
 // grunt.registerTask('dist',  ['concat:dist1', 'concat:dist2']);
  grunt.registerTask('default', ['concat:dist1', 'concat:dist2', 'concat_css', 'uglify']);
  grunt.registerTask('css', ['concat_css']);

};
