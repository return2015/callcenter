package com.returnsoft.callcenter.service.local;

import com.returnsoft.callcenter.dto.CampaignDto;
import com.returnsoft.callcenter.dto.ServerDto;
//import com.returnsoft.callcenter.dto.SessionTypeDto;
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.exception.ConversionException;

public interface ConversionService {
	
	public ServerDto fromServerToSelect(Server server) 
			throws ConversionException;
	
	public UserDto fromUserToDashboard(User user)
			throws ConversionException;
	
	public UserDto fromUserToTable(User user)
			throws ConversionException;
	
	public UserDto fromUserToLogin(User user)
			throws ConversionException;
	
	public UserDto fromUserToSelect(User user)
			throws ConversionException;
	
	/*public SessionTypeDto fromSessionTypeToSelect(SessionType sessionType)
			throws ConversionException;*/
	
	public CampaignDto fromCampaignToSelect(Campaign campaign)
			throws ConversionException;
	
	/////////////
	
	public User toUser(UserDto userDto)
			throws ConversionException;
	
	

}
