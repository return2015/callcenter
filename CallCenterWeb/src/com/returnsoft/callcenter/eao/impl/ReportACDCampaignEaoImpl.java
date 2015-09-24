package com.returnsoft.callcenter.eao.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.returnsoft.callcenter.eao.ReportACDCampaignEao;
import com.returnsoft.callcenter.entity.ReportACDCampaign;
import com.returnsoft.callcenter.exception.EaoException;
@Stateless
public class ReportACDCampaignEaoImpl implements ReportACDCampaignEao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(ReportACDCampaign reportACDCampaign) throws EaoException {
		try {

			em.persist(reportACDCampaign);
			em.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
		
	}

	@Override
	public void deleteByPeriod(Date period, Short interval) throws EaoException {
		try {
			Query query = em.createQuery("DELETE FROM ReportACDCampaign rc WHERE rc.period=:period and rc.interval=:interval");
			query.setParameter("period", period);
			query.setParameter("interval", interval);
	        query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
		
	}

}
