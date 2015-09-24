package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

@Entity
@Table(name="call_transfer")
public class CallTransfer  implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4370222066474735626L;

	@Id
	@Column(name="clltrs_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="clltrs_created_at")
	private Date createdAt;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "clltrs_trs_id")
	private Transfer transfer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "clltrs_cll_id")
	private Call call;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}

	
	
}
