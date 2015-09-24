package com.returnsoft.callcenter.eao;


import java.util.Date;

import com.returnsoft.callcenter.entity.ReportACDCampaign;
import com.returnsoft.callcenter.exception.EaoException;

public interface ReportACDCampaignEao {
	
	public void add(ReportACDCampaign reportACDCampaign) throws EaoException;
	
	//public List<ReportACDCampaign> findByPeriod(Date period) throws EaoException;
	
	public void deleteByPeriod(Date period, Short interval) throws EaoException;

}
