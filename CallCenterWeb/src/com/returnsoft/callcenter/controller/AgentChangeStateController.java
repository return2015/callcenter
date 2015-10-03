package com.returnsoft.callcenter.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.UserLoggedNotFoundException;
import com.returnsoft.callcenter.exception.UserPermissionNotFoundException;
import com.returnsoft.callcenter.service.AgentService;
import com.returnsoft.callcenter.util.FacesUtil;
@ManagedBean
@ViewScoped
public class AgentChangeStateController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4269845216625393134L;
	
	//private CorbaUtil corbaUtil;
	private FacesUtil facesUtil;
	
	//private UserDto user;
	//private Integer userId;
	private List<SelectItem> sessionTypes;
	private String sessionTypeSelected;
	
	@EJB
	private AgentService agentService;
	
	public AgentChangeStateController(){
		//corbaUtil = new CorbaUtil();
		facesUtil = new FacesUtil();
	}
	
	public String initialize(){
		System.out.println("ingreso a initialize");
		
		try {
			
			SessionBean sessionBean = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			
			if (sessionBean!=null && sessionBean.getUser()!=null && sessionBean.getUser().getId()>0) {
				
				if (!sessionBean.getUser().getUserType().equals(UserTypeEnum.AGENT)) {
					throw new UserPermissionNotFoundException();
				}

				Integer userId = sessionBean.getUser().getId();
				sessionTypes = new ArrayList<SelectItem>();
				System.out.println("antes del findSessionTypes");
				List<SessionTypeEnum> sessionTypesDto = agentService.findSessionTypes(userId);
				if (sessionTypesDto!=null && sessionTypesDto.size()>0) {
					for (SessionTypeEnum sessionTypeDto : sessionTypesDto) {
						SelectItem item = new SelectItem();
						item.setValue(sessionTypeDto.getId());
						item.setLabel(sessionTypeDto.getName());
						sessionTypes.add(item);
					}
				}	
				System.out.println("despues del findSessionTypes");	
					//updateSessionTypes();
					
					sessionBean.setCurrentPage("agentChangeState");
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
	
	/*public void updateSessionTypes(){
		
		try {
			
			sessionTypes = new ArrayList<SelectItem>();
			
			List<SessionTypeEnum> sessionTypesDto = corbaUtil.getAgentService().findSessionTypes(userId);
			if (sessionTypesDto!=null && sessionTypesDto.size()>0) {
				for (SessionTypeEnum sessionTypeDto : sessionTypesDto) {
					SelectItem item = new SelectItem();
					item.setValue(sessionTypeDto.getId());
					item.setLabel(sessionTypeDto.getName());
					sessionTypes.add(item);
				}
			}	
				
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}*/
	
	/*public void updateUserInfo() {
		//System.out.println("Ingreso a updateUserInfo");
		try {

			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");

			userId = session.getUser().getId();

			//this.user = corbaUtil.getAgentService().getUser(userId);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}*/
	
	public String changeState(){

		try {

			// OBTIENE LA SESSION

			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");

			UserDto user = session.getUser();
			
			if (sessionTypeSelected!=null && sessionTypeSelected.trim().length()>0) {
				agentService.changeSessionType(user.getId(),
						Short.parseShort(sessionTypeSelected));	
			}

			return "agentBoard.xhtml?faces-redirect=true";

			

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;

		}
	}

	public List<SelectItem> getSessionTypes() {
		return sessionTypes;
	}

	public void setSessionTypes(List<SelectItem> sessionTypes) {
		this.sessionTypes = sessionTypes;
	}

	public String getSessionTypeSelected() {
		return sessionTypeSelected;
	}

	public void setSessionTypeSelected(String sessionTypeSelected) {
		this.sessionTypeSelected = sessionTypeSelected;
	}

	

}
