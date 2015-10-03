package com.returnsoft.callcenter.service;

import java.util.List;

import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.ServiceException;

public interface SupervisorService {
	
	public void loginSupervisor(int userId) throws ServiceException;
	
	public List<UserDto> findUsers(String username, List<UserTypeEnum> userTypes, List<Short> campaigns, int supervisor)
			throws ServiceException;
	
	
	

}
