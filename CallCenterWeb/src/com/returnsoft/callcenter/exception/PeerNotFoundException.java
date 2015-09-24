package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class PeerNotFoundException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PeerNotFoundException(String host,String serverName) {
		super("No se encontró anexo disponible en el servidor "+serverName+" para el host "+host);
		
	}

	

}
