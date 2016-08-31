package com.asterionix.events;

import java.io.BufferedReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBuilder extends AbstractBuilder{

	Logger logger = LoggerFactory.getLogger(EventBuilder.class);

	public EventBuilder(){
		super();
		
	}

	public  AsteriskEvent buildEvent(String s, BufferedReader reader ) {
		
		 String eventName;	
		 AsteriskEvent event = null;
		 Class< ? > eventClass;
		 Constructor< ? > constructor;
		 Object source = this;
		 Map<String, String> bufferMap = new HashMap<String, String>();
		 Map<String, Method> methodMap = new HashMap<String, Method>();
		
		 fillBuffer(s,reader);

		 if (buffer.size() !=0 ){
			 
			 eventName = buffer.get(0).toLowerCase();

			 eventName = eventName.substring(eventName.lastIndexOf(':') + 1 , eventName.length()).replaceAll("\\s","");
			 
			// if (eventName.equals("queuememberstatus")){
			//	 testBuffer();
			// }
  		
			 eventClass = registeredEventClasses.get(eventName);
  			 
			 if (eventClass == null){
				//	logger.info("No event class registered for event type '" + eventName + "'");
					return null;
  			 }
			
			 try {
				constructor = eventClass.getConstructor(new Class[]{Object.class});
		
			 } catch (NoSuchMethodException ex) {
				
				// logger.info("Unable to get constructor of " + eventClass.getName() , ex);
	            
				 return null;
			}
			 
			 try {
				
				 event =  (AsteriskEvent) constructor.newInstance(source);
				
			} catch (Exception ex) {
				
				//logger.error("Unable to create new instance of " + eventClass.getName(), ex);
	            
				return null;
			
			}
			Class <?> buildedClass = event.getClass(); 
			
			 for (Method m: buildedClass.getMethods()){
				 if (m.getName().startsWith("set")){
					 methodMap.put(m.getName().toLowerCase(Locale.ENGLISH), m);
				 }
			 }
		}
		String attr;
		String value;
		String line;
		Method method;
		for (int i=1; i< buffer.size(); i++){
	
			line = buffer.get(i).toLowerCase();
			
			attr = line.substring(0, line.lastIndexOf(':')).replaceAll("\\s","");
			
			if (attr.indexOf("-") > 0){

				attr = attr.replace("-","");
			}
			
			value =  line.substring(line.lastIndexOf(':')+1, line.length() ).replaceAll("\\s","");
			
			bufferMap.put("set" + attr, value);
		}
		for (Map.Entry<String, String> bmap : bufferMap.entrySet() ){
			
			if ((method = methodMap.get(bmap.getKey())) != null){
				
				try {
					method.invoke(event, bmap.getValue());
				
				} catch (Exception e) {
					logger.error("Unable to set property '" + bmap.getKey() + "' to '" + bmap.getValue() + "' on "
	                        + event.getClass().getName(), e);
				}
			}
			
		}
		return event;
	 }
	
	
}
