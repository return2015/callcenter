package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.returnsoft.callcenter.converter.CallEventTypeConverter;
import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;

@Entity
@Table(name = "call_event")
public class CallEvent implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1050183777853179438L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clleve_id")
	private Long id;
	
	//JOIN 
	@Column(name = "clleve_uniqueid")
	private Double uniqueid;
	
	@Column(name="clleve_callerid")
	private String callerID;
	
	@Column(name = "clleve_queue")
	private String queue;
	
	/////CONNECT
	@Column(name = "clleve_peer")
	private String peer;
	
	@Column(name = "clleve_waittime")
	private Short waittime;
	
	@Column(name = "clleve_ringtime")
	private Short ringtime;
	
	////////COMPLETE
	@Column(name="clleve_talktime")
	private Short talktime;
	
	@Column(name="clleve_event_type")
	@Convert(converter=CallEventTypeConverter.class)
	private CallEventTypeEnum callEventType;
	
	@Column(name = "clleve_srv_id")
	private Short serverId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "clleve_created_at")
	private Date createdAt;

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

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
	
}
