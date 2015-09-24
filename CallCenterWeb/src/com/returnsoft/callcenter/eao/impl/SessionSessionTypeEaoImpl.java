package com.returnsoft.callcenter.eao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.SessionSessionTypeEao;
import com.returnsoft.callcenter.entity.SessionSessionType;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class SessionSessionTypeEaoImpl implements SessionSessionTypeEao{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(SessionSessionType sessionSessionType) throws EaoException{

		try {

			em.persist(sessionSessionType);
			em.flush();


		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public SessionSessionType update(SessionSessionType sessionSessionType) throws EaoException{

		try {

			SessionSessionType newSessionSessionType = em.merge(sessionSessionType);
			em.flush();

			return newSessionSessionType;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}

	@Override
	public SessionSessionType findOpenSessionByPeer(Short peer)
			throws EaoException {
		try {
			
			TypedQuery<SessionSessionType> q = em.createQuery(
					"SELECT sst FROM SessionSessionType sst "
					+ "WHERE sst.endedAt is null and sst.peer = :peer", SessionSessionType.class);
			
			q.setParameter("peer", peer);
			
			SessionSessionType sessionSessionType = q.getSingleResult();
			
			return sessionSessionType;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

	/*@Override
	public List<SessionSessionType> findByReport(Date start, Date end)
			throws EaoException {
		try {
			
			TypedQuery<SessionSessionType> q = em.createQuery(
					"SELECT sst FROM SessionSessionType sst "
					+ "WHERE sst.startedAt < :end and (sst.endedAt is null or sst.endedAt >= :start)", SessionSessionType.class);
			
			q.setParameter("start", start);
			q.setParameter("end", end);
			
			List<SessionSessionType> sessionsSessionType = q.getResultList();
			
			return sessionsSessionType;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}*/
	

}
