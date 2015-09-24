package com.returnsoft.callcenter.eao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.callcenter.eao.UserEao;
import com.returnsoft.callcenter.entity.User;
import com.returnsoft.callcenter.enumeration.UserTypeEnum;
// import com.returnsoft.callcenter.entity.UserType;
import com.returnsoft.callcenter.exception.EaoException;


@Stateless
public class UserEaoImpl implements UserEao{
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(User user) throws EaoException {

		try {

				em.persist(user);
				em.flush();
			
		}  catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public User update(User user) throws EaoException {

		try {

			User newUser = em.merge(user);
			em.flush();

			return newUser;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	
	@Override
	public User findById(int userId) throws EaoException{
		try {
			
			User user = em.find(User.class, userId);

			return user;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	@Override
	public User findByUsername(String username) throws EaoException {
		try {

			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u WHERE u.username = :username", User.class);
			q.setParameter("username", username);
			User user = q.getSingleResult();

			return user;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}

	}
	
	@Override
	public List<User> findSupervisorsByCampaigns(List<Short> campaignsId) throws EaoException{
		try {
			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u left join u.campaigns c "
					+ "WHERE u.userType = :userType and c.id in :campaignsId", User.class);
			q.setParameter("campaignsId", campaignsId);
			q.setParameter("userType", UserTypeEnum.SUPERVISOR);
			
			List<User> users = q.getResultList();
			return users;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	@Override
	public List<User> find(String firstname, String lastname, String username, List<Short> campaignsId, List<UserTypeEnum> userTypesId, int supervisorId) throws EaoException{
		try {
			
			String query="SELECT distinct u FROM User u left join fetch u.campaigns cv left join u.campaigns cs left join u.supervisor s where u.id>0 ";
			if (firstname!=null && firstname.trim().length()>0) {
				query+="and u.firstname like :firstname ";
			}
			if (lastname!=null && lastname.trim().length()>0) {
				query+="and u.lastname like :lastname ";
			}
			if (username!=null && username.trim().length()>0) {
				query+="and u.username like :username ";
			}
			if (supervisorId>0) {
				query+="and s.id = :supervisorId ";
			}
			
			/*boolean isAdmin=false;
			for (Integer userTypeId : userTypesId) {
				if (userTypeId.equals(UserTypeEnum.ADMINISTRATOR.getId())) {
					isAdmin=true;
				}
			}
			
			if ((isAdmin && campaignsId.size()==1) || !isAdmin) {
				query+="and cs.id in :campaignsId ";
			}*/
			if (campaignsId!=null && campaignsId.size()>0) {
				query+="and cs.id in :campaignsId ";
			}
			
			query+="and u.userType in :userTypesId ";
			
			TypedQuery<User> q = em.createQuery(query, User.class);
			
			if (firstname!=null && firstname.trim().length()>0) {
				q.setParameter("firstname", "%"+firstname+"%");
			}
			if (lastname!=null && lastname.trim().length()>0) {
				q.setParameter("lastname", "%"+lastname+"%");
			}
			if (username!=null && username.trim().length()>0) {
				q.setParameter("username", "%"+username+"%");
			}
			/*if ((isAdmin && campaignsId.size()==1) || !isAdmin) {
				q.setParameter("campaignsId", campaignsId);
			}*/
			if (campaignsId!=null && campaignsId.size()>0) {
				q.setParameter("campaignsId", campaignsId);
			}
			
				q.setParameter("userTypesId", userTypesId);
				
			if (supervisorId>0) {
				q.setParameter("supervisorId", supervisorId);
			}
			
			List<User> users = q.getResultList();
			
			return users;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	@Override
	public List<User> find(String username, List<Short> campaignsId, List<UserTypeEnum> userTypesId, int supervisorId) throws EaoException{
		try {
			
			String query="SELECT distinct u FROM User u left join fetch u.campaigns cv left join u.campaigns cs left join u.supervisor s left join u.currentSession se where se.id is not null ";

			if (username!=null && username.trim().length()>0) {
				query+="and u.username like :username ";
			}
			if (supervisorId>0) {
				query+="and s.id = :supervisorId ";
			}
			
			boolean isAdmin=false;
			for (UserTypeEnum userTypeId : userTypesId) {
				if (userTypeId.equals(UserTypeEnum.ADMIN)) {
					isAdmin=true;
				}
			}
			
			if ((isAdmin && campaignsId.size()==1) || !isAdmin) {
				query+="and cs.id in :campaignsId ";
			}
			
			query+="and u.userType in :userTypesId ";
			
			TypedQuery<User> q = em.createQuery(query, User.class);
			
			if (username!=null && username.trim().length()>0) {
				q.setParameter("username", "%"+username+"%");
			}
			if ((isAdmin && campaignsId.size()==1) || !isAdmin) {
				q.setParameter("campaignsId", campaignsId);
			}
				
				q.setParameter("userTypesId", userTypesId);
				
			if (supervisorId>0) {
				q.setParameter("supervisorId", supervisorId);
			}
			
			List<User> users = q.getResultList();
			
			return users;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

	@Override
	public List<User> getActiveAgents() throws EaoException {
		try {

			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u WHERE u.userType = :userType and u.isActive=true", User.class);
			q.setParameter("userType", UserTypeEnum.AGENT);
			List<User> users = q.getResultList();

			return users;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}

	@Override
	public User getAgentInfo(int userId) throws EaoException {
		try {
			
			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u "
					+ "left join fetch u.currentSession cs "
					+ "left join fetch u.currentSession.currentSessionSessionType csst "
					+ "left join fetch u.currentSession.currentSessionSessionType.sessionsCampaign sc "
					//+ "left join fetch sc.campaign cmp "
					+ "left join fetch u.currentCall c "
					//+ "left join fetch u.currentCall.queue q "
					+ "left join fetch u.currentSession.sessionsSessionType sst "
					+ "WHERE u.id = :userId ", User.class);
			q.setParameter("userId", userId);
			
			User user = q.getSingleResult();
			return user;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e);
		}
	}
	
	

}
