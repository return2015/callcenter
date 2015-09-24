package com.returnsoft.callcenter.dto;

import java.io.Serializable;

public class AgentInfoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2496839734550404044L;
	private Integer userId;
	private String username;
	private String userNames;
	
	private String campaignNames;
	private String sessionType;
	private String sessionTypeTime;
	
	private String sessionTime;
	private String availableTime;
	private String breakTime;
	private String pauseTime;
	
	private Integer callNumber;
	private String callQueue;
	private String callWaitingTime;
	private String callHoldTime;
	private String callDurationTime;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getCampaignNames() {
		return campaignNames;
	}
	public void setCampaignNames(String campaignNames) {
		this.campaignNames = campaignNames;
	}
	public String getSessionType() {
		return sessionType;
	}
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	public String getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}
	public String getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}
	public String getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}
	public String getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(String pauseTime) {
		this.pauseTime = pauseTime;
	}
	
	public Integer getCallNumber() {
		return callNumber;
	}
	public void setCallNumber(Integer callNumber) {
		this.callNumber = callNumber;
	}
	public String getCallWaitingTime() {
		return callWaitingTime;
	}
	public void setCallWaitingTime(String callWaitingTime) {
		this.callWaitingTime = callWaitingTime;
	}
	public String getCallHoldTime() {
		return callHoldTime;
	}
	public void setCallHoldTime(String callHoldTime) {
		this.callHoldTime = callHoldTime;
	}
	public String getCallDurationTime() {
		return callDurationTime;
	}
	public void setCallDurationTime(String callDurationTime) {
		this.callDurationTime = callDurationTime;
	}
	public String getSessionTypeTime() {
		return sessionTypeTime;
	}
	public void setSessionTypeTime(String sessionTypeTime) {
		this.sessionTypeTime = sessionTypeTime;
	}
	public String getCallQueue() {
		return callQueue;
	}
	public void setCallQueue(String callQueue) {
		this.callQueue = callQueue;
	}
	
	
	
	

}
