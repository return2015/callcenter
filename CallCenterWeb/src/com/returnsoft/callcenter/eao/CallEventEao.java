package com.returnsoft.callcenter.eao;

import com.returnsoft.callcenter.entity.CallEvent;
import com.returnsoft.callcenter.exception.EaoException;

public interface CallEventEao {
	
	public void add(CallEvent callEvent) throws EaoException;
	
	public CallEvent getLastHoldByUniqueId(Double uniqueid) throws EaoException;

}
