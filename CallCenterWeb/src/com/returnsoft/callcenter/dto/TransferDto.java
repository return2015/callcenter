package com.returnsoft.callcenter.dto;

import java.io.Serializable;

public class TransferDto implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599307814718050497L;

	private Short id;
	
	private String name;
	
	private String number;

	
	
	private CampaignDto campaign;




	

	public Short getId() {
		return id;
	}


	public void setId(Short id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}




	public CampaignDto getCampaign() {
		return campaign;
	}


	public void setCampaign(CampaignDto campaign) {
		this.campaign = campaign;
	}


	

	
	
	
	

}
