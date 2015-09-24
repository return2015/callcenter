package com.returnsoft.callcenter.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.returnsoft.callcenter.dto.UserDto;



@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserDto user;
	
	//TYPES
	private Boolean isAdmin;
	private Boolean isSupervisor;
	private Boolean isAgent;
	
	//PAGES
	private Boolean home;
	private Boolean users;
	private Boolean agentBoard;
	private Boolean agentChangeState;
	private Boolean supervisorBoard;
	
	public void setCurrentPage(String page){
		
		this.home=false;
		this.users=false;
		this.agentBoard=false;
		this.supervisorBoard=false;
		this.agentChangeState=false;
		
		if (page.equals("home")) {
			this.home=true;
		}else if (page.equals("users")){
			this.users=true;
		}else if (page.equals("agentBoard")){
			this.agentBoard=true;
		}else if (page.equals("supervisorBoard")){
			this.supervisorBoard=true;
		}else if (page.equals("agentChangeState")){
			this.agentChangeState=true;
		}
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsSupervisor() {
		return isSupervisor;
	}

	public void setIsSupervisor(Boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public Boolean getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}

	public Boolean getHome() {
		return home;
	}

	public void setHome(Boolean home) {
		this.home = home;
	}

	public Boolean getUsers() {
		return users;
	}

	public void setUsers(Boolean users) {
		this.users = users;
	}

	public Boolean getAgentBoard() {
		return agentBoard;
	}

	public void setAgentBoard(Boolean agentBoard) {
		this.agentBoard = agentBoard;
	}

	public Boolean getAgentChangeState() {
		return agentChangeState;
	}

	public void setAgentChangeState(Boolean agentChangeState) {
		this.agentChangeState = agentChangeState;
	}

	public Boolean getSupervisorBoard() {
		return supervisorBoard;
	}

	public void setSupervisorBoard(Boolean supervisorBoard) {
		this.supervisorBoard = supervisorBoard;
	}
	
	

		
}
