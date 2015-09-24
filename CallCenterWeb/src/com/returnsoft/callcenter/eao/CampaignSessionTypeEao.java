package com.returnsoft.callcenter.eao;

import java.util.List;

import com.returnsoft.callcenter.entity.CampaignSessionType;
import com.returnsoft.callcenter.exception.EaoException;

public interface CampaignSessionTypeEao {
	
	public List<CampaignSessionType> findSessionTypesByCampaigns(List<Short> campaignsId) throws EaoException;

}
