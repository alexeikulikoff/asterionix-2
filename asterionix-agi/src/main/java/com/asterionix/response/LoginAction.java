package com.asterionix.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginAction extends AbstractAction implements Action{
	
	static Logger logger = LoggerFactory.getLogger(LoginAction.class);

	public LoginAction(Socket socket,String user, String password) {
	
		super(socket);
	
		action = this;
		
		this.user = user;
		
		this.password = password;
		
	}
	private String user;
	private String password;
	
	@Override
	public String getCommand() {
		
		 return "Action: Login\r\nUsername: " + user + "\r\nSecret: " + password + "\r\n\r\n";
	}

}
