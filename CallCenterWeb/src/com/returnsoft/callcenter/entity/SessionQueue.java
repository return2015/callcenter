package com.returnsoft.callcenter.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="session_campaign_queue")
public class SessionQueue implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -169435443154170437L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sescmpque_id")
	private Integer id;
		
	@Column(name="sescmpque_response")
	private String response;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sescmpque_que_id")
	private Queue queue;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sescmpque_sescmp_id")
	private SessionCampaign sessionCampaign;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Queue getQueue() {
		return queue;
	}


	public void setQueue(Queue queue) {
		this.queue = queue;
	}


	public SessionCampaign getSessionCampaign() {
		return sessionCampaign;
	}


	public void setSessionCampaign(SessionCampaign sessionCampaign) {
		this.sessionCampaign = sessionCampaign;
	}


	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
	}
	
	

}
