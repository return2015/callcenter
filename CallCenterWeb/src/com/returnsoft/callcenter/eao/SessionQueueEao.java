package com.returnsoft.callcenter.eao;

import com.returnsoft.callcenter.entity.SessionQueue;
import com.returnsoft.callcenter.exception.EaoException;


public interface SessionQueueEao {
	
	public void add(SessionQueue sessionQueue) throws EaoException;
	
	public SessionQueue update(SessionQueue sessionQueue) throws EaoException;
	
	//public List<SessionQueue> findByReport(Date start, Date end) throws EaoException;

}
