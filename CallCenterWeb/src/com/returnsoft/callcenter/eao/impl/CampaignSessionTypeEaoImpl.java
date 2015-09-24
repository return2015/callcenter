package com.returnsoft.callcenter.eao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.CampaignSessionTypeEao;
import com.returnsoft.callcenter.entity.CampaignSessionType;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class CampaignSessionTypeEaoImpl implements CampaignSessionTypeEao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<CampaignSessionType> findSessionTypesByCampaigns(
			List<Short> campaignsId) throws EaoException {
		try {
			
			
			TypedQuery<CampaignSessionType> q = em.createQuery(
					"SELECT cst FROM CampaignSessionType cst left join cst.campaign c "
					+ "WHERE c.id in :campaignsId", CampaignSessionType.class);
			
			q.setParameter("campaignsId", campaignsId);
			
			List<CampaignSessionType> campaignSessionTypes = q.getResultList();
			
			return campaignSessionTypes;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

}
