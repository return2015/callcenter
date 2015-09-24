package com.returnsoft.callcenter.eao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.returnsoft.callcenter.eao.SessionQueueEao;
import com.returnsoft.callcenter.entity.SessionQueue;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class SessionQueueEaoImpl implements SessionQueueEao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(SessionQueue sessionQueue) throws EaoException{

		try {

			em.persist(sessionQueue);
			em.flush();

			

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public SessionQueue update(SessionQueue sessionQueue) throws EaoException{

		try {

			sessionQueue = em.merge(sessionQueue);
			em.flush();

			return sessionQueue;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	
	

}
