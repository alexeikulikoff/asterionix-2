package com.asterionix.agi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.asterionix.config.Conf;
import com.asterionix.config.ConfigBean;

@Component("Configs")
public class Configs implements Conf {
	
	static Logger logger = LoggerFactory.getLogger(Configs.class);
	
	public ConfigBean getConfig(){
		Properties prop = new Properties();
    	InputStream input = null;
     	ConfigBean configBean = new ConfigBean();
    	try {
			input = new FileInputStream("/etc/asterionix/agiserver.conf");
	
			try {
				prop.load(input);
				
				configBean.setAsterisk_host(prop.getProperty("asterisk_host"));
				configBean.setAsterisk_port(prop.getProperty("asterisk_port"));
				configBean.setUsername(prop.getProperty("username"));
				configBean.setPassword(prop.getProperty("password"));
				
				configBean.setAgi_server_host(prop.getProperty("agi_server_host"));
				configBean.setAgi_server_port(prop.getProperty("agi_server_port"));
				configBean.setAgi_server_backlog(prop.getProperty("agi_server_backlog"));
			
				configBean.setLicense(prop.getProperty("license"));
			
			} catch (IOException e) {
			
				logger.error("Agi Server error! Can't read config ");
			}
       
    	} catch (FileNotFoundException e) {
		
    		logger.error("Agi Server error! Can't read config ");
		}
    	return configBean;
    }
}
