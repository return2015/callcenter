package com.returnsoft.callcenter.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.returnsoft.callcenter.dto.CampaignDto;
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.UserLoggedNotFoundException;
import com.returnsoft.callcenter.exception.UserPermissionNotFoundException;
import com.returnsoft.callcenter.service.UserService;
import com.returnsoft.callcenter.util.FacesUtil;
@ManagedBean
@ViewScoped
public class UserSearchController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7198162159755122000L;
	
	//private CorbaUtil corbaUtil;
	private FacesUtil facesUtil;
	
	private String firstname;
	private String lastname;
	private String username;
	private List<SelectItem> campaigns;
	private String campaignSelected;
	private List<SelectItem> userTypes;
	private String userTypeSelected;
	private List<SelectItem> supervisors;
	private String supervisorSelected;
	
	//private List<SelectItem> servers;
	//private String serverSelected;
	
	private List<UserDto> users;
	
	//private Boolean serverRendered;
	
	@EJB
	private UserService userService;
	
	
	public UserSearchController() {
		
		//System.out.println("Ingreso a UserSearchController");
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
				
				if (!sessionBean.getUser().getUserType().equals(UserTypeEnum.ADMIN)
						&& !sessionBean.getUser().getUserType().equals(UserTypeEnum.SUPERVISOR)) {
					throw new UserPermissionNotFoundException();
				}
				
				/*if (sessionBean.getUser().getUserType().equals(UserTypeEnum.ADMIN)) {
					serverRendered=true;
					updateServers();
				}else{
					serverRendered=false;
				}*/
					
					
				updateUserTypes();
				updateCampaigns();
				updateSupervisors();
				
				
				sessionBean.setCurrentPage("users");
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
	
	
	
	public String search(){
		try {
			
			SessionBean sessionBean = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			
			
			if (sessionBean!=null && sessionBean.getUser()!=null && sessionBean.getUser().getId()>0) {
				
				if (!sessionBean.getUser().getUserType().equals(UserTypeEnum.ADMIN)
						&& !sessionBean.getUser().getUserType().equals(UserTypeEnum.SUPERVISOR)) {
					throw new UserPermissionNotFoundException();
				}
				
				List<UserTypeEnum> userTypesId=new ArrayList<UserTypeEnum>();
				if (userTypeSelected!=null && userTypeSelected.trim().length()>0) {
					userTypesId.add(UserTypeEnum.findById(Short.parseShort(userTypeSelected)));
				}else{
					/*switch (sessionBean.getUser().getUserType()) {
					case SUPERVISOR:*/
						for (SelectItem item : userTypes) {
							userTypesId.add(UserTypeEnum.findById(Short.parseShort(item.getValue().toString())));
						}
						/*break;
					default:
						break;
					}*/
					
				}
				
				List<Short> campaignsId=new ArrayList<Short>();
				if (campaignSelected!=null && campaignSelected.trim().length()>0) {
					campaignsId.add(Short.parseShort(campaignSelected));
				}else{
					switch (sessionBean.getUser().getUserType()) {
					case SUPERVISOR:
						for (SelectItem item : campaigns) {
							campaignsId.add(Short.parseShort(item.getValue().toString()));
						}
						break;
					default:
						break;
					}
					
				}
				
				int supervisorId=0;
				if (supervisorSelected!=null && supervisorSelected.trim().length()>0) {
					supervisorId = Integer.parseInt(supervisorSelected);
				}
				
				users = userService.findUsers(firstname, lastname, username, userTypesId, campaignsId, supervisorId);
				
				
			}
			
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;
		}
	}
	
public void updateUserTypes(){
		
		//System.out.println("Ingreso a updateUserTypes");
		
		userTypes = new ArrayList<SelectItem>();
		
		SessionBean session = (SessionBean) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");
		
		switch (session.getUser().getUserType()) {
		case SUPERVISOR:
			for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
				if (!userTypeEnum.equals(UserTypeEnum.ADMIN)) {
					SelectItem item = new SelectItem();
					item.setValue(userTypeEnum.getId());
					item.setLabel(userTypeEnum.getName());
					userTypes.add(item);	
				}
			}
			break;
		case ADMIN:
			for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
				
				SelectItem item = new SelectItem();
				item.setValue(userTypeEnum.getId());
				item.setLabel(userTypeEnum.getName());
				userTypes.add(item);
			}
			break;	

		default:
			break;
		}
		
		
	}
	
	
	
	
	public void updateSupervisors(){
		
		//System.out.println("ingreso a updateSupervisors");
		
		try {
			
			supervisors = new ArrayList<SelectItem>();
			
			if (campaignSelected!=null && campaignSelected.trim().length()>0) {
				List<Short> campaignsId = new ArrayList<Short>();
				campaignsId.add(Short.parseShort(campaignSelected));
				
				List<UserDto> supervisorsDto = userService.findSupervisorsByCampaigns(campaignsId);
				
				if (supervisorsDto!=null && supervisorsDto.size()>0) {
					for (UserDto supervisorDto : supervisorsDto) {
						SelectItem item = new SelectItem();
						item.setValue(supervisorDto.getId());
						item.setLabel(supervisorDto.getFirstname() + " " + supervisorDto.getLastname());
						supervisors.add(item);
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}
	
	public void updateCampaigns(){
		
		try {
			
			//System.out.println("Ingreso a updateCampaigns");
			
			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");

			List<CampaignDto> campaignsDto = null;
			
			switch (session.getUser().getUserType()) {
			
			case ADMIN:
				//if (serverSelected!=null && serverSelected.trim().length()>0) {
					campaignsDto = userService.getAllCampaigns();
				//}
				break;
				
			case SUPERVISOR:
				campaignsDto = userService.findCampaignsByUser(session.getUser().getId());
				break;
				
			default:
				break;
				
			}
			
			campaigns = new ArrayList<SelectItem>();
			
			if (campaignsDto!=null && campaignsDto.size()>0) {
				for (CampaignDto campaignDto : campaignsDto) {
					SelectItem item = new SelectItem();
					item.setValue(campaignDto.getId());
					item.setLabel(campaignDto.getName());
					campaigns.add(item);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}
	
	/*public void updateServers(){
		try {
			
			//System.out.println("Ingreso a updateServers");
			
			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			
			List<ServerDto> serversDto = null;
			
			switch (session.getUser().getUserType()) {
				case ADMIN:
					serversDto = corbaUtil.getUserService().findServers();
					break;
				default:
					break;
			}
			
			servers = new ArrayList<SelectItem>();
			
			if (serversDto!=null && serversDto.size()>0) {
				for (ServerDto serverDto : serversDto) {
					SelectItem item = new SelectItem();
					item.setValue(serverDto.getId());
					item.setLabel(serverDto.getName());
					servers.add(item);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}*/
	
	public void changeCampaign(){
		try {
			supervisorSelected=null;
			updateSupervisors();
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
		
	}
	
	public void changeServer(){
		try {
			campaignSelected=null;
			supervisorSelected=null;
			updateCampaigns();
			updateSupervisors();
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCampaignSelected() {
		return campaignSelected;
	}

	public void setCampaignSelected(String campaignSelected) {
		this.campaignSelected = campaignSelected;
	}

	public List<SelectItem> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<SelectItem> campaigns) {
		this.campaigns = campaigns;
	}

	public List<SelectItem> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<SelectItem> userTypes) {
		this.userTypes = userTypes;
	}

	public String getUserTypeSelected() {
		return userTypeSelected;
	}

	public void setUserTypeSelected(String userTypeSelected) {
		this.userTypeSelected = userTypeSelected;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public List<SelectItem> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<SelectItem> supervisors) {
		this.supervisors = supervisors;
	}

	public String getSupervisorSelected() {
		return supervisorSelected;
	}

	public void setSupervisorSelected(String supervisorSelected) {
		this.supervisorSelected = supervisorSelected;
	}

	/*public List<SelectItem> getServers() {
		return servers;
	}

	public void setServers(List<SelectItem> servers) {
		this.servers = servers;
	}

	public String getServerSelected() {
		return serverSelected;
	}

	public void setServerSelected(String serverSelected) {
		this.serverSelected = serverSelected;
	}*/

	/*public Boolean getServerRendered() {
		return serverRendered;
	}

	public void setServerRendered(Boolean serverRendered) {
		this.serverRendered = serverRendered;
	}*/

	
	
	

}
