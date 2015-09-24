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
import com.returnsoft.callcenter.dto.ServerDto;
//import com.returnsoft.callcenter.dto.SessionTypeDto;
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.UserLoggedNotFoundException;
import com.returnsoft.callcenter.exception.UserPermissionNotFoundException;
import com.returnsoft.callcenter.service.remote.AgentService;
import com.returnsoft.callcenter.service.remote.SupervisorService;
import com.returnsoft.callcenter.service.remote.UserService;
import com.returnsoft.callcenter.util.FacesUtil;

@ManagedBean
@ViewScoped
public class SupervisorBoardController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 507787143564280495L;
	
	//private CorbaUtil corbaUtil;
	private FacesUtil facesUtil;
	
	private String username;
	private List<SelectItem> campaigns;
	private String campaignSelected;
	private List<SelectItem> userTypes;
	private List<SelectItem> supervisors;
	private String supervisorSelected;
	
	private List<SelectItem> sessionTypes;
	private String sessionTypeSelected;
	
	private List<SelectItem> servers;
	private String serverSelected;
	
	private List<UserDto> users;
	
	private Integer userId;
	private String name;
	
	private Boolean serverRendered;
	
	@EJB
	private SupervisorService supervisorService;
	
	@EJB
	private UserService userService;
	
	@EJB
	private AgentService agentService;
	
	public SupervisorBoardController() {

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
				
				
				if (sessionBean.getUser().getUserType().equals(UserTypeEnum.ADMIN)) {
					serverRendered=true;
					updateServers();
				}else{
					serverRendered=false;
				}
					
				updateUserTypes();
				updateCampaigns();
				updateSupervisors();
					
					
				sessionBean.setCurrentPage("supervisorBoard");
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("sessionBean", sessionBean);
				
				return null;
			}else{
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
			
			//System.out.println("Ingreso a search");
			
			List<UserTypeEnum> userTypesId=new ArrayList<UserTypeEnum>();
			/*if (userTypeSelected!=null && userTypeSelected.trim().length()>0) {
				userTypesId.add(Integer.parseInt(userTypeSelected));
			}else{*/
				for (SelectItem item : userTypes) {
					userTypesId.add(UserTypeEnum.findById(Short.parseShort(item.getValue().toString())));
				}
			//}
			
			List<Short> campaignsId=new ArrayList<Short>();
			if (campaignSelected!=null && campaignSelected.trim().length()>0) {
				campaignsId.add(Short.parseShort(campaignSelected));
			}else{
				for (SelectItem item : campaigns) {
					campaignsId.add(Short.parseShort(item.getValue().toString()));
				}
			}
			
			int supervisorId=0;
			if (supervisorSelected!=null && supervisorSelected.trim().length()>0) {
				supervisorId = Integer.parseInt(supervisorSelected);
			}
			
			users = supervisorService.findUsers(username, userTypesId, campaignsId, supervisorId);
			
			//System.out.println("cantidad de usuarios: "+users.size());
			
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;
		}
	}
	
	public void updateUserTypes(){
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
				if (serverSelected!=null && serverSelected.trim().length()>0) {
					campaignsDto = userService.findCampaignsByServer(Short.parseShort(serverSelected));
				}
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
	
	public void updateServers(){
		try {
			
			//System.out.println("Ingreso a updateServers");
			
			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			
			List<ServerDto> serversDto = null;
			
			switch (session.getUser().getUserType()) {
				case ADMIN:
					serversDto = userService.getAllServers();
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
	}
	
	public void changeCampaign(){
		try {
			//System.out.println("ingreso a changeCampaign");
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
	
	
	public void logoutUser(Integer userId){
		
		try {
			//System.out.println("ingreso a logoutUser: "+userId);
			agentService.logoutAgent(userId);
			search();
			/*facesUtil.sendConfirmMessage("Se cerr� la sesi�n del usuario # "+userId,
					"");*/
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
		
	}
	
	public void restartUser(Integer userId){
		
		//System.out.println("Ingreso a restartUser: "+userId);
		
		
		
		try {
			
			agentService.restartAgent(userId);
			search();
			/*facesUtil.sendConfirmMessage("Se reinici� la sesi�n del usuario # "+userId,
					"");*/
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
		
		
		
	}
	
	public void beforeChangeState(Integer userId, String names){
		
		//System.out.println("ingreso a beforeChangeState");
		try {
			
			sessionTypes = new ArrayList<SelectItem>();
			
			this.userId=userId;
			this.name = names;
			
			/*if (userId!=null && userId>0) {
				UserDto userFound = corbaUtil.getAgentService().getUser(userId);
				if (userFound!=null && userFound.getId()>0) {
					if (userFound.getCurrentSession()!=null && userFound.getCurrentSession().getCurrentSessionSessionType()!=null) {
						this.userId=userId;
						this.name = userFound.getFirstname()+" "+userFound.getLastname();
						List<Short> campaignsId= new ArrayList<Short>();
						for (SessionCampaignDto sessionCampaignDto : userFound.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()) {
								campaignsId.add(sessionCampaignDto.getCampaign().getId());
						}
						
						if (campaignsId.size()>0) {*/
							
							List<SessionTypeEnum> sessionTypesDto = agentService.findSessionTypes(userId);
							
							if (sessionTypesDto!=null && sessionTypesDto.size()>0) {
								for (SessionTypeEnum sessionTypeDto : sessionTypesDto) {
									SelectItem item = new SelectItem();
									item.setValue(sessionTypeDto.getId());
									item.setLabel(sessionTypeDto.getName());
									sessionTypes.add(item);
								}
							}
						/*}
						
					}
				}
			}*/
			
			
			
		} catch (Exception e) {
			sessionTypes = new ArrayList<SelectItem>();
			userId=null;
			name=null;
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
		
	}
	
	public void changeState(){
		//System.out.println("Ingreso a changeState");
		
		try {
			if (userId!=null && userId>0) {
				if (sessionTypeSelected!=null && sessionTypeSelected.trim().length()>0) {
					agentService.changeSessionType(userId,
							Short.parseShort(sessionTypeSelected));
					facesUtil.sendConfirmMessage("Se cambi� el estado a "+name, "");
					userId=null;
					name=null;
				}else{
					facesUtil.sendErrorMessage("Debe seleccionar un estado", "");	
				}
				
			}else{
				facesUtil.sendErrorMessage("Debe seleccionar un usuario", "");
			}
			
		} catch (Exception e) {
			userId=null;
			name=null;
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<SelectItem> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<SelectItem> campaigns) {
		this.campaigns = campaigns;
	}

	public String getCampaignSelected() {
		return campaignSelected;
	}

	public void setCampaignSelected(String campaignSelected) {
		this.campaignSelected = campaignSelected;
	}

	public List<SelectItem> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<SelectItem> userTypes) {
		this.userTypes = userTypes;
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

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	

	public Integer getUserId() {
		return userId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<SelectItem> getServers() {
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
	}

	public Boolean getServerRendered() {
		return serverRendered;
	}

	public void setServerRendered(Boolean serverRendered) {
		this.serverRendered = serverRendered;
	}
	
	


}