package com.returnsoft.callcenter.service.impl;

import java.util.Date;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.callcenter.eao.CallEao;
import com.returnsoft.callcenter.eao.CallEventEao;
import com.returnsoft.callcenter.eao.QueueEao;
import com.returnsoft.callcenter.eao.SessionSessionTypeEao;
import com.returnsoft.callcenter.eao.UserEao;
import com.returnsoft.callcenter.entity.Call;
import com.returnsoft.callcenter.entity.CallEvent;
import com.returnsoft.callcenter.entity.Queue;
import com.returnsoft.callcenter.entity.SessionSessionType;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.service.local.CallEventService;
@Stateless
public class CallEventServiceImpl implements CallEventService {
	
	@EJB
	private CallEao callEao;
	
	@EJB
	private UserEao userEao;
	
	@EJB
	private QueueEao queueEao;
	
	@EJB
	private CallEventEao callEventEao;
	
	@EJB
	private SessionSessionTypeEao sessionSessionTypeEao;

	@Override
	@Asynchronous
	public void addEvent(CallEvent callEvent){
		try {
			
			callEventEao.add(callEvent);
			
			switch (callEvent.getCallEventType()) {
			case ENTERQUEUE:
				Call call = new Call();
				call.setNumber(Integer.parseInt(callEvent.getCallerID()));
				Queue queue = queueEao.findByName(callEvent.getQueue());
				if (queue!=null) {
					call.setQueue(queue);	
				}
				call.setUniqueid(callEvent.getUniqueid());
				Date startedAt = new Date((long)(callEvent.getUniqueid()*1000));
				call.setStartedAt(startedAt);
				call.setCallState(callEvent.getCallEventType());
				callEao.add(call);
				break;
			case CONNECT:
				Call callJoin = callEao.findByUniqueId(callEvent.getUniqueid());
				if (callJoin!=null) {
					callJoin.setWaittime(callEvent.getWaittime());
					callJoin.setRingtime(callEvent.getRingtime());
					callJoin.setPeer(Short.parseShort(callEvent.getPeer().split("/")[1]));
					callJoin.setCallState(callEvent.getCallEventType());
					SessionSessionType sessionSessionType = sessionSessionTypeEao.findOpenSessionByPeer(Short.parseShort(callEvent.getPeer().split("/")[1]));
					if (sessionSessionType!=null) {
						User user = sessionSessionType.getSession().getUser();
						callJoin.setUser(user);
						user.setCurrentCall(callJoin);
						userEao.update(user);
					}
					callEao.update(callJoin);
				}
				break;
			case COMPLETEAGENT:
				Call callConnect = callEao.findByUniqueId(callEvent.getUniqueid());
				if (callConnect!=null) {
					callConnect.setTalktime(callEvent.getTalktime());
					callConnect.setCallState(callEvent.getCallEventType());
					callEao.update(callConnect);
					//
					if (callConnect.getUser()!=null) {
						User user = callConnect.getUser();
						user.setCurrentCall(null);
						userEao.update(user);
					}
				}
				break;
			case COMPLETECALLER:
				Call callConnect2 = callEao.findByUniqueId(callEvent.getUniqueid());
				if (callConnect2!=null) {
					callConnect2.setTalktime(callEvent.getTalktime());
					callConnect2.setCallState(callEvent.getCallEventType());
					callEao.update(callConnect2);
					//
					if (callConnect2.getUser()!=null) {
						User user = callConnect2.getUser();
						user.setCurrentCall(null);
						userEao.update(user);
					}
				}
				break;
			case UNHOLD:
				CallEvent callEventHoldStart = callEventEao.getLastHoldByUniqueId(callEvent.getUniqueid());
				if (callEventHoldStart!=null) {
					Long timeLong = (callEvent.getCreatedAt().getTime() - callEventHoldStart.getCreatedAt().getTime())/1000;
					Call callConnect3 = callEao.findByUniqueId(callEvent.getUniqueid());
					if (callConnect3!=null) {
						if (callConnect3.getHoldtime()==null) {
							callConnect3.setHoldtime((short)0);
						}
						callConnect3.setHoldtime((short)(callConnect3.getHoldtime()+timeLong.shortValue()));
						callEao.update(callConnect3);
					}
				}
				break;
			case ABANDON:
				Call callJoin2 = callEao.findByUniqueId(callEvent.getUniqueid());
				if (callJoin2!=null) {
					callJoin2.setWaittime(callEvent.getWaittime());
					callJoin2.setCallState(callEvent.getCallEventType());
					callEao.update(callJoin2);
				}
				break;
			default:
				break;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
