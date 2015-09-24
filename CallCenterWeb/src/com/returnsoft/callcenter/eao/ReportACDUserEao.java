package com.returnsoft.callcenter.eao;

import java.util.Date;

import com.returnsoft.callcenter.entity.ReportACDUser;
import com.returnsoft.callcenter.exception.EaoException;

public interface ReportACDUserEao {
	
	public void add(ReportACDUser reportACDUser) throws EaoException;
	
	public void deleteByPeriod(Date period, Short interval) throws EaoException;

}
