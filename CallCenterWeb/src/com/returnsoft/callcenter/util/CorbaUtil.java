package com.returnsoft.callcenter.util;

import java.io.InputStream;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.returnsoft.callcenter.enumeration.CorbaEnum;
import com.returnsoft.callcenter.exception.CallCenterServiceNotFoundException;
import com.returnsoft.callcenter.exception.CorbaFileNotFoundException;
import com.returnsoft.callcenter.exception.CorbaUtilException;
import com.returnsoft.callcenter.service.AgentService;
import com.returnsoft.callcenter.service.SupervisorService;
import com.returnsoft.callcenter.service.UserService;

public class CorbaUtil {

	public final String corbaPropertyFileName = "/resources/corba.properties";
	public InitialContext initialContext;

	public void openInitialContext() throws CorbaUtilException, CallCenterServiceNotFoundException {

		try {
			Properties properties = new Properties();
			InputStream input = this.getClass().getClassLoader()
					.getResourceAsStream(corbaPropertyFileName);

			if (input != null) {
				properties.load(input);
			} else {
				throw new CorbaFileNotFoundException(corbaPropertyFileName);
			}

			initialContext = new InitialContext(properties);

		} catch (NamingException e) {
			e.printStackTrace();
			throw new CallCenterServiceNotFoundException();
		} catch (CorbaFileNotFoundException e) {
			throw new CorbaUtilException(e.getMessage());	
		} catch (Exception e) {
			e.printStackTrace();
			throw new CorbaUtilException(e);
		}

	}

	public void closeInitialContext() throws CorbaUtilException, CallCenterServiceNotFoundException  {

		try {
			if (initialContext != null) {

				initialContext.close();

			}
		} catch (NamingException e) {
			e.printStackTrace();
			throw new CallCenterServiceNotFoundException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CorbaUtilException(e);
		}

	}

	public UserService getUserService() throws CorbaUtilException,CallCenterServiceNotFoundException {

		try {

			openInitialContext();
			UserService userService = (UserService) initialContext
					.lookup(CorbaEnum.USERSERVICE.getName());
			closeInitialContext();

			return userService;

		} catch (NamingException e) {
			e.printStackTrace();
			throw new CallCenterServiceNotFoundException();
		} catch (CorbaUtilException e) {
			throw new CorbaUtilException(e.getMessage());
		} catch (CallCenterServiceNotFoundException e) {
			throw new CorbaUtilException(e.getMessage());		
		} catch (Exception e) {
			e.printStackTrace();
			throw new CorbaUtilException(e);
		}

	}

	public AgentService getAgentService() throws CorbaUtilException,CallCenterServiceNotFoundException {

		try {

			openInitialContext();
			AgentService agentService = (AgentService) initialContext
					.lookup(CorbaEnum.AGENTSERVICE.getName());
			closeInitialContext();

			return agentService;

		} catch (NamingException e) {
			e.printStackTrace();
			throw new CallCenterServiceNotFoundException();
		} catch (CorbaUtilException e) {
			throw new CorbaUtilException(e.getMessage());
		} catch (CallCenterServiceNotFoundException e) {
			throw new CorbaUtilException(e.getMessage());		
		} catch (Exception e) {
			e.printStackTrace();
			throw new CorbaUtilException(e);
		}

	}
	
	
	public SupervisorService getSupervisorService() throws CorbaUtilException,CallCenterServiceNotFoundException {

		try {

			openInitialContext();
			SupervisorService supervisorService = (SupervisorService) initialContext
					.lookup(CorbaEnum.SUPERVISORSERVICE.getName());
			closeInitialContext();

			return supervisorService;

		} catch (NamingException e) {
			e.printStackTrace();
			throw new CallCenterServiceNotFoundException();
		} catch (CorbaUtilException e) {
			throw new CorbaUtilException(e.getMessage());
		} catch (CallCenterServiceNotFoundException e) {
			throw new CorbaUtilException(e.getMessage());		
		} catch (Exception e) {
			e.printStackTrace();
			throw new CorbaUtilException(e);
		}

	}
	

}
