package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class EaoException extends Exception implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1970006661206471020L;

	public EaoException(Throwable arg0) {
		super("Error al consultar la base de datos",arg0);
	}
	

}
