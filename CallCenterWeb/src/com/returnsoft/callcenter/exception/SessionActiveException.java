package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class SessionActiveException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1555241008097030582L;
	
	public SessionActiveException(String host, String usuario) {
		super("Ya existe una sesión activa en el host "+host+" con el usuario "+usuario);
	}

}
