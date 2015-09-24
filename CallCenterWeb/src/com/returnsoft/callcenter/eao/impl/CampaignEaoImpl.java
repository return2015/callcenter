package com.returnsoft.callcenter.eao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.CampaignEao;
import com.returnsoft.callcenter.entity.Campaign;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class CampaignEaoImpl implements CampaignEao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Campaign> findAll() throws EaoException {
		try {
			TypedQuery<Campaign> q = em.createQuery(
					"SELECT c FROM Campaign c ", Campaign.class);
			List<Campaign> campaigns = q.getResultList();

			return campaigns;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}


}
