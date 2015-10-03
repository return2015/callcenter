package com.returnsoft.callcenter.service;

import java.util.List;

import com.returnsoft.callcenter.dto.CampaignDto;
import com.returnsoft.callcenter.dto.ServerDto;
import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.ServiceException;

public interface UserService {
	
	public UserDto loginUser(String username, String password) throws ServiceException;
	
	//
	public List<CampaignDto> getAllCampaigns()
			throws ServiceException;
	
	public List<CampaignDto> findCampaignsByUser(int userId)
			throws ServiceException;
	
	public List<CampaignDto> findCampaignsByServer(Short serverId)
			throws ServiceException;
	
	//
	public List<ServerDto> findServersByCampaigns(List<Short> campaignsId)
			throws ServiceException;
	
	public List<ServerDto> getAllServers()
			throws ServiceException;
	
	//
	public List<UserDto> findSupervisorsByCampaigns(List<Short> campaignsId)
			throws ServiceException;
	
	public List<UserDto> findUsers(String firstname, String lastname, String username, List<UserTypeEnum> userTypes, List<Short> campaigns, int supervisor)
			throws ServiceException;
	
	public UserDto findUserById(Integer userId) throws ServiceException;
	
	public void addUser(UserDto userDto) throws ServiceException;
	
	public void editUser(UserDto userDto) throws ServiceException;
	
	//
	public List<SessionTypeEnum> findSessionTypesByCampaigns(List<Short> campaignsId)
			throws ServiceException;
	
}
