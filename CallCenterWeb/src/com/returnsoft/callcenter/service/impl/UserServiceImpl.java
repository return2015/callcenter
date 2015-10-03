package com.returnsoft.callcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.callcenter.dto.CampaignDto;
import com.returnsoft.callcenter.dto.ServerDto;
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.eao.CampaignEao;
import com.returnsoft.callcenter.eao.CampaignSessionTypeEao;
import com.returnsoft.callcenter.eao.ServerEao;
import com.returnsoft.callcenter.eao.SessionCampaignEao;
import com.returnsoft.callcenter.eao.SessionEao;
import com.returnsoft.callcenter.eao.UserEao;
import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.entity.CampaignSessionType;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.Session;
//import com.returnsoft.callcenter.entity.SessionType;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.ServerNotFoundException;
import com.returnsoft.callcenter.exception.ServiceException;
import com.returnsoft.callcenter.exception.UserCampaignNotFoundException;
import com.returnsoft.callcenter.exception.UserDuplicateException;
import com.returnsoft.callcenter.exception.UserInactiveException;
import com.returnsoft.callcenter.exception.UserNotFoundException;
import com.returnsoft.callcenter.exception.UserWrongPasswordException;
import com.returnsoft.callcenter.service.AmiService;
import com.returnsoft.callcenter.service.ConversionService;
import com.returnsoft.callcenter.service.UserService;

@Stateless
//@Remote(UserService.class)
public class UserServiceImpl implements UserService {
	
	@EJB
	private UserEao userEao;
	
	@EJB
	private CampaignEao campaignEao;
	
	@EJB
	private CampaignSessionTypeEao campaignSessionTypeEao;
	
	@EJB
	private AmiService amiService;
	@EJB
	private SessionEao sessionEao;
	@EJB
	private ServerEao serverEao;
	@EJB
	private SessionCampaignEao sessionCampaignEao;
	@EJB
	private ConversionService conversionService;
	
	@Override
	public UserDto loginUser(String username, String password) throws ServiceException {

		try {
			
			User user = userEao.findByUsername(username);
						
			if (user==null) {
				throw new UserNotFoundException();
			}
			
			if (!user.getPassword().equals(password)) {
				throw new UserWrongPasswordException();
			}
			
			if (user.getIsActive().equals(false)) {
				throw new UserInactiveException();
			}
			
			UserDto userDto = conversionService.fromUserToLogin(user);
			
			return userDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}

	}
	
	@Override
	public List<CampaignDto> getAllCampaigns() throws ServiceException {
		try {

			// VERIFICA QUE EXISTA EL USUARIO
			/*Server server = serverEao.findById(serverId);
			if (server == null) {
				throw new ServerNotFoundException();
			}*/
			
			List<CampaignDto> campaignsDto = null;
			
			//if (server.getCampaigns()!=null && server.getCampaigns().size()>0) {
			List<Campaign> campaigns = campaignEao.findAll();
				campaignsDto = new ArrayList<CampaignDto>();
				for (Campaign campaign : campaigns) {
					CampaignDto campaignDto = conversionService
							.fromCampaignToSelect(campaign);
					campaignsDto.add(campaignDto);
				}
			//}
			
			return campaignsDto;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	@Override
	public List<CampaignDto> findCampaignsByServer(Short serverId) throws ServiceException {
		try {

			// VERIFICA QUE EXISTA EL USUARIO
			Server server = serverEao.findById(serverId);
			if (server == null) {
				throw new ServerNotFoundException();
			}
			
			List<CampaignDto> campaignsDto = null;
			
			if (server.getCampaigns()!=null && server.getCampaigns().size()>0) {
				campaignsDto = new ArrayList<CampaignDto>();
				for (Campaign campaign : server.getCampaigns()) {
					CampaignDto campaignDto = conversionService
							.fromCampaignToSelect(campaign);
					campaignsDto.add(campaignDto);
				}
			}
			
			return campaignsDto;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public List<CampaignDto> findCampaignsByUser(int userId) throws ServiceException {
		try {

			// VERIFICA QUE EXISTA EL USUARIO
			User user = userEao.findById(userId);
			if (user == null) {
				throw new UserNotFoundException();
			}
			
			List<CampaignDto> campaignsDto = null;
			
			/*if (user.getType().equals(UserTypeEnum.ADMINISTRATOR.getId())) {
				List<Campaign> campaigns = campaignEao.findAll();
				if (campaigns != null) {
					campaignsDto = new ArrayList<CampaignDto>();
					for (Campaign campaign : campaigns) {
						CampaignDto campaignDto = conversionService
								.fromCampaignToSelect(campaign);
						campaignsDto.add(campaignDto);
					}
				}
				
			}else if(user.getType().equals(UserTypeEnum.SUPERVISOR.getId())){*/
				// VERIFICA QUE TENGA CAMPAÑAS ASIGNADAS
				if (user.getCampaigns().size() < 1) {
					throw new UserCampaignNotFoundException();
				}

				List<Campaign> campaigns = user.getCampaigns();
				
				if (campaigns != null) {
					campaignsDto = new ArrayList<CampaignDto>();
					for (Campaign campaign : campaigns) {
						CampaignDto campaignDto = conversionService
								.fromCampaignToSelect(campaign);
						campaignsDto.add(campaignDto);
					}
				}
			//}

			

			return campaignsDto;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public List<ServerDto> getAllServers() throws ServiceException{
		try {
			
			List<Server> servers = serverEao.findAll();
			List<ServerDto> serversDto = null;
			if (servers!=null && servers.size()>0) {
				serversDto = new ArrayList<ServerDto>();
				for (Server server : servers) {
					ServerDto serverDto = conversionService.fromServerToSelect(server);
					serversDto.add(serverDto);
				}
			}
			
			return serversDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public List<UserDto> findSupervisorsByCampaigns(List<Short> campaignsId)
			throws ServiceException{
				
		try {
			
			List<User> supervisors = userEao.findSupervisorsByCampaigns(campaignsId);
			List<UserDto> supervisorsDto = null;
			if (supervisors != null) {
				supervisorsDto = new ArrayList<UserDto>();
				for (User supervisor : supervisors) {
					//VERIFICA
					boolean found=false;
					for (UserDto supervisorDto : supervisorsDto) {
						if (supervisor.getId().equals(supervisorDto.getId())) {
							found=true;
						}
					}
					//AGREGA
					if (!found) {
						UserDto supervisorDto = conversionService
								.fromUserToSelect(supervisor);
						supervisorsDto.add(supervisorDto);
					}
					
					
				}
			}
			return supervisorsDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public List<UserDto> findUsers(String firstname, String lastname, String username, List<UserTypeEnum> userTypesId, List<Short> campaignsId, int supervisorId)
			throws ServiceException{
		try {
			
			List<User> users = userEao.find(firstname, lastname, username, campaignsId, userTypesId, supervisorId);
			List<UserDto> usersDto = null;
			if (users != null) {
				usersDto = new ArrayList<UserDto>();
				for (User user : users) {
					
					UserDto userDto = conversionService
							.fromUserToTable(user);
					usersDto.add(userDto);
				}
			}
			return usersDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public void addUser(UserDto userDto) throws ServiceException{
		try {
			
			User user = userEao.findByUsername(userDto.getUsername());
			if(user!=null && user.getId()>0){
				 throw new UserDuplicateException(userDto.getUsername());
			}
			user = conversionService.toUser(userDto);
			userEao.add(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public void editUser(UserDto userDto) throws ServiceException{
		try {
			
			//VERIFICA DISPONIBILIDAD DE USERNAME
			User userFound = userEao.findById(userDto.getId());
			if (!userFound.getUsername().equals(userDto.getUsername())) {
				User userAvailable = userEao.findByUsername(userDto.getUsername());
				if(userAvailable!=null && userAvailable.getId()>0){
					 throw new UserDuplicateException(userDto.getUsername());
				}
			}
			
			User user = conversionService.toUser(userDto);
			//VERIFICA SI TIENE SESSION ACTUAL
			if (userFound.getCurrentSession()!=null && userFound.getCurrentSession().getId()>0) {
				Session currentSession = new Session();
				currentSession.setId(userFound.getCurrentSession().getId());
				user.setCurrentSession(currentSession);
			}
			userEao.update(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	@Override
	public UserDto findUserById(Integer userId) throws ServiceException{
		try {
			
			User user = userEao.findById(userId);
			UserDto userDto = conversionService.fromUserToTable(user);
			
			return userDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	@Override
	public List<SessionTypeEnum> findSessionTypesByCampaigns(List<Short> campaignsId)
			throws ServiceException {
		try {

			List<CampaignSessionType> sessionsType = campaignSessionTypeEao.findSessionTypesByCampaigns(campaignsId);
			List<SessionTypeEnum> sessionsTypeDto = null;
			if (sessionsType != null) {
				sessionsTypeDto = new ArrayList<SessionTypeEnum>();
				for (CampaignSessionType sessionType : sessionsType) {
					//VERIFICA
					boolean found=false;
					for (SessionTypeEnum sessionTypeDto : sessionsTypeDto) {
						if (sessionType.getSessionType().getId().equals(sessionTypeDto.getId())) {
							found=true;
						}
					}
					//AGREGA
					if (!found) {
						/*SessionTypeEnum sessionTypeDto = conversionService
								.fromSessionTypeToSelect(sessionType);*/
						sessionsTypeDto.add(sessionType.getSessionType());
					}
				}
			}

			return sessionsTypeDto;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}

	@Override
	public List<ServerDto> findServersByCampaigns(List<Short> campaignsId)
			throws ServiceException {
		try {
			List<Server> servers = serverEao.findServersByCampaigns(campaignsId);
			List<ServerDto> serversDto = null;
			if (servers != null) {
				serversDto = new ArrayList<ServerDto>();
				for (Server server : servers) {
					//VERIFICA
					boolean found=false;
					for (ServerDto serverDto : serversDto) {
						if (server.getId().equals(serverDto.getId())) {
							found=true;
						}
					}
					//AGREGA
					if (!found) {
						ServerDto serverDto = conversionService
								.fromServerToSelect(server);
						serversDto.add(serverDto);
					}
					
					
				}
			}
			
			return serversDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	

	

}
