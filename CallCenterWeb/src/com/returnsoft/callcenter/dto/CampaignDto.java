package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.List;

public class CampaignDto  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7713681253457680803L;


	private Short id;

	
	private String name;
	
	
	private ServerDto server;
	

	
	private List<QueueDto> queues;

	
	private List<TransferDto> transfers;
	
	
	private List<UserDto> users;
	
	
	private List<CampaignSessionTypeDto> sessionTypes;
	

	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public List<QueueDto> getQueues() {
		return queues;
	}

	public void setQueues(List<QueueDto> queues) {
		this.queues = queues;
	}

	

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	

	public List<TransferDto> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<TransferDto> transfers) {
		this.transfers = transfers;
	}

	public ServerDto getServer() {
		return server;
	}

	public void setServer(ServerDto server) {
		this.server = server;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public List<CampaignSessionTypeDto> getSessionTypes() {
		return sessionTypes;
	}

	public void setSessionTypes(List<CampaignSessionTypeDto> sessionTypes) {
		this.sessionTypes = sessionTypes;
	}

	

	

		

}