package com.returnsoft.callcenter.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import com.returnsoft.callcenter.eao.CallEao;
import com.returnsoft.callcenter.eao.CampaignEao;
import com.returnsoft.callcenter.eao.ReportACDCampaignEao;
import com.returnsoft.callcenter.eao.ReportACDUserEao;
import com.returnsoft.callcenter.eao.SessionCampaignEao;
import com.returnsoft.callcenter.eao.SessionEao;
import com.returnsoft.callcenter.eao.UserEao;
import com.returnsoft.callcenter.entity.Call;
import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.entity.ReportACDCampaign;
import com.returnsoft.callcenter.entity.ReportACDUser;
import com.returnsoft.callcenter.entity.Session;
import com.returnsoft.callcenter.entity.SessionCampaign;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.exception.ServiceException;
import com.returnsoft.callcenter.service.local.ReportService;

@Stateless
public class ReportServiceImpl implements ReportService {
	
	
	@EJB
	private SessionEao sessionEao;
	
	@EJB
	private SessionCampaignEao sessionCampaignEao;
	
	@EJB
	private ReportACDCampaignEao reportACDCampaignEao;
	
	@EJB
	private ReportACDUserEao reportACDUserEao;
	
	@EJB
	private CallEao callEao;
	
	@EJB
	private CampaignEao campaignEao;
	
	@EJB
	private UserEao userEao;
	
	@Override
	//@Schedule(minute="0,5,10,15,20,25,30,35,40,45,50,55", second="0",hour="*", persistent=false)
	public void reportACDCampaignBy5Minutes() throws ServiceException {
		try {
			
			//SE INICIALIZAN LAS FECHAS
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MINUTE, -20);
			
			int currentMinutes = start.get(Calendar.MINUTE);
			int cociente = currentMinutes / 10;
			int resto = currentMinutes % 10;

			if (resto < 5) {
				start.set(Calendar.MINUTE, (cociente*10));	
			}else{
				start.set(Calendar.MINUTE, (cociente*10)+5);
			}
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.MINUTE, 5);
			
			reportACDCampaign(start.getTime(), end.getTime());
			
			
		} catch (Exception e) {
		 e.printStackTrace();
		}
		
	}
	
	@Override
	//@Schedule(minute="5,35", second="0",hour="*", persistent=false)
	public void reportACDCampaignBy30Minutes() throws ServiceException {
		try {
			
			//SE INICIALIZAN LAS FECHAS
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MINUTE, -35);
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.MINUTE, 30);
			
			reportACDCampaign(start.getTime(), end.getTime());
			
			//REGENERA REPORTE DE CADA 5 MIN
			end.setTime(start.getTime());
			for (int i=0; i<12;i++) {
				if(i>0){
					start.add(Calendar.MINUTE, 5);
				}
				end.add(Calendar.MINUTE, 5);
				//ELIMINA
				reportACDCampaignEao.deleteByPeriod(start.getTime(), (short)5);
				//VUELVE A CREAR
				reportACDCampaign(start.getTime(), end.getTime());
			}
			
			
		} catch (Exception e) {
		 e.printStackTrace();
		}
		
	}
	
	@Override
	//@Schedule(minute="0", second="0",hour="1,7,13,19", persistent=false)
	public void reportACDCampaignBy360Minutes() throws ServiceException {
		try {
			
			//SE INICIALIZAN LAS FECHAS
			Calendar start = Calendar.getInstance();
			start.add(Calendar.HOUR, 7);
			start.set(Calendar.MINUTE, 0);
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			
			
			//REGENERA INTERVALOS DE 30
			for (int i = 0; i < 12; i++) {
				if(i>0){
					start.add(Calendar.MINUTE, 5);
					end.add(Calendar.MINUTE, 5);
				}else{
					end.add(Calendar.MINUTE, 30);
				}
				
				reportACDCampaignEao.deleteByPeriod(start.getTime(), (short)30);
				reportACDCampaign(start.getTime(), end.getTime());
				
				//REGENERA INTERVALOS DE 5
				end.setTime(start.getTime());
				for (int j=0; j<12;i++) {
					if(j>0){
						start.add(Calendar.MINUTE, 5);
					}
					end.add(Calendar.MINUTE, 5);
					//ELIMINA
					reportACDCampaignEao.deleteByPeriod(start.getTime(), (short)5);
					//VUELVE A CREAR
					reportACDCampaign(start.getTime(), end.getTime());
				}
			}
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	
	

	
	
	@Override
	//@Schedule(minute="0,5,10,15,20,25,30,35,40,45,50,55", second="0",hour="*", persistent=false)
	public void reportACDUserBy5Minutes() throws ServiceException{
		
		try {
			
			//SE INICIALIZAN LAS FECHAS
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MINUTE, -20);
			
			int currentMinutes = start.get(Calendar.MINUTE);
			int cociente = currentMinutes / 10;
			int resto = currentMinutes % 10;

			if (resto < 5) {
				start.set(Calendar.MINUTE, (cociente*10));
			}else{
				start.set(Calendar.MINUTE, (cociente*10)+5);
			}
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.MINUTE, 5);
			
			reportACDUser(start.getTime(), end.getTime());
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	@Override
	//@Schedule(minute="5,35", second="0",hour="*", persistent=false)
	public void reportACDUserBy30Minutes() throws ServiceException{
		
		try {
			
			//SE INICIALIZAN LAS FECHAS
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MINUTE, -35);
			
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.MINUTE, 30);
			
			reportACDUser(start.getTime(), end.getTime());
			
			//REGENERA REPORTE DE CADA 5 MIN
			end.setTime(start.getTime());
			for (int i=0; i<12;i++) {
				if(i>0){
					start.add(Calendar.MINUTE, 5);
				}
				end.add(Calendar.MINUTE, 5);
				//ELIMINA
				reportACDUserEao.deleteByPeriod(start.getTime(), (short)5);
				//VUELVE A CREAR
				reportACDUser(start.getTime(), end.getTime());
			}
			
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	@Override
	//@Schedule(minute="0", second="0",hour="1,7,13,19", persistent=false)
	public void reportACDUserBy360Minutes() throws ServiceException {
		try {
			
			//SE INICIALIZAN LAS FECHAS
			Calendar start = Calendar.getInstance();
			start.add(Calendar.HOUR, 7);
			start.set(Calendar.MINUTE, 0);
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			
			
			//REGENERA INTERVALOS DE 30
			for (int i = 0; i < 12; i++) {
				if(i>0){
					start.add(Calendar.MINUTE, 5);
					end.add(Calendar.MINUTE, 5);
				}else{
					end.add(Calendar.MINUTE, 30);
				}
				
				reportACDCampaignEao.deleteByPeriod(start.getTime(), (short)30);
				reportACDCampaign(start.getTime(), end.getTime());
				
				//REGENERA INTERVALOS DE 5
				end.setTime(start.getTime());
				for (int j=0; j<12;i++) {
					if(j>0){
						start.add(Calendar.MINUTE, 5);
					}
					end.add(Calendar.MINUTE, 5);
					//ELIMINA
					reportACDCampaignEao.deleteByPeriod(start.getTime(), (short)5);
					//VUELVE A CREAR
					reportACDCampaign(start.getTime(), end.getTime());
				}
			}
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	public void reportACDUser(Date start, Date end) throws ServiceException{
		
		try {
			
			Long interval = (end.getTime() - start.getTime()) / 1000;
			
			Date current = new Date();
			
			System.out.println("fecha inicio: "+start);
			System.out.println("fecha fin: "+end);
			
			//SE BUSCAN USUARIOS ACTIVOS
			List<User> users = userEao.getActiveAgents();
			
			//SE INICIALIZA EL REPORTE
			List<ReportACDUser> reportsACDUser = new ArrayList<ReportACDUser>();
			
			for (User user : users) {
				
				ReportACDUser reportACDUser = new ReportACDUser();
				reportACDUser.setPeriod(start);
				reportACDUser.setInterval(interval.shortValue());
				reportACDUser.setUserId(user.getId());
				reportACDUser.setUsername(user.getUsername());
				reportACDUser.setNames(user.getFirstname()+" "+user.getLastname());
				reportACDUser.setSessionsTime(0);
				reportACDUser.setSessionsAvailableTime(0);
				reportACDUser.setSessionsBreakTime(0);
				reportACDUser.setSessionsPauseTime(0);
				reportACDUser.setCallsComplete(0);
				reportACDUser.setCallsDurationTime(0);
				reportACDUser.setCallsWaitingTime(0);
				reportACDUser.setCallsConversationTime(0);
				reportACDUser.setCallsHoldTime(0);
				reportsACDUser.add(reportACDUser);
				
			}
			
			//OBTIENE LOGUEOS
			List<SessionCampaign> sessionsCampaign = sessionCampaignEao.findLoginsForReportACDUser(start, end);
			
			for (SessionCampaign sessionCampaign : sessionsCampaign) {
				
				//SE OBTIENE DURACION DE LOGUEO
				Date startedAt = sessionCampaign.getSessionSessionType().getStartedAt();
				Date endedAt = sessionCampaign.getSessionSessionType().getEndedAt();
				if (startedAt.getTime()<start.getTime()) {
					startedAt=start;
				}
				if(endedAt!=null){
					if (endedAt.getTime() > end.getTime()) {
						endedAt=end;
					}
				}else{
					if (current.getTime() >= start.getTime() && current.getTime() < end.getTime()) {
						endedAt=current;
					}else{
						endedAt=end;	
					}
				}
				Long duration = (endedAt.getTime()-startedAt.getTime())/1000;
				
				for (ReportACDUser reportACDUser : reportsACDUser) {
					if (sessionCampaign.getSessionSessionType().getSession().getUser().getId().equals(reportACDUser.getUserId())) {
						reportACDUser.setSessionsTime(reportACDUser.getSessionsTime()+duration.intValue());
						switch (sessionCampaign.getSessionSessionType().getSessionType()) {
						case AVAILABLE:
							reportACDUser.setSessionsAvailableTime(reportACDUser.getSessionsAvailableTime()+duration.intValue());
							break;
						case BREAK:
							reportACDUser.setSessionsBreakTime(reportACDUser.getSessionsBreakTime()+duration.intValue());
							break;
						case PAUSE:
							reportACDUser.setSessionsPauseTime(reportACDUser.getSessionsPauseTime()+duration.intValue());
							break;	
						default:
							break;
						}
						break;
					}
				}	
			}
			
			for (ReportACDUser reportACDUser : reportsACDUser) {
				reportACDUserEao.add(reportACDUser);
			}
			
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	
	public void reportACDCampaign(Date start, Date end) throws ServiceException {
		try {
			
			Long interval = (end.getTime() - start.getTime()) / 1000;
			
			Date current = new Date();
			
			//SE BUSCAN LAS CAMPAÑAS
			List<Campaign> campaigns = campaignEao.findAll();
			
			//SE INICIALIZA EL REPORTE
			List<ReportACDCampaign> reportsACDCampaign = new ArrayList<ReportACDCampaign>();
			for (Campaign campaign : campaigns) {
				
				//SE OBTIENE LA CANTIDAD DE SESIONES
				List<Session> sessions = sessionEao.findLoginsByCampaign(campaign.getId(), start, end);
				
				ReportACDCampaign reportACDCampaign = new ReportACDCampaign();
				reportACDCampaign.setPeriod(start);
				reportACDCampaign.setInterval(interval.shortValue());
				
				if (sessions!=null) {
					reportACDCampaign.setUsers((short)sessions.size());
				}else{
					reportACDCampaign.setUsers((short)0);
				}
				
				reportACDCampaign.setCampaignId(campaign.getId());
				reportACDCampaign.setCampaignName(campaign.getName());
				reportACDCampaign.setSessionsTime(0);
				reportACDCampaign.setSessionsAvailableTime(0);
				reportACDCampaign.setSessionsBreakTime(0);
				reportACDCampaign.setSessionsPauseTime(0);
				//if abandon
				reportACDCampaign.setCallsAbandon(0);
				reportACDCampaign.setCallsAbandon5(0);
				reportACDCampaign.setCallsAbandonTime(0);
				
				//if complete
				reportACDCampaign.setCallsComplete(0);
				reportACDCampaign.setCallsConversationTime(0);
				reportACDCampaign.setCallsInbound(0);
				reportACDCampaign.setCallsWaiting20(0);
				reportACDCampaign.setCallsWaiting24(0);
				reportACDCampaign.setCallsWaiting30(0);
				reportACDCampaign.setCallsWaitingTime(0);
				reportACDCampaign.setCallsWaitingTime20(0);
				reportACDCampaign.setCallsWaitingTime24(0);
				reportACDCampaign.setCallsWaitingTime30(0);
				
				reportsACDCampaign.add(reportACDCampaign);
			}
			
			// SE OBTIENEN LOS LOGUEOS POR TIPO
			
			List<SessionCampaign> sessionsCampaign = sessionCampaignEao.findLoginsForReportACDCampaign(start, end);
			for (SessionCampaign sessionCampaign : sessionsCampaign) {
				//SE OBTIENE DURACION DE LOGUEO
				Date startedAt = sessionCampaign.getSessionSessionType().getStartedAt();
				Date endedAt = sessionCampaign.getSessionSessionType().getEndedAt();
				if (startedAt.getTime()<start.getTime()) {
					startedAt=start;
				}
				if(endedAt!=null){
					if (endedAt.getTime() > end.getTime()) {
						endedAt=end;
					}
				}else{
					if (current.getTime() >= start.getTime() && current.getTime() < end.getTime()) {
						endedAt=current;
					}else{
						endedAt=end;	
					}
				}
				Long duration = (endedAt.getTime()-startedAt.getTime())/1000;
				
				//SE ALMACENA EL LOGUEO
				for (ReportACDCampaign reportACDCampaign : reportsACDCampaign) {
					if (reportACDCampaign.getCampaignId().equals(sessionCampaign.getCampaign().getId())) {
						reportACDCampaign.setSessionsTime(reportACDCampaign.getSessionsTime()+duration.intValue());
						switch (sessionCampaign.getSessionSessionType().getSessionType()) {
						case AVAILABLE:
							reportACDCampaign.setSessionsAvailableTime(reportACDCampaign.getSessionsAvailableTime()+duration.intValue());
							break;
						case BREAK:
							reportACDCampaign.setSessionsBreakTime(reportACDCampaign.getSessionsBreakTime()+duration.intValue());
							break;
						case PAUSE:
							reportACDCampaign.setSessionsPauseTime(reportACDCampaign.getSessionsPauseTime()+duration.intValue());
							break;	
						default:
							break;
						}
						break;
					}
				}
		
			}
			
			List<Call> calls = callEao.findByReport(start, end);
			for (Call call : calls) {
				for (ReportACDCampaign reportACDCampaign : reportsACDCampaign) {
					if (call.getQueue().getCampaign().getId().equals(reportACDCampaign.getCampaignId())) {
						
						reportACDCampaign.setCallsInbound(reportACDCampaign.getCallsComplete()+1);
						
						switch (call.getCallState()) {
						case ABANDON:
							reportACDCampaign.setCallsAbandon(reportACDCampaign.getCallsAbandon()+1);
							if (call.getWaittime()<5) {
								reportACDCampaign.setCallsAbandon5(reportACDCampaign.getCallsAbandon5()+call.getWaittime());	
							}
							reportACDCampaign.setCallsAbandonTime(reportACDCampaign.getCallsAbandonTime()+call.getWaittime());
							
							break;
						case COMPLETEAGENT:
							reportACDCampaign.setCallsComplete(reportACDCampaign.getCallsComplete()+1);
							reportACDCampaign.setCallsConversationTime(reportACDCampaign.getCallsConversationTime()+call.getTalktime());
							
							if (call.getWaittime()<20) {
								reportACDCampaign.setCallsWaiting20(reportACDCampaign.getCallsWaiting20()+1);
								reportACDCampaign.setCallsWaitingTime20(reportACDCampaign.getCallsWaitingTime20()+call.getWaittime());
							}else if (call.getWaittime()>=20 && call.getWaittime()<24){
								reportACDCampaign.setCallsWaiting24(reportACDCampaign.getCallsWaiting24()+1);
								reportACDCampaign.setCallsWaitingTime24(reportACDCampaign.getCallsWaitingTime24()+call.getWaittime());
							}else if (call.getWaittime()>=24 && call.getWaittime()<30){
								reportACDCampaign.setCallsWaiting30(reportACDCampaign.getCallsWaiting30()+1);
								reportACDCampaign.setCallsWaitingTime30(reportACDCampaign.getCallsWaitingTime30()+call.getWaittime());
							}
							
							reportACDCampaign.setCallsWaitingTime(reportACDCampaign.getCallsWaitingTime()+call.getWaittime());
							
							break;
						case COMPLETECALLER:
							reportACDCampaign.setCallsComplete(reportACDCampaign.getCallsComplete()+1);
							reportACDCampaign.setCallsConversationTime(reportACDCampaign.getCallsConversationTime()+call.getTalktime());
							
							if (call.getWaittime()<20) {
								reportACDCampaign.setCallsWaiting20(reportACDCampaign.getCallsWaiting20()+1);
								reportACDCampaign.setCallsWaitingTime20(reportACDCampaign.getCallsWaitingTime20()+call.getWaittime());
							}else if (call.getWaittime()>=20 && call.getWaittime()<24){
								reportACDCampaign.setCallsWaiting24(reportACDCampaign.getCallsWaiting24()+1);
								reportACDCampaign.setCallsWaitingTime24(reportACDCampaign.getCallsWaitingTime24()+call.getWaittime());
							}else if (call.getWaittime()>=24 && call.getWaittime()<30){
								reportACDCampaign.setCallsWaiting30(reportACDCampaign.getCallsWaiting30()+1);
								reportACDCampaign.setCallsWaitingTime30(reportACDCampaign.getCallsWaitingTime30()+call.getWaittime());
							}
							
							reportACDCampaign.setCallsWaitingTime(reportACDCampaign.getCallsWaitingTime()+call.getWaittime());
							
							break;	

						default:
							break;
						}
					}
				}
				
			}
			
			for (ReportACDCampaign reportACDCampaign : reportsACDCampaign) {
				reportACDCampaignEao.add(reportACDCampaign);
			}
			
		} catch (Exception e) {
		 e.printStackTrace();
		}
		
	}
	

}
