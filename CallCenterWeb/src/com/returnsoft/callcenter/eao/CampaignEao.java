package com.returnsoft.callcenter.eao;

import java.util.List;

import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.exception.EaoException;



public interface CampaignEao {
	
	public List<Campaign> findAll() throws EaoException;

}
