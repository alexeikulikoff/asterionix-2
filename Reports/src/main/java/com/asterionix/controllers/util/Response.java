package com.asterionix.controllers.util;

public class Response {
	private String type;
	private String message;
		
		public Response(String t, String m)
		{
			this.type = t;
			this.message = m;
		}
		public String getType(){
			return this.type;
		}
		public String getMessage(){
			return this.message;
		}
}
