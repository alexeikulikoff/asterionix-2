package com.asterionix.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

abstract class AbstractBuilder {

	protected Map<String, Class< ? >> registeredEventClasses;
	protected ArrayList<String> buffer;
	
	public AbstractBuilder(){
		
		this.registeredEventClasses = new HashMap<>();
		this.buffer = new ArrayList<String>();
		registerEventClasses();
	
	}
	
	private void registerEventClasses(){
		registerEventClasses(HangupEvent.class);
		registerEventClasses(DialEvent.class);
		registerEventClasses(BridgeEvent.class);
		registerEventClasses(QueueMemberAddedEvent.class);
		registerEventClasses(QueueMemberRemovedEvent.class);
		registerEventClasses(QueueMemberStatusEvent.class);
		
	}
	private void registerEventClasses(Class< ? extends AbstractEvent> clazz){
		
		String className;
	    String eventType;

	    className = clazz.getName();
	    eventType = className.substring(className.lastIndexOf('.') + 1).toLowerCase(Locale.ENGLISH);
	    if (!eventType.endsWith("event"))
        {
	    	 throw new IllegalArgumentException(clazz + " is not a AsteriskEvent");
	    	
        }
	    eventType = eventType.substring(0, eventType.length() - "event".length());
	   
	    @SuppressWarnings("unused")
		Constructor< ? > defaultConstructor;
	    
	    try {
			defaultConstructor = clazz.getConstructor(new Class[]{Object.class});
		} catch (NoSuchMethodException ex) {
			
			throw new IllegalArgumentException(clazz + " has no usable constructor");
		}
	    registeredEventClasses.put(eventType.toLowerCase(Locale.US), clazz);
	    
	    
	}
	public void fillBuffer(String line, BufferedReader reader){

		buffer.clear();
		
		try {
			buffer.add(line);
		
			String s;
			while(!(s = reader.readLine()).isEmpty()){
				buffer.add(s);
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void testBuffer() {

		for(int i=0; i< buffer.size(); i++){
		
			System.out.println("Test buffer :'" + buffer.get(i) );
		
		}
	}
}
