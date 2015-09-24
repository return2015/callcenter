package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class AmiException extends Exception implements Serializable{
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 756166427958797122L;

	public AmiException(Throwable arg0) {
		super("Error al consultar AMI",arg0);
	}
	

}
