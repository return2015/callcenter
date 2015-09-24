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
@Table(name="transfer")
public class Transfer implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2645164416510813007L;

	@Id
	@Column(name="trs_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;
		
	@Column(name="trs_name")
	private String name;
	
	
	@Column(name="trs_number")
	private String number;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="trs_cmp_id")
	private Campaign campaign;


	public Short getId() {
		return id;
	}


	public void setId(Short id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}



	public Campaign getCampaign() {
		return campaign;
	}


	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}




	
	
	
	

}
