package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class ServerDifferentException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -330763505481611949L;
	
	public ServerDifferentException() {
		super("Las campañas tienen diferente servidor");
		
	}

}
