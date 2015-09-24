package com.returnsoft.callcenter.entity;

import java.io.Serializable;

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

import com.returnsoft.callcenter.converter.SessionTypeConverter;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;

@Entity
@Table(name = "campaign_session_type")
public class CampaignSessionType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3013107501884017248L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cmpsestyp_id")
	private Short id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cmpsestyp_cmp_id")
	private Campaign campaign;
	
	@Column(name="cmpsestyp_sestyp_id")
	@Convert(converter=SessionTypeConverter.class)
	private SessionTypeEnum sessionType;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public SessionTypeEnum getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
	}
	
	

}
