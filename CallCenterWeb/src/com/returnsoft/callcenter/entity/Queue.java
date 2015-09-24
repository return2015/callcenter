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


/**
 * The persistent class for the queue database table.
 * 
 */
@Entity
@Table(name="queue")
public class Queue  implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2307445613170406385L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="que_id")
	private Short id;
	

	@Column(name="que_name")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="que_cmp_id")
	private Campaign campaign;
	

	public Queue() {
	}

	public Short getId() {
		return this.id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	
	

	

}