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
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.UserLoggedNotFoundException;
import com.returnsoft.callcenter.exception.UserNotFoundException;
import com.returnsoft.callcenter.exception.UserPermissionNotFoundException;
import com.returnsoft.callcenter.service.remote.UserService;
import com.returnsoft.callcenter.util.FacesUtil;
@ManagedBean
@ViewScoped
public class UserEditController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3271167018694164522L;
	
	//private CorbaUtil corbaUtil;
	private FacesUtil facesUtil;
	
	private String userId;
	
	private UserDto user;
	
	private List<SelectItem> campaigns;
	private List<String> campaignsSelected;
	
	private List<SelectItem> userTypes;
	private String userTypeSelected;
	private List<SelectItem> supervisors;
	private String supervisorSelected;
	
	private List<SelectItem> sessionTypes;
	private String sessionTypeSelected;
	
	private List<SelectItem> servers;
	private String serverSelected;
	
	private Boolean serverRendered;
	private Boolean campaignRendered;
	private Boolean sessionTypeRendered;
	private Boolean supervisorRendered;
	
	@EJB
	private UserService userService;
	
	public UserEditController() {

		//System.out.println("Ingreso a UserEditController");
		//corbaUtil = new CorbaUtil();
		facesUtil = new FacesUtil();
		user = new UserDto();

	}
	
	private String validateAccess(){
		
		try {
			
			SessionBean sessionBean = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			
			if (sessionBean!=null && sessionBean.getUser()!=null && sessionBean.getUser().getId()>0) {
				if (!sessionBean.getUser().getUserType().equals(UserTypeEnum.ADMIN)
						&& !sessionBean.getUser().getUserType().equals(UserTypeEnum.SUPERVISOR)) {
					throw new UserPermissionNotFoundException();
				}
					
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
			return "error";
		}
	}
	
	public String initialize() {
		
		//System.out.println("Ingreso a initialize");
		
		String access = validateAccess();
		
		if(access==null){
		
			try {
				
				SessionBean session = (SessionBean) FacesContext
						.getCurrentInstance().getExternalContext().getSessionMap()
						.get("sessionBean");
				
				session.setCurrentPage("users");
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("sessionBean", session);
					
				//OBTIENE USUARIO
				int userIdRequest=0;
				if (userId!=null && userId.trim().length()>0) {
					userIdRequest = Integer.parseInt(userId);
					
				}else{
					throw new UserNotFoundException();
				}
				
				/////////////
				
				reset();
				
				this.user = userService.findUserById(userIdRequest);
				
				//////////////////////////////////////////////////////
				
				// ACTUALIZA TIPO DE USUARIO
				updateUserTypes();
				userTypeSelected = this.user.getUserType().getId()+"";

				switch (this.user.getUserType()) {
				case ADMIN:
					//NO DEBE MOSTRAR SERVIDOR
					//MUESTRA TODAS LAS CAMPAÑAS DE SUPERVISOR. SI ES ADMIN SON TODAS.
					campaignRendered=true;
					//campaigns = new ArrayList<SelectItem>();
					if (session.getIsAdmin()!=null && session.getIsAdmin()) {
						List<CampaignDto> campaignsDto = userService.getAllCampaigns();
						if (campaignsDto!=null && campaignsDto.size()>0) {
							for (CampaignDto campaignDto : campaignsDto) {
								SelectItem item = new SelectItem();
								item.setValue(campaignDto.getId());
								item.setLabel(campaignDto.getName());
								campaigns.add(item);
							}
						}
					}else{
						List<CampaignDto> campaignsDto = userService.findCampaignsByUser(session.getUser().getId());
						if (campaignsDto!=null && campaignsDto.size()>0) {
							for (CampaignDto campaignDto : campaignsDto) {
								SelectItem item = new SelectItem();
								item.setValue(campaignDto.getId());
								item.setLabel(campaignDto.getName());
								campaigns.add(item);
							}
						}
						
					}
					campaignsSelected = new ArrayList<String>();
					if (this.user.getCampaigns()!=null && this.user.getCampaigns().size()>0) {
						for (CampaignDto campaignDto : this.user.getCampaigns()) {
							campaignsSelected.add(campaignDto.getId().toString());
						}
					}
					//NO DEBE MOSTRAR TIPO DE SESION
					//NO DEBE MOSTRAR SUPERVISOR
					break;
				case SUPERVISOR:
					//NO DEBE MOSTRAR SERVIDOR
					//MUESTRA TODAS LAS CAMPAÑAS DE SUPERVISOR. SI ES ADMIN SON TODAS.
					campaignRendered=true;
					//campaigns = new ArrayList<SelectItem>();
					if (session.getIsAdmin()!=null && session.getIsAdmin()) {
						List<CampaignDto> campaignsDto = userService.getAllCampaigns();
						if (campaignsDto!=null && campaignsDto.size()>0) {
							for (CampaignDto campaignDto : campaignsDto) {
								SelectItem item = new SelectItem();
								item.setValue(campaignDto.getId());
								item.setLabel(campaignDto.getName());
								campaigns.add(item);
							}
						}
					}else{
						List<CampaignDto> campaignsDto = userService.findCampaignsByUser(session.getUser().getId());
						if (campaignsDto!=null && campaignsDto.size()>0) {
							for (CampaignDto campaignDto : campaignsDto) {
								SelectItem item = new SelectItem();
								item.setValue(campaignDto.getId());
								item.setLabel(campaignDto.getName());
								campaigns.add(item);
							}
						}
						
					}
					campaignsSelected = new ArrayList<String>();
					if (this.user.getCampaigns()!=null && this.user.getCampaigns().size()>0) {
						for (CampaignDto campaignDto : this.user.getCampaigns()) {
							campaignsSelected.add(campaignDto.getId().toString());
						}
					}
					//NO DEBE MOSTRAR TIPO DE SESION
					//NO DEBE MOSTRAR SUPERVISOR
					break;
				case AGENT:
					//SI DEBE MOSTRAR SERVIDOR. MUESTRA EL SERVIDOR DE LAS CAMPAÑAS DE SUPERVISOR. SI ES ADMIN TODAS
					serverRendered=true;
					//servers = new ArrayList<SelectItem>();
					//
					if (session.getIsAdmin()!=null && session.getIsAdmin()) {
						//
						List<ServerDto> serversDto = userService.getAllServers();
						if (serversDto!=null && serversDto.size()>0) {
							for (ServerDto serverDto : serversDto) {
								SelectItem item = new SelectItem();
								item.setValue(serverDto.getId());
								item.setLabel(serverDto.getName());
								servers.add(item);
							}
						}
						//
						List<Short> campaignsSelectedId = new ArrayList<Short>();
						if (this.user.getCampaigns()!=null && this.user.getCampaigns().size()>0) {
							for (CampaignDto campaignDto : this.user.getCampaigns()) {
								campaignsSelectedId.add(campaignDto.getId());
							}
						}
						List<ServerDto> serversSelectedDto = userService.findServersByCampaigns(campaignsSelectedId);
						if (serversSelectedDto!=null && serversSelectedDto.size()>0) {
							serverSelected = serversSelectedDto.get(0).getId().toString();
						}
						
						
						
					}else{
						//
						List<CampaignDto> campaignsDto = userService.getAllCampaigns();
						List<Short> campaignsId = new ArrayList<Short>();
						if (campaignsDto!=null && campaignsDto.size()>0) {
							for (CampaignDto campaignDto : campaignsDto) {
								campaignsId.add(campaignDto.getId());
							}
						}
						List<ServerDto> serversDto = userService.findServersByCampaigns(campaignsId);
						if (serversDto!=null && serversDto.size()>0) {
							for (ServerDto serverDto : serversDto) {
								SelectItem item = new SelectItem();
								item.setValue(serverDto.getId());
								item.setLabel(serverDto.getName());
								servers.add(item);
							}
						}
						//
						List<Short> campaignsSelectedId = new ArrayList<Short>();
						if (this.user.getCampaigns()!=null && this.user.getCampaigns().size()>0) {
							for (CampaignDto campaignDto : this.user.getCampaigns()) {
								campaignsSelectedId.add(campaignDto.getId());
							}
						}
						List<ServerDto> serversSelectedDto = userService.findServersByCampaigns(campaignsSelectedId);
						if (serversSelectedDto!=null && serversSelectedDto.size()>0) {
							serverSelected = serversSelectedDto.get(0).getId().toString();
						}
						
					}
					
					changeServer();
					
					//MUESTRA CAMPAÑAS VACIAS. SE LLENA CUANDO SELECCIONE SERVER.
					campaignRendered=true;
					//
					campaignsSelected = new ArrayList<String>();
					if (this.user.getCampaigns()!=null && this.user.getCampaigns().size()>0) {
						for (CampaignDto campaignDto : this.user.getCampaigns()) {
							//System.out.println(campaignDto.getId());
							campaignsSelected.add(campaignDto.getId().toString());
						}
					}
					
					sessionTypeRendered=true;
					supervisorRendered=true;
					changeCampaign();
					
					//campaigns = new ArrayList<SelectItem>();
					//MUESTRA TIPO DE SESION VACIO. SE LLENA AL SELECCIONAR CAMPAÑA.
					//
					if (this.user.getSessionType()!=null && this.user.getSessionType().getId()>0) {
						sessionTypeSelected = this.user.getSessionType().getId().toString();
					}
					//sessionTypes = new ArrayList<SelectItem>();
					//MUESTRA SUPERVISOR VACIO. SE LLENA AL SELECCIONAR CAMPAÑA.
					//
					if (this.user.getSupervisor()!=null && this.user.getSupervisor().getId()>0) {
						//System.out.println("el supervisor no es nulo");
						supervisorSelected = this.user.getSupervisor().getId().toString();
					}
					//supervisors = new ArrayList<SelectItem>();
					break;	

				default:
					break;
				}
				
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
						e.getMessage());
				return null;
			}
		}else{
			if (access.equals("error")) {
				return null;
			}
			return access;
		}
	}
	
		
	
	
	
	
	public void changeUserType(){
		String access = validateAccess();
		
		if(access==null){
		
			try {
					
				reset();
					
				SessionBean session = (SessionBean) FacesContext
						.getCurrentInstance().getExternalContext().getSessionMap()
						.get("sessionBean");
				
				if (userTypeSelected!=null && userTypeSelected.trim().length()>0) {
					UserTypeEnum userTypeEnum = UserTypeEnum.findById(Short.parseShort(userTypeSelected));
					switch (userTypeEnum) {
					case ADMIN:
						//NO DEBE MOSTRAR SERVIDOR
						//MUESTRA TODAS LAS CAMPAÑAS DE SUPERVISOR. SI ES ADMIN SON TODAS.
						campaignRendered=true;
						//campaigns = new ArrayList<SelectItem>();
						if (session.getIsAdmin()!=null && session.getIsAdmin()) {
							List<CampaignDto> campaignsDto = userService.getAllCampaigns();
							if (campaignsDto!=null && campaignsDto.size()>0) {
								for (CampaignDto campaignDto : campaignsDto) {
									SelectItem item = new SelectItem();
									item.setValue(campaignDto.getId());
									item.setLabel(campaignDto.getName());
									campaigns.add(item);
								}
							}
						}else{
							List<CampaignDto> campaignsDto = userService.findCampaignsByUser(session.getUser().getId());
							if (campaignsDto!=null && campaignsDto.size()>0) {
								for (CampaignDto campaignDto : campaignsDto) {
									SelectItem item = new SelectItem();
									item.setValue(campaignDto.getId());
									item.setLabel(campaignDto.getName());
									campaigns.add(item);
								}
							}
							
						}
						//NO DEBE MOSTRAR TIPO DE SESION
						//NO DEBE MOSTRAR SUPERVISOR
						break;
					case SUPERVISOR:
						//NO DEBE MOSTRAR SERVIDOR
						//MUESTRA TODAS LAS CAMPAÑAS DE SUPERVISOR. SI ES ADMIN SON TODAS.
						campaignRendered=true;
						//campaigns = new ArrayList<SelectItem>();
						if (session.getIsAdmin()!=null && session.getIsAdmin()) {
							List<CampaignDto> campaignsDto = userService.getAllCampaigns();
							if (campaignsDto!=null && campaignsDto.size()>0) {
								for (CampaignDto campaignDto : campaignsDto) {
									SelectItem item = new SelectItem();
									item.setValue(campaignDto.getId());
									item.setLabel(campaignDto.getName());
									campaigns.add(item);
								}
							}
						}else{
							List<CampaignDto> campaignsDto = userService.findCampaignsByUser(session.getUser().getId());
							if (campaignsDto!=null && campaignsDto.size()>0) {
								for (CampaignDto campaignDto : campaignsDto) {
									SelectItem item = new SelectItem();
									item.setValue(campaignDto.getId());
									item.setLabel(campaignDto.getName());
									campaigns.add(item);
								}
							}
							
						}
						//NO DEBE MOSTRAR TIPO DE SESION
						//NO DEBE MOSTRAR SUPERVISOR
						
						break;
					case AGENT:
						//SI DEBE MOSTRAR SERVIDOR. MUESTRA EL SERVIDOR DE LAS CAMPAÑAS DE SUPERVISOR. SI ES ADMIN TODAS
						serverRendered=true;
						//servers = new ArrayList<SelectItem>();
						//
						if (session.getIsAdmin()!=null && session.getIsAdmin()) {
							List<ServerDto> serversDto = userService.getAllServers();
							if (serversDto!=null && serversDto.size()>0) {
								for (ServerDto serverDto : serversDto) {
									SelectItem item = new SelectItem();
									item.setValue(serverDto.getId());
									item.setLabel(serverDto.getName());
									servers.add(item);
								}
							}
						}else{
							List<CampaignDto> campaignsDto = userService.getAllCampaigns();
							List<Short> campaignsId = new ArrayList<Short>();
							if (campaignsDto!=null && campaignsDto.size()>0) {
								for (CampaignDto campaignDto : campaignsDto) {
									campaignsId.add(campaignDto.getId());
								}
							}
							List<ServerDto> serversDto = userService.findServersByCampaigns(campaignsId);
							if (serversDto!=null && serversDto.size()>0) {
								for (ServerDto serverDto : serversDto) {
									SelectItem item = new SelectItem();
									item.setValue(serverDto.getId());
									item.setLabel(serverDto.getName());
									servers.add(item);
								}
							}
							
						}
						
						//MUESTRA CAMPAÑAS VACIAS. SE LLENA CUANDO SELECCIONE SERVER.
						campaignRendered=true;
						//campaigns = new ArrayList<SelectItem>();
						//MUESTRA TIPO DE SESION VACIO. SE LLENA AL SELECCIONAR CAMPAÑA.
						sessionTypeRendered=true;
						//sessionTypes = new ArrayList<SelectItem>();
						//MUESTRA SUPERVISOR VACIO. SE LLENA AL SELECCIONAR CAMPAÑA.
						supervisorRendered=true;
						//supervisors = new ArrayList<SelectItem>();
	
						break;
					default:
						break;
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
						e.getMessage());
			}
		}
	}
	
	public void changeServer(){
		
		String access = validateAccess();
		
		if(access==null){
			
			try {
					
				campaignsSelected=null;
				campaigns = new ArrayList<SelectItem>();
				sessionTypeSelected=null;
				sessionTypes = new ArrayList<SelectItem>();
				supervisorSelected=null;
				supervisors= new ArrayList<SelectItem>();
				
				if (serverSelected!=null && serverSelected.length()>0) {
					List<CampaignDto> campaignsDto = userService.findCampaignsByServer(Short.parseShort(serverSelected));
					if (campaignsDto!=null && campaignsDto.size()>0) {
						for (CampaignDto campaignDto : campaignsDto) {
							SelectItem item = new SelectItem();
							item.setValue(campaignDto.getId());
							item.setLabel(campaignDto.getName());
							campaigns.add(item);
						}
					}	
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
						e.getMessage());
			}
		}
	}
	
	
	public void changeCampaign(){
		
		String access = validateAccess();
		
		if(access==null){
			
			try {
				
				supervisorSelected=null;
				supervisors = new ArrayList<SelectItem>();
				sessionTypeSelected=null;
				sessionTypes = new ArrayList<SelectItem>();
				
				if (supervisorRendered) {
					if (campaignsSelected!=null && campaignsSelected.size()>0) {
						List<Short> campaignsId = new ArrayList<Short>();
						for (String campaignId : campaignsSelected) {
							if (campaignId!=null && campaignId.trim().length()>0) {
								campaignsId.add(Short.parseShort(campaignId));
							}
							
						}
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
				}
				if(sessionTypeRendered){
					if (campaignsSelected!=null && campaignsSelected.size()>0) {
						List<Short> campaignsId = new ArrayList<Short>();
						for (String campaignId : campaignsSelected) {
							if (campaignId!=null && campaignId.trim().length()>0) {
								campaignsId.add(Short.parseShort(campaignId));
							}
							
						}
						
						List<SessionTypeEnum> sessionTypesDto = userService.findSessionTypesByCampaigns(campaignsId);
						if (sessionTypesDto!=null && sessionTypesDto.size()>0) {
							for (SessionTypeEnum sessionTypeDto : sessionTypesDto) {
								SelectItem item = new SelectItem();
								item.setValue(sessionTypeDto.getId());
								item.setLabel(sessionTypeDto.getName());
								sessionTypes.add(item);
							}
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
						e.getMessage());
			}
		}
		
	}
	
	public void reset(){
		
		serverRendered=false;
		campaignRendered=false;
		sessionTypeRendered=false;
		supervisorRendered=false;
		
		this.serverSelected=null;
		servers = new ArrayList<SelectItem>();
		this.campaignsSelected=new ArrayList<String>();
		campaigns = new ArrayList<SelectItem>();
		this.sessionTypeSelected=null;
		sessionTypes = new ArrayList<SelectItem>();
		this.supervisorSelected=null;
		supervisors = new ArrayList<SelectItem>();
		
		
	}
	
	/*public void changeServer(){
		try {
			campaignsSelected=null;
			supervisorSelected=null;
			sessionTypeSelected=null;
			updateCampaigns();
			updateSupervisors();
			updateSessionTypes();
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}*/
	
	/*public void updateSessionTypes(){
		
		//System.out.println("ingreso a updateSessionTypes");
		
		try {
			
			sessionTypes = new ArrayList<SelectItem>();
			
			if (campaignsSelected!=null && campaignsSelected.size()>0) {
				List<Short> campaignsId = new ArrayList<Short>();
				for (String campaignId : campaignsSelected) {
					//System.out.println("campaignId:"+campaignId);
					if (campaignId!=null && campaignId.trim().length()>0) {
						campaignsId.add(Short.parseShort(campaignId));
					}
					
				}
				
				List<SessionTypeEnum> sessionTypesDto = corbaUtil.getUserService().findSessionTypesByCampaigns(campaignsId);
				
				if (sessionTypesDto!=null && sessionTypesDto.size()>0) {
					for (SessionTypeEnum sessionTypeDto : sessionTypesDto) {
						SelectItem item = new SelectItem();
						item.setValue(sessionTypeDto.getId());
						item.setLabel(sessionTypeDto.getName());
						sessionTypes.add(item);
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
		}
	}*/
	
	/*public void updateSupervisors(){
		
		//System.out.println("ingreso a updateSupervisors");
		
		try {
			
			supervisors = new ArrayList<SelectItem>();
			
			if (campaignsSelected!=null && campaignsSelected.size()>0) {
				List<Short> campaignsId = new ArrayList<Short>();
				for (String campaignId : campaignsSelected) {
					//System.out.println("campaignId:"+campaignId);
					if (campaignId!=null && campaignId.trim().length()>0) {
						campaignsId.add(Short.parseShort(campaignId));
					}
					
				}
				List<UserDto> supervisorsDto = corbaUtil.getUserService().findSupervisorsByCampaigns(campaignsId);
				
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
	}*/
	
	/*public void updateCampaigns(){
		
		try {
			
			//System.out.println("Ingreso a updateCampaigns");
			
			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");

			List<CampaignDto> campaignsDto = null;
			
			switch (session.getUser().getUserType()) {
			
			case ADMIN:
				//if (serverSelected!=null && serverSelected.trim().length()>0) {
					campaignsDto = corbaUtil.getUserService().getAllCampaigns();
				//}
				break;
				
			case SUPERVISOR:
				campaignsDto = corbaUtil.getUserService().findCampaignsByUser(session.getUser().getId());
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
	}*/
	
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
	
	
	
public String edit(){
	
	String access = validateAccess();
	
	if(access==null){
		
		try {
			
			//System.out.println("Ingreso a edit");
			
			if (userTypeSelected!=null && userTypeSelected.length()>0) {
				
				UserTypeEnum userType = UserTypeEnum.findById(Short.parseShort(userTypeSelected));
				
				user.setUserType(userType);

			}
			
			if (campaignsSelected!=null && campaignsSelected.size()>0) {
				List<CampaignDto> campaigns = new ArrayList<CampaignDto>();
				for (String campaignId : campaignsSelected) {
					if (campaignId!=null && campaignId.trim().length()>0) {
						CampaignDto campaignDto = new CampaignDto();
						campaignDto.setId(Short.parseShort(campaignId));
						campaigns.add(campaignDto);
					}
				}
				user.setCampaigns(campaigns);
			}
			
			if (supervisorSelected!=null && supervisorSelected.length()>0) {
				UserDto supervisor = new UserDto();
				supervisor.setId(Integer.parseInt(supervisorSelected));
				user.setSupervisor(supervisor);
			}
			
			if (sessionTypeSelected!=null && sessionTypeSelected.trim().length()>0) {
				
				SessionTypeEnum sessionType = SessionTypeEnum.findById(Short.parseShort(sessionTypeSelected));
				user.setSessionType(sessionType);
			}
			
			
			userService.editUser(user);
			
			facesUtil.sendConfirmMessage("Se actualizó correctamente el usuario: "
					+user.getUsername(),"");
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;
		}
	} else{
		if (access.equals("error")) {
			return null;
		}
		return access;
	}
	}


	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<SelectItem> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<SelectItem> campaigns) {
		this.campaigns = campaigns;
	}

	public List<String> getCampaignsSelected() {
		return campaignsSelected;
	}

	public void setCampaignsSelected(List<String> campaignsSelected) {
		this.campaignsSelected = campaignsSelected;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Boolean getCampaignRendered() {
		return campaignRendered;
	}

	public void setCampaignRendered(Boolean campaignRendered) {
		this.campaignRendered = campaignRendered;
	}

	public Boolean getSessionTypeRendered() {
		return sessionTypeRendered;
	}

	public void setSessionTypeRendered(Boolean sessionTypeRendered) {
		this.sessionTypeRendered = sessionTypeRendered;
	}

	public Boolean getSupervisorRendered() {
		return supervisorRendered;
	}

	public void setSupervisorRendered(Boolean supervisorRendered) {
		this.supervisorRendered = supervisorRendered;
	}

	
	
	

}
