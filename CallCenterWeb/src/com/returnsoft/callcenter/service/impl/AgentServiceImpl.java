package com.returnsoft.callcenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.returnsoft.callcenter.dto.AgentInfoDto;
import com.returnsoft.callcenter.eao.CampaignSessionTypeEao;
import com.returnsoft.callcenter.eao.SessionCampaignEao;
import com.returnsoft.callcenter.eao.SessionEao;
import com.returnsoft.callcenter.eao.SessionQueueEao;
import com.returnsoft.callcenter.eao.SessionSessionTypeEao;
import com.returnsoft.callcenter.eao.UserEao;
import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.entity.CampaignSessionType;
import com.returnsoft.callcenter.entity.Queue;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.Session;
import com.returnsoft.callcenter.entity.SessionCampaign;
import com.returnsoft.callcenter.entity.SessionQueue;
import com.returnsoft.callcenter.entity.SessionSessionType;
//import com.returnsoft.callcenter.entity.SessionType;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.exception.PeerNotFoundException;
import com.returnsoft.callcenter.exception.ServerDifferentException;
import com.returnsoft.callcenter.exception.ServiceException;
import com.returnsoft.callcenter.exception.SessionActiveException;
import com.returnsoft.callcenter.exception.SessionNoActiveException;
import com.returnsoft.callcenter.exception.SessionTypeInvalidException;
import com.returnsoft.callcenter.exception.UserCampaignNotFoundException;
import com.returnsoft.callcenter.exception.UserNotFoundException;
import com.returnsoft.callcenter.exception.UserSessionTypeNotFoundException;
import com.returnsoft.callcenter.service.local.AmiService;
import com.returnsoft.callcenter.service.local.ConversionService;
import com.returnsoft.callcenter.service.remote.AgentService;
//import com.returnsoft.callcenter.dto.SessionTypeDto;

@Stateless
@Remote(AgentService.class)
public class AgentServiceImpl implements AgentService {

	@EJB
	private UserEao userEao;
	@EJB
	private AmiService amiService;
	@EJB
	private SessionEao sessionEao;
	@EJB
	private SessionQueueEao sessionQueueEao;
	@EJB
	private SessionCampaignEao sessionCampaignEao;
	@EJB
	private SessionSessionTypeEao sessionSessionTypeEao;
	//@EJB
	//private SessionTypeEao sessionTypeEao;
	@EJB
	private CampaignSessionTypeEao campaignSessionTypeEao;
	@EJB
	private ConversionService conversionService;

	
	@Override
	public AgentInfoDto getAgentInfo(int userId) throws ServiceException {

		try {
			
			//OBTIENE LA INFORMACION DEL AGENTE
			System.out.println("obtiene info de agente");
			User agentInfo = userEao.getAgentInfo(userId);

			// VERIFICA QUE EXISTA EL USUARIO
			/*System.out.println("verifica que exista el usuario");
			User userValidate = userEao.findById(userId);*/
			if (agentInfo == null) {
				throw new UserNotFoundException();
			}

			// VERIFICA QUE TENGA SESION ACTIVA
			/*System.out.println("verifica que tenga sesion activa");*/
			if (agentInfo.getCurrentSession() == null) {
				throw new SessionNoActiveException();
			}
			
			
			
			AgentInfoDto agentInfoDto = new AgentInfoDto();
			agentInfoDto.setUserId(agentInfo.getId());
			agentInfoDto.setUsername(agentInfo.getUsername());
			agentInfoDto.setUserNames(agentInfo.getFirstname()+" "+agentInfo.getLastname());
			
			//int sessionId = agentInfo.getCurrentSession().getId();
			
			//Session session = sessionEao.getSessionForAgent(sessionId);
			
			//System.out.println(session.getId());
			
			/*String campaignNames="";
			for (int i=0; i<session.getCurrentSessionSessionType().getSessionsCampaign().size(); i++) {
				SessionCampaign sessionCampaign = session.getCurrentSessionSessionType().getSessionsCampaign().get(i);
				if (i==0) {
					campaignNames += sessionCampaign.getCampaign().getName();	
				}else{
					campaignNames += ", "+sessionCampaign.getCampaign().getName();
				}
			}
			agentInfoDto.setCampaignNames(campaignNames);*/
			
			String campaignNames="";
			for (int i=0; i<agentInfo.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign().size(); i++) {
				SessionCampaign sessionCampaign = agentInfo.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign().get(i);
				if (i==0) {
					campaignNames += sessionCampaign.getCampaign().getName();	
				}else{
					campaignNames += ", "+sessionCampaign.getCampaign().getName();
				}
			}
			agentInfoDto.setCampaignNames(campaignNames);
			agentInfoDto.setSessionType(agentInfo.getCurrentSession().getCurrentSessionSessionType().getSessionType().getName());
			
			
			
			long sessionTime=0;
			long availableTime=0;
			long breakTime=0;
			long pauseTime=0;
			Date currentTime = new Date();
			
			long sessionTypeTime = currentTime.getTime() - agentInfo.getCurrentSession().getCurrentSessionSessionType().getStartedAt().getTime();
			agentInfoDto.setSessionTypeTime(convertMilisToTime(sessionTypeTime));
			
			for (SessionSessionType sessionSessionType : agentInfo.getCurrentSession().getSessionsSessionType()) {
				
				Date startedAt = sessionSessionType.getStartedAt();
				Date endedAt = null;
				if (sessionSessionType.getEndedAt()==null) {
					endedAt = currentTime;
				}else{
					endedAt = sessionSessionType.getEndedAt();
				}
				
				long duration = endedAt.getTime()-startedAt.getTime();
				
				sessionTime += duration; 
				
				switch (sessionSessionType.getSessionType()) {
				
				case AVAILABLE:
					availableTime += duration;
					break;
				case PAUSE:
					pauseTime += duration;
					break;
				case BREAK:
					breakTime += duration;
					break;	
				default:
					break;
					
				}
			}
			
			agentInfoDto.setSessionTime(convertMilisToTime(sessionTime));
			agentInfoDto.setAvailableTime(convertMilisToTime(availableTime));
			agentInfoDto.setBreakTime(convertMilisToTime(breakTime));
			agentInfoDto.setPauseTime(convertMilisToTime(pauseTime));
			
			if (agentInfo.getCurrentCall()!=null) {
				agentInfoDto.setCallNumber(agentInfo.getCurrentCall().getNumber());
				long waitingTime = agentInfo.getCurrentCall().getWaittime()*1000;
				agentInfoDto.setCallWaitingTime(convertMilisToTime(waitingTime));
				long holdTime = agentInfo.getCurrentCall().getHoldtime()*1000;
				agentInfoDto.setCallHoldTime(convertMilisToTime(holdTime));
				long durationTime= currentTime.getTime()-agentInfo.getCurrentCall().getStartedAt().getTime();
				agentInfoDto.setCallDurationTime(convertMilisToTime(durationTime));
				agentInfoDto.setCallQueue(agentInfo.getCurrentCall().getQueue().getName());
			}
			
			


			return agentInfoDto;

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
	public List<SessionTypeEnum> findSessionTypes(int userId)
			throws ServiceException {
		try {
			
			// VERIFICA QUE EXISTA EL USUARIO
			User userValidate = userEao.findById(userId);
			if (userValidate == null) {
				throw new UserNotFoundException();
			}

			// VERIFICA QUE TENGA SESION ACTIVA
			if (userValidate.getCurrentSession() == null) {
				throw new SessionNoActiveException();
			}
			
			//OBTIENE LA INFORMACION DEL AGENTE
			User agentInfo = userEao.getAgentInfo(userId);
			
			List<Short> campaignsId = new ArrayList<Short>();
			
			for (SessionCampaign sessionCampaign : agentInfo.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()) {
				campaignsId.add(sessionCampaign.getCampaign().getId());
			}
			

			List<CampaignSessionType> sessionsType = campaignSessionTypeEao.findSessionTypesByCampaigns(campaignsId);
			List<SessionTypeEnum> sessionsTypeDto = null;
			if (sessionsType != null) {
				sessionsTypeDto = new ArrayList<SessionTypeEnum>();
				for (CampaignSessionType sessionType : sessionsType) {
					sessionsTypeDto.add(sessionType.getSessionType());
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
	public void changeSessionType(int userId, short sessionTypeSelected)
			throws ServiceException {

		try {
			
			System.out.println("------------------------------");
			System.out.println("------------------------------");
			System.out.println("------------------------------");

			// VERIFICA QUE EXISTA EL USUARIO
			System.out.println("VERIFICA QUE EXISTA USUARIO");
			User user = userEao.findById(userId);
			if (user == null) {
				throw new UserNotFoundException();
			}
			
			// VERIFICA QUE TENGA CAMPAÑAS ASIGNADAS
			/*System.out.println("VERIFICA QUE TENGA CAMPAÑAS ASIGNADAS");
			if (user.getCampaigns().size() < 1) {
				throw new UserCampaignNotFoundException();
			}*/
			
			// VERIFICA QUE TODAS LAS CAMPAÑAS TENGAN EL MISMO SERVIDOR
			/*System.out.println("VERIFICA QUE TODAS LAS CAMPAÑAS TENGAN EL MISMO SERVIDOR");
			Server server = user.getCampaigns().get(0).getServer();
			for (Campaign campaign : user.getCampaigns()) {
				if (!campaign.getServer().getId().equals(server.getId())) {
					throw new ServerDifferentException();
				}
			}*/
			
			// VERIFICA QUE EXISTA EL TIPO DE SESION
			System.out.println("VERIFICA QUE EXISTA EL TIPO DE SESION");
			SessionTypeEnum sessionTypeEnum=SessionTypeEnum.findById(sessionTypeSelected); 
			if (sessionTypeEnum == null) {
				throw new SessionTypeInvalidException();
			}
			
			// VERIFICA QUE EL USUARIO TENGA UNA SESION ACTIVA
			System.out.println("VERIFICA QUE EL USUARIO TENGA UNA SESION ACTIVA");
			String host = "";
			Short peer = null;
			if (user.getCurrentSession() != null 
					&& user.getCurrentSession().getCurrentSessionSessionType()!=null) {
				host=user.getCurrentSession().getCurrentSessionSessionType().getHost();
				peer=user.getCurrentSession().getCurrentSessionSessionType().getPeer();
			}else{
				throw new SessionNoActiveException();
			}
			
			// SE ELIMINA ANEXO DE LAS COLAS - NO
			/*System.out.println("SE ELIMINA ANEXO DE LAS COLAS");
			for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()) {
				for (SessionQueue sessionQueue : sessionCampaign.getSessionsQueue()) {
					Short peerName = user.getCurrentSession().getCurrentSessionSessionType().getPeer();
					String response = amiService.removeQueue(sessionQueue.getQueue()
							.getName(), peerName.toString(), sessionCampaign
							.getCampaign().getServer());
					sessionQueue.setResponse(response);
					sessionQueueEao.update(sessionQueue);
				}
			}*/
			
			
			// SE CIERRA LA SESION POR TIPO DE SESION - NO
			System.out.println("SE CIERRA LA SESION POR TIPO DE SESION");
			user.getCurrentSession().getCurrentSessionSessionType().setEndedAt(new Date());
			sessionSessionTypeEao.update(user.getCurrentSession().getCurrentSessionSessionType());
			
			// SE ACTUALIZA EL 'TIPO DE SESION' DE LA 'SESION'
			System.out.println("SE ACTUALIZA EL 'TIPO DE SESION' DE LA 'SESION'");
			user.getCurrentSession().setCurrentSessionSessionType(null);
			sessionEao.update(user.getCurrentSession());
					
			// SE CREA LA SESION POR TIPO DE SESION - SI
			System.out.println("SE CREA LA SESION POR TIPO DE SESION");
			SessionSessionType newSessionSessionType = new SessionSessionType();
			newSessionSessionType.setSession(user.getCurrentSession());
			newSessionSessionType.setSessionType(sessionTypeEnum);
			newSessionSessionType.setStartedAt(new Date());
			newSessionSessionType.setHost(host);
			newSessionSessionType.setPeer(peer);
			sessionSessionTypeEao.add(newSessionSessionType);
			
			// SE CREAN LAS SESIONES POR CAMPAÑA - SI
			System.out.println("SE CREAN LAS SESIONES POR CAMPAÑA");
			newSessionSessionType.setSessionsCampaign(new ArrayList<SessionCampaign>());
			for (Campaign campaign : user.getCampaigns()) {
				SessionCampaign newSessionCampaign = new SessionCampaign();
				newSessionCampaign.setSessionSessionType(newSessionSessionType);
				newSessionCampaign.setCampaign(campaign);
				sessionCampaignEao.add(newSessionCampaign);
				// SE CREAN SESIONES POR COLA
				System.out.println("SE CREAN SESIONES POR COLA");
				if (campaign.getQueues().size() > 0) {
					newSessionCampaign.setSessionsQueue(new ArrayList<SessionQueue>());
					for (Queue queue : campaign.getQueues()) {
						SessionQueue newSessionQueue = new SessionQueue();
						newSessionQueue.setQueue(queue);
						newSessionQueue.setSessionCampaign(newSessionCampaign);
						sessionQueueEao.add(newSessionQueue);
						newSessionCampaign.getSessionsQueue().add(newSessionQueue);
					}
				}
				newSessionSessionType.getSessionsCampaign().add(newSessionCampaign);
				
			}
			
			// SE ACTUALIZA EL 'TIPO DE SESION' DE LA 'SESION'
			System.out.println("SE ACTUALIZA EL 'TIPO DE SESION' DE LA 'SESION'");
			user.getCurrentSession().setCurrentSessionSessionType(newSessionSessionType);
			sessionEao.update(user.getCurrentSession());
			
			//newSession.setSessionsSessionType(new ArrayList<SessionSessionType>());
			//newSession.getSessionsSessionType().add(newSessionSessionType);
			//newSession.setCurrentSessionSessionType(newSessionSessionType);
			
			// SE ACTUALIZA LA 'SESION' DEL 'USUARIO'
			/*user.setCurrentSession(newSession);
			user = userEao.update(user);*/
			
			// SE AGREGAN LOS ANEXOS A LAS COLAS
			/*System.out.println("SE AGREGAN LOS ANEXOS A LAS COLAS");
			for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType()
					.getSessionsCampaign()) {
				for (SessionQueue sessionQueue : sessionCampaign
						.getSessionsQueue()) {
					//String peerName = newSession.getCurrentSessionSessionType().getPeer();
					String response = amiService.addQueue(sessionQueue.getQueue()
							.getName(), peer.toString(), sessionCampaign
							.getCampaign().getServer(), sessionTypeEnum
							.getIsPaused());
					sessionQueue.setResponse(response);
				}
			}*/
			
			//SE CAMBIAN LOS ANEXOS EN LAS COLAS
			System.out.println("SE CAMBIAN LOS ANEXOS EN LAS COLAS");
			for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType()
					.getSessionsCampaign()) {
				
				/*for (SessionQueue sessionQueue : sessionCampaign
						.getSessionsQueue()) {
					/*String response = amiService.addQueue(sessionQueue.getQueue()
							.getName(), peer.toString(), sessionCampaign
							.getCampaign().getServer(), sessionTypeEnum
							.getIsPaused());
					sessionQueue.setResponse(response);
					
				}*/
				
				
				switch (sessionTypeEnum) {
				case PAUSE:
					amiService.pauseManyQueues(sessionCampaign.getSessionsQueue(), peer.toString(), sessionCampaign.getCampaign().getServer());
					break;
				case BREAK:
					amiService.pauseManyQueues(sessionCampaign.getSessionsQueue(), peer.toString(), sessionCampaign.getCampaign().getServer());
					break;
				case AVAILABLE:
					amiService.unpauseManyQueues(sessionCampaign.getSessionsQueue(), peer.toString(), sessionCampaign.getCampaign().getServer());
					break;	

				default:
					break;
				}
				
			}
			
			
			System.out.println("------------------------------");
			System.out.println("------------------------------");
			System.out.println("------------------------------");
			
			

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
	public void loginAgent(int userId, String host) throws ServiceException {

		try {

			// VERIFICA QUE EXISTA EL USUARIO
			User user = userEao.findById(userId);
			if (user == null) {
				throw new UserNotFoundException();
			}
			
			// VERIFICA QUE TENGA CAMPAÑAS ASIGNADAS
			if (user.getCampaigns().size() < 1) {
				throw new UserCampaignNotFoundException();
			}
			
			// VERIFICA QUE TODAS LAS CAMPAÑAS TENGAN EL MISMO SERVIDOR
			Server server = user.getCampaigns().get(0).getServer();
			for (Campaign campaign : user.getCampaigns()) {
				if (!campaign.getServer().getId().equals(server.getId())) {
					throw new ServerDifferentException();
				}
			}
			
			// VERIFICA QUE EL ANEXO ESTE DISPONIBLE
			String peerName = amiService.searchPeer(host,server);
			if (peerName == null) {
				throw new PeerNotFoundException(host,server.getName());
			}	
			
			// VERIFICA QUE EL USUARIO TENGA UN TIPO DE SESION INICIAL
			if (user.getSessionType() == null) {
				throw new UserSessionTypeNotFoundException();
			}
			
			
			// VERIFICA CONCURRENCIA DE HOST SI LA SESION ESTA ABIERTA
			boolean sessionIsOpen=false;
			if (user.getCurrentSession() != null) {
				if (user.getCurrentSession().getCurrentSessionSessionType()!=null) {
					if (user.getCurrentSession().getCurrentSessionSessionType().getHost().equals(host)) {
						sessionIsOpen=true;
					}else{
						logoutAgent(userId);
					}	
				}
			}
			
			if (!sessionIsOpen) {
				
				//VERIFICA CONCURRENCIA DE HOST SI LA SESION ESTA CERRADA
				List<Session> sessions = sessionEao.findOpenSessionByHost(host);
				if (sessions!=null && sessions.size()>0) {
					for (Session session : sessions) {
						throw new SessionActiveException(host,session.getUser().getUsername());
					}
				}
				
				// SE CREA LA NUEVA SESION
				Session newSession = new Session();
				newSession.setUser(user);
				newSession.setStartedAt(new Date());
				sessionEao.add(newSession);
				
				// SE CREA LA SESION X TIPO DE SESION
				SessionSessionType newSessionSessionType = new SessionSessionType();
				newSessionSessionType.setSession(newSession);
				newSessionSessionType.setSessionType(user.getSessionType());
				newSessionSessionType.setStartedAt(new Date());
				newSessionSessionType.setHost(host);
				newSessionSessionType.setPeer(Short.parseShort(peerName));
				sessionSessionTypeEao.add(newSessionSessionType);
				
				// SE CREAN LAS SESIONES POR CAMPAÑA
				newSessionSessionType.setSessionsCampaign(new ArrayList<SessionCampaign>());
				for (Campaign campaign : user.getCampaigns()) {
					SessionCampaign newSessionCampaign = new SessionCampaign();
					newSessionCampaign.setSessionSessionType(newSessionSessionType);
					newSessionCampaign.setCampaign(campaign);
					sessionCampaignEao.add(newSessionCampaign);
					// SE CREAN SESIONES POR COLA
					if (campaign.getQueues().size() > 0) {
						newSessionCampaign.setSessionsQueue(new ArrayList<SessionQueue>());
						for (Queue queue : campaign.getQueues()) {
							SessionQueue newSessionQueue = new SessionQueue();
							newSessionQueue.setQueue(queue);
							newSessionQueue.setSessionCampaign(newSessionCampaign);
							sessionQueueEao.add(newSessionQueue);
							newSessionCampaign.getSessionsQueue().add(newSessionQueue);
						}
					}
					newSessionSessionType.getSessionsCampaign().add(newSessionCampaign);
					
				}
				//newSession.setSessionsSessionType(new ArrayList<SessionSessionType>());
				//newSession.getSessionsSessionType().add(newSessionSessionType);
				
				//SE ACTUALIZA EL 'TIPO DE SESION' ACTUAL EN LA 'SESION'
				newSession.setCurrentSessionSessionType(newSessionSessionType);
				
				// SE ACTUALIZA LA 'SESION' DEL 'USUARIO'
				user.setCurrentSession(newSession);
				user = userEao.update(user);
				
				// SE AGREGAN LOS ANEXOS A LAS COLAS
				for (SessionCampaign sessionCampaign : newSession.getCurrentSessionSessionType()
						.getSessionsCampaign()) {
					for (SessionQueue sessionQueue : sessionCampaign
							.getSessionsQueue()) {
						//String peerName = newSession.getCurrentSessionSessionType().getPeer();
						String response = amiService.addQueue(sessionQueue.getQueue()
								.getName(), peerName, sessionCampaign
								.getCampaign().getServer(), user.getSessionType()
								.getIsPaused());
						sessionQueue.setResponse(response);
					}
				}
				
			}
			
			
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
	public void logoutAgent(int userId) throws ServiceException {

		try {

			// VERIFICA QUE EXISTA EL USUARIO
			User user = userEao.findById(userId);
			if (user == null) {
				throw new UserNotFoundException();
			}
			
			// SE CIERRA LA SESION ACTUAL
			if (user.getCurrentSession() != null) {
				
				// SE ELIMINA ANEXO DE LAS COLAS
				for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()) {
					for (SessionQueue sessionQueue : sessionCampaign.getSessionsQueue()) {
						Short peerName = user.getCurrentSession().getCurrentSessionSessionType().getPeer();
						String response = amiService.removeQueue(sessionQueue.getQueue()
								.getName(), peerName.toString(), sessionCampaign
								.getCampaign().getServer());
						sessionQueue.setResponse(response);
						sessionQueueEao.update(sessionQueue);
					}
				}
				
				// SE CIERRA LA SESION POR TIPO DE SESION
				if (user.getCurrentSession().getCurrentSessionSessionType()!=null) {
					user.getCurrentSession().getCurrentSessionSessionType().setEndedAt(new Date());
					sessionSessionTypeEao.update(user.getCurrentSession().getCurrentSessionSessionType());
				}
			
				// SE CIERRA LA SESION
				user.getCurrentSession().setEndedAt(new Date());
				user.getCurrentSession().setCurrentSessionSessionType(null);
				sessionEao.update(user.getCurrentSession());

				// SE ACTUALIZA LA 'SESION' DEL 'USUARIO'
				user.setCurrentSession(null);
				userEao.update(user);
				
				
				

			} else {
				throw new SessionNoActiveException();
			}

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
	public void restartAgent(int userId) throws ServiceException {
		try {
			
			// VERIFICA QUE EXISTA EL USUARIO
			User user = userEao.findById(userId);
			if (user == null) {
				throw new UserNotFoundException();
			}
			
			// VERIFICA QUE TENGA CAMPAÑAS ASIGNADAS
			if (user.getCampaigns().size() < 1) {
				throw new UserCampaignNotFoundException();
			}
			
			// VERIFICA QUE TODAS LAS CAMPAÑAS TENGAN EL MISMO SERVIDOR
			Server server = user.getCampaigns().get(0).getServer();
			for (Campaign campaign : user.getCampaigns()) {
				if (!campaign.getServer().getId().equals(server.getId())) {
					throw new ServerDifferentException();
				}
			}
			
			// VERIFICA QUE EL USUARIO TENGA UN TIPO DE SESION INICIAL
			if (user.getSessionType() == null) {
				throw new UserSessionTypeNotFoundException();
			}
			
			// VERIFICA QUE EL USUARIO TENGA UNA SESION ACTIVA
			String host = "";
			Short peer = null;
			if (user.getCurrentSession() != null 
					&& user.getCurrentSession().getCurrentSessionSessionType()!=null) {
				host=user.getCurrentSession().getCurrentSessionSessionType().getHost();
				peer=user.getCurrentSession().getCurrentSessionSessionType().getPeer();
			}else{
				throw new SessionNoActiveException();
			}
			
			// SE ELIMINA ANEXO DE LAS COLAS
			for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType().getSessionsCampaign()) {
				for (SessionQueue sessionQueue : sessionCampaign.getSessionsQueue()) {
					Short peerName = user.getCurrentSession().getCurrentSessionSessionType().getPeer();
					String response = amiService.removeQueue(sessionQueue.getQueue()
							.getName(), peerName.toString(), sessionCampaign
							.getCampaign().getServer());
					sessionQueue.setResponse(response);
					sessionQueueEao.update(sessionQueue);
				}
			}
						
			
			// SE CIERRA LA SESION POR TIPO DE SESION 
			user.getCurrentSession().getCurrentSessionSessionType().setEndedAt(new Date());
			sessionSessionTypeEao.update(user.getCurrentSession().getCurrentSessionSessionType());
			
			// SE ACTUALIZA EL 'TIPO DE SESION' DE LA 'SESION'
			user.getCurrentSession().setCurrentSessionSessionType(null);
			sessionEao.update(user.getCurrentSession());
					
			// SE CREA LA SESION POR TIPO DE SESION
			SessionSessionType newSessionSessionType = new SessionSessionType();
			newSessionSessionType.setSession(user.getCurrentSession());
			newSessionSessionType.setSessionType(user.getSessionType());
			newSessionSessionType.setStartedAt(new Date());
			newSessionSessionType.setHost(host);
			newSessionSessionType.setPeer(peer);
			sessionSessionTypeEao.add(newSessionSessionType);
			
			
			// SE CREAN LAS SESIONES POR CAMPAÑA
			newSessionSessionType.setSessionsCampaign(new ArrayList<SessionCampaign>());
			for (Campaign campaign : user.getCampaigns()) {
				SessionCampaign newSessionCampaign = new SessionCampaign();
				newSessionCampaign.setSessionSessionType(newSessionSessionType);
				newSessionCampaign.setCampaign(campaign);
				sessionCampaignEao.add(newSessionCampaign);
				// SE CREAN SESIONES POR COLA
				if (campaign.getQueues().size() > 0) {
					newSessionCampaign.setSessionsQueue(new ArrayList<SessionQueue>());
					for (Queue queue : campaign.getQueues()) {
						SessionQueue newSessionQueue = new SessionQueue();
						newSessionQueue.setQueue(queue);
						newSessionQueue.setSessionCampaign(newSessionCampaign);
						sessionQueueEao.add(newSessionQueue);
						newSessionCampaign.getSessionsQueue().add(newSessionQueue);
					}
				}
				newSessionSessionType.getSessionsCampaign().add(newSessionCampaign);
				
			}
			
			// SE ACTUALIZA EL 'TIPO DE SESION' DE LA 'SESION'
			user.getCurrentSession().setCurrentSessionSessionType(newSessionSessionType);
			sessionEao.update(user.getCurrentSession());
			
			//newSession.setSessionsSessionType(new ArrayList<SessionSessionType>());
			//newSession.getSessionsSessionType().add(newSessionSessionType);
			//newSession.setCurrentSessionSessionType(newSessionSessionType);
			
			// SE ACTUALIZA LA 'SESION' DEL 'USUARIO'
			/*user.setCurrentSession(newSession);
			user = userEao.update(user);*/
			
			// SE AGREGAN LOS ANEXOS A LAS COLAS
			for (SessionCampaign sessionCampaign : user.getCurrentSession().getCurrentSessionSessionType()
					.getSessionsCampaign()) {
				for (SessionQueue sessionQueue : sessionCampaign
						.getSessionsQueue()) {
					//String peerName = newSession.getCurrentSessionSessionType().getPeer();
					String response = amiService.addQueue(sessionQueue.getQueue()
							.getName(), peer.toString(), sessionCampaign
							.getCampaign().getServer(), user.getSessionType()
							.getIsPaused());
					sessionQueue.setResponse(response);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	private String convertMilisToTime(Long miliseconds){
		
		long diffSeconds = miliseconds / 1000 % 60;
	    long diffMinutes = miliseconds / (60 * 1000) % 60;
	    long diffHours = miliseconds / (60 * 60 * 1000);
	    String duration = ((diffHours==0)?"00":(diffHours<10?"0"+diffHours:""+diffHours))
	    					+":"+((diffMinutes==0)?"00":(diffMinutes<10?"0"+diffMinutes:""+diffMinutes))
	    					+":"+((diffSeconds==0)?"00":(diffSeconds<10?"0"+diffSeconds:""+diffSeconds));
		
		return duration;
	}

}
