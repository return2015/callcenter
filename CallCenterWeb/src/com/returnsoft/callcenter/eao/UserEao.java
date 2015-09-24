package com.returnsoft.callcenter.eao;

import java.util.List;

import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
//import com.returnsoft.callcenter.entity.UserType;
import com.returnsoft.callcenter.exception.EaoException;


public interface UserEao {
	
	public User findByUsername(String username) throws EaoException;
	
	public User findById(int userId) throws EaoException;
	
	public List<User> findSupervisorsByCampaigns(List<Short> campaignsId) throws EaoException;
	
	public List<User> find(String firstname, String lastname, String username, List<Short> campaignsId, List<UserTypeEnum> userTypesId, int supervisorId) throws EaoException;
	
	public List<User> find(String username, List<Short> campaignsId, List<UserTypeEnum> userTypesId, int supervisorId) throws EaoException;
	
	public void add(User user) throws EaoException;
	
	public User update(User user) throws EaoException;
	
	public List<User> getActiveAgents() throws EaoException;
	
	public User getAgentInfo(int userId) throws EaoException;

}
