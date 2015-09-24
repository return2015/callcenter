package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class UserDuplicateException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -79500933188697686L;
	
	public UserDuplicateException(String username) {
		super("Es usuario "+username+" ya se encuentra registrado.");
	}

}
