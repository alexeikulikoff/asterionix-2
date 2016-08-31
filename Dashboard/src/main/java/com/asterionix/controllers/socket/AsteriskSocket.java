package com.asterionix.controllers.socket;

import java.net.Socket;

import com.asterionix.exception.SocketExceptionExt;

public interface AsteriskSocket {
	void openSocket() throws SocketExceptionExt;
	void closeSocket() throws SocketExceptionExt;
	Socket getSocket();
}
