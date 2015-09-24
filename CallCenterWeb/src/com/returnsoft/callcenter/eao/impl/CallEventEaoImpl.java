package com.returnsoft.callcenter.eao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.CallEventEao;
import com.returnsoft.callcenter.entity.CallEvent;
import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;
import com.returnsoft.callcenter.exception.EaoException;
@Stateless
public class CallEventEaoImpl implements CallEventEao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(CallEvent callEvent) throws EaoException {
		try {
			em.persist(callEvent);
			em.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
		
	}
	
	@Override
	public CallEvent getLastHoldByUniqueId(Double uniqueid) throws EaoException {
		try {
			
			TypedQuery<CallEvent> q = em.createQuery(
					"SELECT ce FROM CallEvent ce "
					+ "WHERE ce.callEventType=:callEventType and ce.uniqueid = :uniqueid order by ce.createdAt desc", CallEvent.class);
			
			q.setParameter("callEventType", CallEventTypeEnum.HOLD);
			q.setParameter("uniqueid", uniqueid);
			q.setMaxResults(1);
			CallEvent callEventHold = q.getSingleResult();
			
			return callEventHold;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	

}
