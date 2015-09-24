package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.returnsoft.callcenter.converter.SessionTypeConverter;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;

@Entity
@Table(name="session_session_type")
public class SessionSessionType implements Serializable{
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -399712390512256922L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sessestyp_id")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sessestyp_ended_at")
	private Date endedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sessestyp_started_at")
	private Date startedAt;
	
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="sessestyp_sestyp_id")
	@Column(name="sessestyp_sestyp_id")
	@Convert(converter=SessionTypeConverter.class)
	private SessionTypeEnum sessionType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sessestyp_ses_id")
	private Session session;
	
	@Column(name = "sessestyp_host")
	private String host;
	
	@Column(name = "sessestyp_peer")
	private Short peer;
	
	@OneToMany(mappedBy = "sessionSessionType", fetch = FetchType.LAZY)
	private List<SessionCampaign> sessionsCampaign;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	

	public SessionTypeEnum getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public List<SessionCampaign> getSessionsCampaign() {
		return sessionsCampaign;
	}

	public void setSessionsCampaign(List<SessionCampaign> sessionsCampaign) {
		this.sessionsCampaign = sessionsCampaign;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Short getPeer() {
		return peer;
	}

	public void setPeer(Short peer) {
		this.peer = peer;
	}


	
	

}
