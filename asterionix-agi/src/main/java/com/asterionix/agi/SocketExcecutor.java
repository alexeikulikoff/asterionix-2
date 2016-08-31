package com.asterionix.agi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asterionix.main.AsteriskClientImpl;


public class SocketExcecutor  {
	static Logger logger = LoggerFactory.getLogger(SocketExcecutor.class);
	private Socket socket;
	private PrintStream os;
	private BufferedReader reader;
	private String responseLine = null;
	private AsteriskClientImpl client;  
	private CoursesRepository coursesRepository;
	private AbandonRepository abandonRepository;
	private CustomersRepository customersRepository;
	
	public SocketExcecutor(AsteriskClientImpl client, CoursesRepository coursesRepository, AbandonRepository abandonRepository,
									CustomersRepository customersRepository) 
	{
      //  this.socket = socket;
        this.client = client;
        this.coursesRepository = coursesRepository;
        this.abandonRepository = abandonRepository;
        this.customersRepository = customersRepository;
        
    }

	private String GetQueueWithMaxWeight(){
		List<CoursesEntity> coursesEntity  = coursesRepository.findAll();
    	 int max = Integer.parseInt(coursesEntity.get(0).getWeight());
    	 for(int k=0; k < coursesEntity.size(); k++ ){
    		 if (Integer.parseInt(coursesEntity.get(k).getWeight()) > max){
    			 max = Integer.parseInt(coursesEntity.get(k).getWeight());
    		 }
    	 }
    	 CoursesEntity maxWeightEntity = coursesRepository.findByWeight(Integer.toString(max));
    	 String queueName = maxWeightEntity.getCoursename();
    	
    	 return queueName;
	}
	private String GetQueueWithMinWeight(){
		List<CoursesEntity> coursesEntity  = coursesRepository.findAll();
    	int min = Integer.parseInt(coursesEntity.get(0).getWeight());
    	for(int k=0; k < coursesEntity.size(); k++ ){
    		 if (Integer.parseInt(coursesEntity.get(k).getWeight()) < min){
    			 min = Integer.parseInt(coursesEntity.get(k).getWeight());
    		 }
    	 }
    	 CoursesEntity maxWeightEntity = coursesRepository.findByWeight(Integer.toString(min));
    	 String queueName = maxWeightEntity.getCoursename();
    	
    	 return queueName;
	}

	
	public void Execute(Socket socket) {
		String action = null;
		String extension = null;
		String agi_callerid = null;
		try {
              os = new PrintStream(socket.getOutputStream());
              reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
              int i=0;
               while(i < 23){
            	   responseLine = reader.readLine();
            	   if (responseLine.startsWith("agi_arg_1")){
            	    	action = responseLine.split(":")[1];
            	   }
            	   if (responseLine.startsWith("agi_extension")){
            		  extension = responseLine.split(":")[1];
            	   } 
            	   if (responseLine.startsWith("agi_callerid")){
            		   agi_callerid = responseLine.split(":")[1];
             	   } 
            	    i++;
               }
             if (action.contains("queue_login"))
             {
            	 client.setAgi_callerid(agi_callerid);
            	 client.setExtension(extension);
            	 client.setServerSocket(socket);
            	 client.doActionQueueShow();
            
            
             }
             else if (action.contains("check_abandon"))
             {
            	 
            	 String pure_agi_callerid = agi_callerid.replaceAll("\\s+","");;
            	
            	 Customers customer = customersRepository.findByNumber(pure_agi_callerid);
            	 
            	 if (customer == null){
	            	 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            	 Calendar cal = Calendar.getInstance();
	            	 String calldate = dateFormat.format(cal.getTime());
	            	 String queueName = null;
	            	 List<Abandon> abandons = abandonRepository.findByCallernumberAndCalldateGreaterThan(pure_agi_callerid, calldate);
	            	 if (abandons.size() == 0){
	            		 queueName = GetQueueWithMinWeight();
	            		 if (queueName != null){
	            			 os.println("SET VARIABLE QUEUE_NAME " + queueName + "\n");
	            			 
	            		 }
	            	 }
	            	 if (abandons.size() > 0){
	            		 queueName = GetQueueWithMaxWeight();
	            		 if (queueName != null){
	            			 os.println("SET VARIABLE QUEUE_NAME " + queueName + "\n");
	            			 abandonRepository.delete(abandons);
	            		 }
	            	 }
            	 }else{
            		 CoursesEntity coursesEntity  = coursesRepository.findById(customer.getCoursesEntity().getId());
            		 String queueName = coursesEntity.getCoursename();
            		 os.println("SET VARIABLE QUEUE_NAME " + queueName + "\n");
            	 }
            	try {
    				socket.close();
    				
    			} catch (IOException e) {
    					
    				logger.error("Can't close socket!");
    			}
             }
             else {
            	 os.println("NOOP\n");
            	 try {
        			  
            		 socket.close();
        				
        		  } catch (IOException e) {
        					
        				logger.error("Can't close socket!");
        		  }
             }
		   } catch (IOException e) {
			  
			   logger.info("Error handling client " +  e);
    
           } 
      }
		
}


