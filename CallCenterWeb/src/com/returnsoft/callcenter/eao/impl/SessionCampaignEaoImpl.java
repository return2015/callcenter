package com.returnsoft.callcenter.eao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.SessionCampaignEao;
import com.returnsoft.callcenter.entity.SessionCampaign;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class SessionCampaignEaoImpl implements SessionCampaignEao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(SessionCampaign sessionCampaign) throws EaoException{

		try {

			em.persist(sessionCampaign);
			em.flush();

			

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public SessionCampaign update(SessionCampaign sessionCampaign) throws EaoException{

		try {

			SessionCampaign newSessionCampaign = em.merge(sessionCampaign);
			em.flush();

			return newSessionCampaign;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public List<SessionCampaign> findLoginsForReportACDCampaign(Date start, Date end)
			throws EaoException {
		try {
			
			TypedQuery<SessionCampaign> q = em.createQuery(
					"SELECT sc FROM SessionCampaign sc "
					+ "left join fetch sc.campaign c "
					+ "left join fetch sc.sessionSessionType sst "
					+ "WHERE sst.startedAt < :end and (sst.endedAt is null or sst.endedAt >= :start)", SessionCampaign.class);
			
			q.setParameter("start", start);
			q.setParameter("end", end);
			
			List<SessionCampaign> sessionsCampaign = q.getResultList();
			
			return sessionsCampaign;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	@Override
	public List<SessionCampaign> findLoginsForReportACDUser(Date start, Date end)
			throws EaoException {
		try {
			
			TypedQuery<SessionCampaign> q = em.createQuery(
					"SELECT sc FROM SessionCampaign sc "
					+ "left join fetch sc.sessionSessionType sst "
					+ "left join fetch sst.session s "
					+ "left join fetch s.user u "		
					+ "WHERE sst.startedAt < :end and (sst.endedAt is null or sst.endedAt >= :start)", SessionCampaign.class);
			
			q.setParameter("start", start);
			q.setParameter("end", end);
			
			List<SessionCampaign> sessionsCampaign = q.getResultList();
			
			return sessionsCampaign;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	

}
