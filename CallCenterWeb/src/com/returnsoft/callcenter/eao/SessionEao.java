package com.returnsoft.callcenter.eao;

import java.util.Date;
import java.util.List;

import com.returnsoft.callcenter.entity.Session;
import com.returnsoft.callcenter.exception.EaoException;


public interface SessionEao {

	public void add(Session session) throws EaoException;
	
	public Session update(Session session) throws EaoException;
	
	public List<Session> findOpenSessionByHost(String host) throws EaoException;
	
	public List<Session> findLoginsByCampaign(Short campaignId, Date start, Date end) throws EaoException;
	
	//public Session getSessionForAgent(int sessionId) throws EaoException;


}
