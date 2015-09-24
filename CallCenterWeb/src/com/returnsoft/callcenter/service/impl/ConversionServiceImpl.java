package com.returnsoft.callcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.returnsoft.callcenter.dto.CampaignDto;
import com.returnsoft.callcenter.dto.ServerDto;
import com.returnsoft.callcenter.dto.SessionCampaignDto;
import com.returnsoft.callcenter.dto.SessionDto;
import com.returnsoft.callcenter.dto.SessionSessionTypeDto;
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.SessionCampaign;
import com.returnsoft.callcenter.entity.SessionSessionType;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.exception.ConversionException;
import com.returnsoft.callcenter.service.local.ConversionService;

@Stateless
public class ConversionServiceImpl implements ConversionService {
	
	@Override
	public ServerDto fromServerToSelect(Server server) throws ConversionException {

		ServerDto serverDto = null;
		try {
			if (server != null) {
				serverDto = new ServerDto();
				serverDto.setId(server.getId());
				serverDto.setName(server.getName());
			}
			return serverDto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}
	@Override
	public UserDto fromUserToSelect(User user) throws ConversionException {

		UserDto userDto = null;
		try {

			if (user != null) {
				//SE CARGAN LOS DATOS DE USUARIO
				userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setFirstname(user.getFirstname());
				userDto.setLastname(user.getLastname());
			
			}

			return userDto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}
	@Override
	public UserDto fromUserToLogin(User user) throws ConversionException {

		UserDto userDto = null;
		try {

			if (user != null) {
				//SE CARGAN LOS DATOS DE USUARIO
				userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setFirstname(user.getFirstname());
				userDto.setLastname(user.getLastname());
				userDto.setUserType(user.getUserType());
				
				/*for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
					if (user.getType().equals(userTypeEnum.getId())) {
						userDto.setUserType(userTypeEnum);
					}
				}*/
				
			}

			return userDto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}
	
	@Override
	public UserDto fromUserToTable(User user) throws ConversionException {

		UserDto userDto = null;
		try {

			if (user != null) {
				//SE CARGAN LOS DATOS DE USUARIO
				userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setFirstname(user.getFirstname());
				userDto.setLastname(user.getLastname());
				userDto.setUsername(user.getUsername());
				userDto.setDocumentNumber(user.getDocumentNumber());
				userDto.setPassword(user.getPassword());
				userDto.setIsActive(user.getIsActive());
				userDto.setUserType(user.getUserType());
				/*for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
					if (user.getType().equals(userTypeEnum.getId())) {
						userDto.setUserType(userTypeEnum);
					}
				}*/
				if (user.getSupervisor()!=null) {
					//SE CARGA EL SUPERVISOR
					UserDto supervisorDto = new UserDto();
					supervisorDto.setId(user.getSupervisor().getId());
					supervisorDto.setFirstname(user.getSupervisor().getFirstname());
					supervisorDto.setLastname(user.getSupervisor().getLastname());
					userDto.setSupervisor(supervisorDto);
				}
				if (user.getCampaigns()!=null && user.getCampaigns().size()>0) {
					//SE CARGAN CAMPAÑAS
					List<CampaignDto> campaignsDto = new ArrayList<CampaignDto>();
					for (Campaign campaign : user.getCampaigns()) {
						CampaignDto campaignDto = new CampaignDto();
						campaignDto.setId(campaign.getId());
						campaignDto.setName(campaign.getName());
						campaignsDto.add(campaignDto);
						if (campaign.getServer()!=null) {
							//System.out.println("en el convert no es nulo");
							ServerDto serverDto = new ServerDto();
							serverDto.setId(campaign.getServer().getId());
							campaignDto.setServer(serverDto);
						}else{
							//System.out.println("en el convert si es nulo");
						}
					}
					userDto.setCampaigns(campaignsDto);
				}
				if (user.getSessionType()!=null && user.getSessionType().getId()>0) {
					
					SessionTypeEnum sessionTypeDto = SessionTypeEnum.findById(user.getSessionType().getId());
					userDto.setSessionType(sessionTypeDto);
				}
				
			}

			return userDto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}
	@Override
	public UserDto fromUserToDashboard(User user) throws ConversionException {

		UserDto userDto = null;
		try {

			if (user != null) {
				userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setDocumentNumber(user.getDocumentNumber());
				userDto.setFirstname(user.getFirstname());
				userDto.setLastname(user.getLastname());
				userDto.setUsername(user.getUsername());
				userDto.setIsActive(user.getIsActive());
				if (user.getSupervisor()!=null) {
					//SE CARGA EL SUPERVISOR
					UserDto supervisorDto = new UserDto();
					supervisorDto.setId(user.getSupervisor().getId());
					supervisorDto.setFirstname(user.getSupervisor().getFirstname());
					supervisorDto.setLastname(user.getSupervisor().getLastname());
					userDto.setSupervisor(supervisorDto);
				}
				/*if(user.getCampaigns()!=null && user.getCampaigns().size()>0){
					List<CampaignDto> campaignsDto = new ArrayList<CampaignDto>();
					for (Campaign campaign : user.getCampaigns()) {
						CampaignDto campaignDto = new CampaignDto();
						campaignDto.setId(campaign.getId());
						campaignDto.setName(campaign.getName());
						campaignsDto.add(campaignDto);
					}
					userDto.setCampaigns(campaignsDto);
				}*/
				
				if (user.getCurrentSession()!=null) {
					//SE CARGA LA SESION ACTUAL
					SessionDto sessionDto = new SessionDto();
					sessionDto.setId(user.getCurrentSession().getId());
					sessionDto.setStartedAt(user.getCurrentSession().getStartedAt());
					sessionDto.setEndedAt(user.getCurrentSession().getEndedAt());
					
					//SE CARGA EL TIPO DE SESION ACTUAL
					if (user.getCurrentSession().getCurrentSessionSessionType()!=null) {
						SessionSessionTypeDto sessionSessionTypeDto = new SessionSessionTypeDto();
						sessionSessionTypeDto.setId(user.getCurrentSession().getCurrentSessionSessionType().getId());
						sessionSessionTypeDto.setStartedAt(user.getCurrentSession().getCurrentSessionSessionType().getStartedAt());
						sessionSessionTypeDto.setEndedAt(user.getCurrentSession().getCurrentSessionSessionType().getEndedAt());
						sessionSessionTypeDto.setHost(user.getCurrentSession().getCurrentSessionSessionType().getHost());
						sessionSessionTypeDto.setPeer(user.getCurrentSession().getCurrentSessionSessionType().getPeer());
						if (user.getCurrentSession().getCurrentSessionSessionType().getSessionType()!=null) {
							sessionSessionTypeDto.setSessionType(user.getCurrentSession().getCurrentSessionSessionType().getSessionType());
						}
						// SE CARGAN LAS SESIONES POR CAMPAÑA
						if (user.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()!=null 
								&& user.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign().size()>0) {
							List<SessionCampaignDto> sessionsCampaignDto = new ArrayList<SessionCampaignDto>();
							for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()) {
								SessionCampaignDto sessionCampaignDto = new SessionCampaignDto();
								sessionCampaignDto.setId(sessionCampaign.getId());
								if (sessionCampaign.getCampaign()!=null) {
									CampaignDto campaignDto = new CampaignDto();
									campaignDto.setId(sessionCampaign.getCampaign().getId());
									campaignDto.setName(sessionCampaign.getCampaign().getName());
									sessionCampaignDto.setCampaign(campaignDto);
								}
								sessionsCampaignDto.add(sessionCampaignDto);
							}
							sessionSessionTypeDto.setSessionsCampaign(sessionsCampaignDto);
						}
						sessionDto.setCurrentSessionSessionType(sessionSessionTypeDto);
					}
					
					// SE CARGAN TODOS LOS TIPOS DE SESION
					if (user.getCurrentSession().getSessionsSessionType()!=null && user.getCurrentSession().getSessionsSessionType().size()>0) {
						List<SessionSessionTypeDto> sessionSessionTypesDto = new ArrayList<SessionSessionTypeDto>();
						for (SessionSessionType sessionSessionType : user.getCurrentSession().getSessionsSessionType()) {
							SessionSessionTypeDto sessionSessionTypeDto = new SessionSessionTypeDto();
							sessionSessionTypeDto.setId(sessionSessionType.getId());
							sessionSessionTypeDto.setStartedAt(sessionSessionType.getStartedAt());
							sessionSessionTypeDto.setEndedAt(sessionSessionType.getEndedAt());
							sessionSessionTypeDto.setHost(sessionSessionType.getHost());
							sessionSessionTypeDto.setPeer(sessionSessionType.getPeer());
							if (sessionSessionType.getSessionType()!=null) {
								sessionSessionTypeDto.setSessionType(sessionSessionType.getSessionType());	
							}
							sessionSessionTypesDto.add(sessionSessionTypeDto);
						}
						sessionDto.setSessionsSessionType(sessionSessionTypesDto);
					}
					
					userDto.setCurrentSession(sessionDto);
				}
				
			}

			return userDto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}

	
	/*@Override
	public SessionTypeDto fromSessionTypeToSelect(SessionType sessionType)
			throws ConversionException {
		
		try {
			SessionTypeDto sessionTypeDto = null;
			
			if (sessionType != null) {
				sessionTypeDto = new SessionTypeDto();
				sessionTypeDto.setId(sessionType.getId());
				sessionTypeDto.setName(sessionType.getName());
			}

			return sessionTypeDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}*/
	
	@Override
	public CampaignDto fromCampaignToSelect(Campaign campaign)
			throws ConversionException {
		
		try {
			CampaignDto campaignDto = null;
			
			if (campaign != null) {
				campaignDto = new CampaignDto();
				campaignDto.setId(campaign.getId());
				campaignDto.setName(campaign.getName());
				
			}

			return campaignDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}
	@Override
	public User toUser(UserDto userDto)
			throws ConversionException{
		
		try {
			User user = null;
			
			if (userDto!=null) {
				user = new User();
				user.setId(userDto.getId());
				user.setFirstname(userDto.getFirstname());
				user.setLastname(userDto.getLastname());
				user.setDocumentNumber(userDto.getDocumentNumber());
				user.setUsername(userDto.getUsername());
				user.setPassword(userDto.getPassword());
				user.setUserType(userDto.getUserType());
				//user.setType(userDto.getUserType().getId());
				user.setIsActive(userDto.getIsActive());
				if (userDto.getSupervisor()!=null) {
					User supervisor = new User();
					supervisor.setId(userDto.getSupervisor().getId());
					user.setSupervisor(supervisor);
				}
				if (userDto.getCampaigns()!=null && userDto.getCampaigns().size()>0) {
					List<Campaign> campaigns = new ArrayList<Campaign>();
					for (CampaignDto campaignDto : userDto.getCampaigns()) {
						Campaign campaign = new Campaign();
						campaign.setId(campaignDto.getId());
						campaigns.add(campaign);
					}
					user.setCampaigns(campaigns);
				}
				if (userDto.getSessionType()!=null) {
					user.setSessionType(userDto.getSessionType());
				}
			}
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}

}
