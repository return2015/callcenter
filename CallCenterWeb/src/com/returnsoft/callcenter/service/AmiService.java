package com.returnsoft.callcenter.service;

import java.util.List;
import java.util.concurrent.Future;

import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.SessionQueue;
import com.returnsoft.callcenter.exception.AmiException;
public interface AmiService {
	
	public String searchPeer(String ipHost, Server pbxServer) throws AmiException;
	
	//public String addQueue(String queueCode, String peerName, Server pbxServer, Boolean isPaused) throws AmiException;
	//public String removeQueue(String queueCode, String peerName, Server pbxServer) throws AmiException;

	public Future<String> listenEvents(Server pbxServer) throws AmiException;
	
	
	public void pauseManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException;
	public void unpauseManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException;
	
	public void addManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer, Boolean isPaused) throws AmiException;
	public void removeManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException;
	

}
