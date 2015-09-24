package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class UserCampaignNotFoundException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4735804782441037735L;
	
	public UserCampaignNotFoundException(){
		super("El usuario no tiene campañas asignadas");
	}
	
	

}
