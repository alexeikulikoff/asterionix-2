package com.asterionix.controllers.dashboard;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.asterionix.controllers.event.AsteriskEvent;
import com.asterionix.controllers.response.AsteriskResponse;


public abstract class AbstractDashBoard {

	static Logger logger = LoggerFactory.getLogger(AbstractDashBoard.class);
	protected SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	public AbstractDashBoard(SimpMessagingTemplate template){
	
		this.simpMessagingTemplate = template;
	}
	
	protected String getPureClassName(AsteriskEvent event){
		
		String className = event.getClass().getName();
		
		className = className.substring(className.lastIndexOf('.') + 1);
		
		return className;
	}	
	protected String getPureClassName(AsteriskResponse response){
		
		String className = response.getClass().getName();
		
		className = className.substring(className.lastIndexOf('.') + 1);
		
		return className;
	}	
	
	
	protected void reAssign(AsteriskEvent source, AsteriskEvent destination) {
		
		Map<String, String> getters = new HashMap<String, String>();
		
		Class<? extends AsteriskEvent> sourceClass = source.getClass();
		
		for(Method m : sourceClass.getDeclaredMethods()){
			if (m.getName().startsWith("get")){
				String name = m.getName().substring(3);
				try {
					String value = (String) m.invoke(source);
					getters.put(name, value);
				} catch (Exception e) {
					logger.info("Unable to get values for reassing  event class: " + source.getClass().getName());
				}
			}
		}
		Class<? extends AsteriskEvent> clazz = destination.getClass();
	
		for(Method m : clazz.getDeclaredMethods()){
			String value = null;
			if (m.getName().startsWith("set")){
				String name = m.getName().substring(3);
				value = getters.get(name);
				if (value != null){
					try {
						m.invoke(destination, value);
					} catch (Exception e) {
						logger.info("Unable to  reassing  event class: " + source.getClass().getName());
					}
				}
				
			}
		}
	
	}
	
	protected void reAssign(AsteriskResponse source, AsteriskResponse destination) {
		
		Map<String, String> getters = new HashMap<String, String>();
		
		Class<? extends AsteriskResponse> sourceClass = source.getClass();
		
		for(Method m : sourceClass.getDeclaredMethods()){
			if (m.getName().startsWith("get")){
				String name = m.getName().substring(3);
				try {
					String value = (String) m.invoke(source);
					getters.put(name, value);
				} catch (Exception e) {
					logger.info("Unable to get values for reassing  event class: " + source.getClass().getName());
				}
			}
		}
		Class<? extends AsteriskResponse> clazz = destination.getClass();
	
		for(Method m : clazz.getDeclaredMethods()){
			String value = null;
			if (m.getName().startsWith("set")){
				String name = m.getName().substring(3);
				value = getters.get(name);
				if (value != null){
					try {
						m.invoke(destination, value);
					} catch (Exception e) {
						logger.info("Unable to  reassing  event class: " + source.getClass().getName());
					}
				}
				
			}
		}
	
	}	

}
