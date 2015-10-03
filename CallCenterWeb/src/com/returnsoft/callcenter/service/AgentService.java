package com.returnsoft.callcenter.service;

import java.util.List;

import com.returnsoft.callcenter.dto.AgentInfoDto;
import com.returnsoft.callcenter.enumeration.SessionTypeEnum;
import com.returnsoft.callcenter.exception.ServiceException;

public interface AgentService {

	// //////

	public void changeSessionType(int userId, short sessionTypeSelected)
			throws ServiceException;

	public void loginAgent(int userId, String host) throws ServiceException;

	public void logoutAgent(int userId) throws ServiceException;
	
	public void restartAgent(int userId) throws ServiceException;

	// ///////

	public AgentInfoDto getAgentInfo(int userId) throws ServiceException;

	public List<SessionTypeEnum> findSessionTypes(int userId)
			throws ServiceException;

}
