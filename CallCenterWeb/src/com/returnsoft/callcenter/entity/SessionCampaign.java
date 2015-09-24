package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "session_campaign")
public class SessionCampaign implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8648119677249812465L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sescmp_id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sescmp_cmp_id")
	private Campaign campaign;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sescmp_sessestyp_id")
	private SessionSessionType sessionSessionType;

	@OneToMany(mappedBy = "sessionCampaign", fetch = FetchType.LAZY)
	private List<SessionQueue> sessionsQueue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}


	public List<SessionQueue> getSessionsQueue() {
		return sessionsQueue;
	}

	public void setSessionsQueue(List<SessionQueue> sessionsQueue) {
		this.sessionsQueue = sessionsQueue;
	}

	public SessionSessionType getSessionSessionType() {
		return sessionSessionType;
	}

	public void setSessionSessionType(SessionSessionType sessionSessionType) {
		this.sessionSessionType = sessionSessionType;
	}
	
	

}
