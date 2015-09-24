package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class UserSessionTypeNotFoundException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7701586568398593409L;
	
	public UserSessionTypeNotFoundException(){
		super();
	}
	
	public String getMessage(){
		return "El usuario no tiene tipo de sesión inicial.";
	}

}
