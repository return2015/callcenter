package com.returnsoft.callcenter.eao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.CallEao;
import com.returnsoft.callcenter.entity.Call;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class CallEaoImpl implements CallEao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Call call) throws EaoException {
		try {

			em.persist(call);
			em.flush();
		
	}  catch (Exception e) {
		e.printStackTrace();
		throw new EaoException(e);
	}
		
	}

	@Override
	public Call findByUniqueId(Double uniqueid) throws EaoException {
		try {
			
			TypedQuery<Call> q = em.createQuery(
					"SELECT c FROM Call c "
					+ "WHERE c.uniqueid = :uniqueid", Call.class);
			
			q.setParameter("uniqueid", uniqueid);
			
			Call call = q.getSingleResult();
			
			return call;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

	@Override
	public Call update(Call call) throws EaoException {
		try {

			Call newCall = em.merge(call);
			em.flush();

			return newCall;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
		
	}
	
	@Override
	public List<Call> findByReport(Date start, Date end)
			throws EaoException {
		try {
			
			TypedQuery<Call> q = em.createQuery(
					"SELECT c FROM Call c "
					+ "WHERE c.startedAt between :start and :end", Call.class);
			
			q.setParameter("start", start);
			q.setParameter("end", end);
			
			List<Call> calls = q.getResultList();
			
			return calls;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	
}
