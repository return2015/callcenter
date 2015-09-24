package com.returnsoft.callcenter.service.local;

import com.returnsoft.callcenter.exception.ServiceException;

public interface ReportService {
	
	public void reportACDCampaignBy5Minutes() throws ServiceException;
	
	public void reportACDCampaignBy30Minutes() throws ServiceException;
	
	public void reportACDCampaignBy360Minutes() throws ServiceException;
	
	public void reportACDUserBy5Minutes() throws ServiceException;
	
	public void reportACDUserBy30Minutes() throws ServiceException;
	
	public void reportACDUserBy360Minutes() throws ServiceException;
	

}
