package com.returnsoft.callcenter.enumeration;

public enum CorbaEnum {
	
	AGENTSERVICE("java:global/CallCenterService/AgentServiceImpl!com.returnsoft.callcenter.service.remote.AgentService"),
	USERSERVICE("java:global/CallCenterService/UserServiceImpl!com.returnsoft.callcenter.service.remote.UserService"),
	SUPERVISORSERVICE("java:global/CallCenterService/SupervisorServiceImpl!com.returnsoft.callcenter.service.remote.SupervisorService");
	
	private final String name;
	
	private CorbaEnum(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	

}
