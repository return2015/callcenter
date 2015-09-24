package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class CorbaFileNotFoundException extends Exception implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6232689112595115680L;

	public CorbaFileNotFoundException(String arg0){
		super("No se encontr� el archivo corba "+ arg0);
	}
	
	

}
