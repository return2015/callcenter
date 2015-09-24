package com.returnsoft.callcenter.eao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.ServerEao;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
import com.returnsoft.callcenter.exception.EaoException;

@Stateless
public class ServerEaoImpl implements ServerEao{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Server> findAll() throws EaoException {
		try {

			TypedQuery<Server> q = em.createQuery(
					"SELECT s FROM Server s ", Server.class);
			List<Server> servers = q.getResultList();

			return servers;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	@Override
	public Server findById(Short serverId) throws EaoException{
		try {
			
			Server server = em.find(Server.class, serverId);

			return server;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	@Override
	public List<Server> findServersByCampaigns(List<Short> campaignsId) throws EaoException{
		try {
			
			TypedQuery<Server> q = em.createQuery(
					"SELECT s FROM Campaign c left join c.server s "
					+ "WHERE c.id in :campaignsId", Server.class);
			q.setParameter("campaignsId", campaignsId);
			List<Server> servers = q.getResultList();
			
			return servers;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	

}
