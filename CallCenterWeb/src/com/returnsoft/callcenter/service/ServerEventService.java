package com.returnsoft.callcenter.service;

import java.util.List;

import com.returnsoft.callcenter.dto.ServerDto;

public interface ServerEventService {
	
	
	public void startEvents();
	
	public void stopEvents();
	
	public List<ServerDto> getServers();
	
	
	

}
