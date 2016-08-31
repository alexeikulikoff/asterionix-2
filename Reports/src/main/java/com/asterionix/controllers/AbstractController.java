package com.asterionix.controllers;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import com.asterionix.controllers.report.IQuery;
import com.asterionix.controllers.report.Report;
import com.asterionix.controllers.report.ReportService;
import com.asterionix.controllers.util.FindBy;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Params;
import com.asterionix.exception.EntitySaveException;


abstract class AbstractController {
	 
	static Logger logger = LoggerFactory.getLogger(AbstractController.class);
	 public AbstractController() {
		
	 }
	@SuppressWarnings("unchecked")
	public <T> void saveEntity(Class<? extends Serializable> entity, CrudRepository<T,Long> repository, Params params) throws EntitySaveException{
		 
		 Class<?> c = entity.getClass();
		 Object t = null;
		 try {
			 Constructor<? extends Serializable> con = entity.getDeclaredConstructor();
			 try {
				t = con.newInstance();
				 String methodName = null;
				 for(Param p : params.getParams()){ 
					 try {
						 methodName = Character.toUpperCase(p.getName().charAt(0)) + p.getName().substring(1); 
						 Method method = entity.getDeclaredMethod("set" + methodName, String.class);
						 try{
							 method.invoke(t, p.getValue());
						 }catch(Exception e){
							 throw new EntitySaveException("Error invoke");
						 }
					 }catch(Exception e){
						 throw new EntitySaveException("Error getDecleared Methods");
					 }
				 }
				 try{
					 repository.save( (T) t);
				 }catch(Exception e){
					 throw new EntitySaveException("Error saving");
				 }
			} catch (Exception e) {
				throw new EntitySaveException("Error new Instance");
			} 
		} catch (Exception e2) {
			throw new EntitySaveException("Error get Constructor");
		}
	 }
	 public Report createReport(Class<? extends ReportService> clazz, IQuery query, CrudRepository<?, Long> repository) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		 	Report report  = null;
		 	ReportService service = null;
		 	Constructor< ? > constructor = null;
		
		 	
		    Map<String, Method>map = new HashMap<String, Method>();
		    
		    Method[] mappedMethds = ReportService.class.getDeclaredMethods();
		    
		    for(Method m: mappedMethds ){
		    	
		    
		    	map.put(m.getName(),m);
		    }
			String methodName = "findBySrcDstDispositionCalldateBetween";
			
			Method[] methods = query.getClass().getDeclaredMethods();
			for(Method method : methods )
			{
				Annotation annotation = method.getAnnotation(FindBy.class);
				if (annotation != null){
					FindBy a = (FindBy)annotation;
					try {
						String value = (String) method.invoke(query);
						if(value.equals("")){
							methodName = methodName.replace(a.name(),"");
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				
				constructor = clazz.getConstructor(new Class[]{CrudRepository.class});
				
			} catch (NoSuchMethodException e) {
				
				e.printStackTrace();
				
			} catch (SecurityException e) {
				
				e.printStackTrace();
			}
			
			try {
				service = (ReportService) constructor.newInstance(repository);
				
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			}
			
			Method mm = map.get(methodName);
			if (mm != null){
				report = (Report) mm.invoke(service,query);
			}
			return report;
			
		 
	 }
	
	 
}
