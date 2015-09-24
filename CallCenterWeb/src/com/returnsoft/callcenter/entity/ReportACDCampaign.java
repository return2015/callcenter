package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="report_acd_campaign")
public class ReportACDCampaign implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8698173006224400406L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="repacdcam_id")
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "repacdcam_period")
	private Date period;
	@Column(name = "repacdcam_interval")
	private Short interval;
	@Column(name = "repacdcam_campaign_id")
	private Short campaignId;
	@Column(name = "repacdcam_campaign_name")
	private String campaignName;
	
	
	@Column(name = "repacdcam_calls_inbound")
	private Integer callsInbound;
	@Column(name = "repacdcam_calls_complete")
	private Integer callsComplete;
	@Column(name = "repacdcam_calls_abandon")
	private Integer callsAbandon;
	@Column(name = "repacdcam_calls_abandon_5")
	private Integer callsAbandon5;
	
	@Column(name = "repacdcam_calls_conversation_time")
	private Integer callsConversationTime;
	@Column(name = "repacdcam_calls_abandon_time")
	private Integer callsAbandonTime;
	
	@Column(name = "repacdcam_calls_waiting_time")
	private Integer callsWaitingTime;
	@Column(name = "repacdcam_calls_waiting_time_20")
	private Integer callsWaitingTime20;
	@Column(name = "repacdcam_calls_waiting_time_24")
	private Integer callsWaitingTime24;
	@Column(name = "repacdcam_calls_waiting_time_30")
	private Integer callsWaitingTime30;
	
	@Column(name = "repacdcam_calls_waiting_20")
	private Integer callsWaiting20;
	@Column(name = "repacdcam_calls_waiting_24")
	private Integer callsWaiting24;
	@Column(name = "repacdcam_calls_waiting_30")
	private Integer callsWaiting30;
	
	@Column(name = "repacdcam_users")
	private Short users;
	@Column(name = "repacdcam_sessions_time")
	private Integer sessionsTime;
	@Column(name = "repacdcam_sessions_available_time")
	private Integer sessionsAvailableTime;
	@Column(name = "repacdcam_sessions_break_time")
	private Integer sessionsBreakTime;
	@Column(name = "repacdcam_sessions_pause_time")
	private Integer sessionsPauseTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getPeriod() {
		return period;
	}
	public void setPeriod(Date period) {
		this.period = period;
	}
	
	
	public Short getInterval() {
		return interval;
	}
	public void setInterval(Short interval) {
		this.interval = interval;
	}
	public void setUsers(Short users) {
		this.users = users;
	}
	public Short getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Short campaignId) {
		this.campaignId = campaignId;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public Integer getCallsInbound() {
		return callsInbound;
	}
	public void setCallsInbound(Integer callsInbound) {
		this.callsInbound = callsInbound;
	}
	public Integer getCallsComplete() {
		return callsComplete;
	}
	public void setCallsComplete(Integer callsComplete) {
		this.callsComplete = callsComplete;
	}
	public Integer getCallsAbandon() {
		return callsAbandon;
	}
	public void setCallsAbandon(Integer callsAbandon) {
		this.callsAbandon = callsAbandon;
	}
	public Integer getCallsAbandon5() {
		return callsAbandon5;
	}
	public void setCallsAbandon5(Integer callsAbandon5) {
		this.callsAbandon5 = callsAbandon5;
	}
	
	public Integer getCallsConversationTime() {
		return callsConversationTime;
	}
	public void setCallsConversationTime(Integer callsConversationTime) {
		this.callsConversationTime = callsConversationTime;
	}
	public Integer getCallsAbandonTime() {
		return callsAbandonTime;
	}
	public void setCallsAbandonTime(Integer callsAbandonTime) {
		this.callsAbandonTime = callsAbandonTime;
	}
	public Integer getCallsWaitingTime() {
		return callsWaitingTime;
	}
	public void setCallsWaitingTime(Integer callsWaitingTime) {
		this.callsWaitingTime = callsWaitingTime;
	}
	public Integer getCallsWaitingTime20() {
		return callsWaitingTime20;
	}
	public void setCallsWaitingTime20(Integer callsWaitingTime20) {
		this.callsWaitingTime20 = callsWaitingTime20;
	}
	public Integer getCallsWaitingTime24() {
		return callsWaitingTime24;
	}
	public void setCallsWaitingTime24(Integer callsWaitingTime24) {
		this.callsWaitingTime24 = callsWaitingTime24;
	}
	public Integer getCallsWaitingTime30() {
		return callsWaitingTime30;
	}
	public void setCallsWaitingTime30(Integer callsWaitingTime30) {
		this.callsWaitingTime30 = callsWaitingTime30;
	}
	public Integer getCallsWaiting20() {
		return callsWaiting20;
	}
	public void setCallsWaiting20(Integer callsWaiting20) {
		this.callsWaiting20 = callsWaiting20;
	}
	public Integer getCallsWaiting24() {
		return callsWaiting24;
	}
	public void setCallsWaiting24(Integer callsWaiting24) {
		this.callsWaiting24 = callsWaiting24;
	}
	public Integer getCallsWaiting30() {
		return callsWaiting30;
	}
	public void setCallsWaiting30(Integer callsWaiting30) {
		this.callsWaiting30 = callsWaiting30;
	}
	
	public Integer getSessionsTime() {
		return sessionsTime;
	}
	public void setSessionsTime(Integer sessionsTime) {
		this.sessionsTime = sessionsTime;
	}
	public Integer getSessionsAvailableTime() {
		return sessionsAvailableTime;
	}
	public void setSessionsAvailableTime(Integer sessionsAvailableTime) {
		this.sessionsAvailableTime = sessionsAvailableTime;
	}
	public Integer getSessionsBreakTime() {
		return sessionsBreakTime;
	}
	public void setSessionsBreakTime(Integer sessionsBreakTime) {
		this.sessionsBreakTime = sessionsBreakTime;
	}
	public Integer getSessionsPauseTime() {
		return sessionsPauseTime;
	}
	public void setSessionsPauseTime(Integer sessionsPauseTime) {
		this.sessionsPauseTime = sessionsPauseTime;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
