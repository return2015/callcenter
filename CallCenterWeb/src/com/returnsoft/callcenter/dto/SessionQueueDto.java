package com.returnsoft.callcenter.dto;

import java.io.Serializable;

public class SessionQueueDto implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4664760286409965710L;

	private Integer id;

	private QueueDto queue;

	private SessionCampaignDto sessionCampaign;
	
	private String response;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public QueueDto getQueue() {
		return queue;
	}

	public void setQueue(QueueDto queue) {
		this.queue = queue;
	}

	public SessionCampaignDto getSessionCampaign() {
		return sessionCampaign;
	}

	public void setSessionCampaign(SessionCampaignDto sessionCampaign) {
		this.sessionCampaign = sessionCampaign;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	
	
	

}
