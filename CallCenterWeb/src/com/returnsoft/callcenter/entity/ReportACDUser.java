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
@Table(name="report_acd_user")
public class ReportACDUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2045895081605195861L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="repacdusr_id")
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "repacdusr_period")
	private Date period;
	@Column(name = "repacdusr_interval")
	private Short interval;
	@Column(name = "repacdusr_user_id")
	private Integer userId;
	@Column(name = "repacdusr_names")
	private String names;
	@Column(name = "repacdusr_username")
	private String username;
	
	@Column(name = "repacdusr_sessions_time")
	private Integer sessionsTime;
	@Column(name = "repacdusr_sessions_pause_time")
	private Integer sessionsPauseTime;
	@Column(name = "repacdusr_sessions_break_time")
	private Integer sessionsBreakTime;
	@Column(name = "repacdusr_sessions_available_time")
	private Integer sessionsAvailableTime;
	
	@Column(name = "repacdusr_calls_complete")
	private Integer callsComplete;
	@Column(name = "repacdusr_calls_duration_time")
	private Integer callsDurationTime;
	@Column(name = "repacdusr_calls_waiting_time")
	private Integer callsWaitingTime;
	@Column(name = "repacdusr_calls_conversation_time")
	private Integer callsConversationTime;
	@Column(name = "repacdusr_calls_hold_time")
	private Integer callsHoldTime;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getCallsComplete() {
		return callsComplete;
	}
	public void setCallsComplete(Integer callsComplete) {
		this.callsComplete = callsComplete;
	}
	public Integer getSessionsTime() {
		return sessionsTime;
	}
	public void setSessionsTime(Integer sessionsTime) {
		this.sessionsTime = sessionsTime;
	}
	
	public Integer getSessionsPauseTime() {
		return sessionsPauseTime;
	}
	public void setSessionsPauseTime(Integer sessionsPauseTime) {
		this.sessionsPauseTime = sessionsPauseTime;
	}
	public Integer getSessionsBreakTime() {
		return sessionsBreakTime;
	}
	public void setSessionsBreakTime(Integer sessionsBreakTime) {
		this.sessionsBreakTime = sessionsBreakTime;
	}
	public Integer getSessionsAvailableTime() {
		return sessionsAvailableTime;
	}
	public void setSessionsAvailableTime(Integer sessionsAvailableTime) {
		this.sessionsAvailableTime = sessionsAvailableTime;
	}
	public Integer getCallsDurationTime() {
		return callsDurationTime;
	}
	public void setCallsDurationTime(Integer callsDurationTime) {
		this.callsDurationTime = callsDurationTime;
	}
	public Integer getCallsWaitingTime() {
		return callsWaitingTime;
	}
	public void setCallsWaitingTime(Integer callsWaitingTime) {
		this.callsWaitingTime = callsWaitingTime;
	}
	public Integer getCallsConversationTime() {
		return callsConversationTime;
	}
	public void setCallsConversationTime(Integer callsConversationTime) {
		this.callsConversationTime = callsConversationTime;
	}
	public Integer getCallsHoldTime() {
		return callsHoldTime;
	}
	public void setCallsHoldTime(Integer callsHoldTime) {
		this.callsHoldTime = callsHoldTime;
	}
	
	
	

	

}
