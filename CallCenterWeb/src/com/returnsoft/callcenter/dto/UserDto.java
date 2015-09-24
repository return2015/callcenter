package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.List;

import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;

public class UserDto implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3405796001201829781L;

	private Integer id;

	private String firstname;

	private String lastname;

	private String documentNumber;

	private String username;

	private String password;
	
	private Boolean isActive;

	private UserDto supervisor;

	private UserTypeEnum userType;

	private List<CampaignDto> campaigns;
	
	//private List<SessionDto> sessions;
	
	private SessionDto currentSession;
	
	private CallDto currentCall;
	
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

	public UserDto getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(UserDto supervisor) {
		this.supervisor = supervisor;
	}


	public List<CampaignDto> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<CampaignDto> campaigns) {
		this.campaigns = campaigns;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	

	public SessionDto getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(SessionDto currentSession) {
		this.currentSession = currentSession;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}
	
	public String getState(){
		if (this.isActive!=null && this.isActive==true) {
			return "Habilitado";
		}else{
			return "Deshabilitado";
		}
	}
	
	public String getCampaignNames(){
		String campaignNames="";
				if (this.campaigns!=null && this.campaigns.size()>0) {
					for (int i = 0; i < this.campaigns.size(); i++) {
						CampaignDto campaignDto = this.campaigns.get(i); 
						if (i==0) {
							campaignNames += campaignDto.getName();	
						}else{
							campaignNames += ", "+campaignDto.getName();
						}
					}
					
				}
		return campaignNames;		
	}

	public SessionTypeEnum getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
	}

	public CallDto getCurrentCall() {
		return currentCall;
	}

	public void setCurrentCall(CallDto currentCall) {
		this.currentCall = currentCall;
	}


	
	
	

}
