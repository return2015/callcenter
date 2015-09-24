package com.returnsoft.callcenter.dto;

import java.io.Serializable;


public class QueueDto  implements Serializable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5492242259506524126L;

	private Short id;

	private String name;
	
	private CampaignDto campaign;
	
	
	

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public CampaignDto getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignDto campaign) {
		this.campaign = campaign;
	}

	

}