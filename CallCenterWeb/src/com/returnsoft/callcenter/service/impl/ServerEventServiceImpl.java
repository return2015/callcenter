package com.returnsoft.callcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.returnsoft.callcenter.dto.ServerDto;
import com.returnsoft.callcenter.eao.ServerEao;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.exception.AmiException;
import com.returnsoft.callcenter.exception.EaoException;
import com.returnsoft.callcenter.service.local.AmiService;
import com.returnsoft.callcenter.service.remote.ServerEventService;

//@Startup
@Singleton
@Remote(ServerEventService.class)
public class ServerEventServiceImpl implements ServerEventService{
	
	@EJB
	private AmiService amiService;
	
	@EJB
	private ServerEao serverEao;
	
	@Resource
    private SessionContext ctx;
	
	private List<ServerDto> serversDto;
	
	/*@PostConstruct
	public void initialize(){
		futures = new ArrayList<Future<String>>();
	}*/
	

	@Override
	@PostConstruct 
	public void startEvents() {
		// TODO Auto-generated method stub
		try {
			
			if (serversDto!=null && serversDto.size()>0) {
				for (ServerDto serverDto : serversDto) {
					if (!serverDto.getFuture().isDone()) {
						serverDto.getFuture().cancel(true);
						Thread.sleep(1000);
						startEvents();
					}
				}
			}
			
			//OBTIENE EL OBJETO
			serversDto = new ArrayList<ServerDto>();
			List<Server> servers = serverEao.findAll();
			if (servers!=null && servers.size()>0) {
				for (Server server : servers) {
					if (server.getEvents()) {
						Future<String> future = amiService.listenEvents(server);
						ServerDto serverNew = new ServerDto();
						serverNew.setId(server.getId());
						serverNew.setName(server.getName());
						serverNew.setFuture(future);
						serversDto.add(serverNew);
					}
				}
			}
						
			
		} catch (AmiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	@PreDestroy 
	public void stopEvents(){
		
		try {
			
			if (serversDto!=null && serversDto.size()>0) {
				for (ServerDto serverDto : serversDto) {
					if (!serverDto.getFuture().isDone()) {
						serverDto.getFuture().cancel(true);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	



	@Override
	public List<ServerDto> getServers() {
		if (serversDto!=null && serversDto.size()>0) {
			List<ServerDto> serversDto2 = new ArrayList<ServerDto>();
			for (ServerDto serverDto : serversDto) {
				ServerDto serverDto2 = new ServerDto();
				serverDto2.setId(serverDto.getId());
				serverDto2.setName(serverDto.getName());
				serverDto2.setIsDoneEvent(serverDto.getFuture().isDone());
				serversDto2.add(serverDto2);
			}
			return serversDto2;
		}else{
			return null;
		}
		
	}
	

}
