package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="server")
public class Server implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5851831614995367858L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="srv_id")
	private Short id;
	
	@Column(name="srv_name")
	private String name;
	
	@Column(name="srv_host")
	private String host;
	
	@Column(name="srv_username")
	private String username;
	
	@Column(name="srv_password")
	private String password;
	
	@Column(name="srv_port")
	private Short port;
	
	@Column(name = "srv_events")
	private Boolean events;
	
	@OneToMany(mappedBy = "server",fetch=FetchType.LAZY)
	private List<Campaign> campaigns;

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public Short getPort() {
		return port;
	}

	public void setPort(Short port) {
		this.port = port;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public Boolean getEvents() {
		return events;
	}

	public void setEvents(Boolean events) {
		this.events = events;
	}
	
	
	

}
