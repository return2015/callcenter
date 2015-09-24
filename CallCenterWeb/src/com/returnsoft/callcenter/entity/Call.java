package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.returnsoft.callcenter.converter.CallEventTypeConverter;
import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;

@Entity
@Table(name = "call_log")
public class Call implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6255653312949865783L;

	@Id
	@Column(name="cll_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="cll_uniqueid")
	private Double uniqueid;
	
	@Column(name="cll_number")
	private Integer number;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cll_que_id")
	private Queue queue;
	
	
	///////
	
	@Column(name="cll_peer")
	private Short peer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cll_usr_id")
	private User user;
	
	@Column(name="cll_waittime")
	private Short waittime;
	
	@Column(name="cll_ringtime")
	private Short ringtime;
	
	@Column(name="cll_talktime")
	private Short talktime;
	
	@Column(name="cll_holdtime")
	private Short holdtime;
	
	/*@Column(name="cll_complete")
	private String complete;*/
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cll_started_at")
	private Date startedAt;
	
	@Column(name = "cll_state")
	@Convert(converter=CallEventTypeConverter.class)
	private CallEventTypeEnum callState;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	

	public Short getPeer() {
		return peer;
	}

	public void setPeer(Short peer) {
		this.peer = peer;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	

	/*public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}*/

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

	public Double getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(Double uniqueid) {
		this.uniqueid = uniqueid;
	}
	
	
	
	

}
