package com.returnsoft.callcenter.dto;

import java.io.Serializable;

import com.returnsoft.callcenter.enumeration.SessionTypeEnum;

public class CampaignSessionTypeDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7876919288229685593L;

	private Short id;
	
	private CampaignDto campaign;
	
	private SessionTypeEnum sessionType;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public CampaignDto getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignDto campaign) {
		this.campaign = campaign;
	}

	public SessionTypeEnum getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
	}


}
