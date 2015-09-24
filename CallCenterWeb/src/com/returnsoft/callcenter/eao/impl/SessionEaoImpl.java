package com.returnsoft.callcenter.eao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.SessionEao;
import com.returnsoft.callcenter.entity.Session;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class SessionEaoImpl implements SessionEao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Session session) throws EaoException {

		try {

			em.persist(session);
			em.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public Session update(Session session) throws EaoException {

		try {

			Session newSession = em.merge(session);
			em.flush();

			return newSession;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}


	@Override
	public List<Session> findOpenSessionByHost(String host) throws EaoException{
		try {

			TypedQuery<Session> q = em.createQuery(
					"SELECT s FROM Session s left join s.currentSessionSessionType csst "
					+ "WHERE s.endedAt is null and csst.host = :host", Session.class);
			q.setParameter("host", host);
			List<Session> sessions = q.getResultList();

			return sessions;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
		
	}
	
	@Override
	public List<Session> findLoginsByCampaign(Short campaignId, Date start, Date end)
			throws EaoException {
		try {
			
			TypedQuery<Session> q = em.createQuery(
					"SELECT s FROM Session s "
					+ "left join s.currentSessionSessionType csst "
					+ "left join csst.sessionsCampaign sc "		
					+ "left join sc.campaign c "		
					+ "WHERE c.id= :campaignId and s.startedAt < :end and (s.endedAt is null or s.endedAt >= :start)", Session.class);
			
			q.setParameter("campaignId", campaignId);
			q.setParameter("start", start);
			q.setParameter("end", end);
			
			List<Session> sessions = q.getResultList();
			
			return sessions;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

	/*@Override
	public Session getSessionForAgent(int sessionId) throws EaoException {
		try {
			
			TypedQuery<Session> q = em.createQuery(
					"SELECT s FROM Session s "
					+ "left join fetch s.currentSessionSessionType csst "
					+ "left join fetch csst.sessionsCampaign sc "
					+ "left join fetch sc.campaign cmp "
					+ "left join fetch s.sessionsSessionType sst "
					+ "WHERE s.id = :sessionId ", Session.class);
			q.setParameter("sessionId", sessionId);
			
			Session session = q.getSingleResult();

			return session;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}*/

}
