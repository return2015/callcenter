package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class ConversionException extends Exception implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6795918657588874539L;

	public ConversionException(Throwable arg0) {
		super("Error al realizar la conversión DTO",arg0);
	}
	

}
