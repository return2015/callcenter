package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

public class ServerDto implements Serializable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3154663390461987349L;


	private Short id;
	
	
	private String name;
	
	
	private String host;
	
	
	private String username;
	
	
	private String password;
	
	
	private Short port;
	
	
	private Boolean events;
	
	
	private List<CampaignDto> campaigns;
	
	private Future<String> future;
	
	private Boolean isDoneEvent;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public Boolean getEvents() {
		return events;
	}

	public void setEvents(Boolean events) {
		this.events = events;
	}

	public Future<String> getFuture() {
		return future;
	}

	public void setFuture(Future<String> future) {
		this.future = future;
	}

	public Boolean getIsDoneEvent() {
		return isDoneEvent;
	}

	public void setIsDoneEvent(Boolean isDoneEvent) {
		this.isDoneEvent = isDoneEvent;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public Short getPort() {
		return port;
	}

	public void setPort(Short port) {
		this.port = port;
	}

	public List<CampaignDto> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<CampaignDto> campaigns) {
		this.campaigns = campaigns;
	}
	
	
	

}
