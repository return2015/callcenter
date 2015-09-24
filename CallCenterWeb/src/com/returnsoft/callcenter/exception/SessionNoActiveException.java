package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class SessionNoActiveException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8385809634310633904L;
	
	public SessionNoActiveException() {
		super("No tiene una sesión activa");
	}

	

}
