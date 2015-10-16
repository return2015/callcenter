package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class CampaignNotFoundException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6291997603808820533L;
	
	public CampaignNotFoundException(Short campaignId) {
		super("No se encontró la campaña con id "+campaignId);
	}
	

}
