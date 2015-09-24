package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class ServerNotFoundException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3056860130945763390L;
	
	public ServerNotFoundException() {
		super("No se encontró el servidor");
	}

}
