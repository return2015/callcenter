package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the event database table.
 * 
 */
@Entity
@Table(name = "session")
public class Session implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8479246856214068524L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ses_id")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ses_ended_at")
	private Date endedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ses_started_at")
	private Date startedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ses_usr_id")
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ses_sessestyp_id")
	private SessionSessionType currentSessionSessionType;

	@OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
	private List<SessionSessionType> sessionsSessionType;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEndedAt() {
		return this.endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}

	public Date getStartedAt() {
		return this.startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public List<SessionSessionType> getSessionsSessionType() {
		return sessionsSessionType;
	}

	public void setSessionsSessionType(
			List<SessionSessionType> sessionsSessionType) {
		this.sessionsSessionType = sessionsSessionType;
	}

	public SessionSessionType getCurrentSessionSessionType() {
		return currentSessionSessionType;
	}

	public void setCurrentSessionSessionType(
			SessionSessionType currentSessionSessionType) {
		this.currentSessionSessionType = currentSessionSessionType;
	}

	
}