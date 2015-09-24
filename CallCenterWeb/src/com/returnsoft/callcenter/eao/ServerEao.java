package com.returnsoft.callcenter.eao;

import java.util.List;

import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.exception.EaoException;


public interface ServerEao {
	
	public List<Server> findAll() throws EaoException;
	
	public Server findById(Short serverId) throws EaoException;
	
	public List<Server> findServersByCampaigns(List<Short> campaignsId) throws EaoException;

}
