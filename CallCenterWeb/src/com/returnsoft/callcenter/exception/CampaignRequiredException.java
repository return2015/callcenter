package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class CampaignRequiredException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3499952321072075974L;
	
	public CampaignRequiredException() {
		super("Debe seleccionar campañas");
	}
	

}
