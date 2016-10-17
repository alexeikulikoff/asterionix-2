package com.asterionix.controllers.event;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.asterionix.controllers.dashboard.DashBoard;
import com.asterionix.controllers.response.AsteriskResponse;

public class DashBoardEventListener {
	private DashBoard target;
	public DashBoardEventListener(DashBoard target){
		this.target = target;
		
	}
	public void dispatchEvent(AsteriskEvent event){
		ExecutorService  executor =  Executors.newCachedThreadPool();
		executor.execute(new Runnable(){
			@Override
			public void run() {
				event.onEvent(target,event);
			}
				
		});
		executor.shutdown();
		
	}
	public void dispatchEvent(AsteriskResponse response){
		ExecutorService  executor =  Executors.newCachedThreadPool();
		executor.execute(new Runnable(){
			@Override
			public void run() {
				response.onResponse(target,response);	
			}
			
		});
		executor.shutdown();
	}		
	

}