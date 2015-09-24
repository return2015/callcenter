package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.List;

public class SessionCampaignDto implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6521836922261045645L;

	private Integer id;

	private CampaignDto campaign;

	private SessionSessionTypeDto sessionSessionType;

	private List<SessionQueueDto> sessionsQueue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public CampaignDto getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignDto campaign) {
		this.campaign = campaign;
	}

	

	public SessionSessionTypeDto getSessionSessionType() {
		return sessionSessionType;
	}

	public void setSessionSessionType(SessionSessionTypeDto sessionSessionType) {
		this.sessionSessionType = sessionSessionType;
	}

	public List<SessionQueueDto> getSessionsQueue() {
		return sessionsQueue;
	}

	public void setSessionsQueue(List<SessionQueueDto> sessionsQueue) {
		this.sessionsQueue = sessionsQueue;
	}

}
