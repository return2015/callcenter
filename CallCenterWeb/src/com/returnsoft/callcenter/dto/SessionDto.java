package com.returnsoft.callcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SessionDto implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3143647911669010693L;

	private Integer id;

	private Date endedAt;

	private Date startedAt;

	private UserDto user;
		
	private List<SessionSessionTypeDto> sessionsSessionType;
	
	private SessionSessionTypeDto currentSessionSessionType;
	
	public SessionDto() {

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEndedAt() {
		return this.endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}

	public Date getStartedAt() {
		return this.startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}


	public List<SessionSessionTypeDto> getSessionsSessionType() {
		return sessionsSessionType;
	}

	public void setSessionsSessionType(
			List<SessionSessionTypeDto> sessionsSessionType) {
		this.sessionsSessionType = sessionsSessionType;
	}

	public SessionSessionTypeDto getCurrentSessionSessionType() {
		return currentSessionSessionType;
	}

	public void setCurrentSessionSessionType(
			SessionSessionTypeDto currentSessionSessionType) {
		this.currentSessionSessionType = currentSessionSessionType;
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


	

}