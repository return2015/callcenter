package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.returnsoft.callcenter.enumeration.SessionTypeEnum;

public class SessionSessionTypeDto implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6538298834334258500L;

	private Integer id;
	
	private Date endedAt;

	private Date startedAt;

	private SessionTypeEnum sessionType;
	
	private SessionDto session;
	
	private List<SessionCampaignDto> sessionsCampaign;
	
	private String host;
	
	private Short peer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}
	
	public String getCampaignNames(){
		String campaignNames="";
		
		if (this.sessionsCampaign!=null && this.sessionsCampaign.size()>0) {
			int count=0;
			for (SessionCampaignDto sessionCampaignDto : this.sessionsCampaign) {
				if (count==0) {
					campaignNames += sessionCampaignDto.getCampaign().getName();
					count++;
				}else{
					campaignNames += ", "+sessionCampaignDto.getCampaign().getName();
				}
				
			}
			
		}
				
				
		return campaignNames;		
	}
	
	
	public String getDuration() {
		
		if (startedAt!=null && endedAt==null) {
			//CUANDO LA SESION ESTA ABIERTA
			Date current = new Date();
			Long diff = current.getTime()-startedAt.getTime();
			long diffSeconds = diff / 1000 % 60;
		    long diffMinutes = diff / (60 * 1000) % 60;
		    long diffHours = diff / (60 * 60 * 1000);
		    String duration = ((diffHours==0)?"00":(diffHours<10?"0"+diffHours:""+diffHours))
		    					+":"+((diffMinutes==0)?"00":(diffMinutes<10?"0"+diffMinutes:""+diffMinutes))
		    					+":"+((diffSeconds==0)?"00":(diffSeconds<10?"0"+diffSeconds:""+diffSeconds));
			
			return duration;
			
		}else if(startedAt!=null && endedAt!=null){
			//CUANDO LA SESION ESTA CERRADA
			Long diff = endedAt.getTime()-startedAt.getTime();
			long diffSeconds = diff / 1000 % 60;
		    long diffMinutes = diff / (60 * 1000) % 60;
		    long diffHours = diff / (60 * 60 * 1000);
		    String duration = ((diffHours==0)?"00":(diffHours<10?"0"+diffHours:""+diffHours))
		    					+":"+((diffMinutes==0)?"00":(diffMinutes<10?"0"+diffMinutes:""+diffMinutes))
		    					+":"+((diffSeconds==0)?"00":(diffSeconds<10?"0"+diffSeconds:""+diffSeconds));
			
			return duration;
		}else{
			return null;
		}
		
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public SessionTypeEnum getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
	}

	public SessionDto getSession() {
		return session;
	}

	public void setSession(SessionDto session) {
		this.session = session;
	}

	
	public List<SessionCampaignDto> getSessionsCampaign() {
		return sessionsCampaign;
	}

	public void setSessionsCampaign(List<SessionCampaignDto> sessionsCampaign) {
		this.sessionsCampaign = sessionsCampaign;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Short getPeer() {
		return peer;
	}

	public void setPeer(Short peer) {
		this.peer = peer;
	}

	
	
	

}
