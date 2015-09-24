package com.returnsoft.callcenter.eao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.QueueEao;
import com.returnsoft.callcenter.entity.Queue;
import com.returnsoft.callcenter.exception.EaoException;
@Stateless
public class QueueEaoImpl implements QueueEao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Queue findByName(String queueName) throws EaoException {
		try {
			
			TypedQuery<Queue> q = em.createQuery(
					"SELECT q FROM Queue q "
					+ "WHERE q.name = :queueName", Queue.class);
			
			q.setParameter("queueName", queueName);
			
			Queue queue = q.getSingleResult();
			
			return queue;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

}
