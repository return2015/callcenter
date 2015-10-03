package com.returnsoft.callcenter.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.returnsoft.callcenter.dto.AgentInfoDto;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.UserLoggedNotFoundException;
import com.returnsoft.callcenter.exception.UserPermissionNotFoundException;
import com.returnsoft.callcenter.service.AgentService;
import com.returnsoft.callcenter.util.FacesUtil;
@ManagedBean
@ViewScoped
public class AgentBoardController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1058874127936311379L;
	
	//private CorbaUtil corbaUtil;
	private FacesUtil facesUtil;
	
	//private UserDto user;
	//private List<SessionTypeDurationDto> sessionTypesDuration;
	@EJB
	private AgentService agentService;
	
	private AgentInfoDto agentInfo;
	
	public AgentBoardController(){
		//corbaUtil = new CorbaUtil();
		facesUtil = new FacesUtil();
	}
	
	public String initialize(){
		//System.out.println("ingreso a initialize");
		
		try {
			
			SessionBean sessionBean = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			
			if (sessionBean!=null && sessionBean.getUser()!=null && sessionBean.getUser().getId()>0) {
				
				if (!sessionBean.getUser().getUserType().equals(UserTypeEnum.AGENT)) {
					throw new UserPermissionNotFoundException();
				}
					
					updateAgentInfo();
					//updateSessionTypesDuration();
					
					sessionBean.setCurrentPage("agentBoard");
					FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().put("sessionBean", sessionBean);
					
					return null;
				
			} else{
				throw new UserLoggedNotFoundException();
			}
			
		} catch (UserLoggedNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (UserPermissionNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return "login.xhtml?faces-redirect=true";	
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;
		}
		
		
	}
	
	public void updateAgentInfo() {
		//System.out.println("Ingreso a updateUserInfo");
		try {

			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");

			int userId = session.getUser().getId();

			agentInfo = agentService.getAgentInfo(userId);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}
	
/*public void updateSessionTypesDuration(){
		
		try {
			this.sessionTypesDuration = new ArrayList<SessionTypeDurationDto>();
			
			for (SessionSessionTypeDto sessionSessionType : this.user.getCurrentSession().getSessionsSessionType()) {
				boolean found=false;
				for (int i=0; i<this.sessionTypesDuration.size();i++) {
					SessionTypeDurationDto sessionTypeDurationDto = sessionTypesDuration.get(i);
					if (sessionSessionType.getSessionType().getId().equals(sessionTypeDurationDto.getSessionType().getId())) {
						long before = sessionTypeDurationDto.getMiliseconds();
						long after = 0;
						if (sessionSessionType.getEndedAt()==null) {
							after = before + (new Date()).getTime() - sessionSessionType.getStartedAt().getTime();
						}else{
							after = before + sessionSessionType.getEndedAt().getTime() - sessionSessionType.getStartedAt().getTime();
						}
						sessionTypeDurationDto.setMiliseconds(after);
						found=true;
						sessionTypesDuration.set(i, sessionTypeDurationDto);
					}
				}
				if (!found) {
					long actual=0;
					if (sessionSessionType.getEndedAt()==null) {
						actual = (new Date()).getTime() - sessionSessionType.getStartedAt().getTime();
					}else{
						actual = sessionSessionType.getEndedAt().getTime() - sessionSessionType.getStartedAt().getTime();
					}
					SessionTypeDurationDto newSessionTypeDuration = new SessionTypeDurationDto();
					newSessionTypeDuration.setMiliseconds(actual);
					newSessionTypeDuration.setSessionType(sessionSessionType.getSessionType());
					sessionTypesDuration.add(newSessionTypeDuration);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
		
	}*/

public AgentInfoDto getAgentInfo() {
	return agentInfo;
}

public void setAgentInfo(AgentInfoDto agentInfo) {
	this.agentInfo = agentInfo;
}


	/*public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<SessionTypeDurationDto> getSessionTypesDuration() {
		return sessionTypesDuration;
	}

	public void setSessionTypesDuration(
			List<SessionTypeDurationDto> sessionTypesDuration) {
		this.sessionTypesDuration = sessionTypesDuration;
	}*/
	
	
	

}
