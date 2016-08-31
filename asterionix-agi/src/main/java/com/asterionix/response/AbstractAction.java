package com.asterionix.response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractAction {

	static Logger logger = LoggerFactory.getLogger(AbstractAction.class);
	
	public ArrayList<String> buffer;
	
	private Socket socket;
	
	public Action action;

	private Class<? extends AsteriskResponse> target;
	
	private DataOutputStream os;
	
	public AbstractAction(Socket socket){
		
		this.socket = socket;
		
		buffer = new ArrayList<String>();
		
		try {
			os = new DataOutputStream(socket.getOutputStream());
			
		 } catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendAction() {
		
		try {
			os.writeBytes(action.getCommand());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private AsteriskResponse createResponseInstance(){
		
		AsteriskResponse response;
		
		Object source = this;
		
		Constructor< ? > constructor;
		
		try {
				constructor = target.getConstructor(new Class[]{Object.class});
			} catch (Exception e) {
				 
				logger.info("Unable to get constructor of " + target.getName() , e);
		            
				 return null;
			}
			
			 try {
				response =  (AsteriskResponse) constructor.newInstance(source);
				
			} catch (Exception e) {
				
				logger.error("Unable to create new instance of " + target.getName(), e);
				
				return null;
			}
			 
			return response;
	}
	private Map<String, Method> fillMethodMap(Class <? extends AsteriskResponse> buildedClass ){
		
		Map<String, Method> methodMap = new HashMap<String, Method>();
		 for (Method m: buildedClass.getMethods()){
			
			 if (m.getName().startsWith("set")){
				 methodMap.put(m.getName().toLowerCase(Locale.ENGLISH), m);
				 
			 }
		 }
		 return methodMap;
	}
	private Map<String, String> fillBufferMap(){
		
		Map<String, String> bufferMap = new HashMap<String, String>();
		String attr;
		String value;
		String line;
	
		for (int i=0; i< buffer.size(); i++){
			
			line = buffer.get(i).toLowerCase();
			
			attr = line.substring(0, line.lastIndexOf(':')).replaceAll("\\s","");
			
			if (attr.indexOf("-") > 0){

				attr = attr.replace("-","");
			}
			
			value =  line.substring(line.lastIndexOf(':')+1, line.length() ).replaceAll("\\s","");
			
		
			
			bufferMap.put("set" + attr, value);
		}
		return bufferMap;
		
	}
	public AsteriskResponse buildActionResponse() {

		
		Map<String, String> bufferMap;
	
		Map<String, Method> methodMap;
		
		AsteriskResponse response = createResponseInstance();
		
		Class <? extends AsteriskResponse> buildedClass = response.getClass();
		
		methodMap = fillMethodMap(buildedClass);

		bufferMap = fillBufferMap();
		
		Method method;
		
		for (Map.Entry<String, String> bmap : bufferMap.entrySet() ){
			
			if ((method = methodMap.get(bmap.getKey())) != null){
				try {
					method.invoke(response, bmap.getValue());
				
				} catch (Exception e) {
					logger.error("Unable to set property '" + bmap.getKey() + "' to '" + bmap.getValue() + "' on "
	                        + response.getClass().getName(), e);
				}
			}
			
			}
		
		return response;
	}

	
	public void fillResponseBuffer(String line, BufferedReader reader) {

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
		
		//	System.out.println("Test buffer :'" + buffer.get(i) );
		
		}
	}
	
	public void setResponseClass(Class<? extends AsteriskResponse> response) {
		
		this.target = response;
		
		
	}	
	
	
}
