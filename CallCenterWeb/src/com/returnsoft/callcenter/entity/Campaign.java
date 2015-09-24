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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the campaign database table.
 * 
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5611840972844988419L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cmp_id")
	private Short id;

	@Column(name = "cmp_name")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cmp_srv_id")
	private Server server;
	

	@OneToMany(mappedBy = "campaign",fetch=FetchType.LAZY)
	private List<Queue> queues;

	@OneToMany(mappedBy = "campaign",fetch=FetchType.LAZY)
	private List<Transfer> transfers;
	
	@ManyToMany(mappedBy = "campaigns",fetch=FetchType.LAZY)
	private List<User> users;
	
	@OneToMany(mappedBy = "campaign",fetch=FetchType.LAZY)
	private List<CampaignSessionType> sessionTypes;
	
	

	public Campaign() {
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

	

	public List<Queue> getQueues() {
		return queues;
	}

	public void setQueues(List<Queue> queues) {
		this.queues = queues;
	}

	public List<Transfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	

	public List<CampaignSessionType> getSessionTypes() {
		return sessionTypes;
	}

	public void setSessionTypes(List<CampaignSessionType> sessionTypes) {
		this.sessionTypes = sessionTypes;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	

	

	

		

}