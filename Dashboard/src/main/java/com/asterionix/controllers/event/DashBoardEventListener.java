package com.asterionix.controllers.event;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.asterionix.controllers.dashboard.DashBoard;
import com.asterionix.controllers.response.AsteriskResponse;

public class DashBoardEventListener {

	private ExecutorService executor;
	
	private DashBoard target;

	public DashBoardEventListener(DashBoard target){
		
		this.target = target;
		
		executor =  Executors.newCachedThreadPool();
		
		
	}
	public void dispatchEvent(AsteriskEvent event){
	
		executor.execute(new Runnable(){
			@Override
			public void run() {
				event.onEvent(target,event);
			}
				
		});
		
	}
	public void dispatchEvent(AsteriskResponse response){
		executor.execute(new Runnable(){
			@Override
			public void run() {
				response.onResponse(target,response);	
			}
			
		});
	}		
	

}