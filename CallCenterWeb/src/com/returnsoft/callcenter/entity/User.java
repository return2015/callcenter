package com.returnsoft.callcenter.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.returnsoft.callcenter.converter.SessionTypeConverter;
import com.returnsoft.callcenter.converter.UserTypeConverter;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;

@Entity
@Table(name = "user")
public class User implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3324575715062955319L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id")
	private Integer id;

	@Column(name = "usr_firstname")
	private String firstname;

	@Column(name = "usr_lastname")
	private String lastname;

	@Column(name = "usr_document_number")
	private String documentNumber;

	
	@Column(name = "usr_username")
	private String username;

	@Column(name = "usr_password")
	private String password;
	
	@Column(name = "usr_type")
	@Convert(converter = UserTypeConverter.class)
	private UserTypeEnum userType;
	
	@Column(name = "usr_is_active")
	private Boolean isActive;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "usr_id_parent")
	private User supervisor;

	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "usr_ses_id")
	private Session currentSession;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "usr_cll_id")
	private Call currentCall;

	// bi-directional many-to-many association to Campaign
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_campaign", joinColumns = { @JoinColumn(name = "usrcmp_usr_id") }, inverseJoinColumns = { @JoinColumn(name = "usrcmp_cmp_id") })
	private List<Campaign> campaigns;
	
	
	@Column(name = "usr_sestyp_id")
	@Convert(converter=SessionTypeConverter.class)
	private SessionTypeEnum sessionType;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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

	public User getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(User supervisor) {
		this.supervisor = supervisor;
	}

	

	
	
	
	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public SessionTypeEnum getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
	}

	public Call getCurrentCall() {
		return currentCall;
	}

	public void setCurrentCall(Call currentCall) {
		this.currentCall = currentCall;
	}

	
	
	
	

}
