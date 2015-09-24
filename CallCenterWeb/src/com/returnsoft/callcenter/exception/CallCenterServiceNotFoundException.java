package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class CallCenterServiceNotFoundException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7730832482399188604L;
	
	public CallCenterServiceNotFoundException(){
		super("No se encontró el servicio CallCenterService");
	}

}
