package com.asterionix.response;

import java.io.BufferedReader;
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

public class LogOffAction extends AbstractAction implements Action{

	static Logger logger = LoggerFactory.getLogger(LogOffAction.class);

	public LogOffAction(Socket socket) {
		
		super(socket);
		
		action = this;
	}

	@Override
	public String getCommand() {

		return "Action: Logoff\r\n\r\n";
		// return "Action: COMMAND\r\ncommand: sip show peer 7822\r\n\r\n";
	}
}
