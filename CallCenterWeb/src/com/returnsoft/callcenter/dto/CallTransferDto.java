package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class CallTransferDto implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6080181435225635559L;

	private Integer id;
	
	private Date createdAt;
	
	private TransferDto transfer;
	
	private CallDto call;
	
	
	

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

	public TransferDto getTransfer() {
		return transfer;
	}

	public void setTransfer(TransferDto transfer) {
		this.transfer = transfer;
	}

	public CallDto getCall() {
		return call;
	}

	public void setCall(CallDto call) {
		this.call = call;
	}

	
	
	
}
