package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.Date;

import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;

public class CallEventDto implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6321078656003317692L;
	private Long id;
	private Double uniqueid;
	
	private String callerID;
	
	private String queue;
	
	private String peer;
	
	private Short waittime;
	
	private Short ringtime;
	
	private Short talktime;
	
	private CallEventTypeEnum callEventType;
	
	private Short serverId;
	
	private Date createdAt;

	

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(Double uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public String getPeer() {
		return peer;
	}

	public void setPeer(String peer) {
		this.peer = peer;
	}

	public Short getWaittime() {
		return waittime;
	}

	public void setWaittime(Short waittime) {
		this.waittime = waittime;
	}

	public Short getRingtime() {
		return ringtime;
	}

	public void setRingtime(Short ringtime) {
		this.ringtime = ringtime;
	}

	public Short getTalktime() {
		return talktime;
	}

	public void setTalktime(Short talktime) {
		this.talktime = talktime;
	}

	public CallEventTypeEnum getCallEventType() {
		return callEventType;
	}

	public void setCallEventType(CallEventTypeEnum callEventType) {
		this.callEventType = callEventType;
	}

	public Short getServerId() {
		return serverId;
	}

	public void setServerId(Short serverId) {
		this.serverId = serverId;
	}

	
	
	
	
	
	

}
