package com.asterionix.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.asterionix.events.AsteriskEvent;
import com.asterionix.response.AsteriskResponse;


public class AsteriskClientEventListener {

	private ExecutorService executor;
	
	private AsteriskClient target;

	public AsteriskClientEventListener(AsteriskClient target){
		
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
