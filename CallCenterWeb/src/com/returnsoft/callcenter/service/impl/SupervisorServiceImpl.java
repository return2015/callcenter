package com.returnsoft.callcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.eao.UserEao;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.ServiceException;
import com.returnsoft.callcenter.exception.UserCampaignNotFoundException;
import com.returnsoft.callcenter.exception.UserNotFoundException;
import com.returnsoft.callcenter.service.ConversionService;
import com.returnsoft.callcenter.service.SupervisorService;

@Stateless
//@Remote(SupervisorService.class)
public class SupervisorServiceImpl implements SupervisorService {

	@EJB
	private UserEao userEao;

	@EJB
	private ConversionService conversionService;
	
	@Override
	public void loginSupervisor(int userId) throws ServiceException {
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
	public List<UserDto> findUsers(String username, List<UserTypeEnum> userTypesId, List<Short> campaignsId, int supervisorId)
			throws ServiceException{
		try {
			
			List<User> users = userEao.find(username, campaignsId, userTypesId, supervisorId);
			List<UserDto> usersDto = null;
			if (users != null) {
				usersDto = new ArrayList<UserDto>();
				for (User user : users) {
					
					UserDto userDto = conversionService
							.fromUserToDashboard(user);
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
	
	
	
	

}
