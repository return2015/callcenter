package com.returnsoft.callcenter.eao;

import com.returnsoft.callcenter.entity.SessionSessionType;
import com.returnsoft.callcenter.exception.EaoException;


public interface SessionSessionTypeEao {
	
	public void add(SessionSessionType sessionSessionType) throws EaoException;
	
	public SessionSessionType update(SessionSessionType sessionSessionType) throws EaoException;
	
	public SessionSessionType findOpenSessionByPeer(Short peer) throws EaoException;
	
	

}
