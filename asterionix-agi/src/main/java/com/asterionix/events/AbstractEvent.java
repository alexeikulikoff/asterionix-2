package com.asterionix.events;

import java.lang.reflect.Method;
import java.util.EventObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



abstract class AbstractEvent extends EventObject implements AsteriskEvent{

	public AbstractEvent(Object source) {
		super(source);
		
	}

	Logger logger = LoggerFactory.getLogger(AbstractEvent.class);
	
	private static final long serialVersionUID = 1L;

	@Override
	public void onEvent(Object source, AsteriskEvent ev) {
		
		String evClassName = ev.getClass().getName();
	    
		String className = evClassName.substring(evClassName.lastIndexOf('.') + 1);

		source.getClass().getName();
	    
		Class<? extends Object> clazz = source.getClass();
		
		for(Method m : clazz.getMethods()){
			if ( m.getName().startsWith("On")){
				if (m.getName().equals("On" + className)){
					try {
						m.invoke(source, ev);
					} catch (Exception e) {
						logger.info("Unable to execute OnEvent for : " + source.getClass().getName() + " : " + m.getName());
						
					}
				}
			}
		}
	}
	 

}
