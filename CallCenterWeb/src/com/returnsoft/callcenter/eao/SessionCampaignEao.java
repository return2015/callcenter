package com.returnsoft.callcenter.eao;

import java.util.Date;
import java.util.List;

import com.returnsoft.callcenter.entity.SessionCampaign;
import com.returnsoft.callcenter.exception.EaoException;


public interface SessionCampaignEao {
	
	public void add(SessionCampaign sessionCampaign) throws EaoException;
	
	public SessionCampaign update(SessionCampaign sessionCampaign) throws EaoException;
	
	public List<SessionCampaign> findLoginsForReportACDCampaign(Date start, Date end) throws EaoException;
	
	public List<SessionCampaign> findLoginsForReportACDUser(Date start, Date end) throws EaoException;

}
