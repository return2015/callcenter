package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.Date;

import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;

public class CallDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2926741967552841279L;


	private Long id;
	
	
	private Double uniqueid;
	
	
	private Integer number;
	
	
	private QueueDto queue;
	
	
	///////
	
	
	private Short peer;
	
	private UserDto user;
	
	
	private Short waittime;
	
	
	private Short ringtime;
	
	
	private Short talktime;
	
	
	private Short holdtime;
	
	
	private Date startedAt;
	
	
	private CallEventTypeEnum callState;
	
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Short getPeer() {
		return peer;
	}
	public void setPeer(Short peer) {
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
	public Short getHoldtime() {
		return holdtime;
	}
	public void setHoldtime(Short holdtime) {
		this.holdtime = holdtime;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public CallEventTypeEnum getCallState() {
		return callState;
	}
	public void setCallState(CallEventTypeEnum callState) {
		this.callState = callState;
	}
	public QueueDto getQueue() {
		return queue;
	}
	public void setQueue(QueueDto queue) {
		this.queue = queue;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	
	
	
	

}
