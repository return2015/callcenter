package com.returnsoft.callcenter.service.local;

import java.util.List;
import java.util.concurrent.Future;

import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.SessionQueue;
import com.returnsoft.callcenter.exception.AmiException;
public interface AmiService {
	
	public String searchPeer(String ipHost, Server pbxServer) throws AmiException;
	
	public void pauseManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException;
	public void unpauseManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException;
	
	public String addQueue(String queueCode, String peerName, Server pbxServer, Boolean isPaused) throws AmiException;
	public String removeQueue(String queueCode, String peerName, Server pbxServer) throws AmiException;
	//public String checkPeerQueueStatus(String queueCode, String peerName, Server pbxServer) throws AmiException;
	public Future<String> listenEvents(Server pbxServer) throws AmiException;
	
	//public String checkPeerStatus(String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort) throws AmiException;
	//public String addManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort) throws AmiException;
	//public String removeManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort) throws AmiException;
	//public String pauseManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort) throws AmiException;
	//public String unpauseManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort) throws AmiException;

}
