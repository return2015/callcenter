package com.returnsoft.callcenter.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.returnsoft.callcenter.eao.ServerEao;
import com.returnsoft.callcenter.entity.CallEvent;
import com.returnsoft.callcenter.entity.Server;
import com.returnsoft.callcenter.entity.SessionQueue;
import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;
import com.returnsoft.callcenter.exception.AmiException;
import com.returnsoft.callcenter.service.local.AmiService;
import com.returnsoft.callcenter.service.local.CallEventService;


@Stateless
public class AmiServiceImpl implements AmiService {
	
	@EJB
	private ServerEao serverEao;
	
	@Resource
    private SessionContext ctx;
	
	@EJB
	private CallEventService callEventService;
	
	@Override
	public String searchPeer(String ipHost, Server pbxServer) throws AmiException {

		try {

			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("Events: off \r\n");
			dos.writeBytes("\r\n");
			dos.writeBytes("Action: SIPpeers\r\n");
			dos.writeBytes("\r\n");
			dos.writeBytes("Action: Logoff\r\n");
			dos.writeBytes("\r\n");
			
			String line;
			StringBuilder lines = new StringBuilder();
			
			while ((line = br.readLine())!=null) {
				lines.append(line+"|&|");
			}
			
			socket.close();
			
			int positionIpInicial = lines.indexOf(ipHost+"|&|");
			
			if (positionIpInicial>0) {
				int positionFinalPeer = lines.lastIndexOf("|&|", positionIpInicial-1);
				int positionInicialPeer = lines.lastIndexOf("|&|", positionFinalPeer-1);
				int positionInicialObjectName = lines.lastIndexOf("|&|", positionInicialPeer-1);
				line = lines.substring(positionInicialObjectName+15, positionInicialPeer);
			}
			
			return line;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AmiException(e);
		}

	}
	@Override
	public void pauseManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException {
		
		System.out.println("ingreso a pauseManyQueues");

		try {
			
			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			
			System.out.println("logueandose a ami");
			
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("Events: off \r\n");
			dos.writeBytes("\r\n");

			String line;
			String loginResponse="";
			while ((line = br.readLine()).length()>0) {
				if (line.startsWith("Response")) {
					loginResponse = line.substring(line.indexOf(":")+1).trim();
				}
			}
			
			if (loginResponse.equals("Success")) {
				for (int i=0; i< sessionQueues.size();i++) {
					SessionQueue sessionQueue = sessionQueues.get(i);
					System.out.println("pausando cola:"+sessionQueue.getQueue().getName());
					dos.writeBytes("Action: QueuePause\r\n");
					dos.writeBytes("Queue: " + sessionQueue.getQueue().getName() + "\r\n");
					dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
					dos.writeBytes("Paused: true\r\n");
					dos.writeBytes("\r\n");
					String queueResponse;
					while ((queueResponse = br.readLine()).length()>0) {
						if (queueResponse.startsWith("Message")) {
							queueResponse=queueResponse.substring(queueResponse.indexOf(":")+1).trim();
							sessionQueue.setResponse(queueResponse);
						}
					}
				}
				System.out.println("deslogueando de AMI");
				dos.writeBytes("Action: Logoff\r\n");
				dos.writeBytes("\r\n");
			}else{
				dos.writeBytes("Action: Logoff\r\n");
				dos.writeBytes("\r\n");
			}

			
			/*dos.writeBytes("Action: Logoff\r\n");
			dos.writeBytes("\r\n");*/
			
			/*String line;
			StringBuilder lines = new StringBuilder();

			while ((line = br.readLine())!=null) {
				lines.append(line+"|&|");
			}*/
			
			socket.close();

			/*System.out.println("---------LINES----------");
			System.out.println("---------LINES----------");
			System.out.println("---------LINES----------");
			System.out.println(lines);
			System.out.println("---------LINES----------");
			System.out.println("---------LINES----------");
			System.out.println("---------LINES----------");*/
			//return sessionQueues;

		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AmiException(e);
		}
	}
	
	public void unpauseManyQueues(List<SessionQueue> sessionQueues, String peerName, Server pbxServer) throws AmiException {
		
		System.out.println("ingreso a unpauseManyQueues");

		try {
			
			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			
			System.out.println("logueandose a ami");
			
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("Events: off \r\n");
			dos.writeBytes("\r\n");

			String line;
			String loginResponse="";
			while ((line = br.readLine()).length()>0) {
				if (line.startsWith("Response")) {
					loginResponse = line.substring(line.indexOf(":")+1).trim();
				}
			}
			
			
			if (loginResponse.equals("Success")) {
				for (int i=0; i< sessionQueues.size();i++) {
					SessionQueue sessionQueue = sessionQueues.get(i);
					System.out.println("despausando cola:"+sessionQueue.getQueue().getName());
					dos.writeBytes("Action: QueuePause\r\n");
					dos.writeBytes("Queue: " + sessionQueue.getQueue().getName() + "\r\n");
					dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
					dos.writeBytes("Paused: false\r\n");
					dos.writeBytes("\r\n");
					String queueResponse;
					while ((queueResponse = br.readLine()).length()>0) {
						if (queueResponse.startsWith("Message")) {
							queueResponse=queueResponse.substring(queueResponse.indexOf(":")+1).trim();
							sessionQueue.setResponse(queueResponse);
						}
					}
				}
				
				System.out.println("deslogueando de AMI");
				dos.writeBytes("Action: Logoff\r\n");
				dos.writeBytes("\r\n");
			}else{
				dos.writeBytes("Action: Logoff\r\n");
				dos.writeBytes("\r\n");
			}
			
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AmiException(e);
		}
	}
	
	
	@Override
	public String addQueue(String queueCode, String peerName, Server pbxServer, Boolean isPaused) throws AmiException {

		try {

			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("Events: off \r\n");
			dos.writeBytes("\r\n");
			
			dos.writeBytes("Action: QueueAdd\r\n");
			dos.writeBytes("Queue: " + queueCode + "\r\n");
			dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
			dos.writeBytes("Paused: "+isPaused+"\r\n");
			dos.writeBytes("Priority: 1\r\n");
			dos.writeBytes("\r\n");
			
			dos.writeBytes("Action: Logoff\r\n");
			dos.writeBytes("\r\n");
			
			String line;
			StringBuilder lines = new StringBuilder();

			while ((line = br.readLine())!=null) {
				lines.append(line+"|&|");
			}
			
			socket.close();
			
			int positionFirstMessage = lines.indexOf("|&|Message:");
			
			if (positionFirstMessage>0) {
				int positionSecondMessage = lines.indexOf("|&|Message:",positionFirstMessage+1);
				int positionFinalMessage = lines.indexOf("|&|",positionSecondMessage+1);
				line = lines.substring(positionSecondMessage+11, positionFinalMessage);
			}

			return line;

		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AmiException(e);
		}
		

	}
	@Override
	public String removeQueue(String queueCode, String peerName, Server pbxServer) throws AmiException {
		
		try {

			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));

			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("Events: off \r\n");
			dos.writeBytes("\r\n");

			dos.writeBytes("Action: QueueRemove\r\n");
			dos.writeBytes("Queue: " + queueCode + "\r\n");
			dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
			dos.writeBytes("\r\n");

			dos.writeBytes("Action: Logoff\r\n");
			dos.writeBytes("\r\n");
			
			String line;
			StringBuilder lines = new StringBuilder();
			
			while ((line = br.readLine())!=null) {
				lines.append(line+"|&|");
			}
			socket.close();
			
			int positionFirstMessage = lines.indexOf("|&|Message:");
			
			if (positionFirstMessage>0) {
				int positionSecondMessage = lines.indexOf("|&|Message:",positionFirstMessage+1);
				int positionFinalMessage = lines.indexOf("|&|",positionSecondMessage+1);
				line = lines.substring(positionSecondMessage+11, positionFinalMessage);
			}
			
			
			return line;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AmiException(e);
		}
	}
	
	
	@Override
	@Asynchronous
	public Future<String> listenEvents(Server pbxServer) throws AmiException {
		
		try {
			
			System.out.println("Ingreso a listenEvents");
				
			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));

			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("\r\n");

			dos.writeBytes("Action: Events\r\n");
			dos.writeBytes("EventMask: on\r\n");
			dos.writeBytes("\r\n");

			String line;
			//StringBuilder lines = new StringBuilder();
			
			while (((line = br.readLine())!=null && !ctx.wasCancelCalled())) {
				//System.out.println(line+"|&|\r\n");
				//lines.append(line+"|&|\r\n");

				try {
					
					
					if (line.equals("Event: QueueCallerAbandon")) {
						//System.out.println(line+"|&|\r\n");
						CallEvent callEvent = new CallEvent();
						//queue
						callEvent.setQueue(br.readLine().split(":")[1].trim());
						//position
						br.readLine();
						//originalPosition
						br.readLine();
						//holdTime
						callEvent.setWaittime(Short.parseShort(br.readLine().split(":")[1].trim()));
						//uniqueid
						callEvent.setUniqueid(Double.parseDouble(br.readLine().split(":")[1].trim()));
						
						callEvent.setServerId(pbxServer.getId().shortValue());
						callEvent.setCallEventType(CallEventTypeEnum.ABANDON);
						callEvent.setCreatedAt(new Date());
						callEventService.addEvent(callEvent);
						
						
					} else if (line.equals("Event: Join")) {
						CallEvent callEvent = new CallEvent();
						//privilege
						br.readLine();
						//channel
						br.readLine();
						//callerIDNum
						callEvent.setCallerID(br.readLine().split(":")[1].trim());
						//callerIDName
						br.readLine();
						//setConnectedLineNum
						br.readLine();
						//setConnectedLineName
						br.readLine();
						//setQueue
						callEvent.setQueue(br.readLine().split(":")[1].trim());
						//position
						br.readLine();
						//count
						br.readLine();
						//uniqueid
						callEvent.setUniqueid(Double.parseDouble(br.readLine().split(":")[1].trim()));
						
						callEvent.setServerId(pbxServer.getId().shortValue());
						callEvent.setCallEventType(CallEventTypeEnum.ENTERQUEUE);
						callEvent.setCreatedAt(new Date());
						callEventService.addEvent(callEvent);
						
					}else if(line.equals("Event: AgentConnect")){
						CallEvent callEvent = new CallEvent();
						//privilege
						br.readLine();
						//setQueue
						callEvent.setQueue(br.readLine().split(":")[1].trim());
						//uniqueid
						callEvent.setUniqueid(Double.parseDouble(br.readLine().split(":")[1].trim()));
						//channel
						br.readLine();
						//member
						callEvent.setPeer(br.readLine().split(":")[1].trim());
						//memberName
						br.readLine();
						//HoldTime
						callEvent.setWaittime(Short.parseShort(br.readLine().split(":")[1].trim()));
						//brigdechannel
						br.readLine();
						//ringtime
						callEvent.setRingtime(Short.parseShort(br.readLine().split(":")[1].trim()));
						
						callEvent.setServerId(pbxServer.getId().shortValue());
						callEvent.setCallEventType(CallEventTypeEnum.CONNECT);
						callEvent.setCreatedAt(new Date());
						callEventService.addEvent(callEvent);
						
					}else if(line.equals("Event: AgentComplete")){
						CallEvent callEvent = new CallEvent();
						//privilege
						br.readLine();
						//setQueue
						callEvent.setQueue(br.readLine().split(":")[1].trim());
						//uniqueid
						callEvent.setUniqueid(Double.parseDouble(br.readLine().split(":")[1].trim()));
						//channel
						br.readLine();
						//member
						callEvent.setPeer(br.readLine().split(":")[1].trim());
						//membername
						br.readLine();
						//holdtime
						br.readLine();
						//talktime
						callEvent.setTalktime(Short.parseShort(br.readLine().split(":")[1].trim()));
						//reason
						String complete = br.readLine().split(":")[1].trim();
						if (complete.equals("agent")) {
							callEvent.setCallEventType(CallEventTypeEnum.COMPLETEAGENT);
						}else if (complete.equals("caller")){
							callEvent.setCallEventType(CallEventTypeEnum.COMPLETECALLER);
						}
						callEvent.setServerId(pbxServer.getId().shortValue());
						callEvent.setCreatedAt(new Date());
						callEventService.addEvent(callEvent);
						
						
					}else if(line.equals("Event: MusicOnHold")){
						CallEvent callEvent = new CallEvent();
						//privilege
						br.readLine();
						//state
						String state = br.readLine().split(":")[1].trim();
						if (state.equals("Start")) {
							callEvent.setCallEventType(CallEventTypeEnum.HOLD);
						}else if (state.equals("Stop")) {
							callEvent.setCallEventType(CallEventTypeEnum.UNHOLD);
						}
						//channel
						br.readLine();
						//uniqueid
						callEvent.setUniqueid(Double.parseDouble(br.readLine().split(":")[1].trim()));
						
						callEvent.setCreatedAt(new Date());
						callEvent.setServerId(pbxServer.getId().shortValue());
						callEventService.addEvent(callEvent);
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
				
			//desloguea
			dos.writeBytes("Action: Logoff\r\n");
			dos.writeBytes("\r\n");
			
			//cierra flujo saliente
			dos.close();
			//cierra buffer reader
			br.close();
			//cierra flujo entrante
			dis.close();
			//cierra socket
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return new AsyncResult<String>("unk  listening");
		} catch (IOException e) {
			e.printStackTrace();
			
			return new AsyncResult<String>("io  listening");
		}
		return new AsyncResult<String>("Termino el listening");
	}

	
	public String checkPeerQueueStatus(String queueName, String peerName, Server pbxServer) throws AmiException {

		try {

			Socket socket = new Socket(pbxServer.getHost(), pbxServer.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("Username: " + pbxServer.getUsername() + "\r\n");
			dos.writeBytes("Secret: " + pbxServer.getPassword() + "\r\n");
			dos.writeBytes("Events: off \r\n");
			dos.writeBytes("\r\n");
			dos.writeBytes("Action: QueueStatus\r\n");
			dos.writeBytes("Queue: "+queueName+"\r\n");
			dos.writeBytes("Member: SIP/"+peerName+"\r\n");
			dos.writeBytes("\r\n");
			dos.writeBytes("Action: Logoff\r\n");
			dos.writeBytes("\r\n");
			
			String line;
			StringBuilder lines = new StringBuilder();
			
			while ((line = br.readLine())!=null) {
				lines.append(line+"|&|");
			}
			
			socket.close();
			
			int positionEventInicial = lines.indexOf("Event: QueueMember|&|");
			
			if (positionEventInicial>0) {
				int positionInicialStatus = lines.indexOf("Status: ", positionEventInicial);
				int positionFinalStatus = lines.indexOf("|&|", positionInicialStatus+8);
				String status=lines.substring(positionInicialStatus+8,positionFinalStatus);
				int positionInicialPaused = lines.indexOf("Paused: ", positionEventInicial);
				int positionFinalPaused = lines.indexOf("|&|", positionInicialPaused+8);
				String paused=lines.substring(positionInicialPaused+8,positionFinalPaused);
				line=status+paused;
			}
			
			return line;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AmiException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AmiException(e);
		}

	}
	
	

	/*public String checkPeerStatus(String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort) throws ServiceException {

		try {

			List<String> lines = new ArrayList<String>();

			InetAddress addr = InetAddress.getByName(serverPbx);

			SocketAddress sockaddr = new InetSocketAddress(addr, managerPort);
			Socket socket = new Socket();
			socket.connect(sockaddr);
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String line = "";
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("username: " + managerUser + "\r\n");
			dos.writeBytes("secret: " + managerPassword + "\r\n");
			// dos.writeBytes("ActionID: 1\r\n");
			dos.writeBytes("\r\n");

			dos.writeBytes("Action: ExtensionState\r\n");
			dos.writeBytes("Exten: " + peerName + "\r\n");
			// dos.writeBytes("ActionID: 2\r\n");
			dos.writeBytes("\r\n");

			dos.writeBytes("Action: Logoff\r\n");
			// dos.writeBytes("ActionID: 3\r\n");
			dos.writeBytes("\r\n");

			while (line != null) {
				// LOGGER.info("line: "+line);
				// System.out.println("line4: " + line);
				lines.add(line);
				line = br.readLine();
			}

			socket.close();

			if (lines.size() > 14) {
				String status = lines.get(14);
				// System.out.println("STATUS!!!" + status);
				return status;
			} else {
				throw new AmiException("Error al verificar estado del peer.");
			}

		} catch (UnknownHostException e) {
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}*/
	
	
	/*public String addManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort)
			throws ServiceException {

		try {
			
			System.out.println("ingreso a addQueue");

			List<String> lines = new ArrayList<String>();

			InetAddress addr = InetAddress.getByName(serverPbx);
			SocketAddress sockaddr = new InetSocketAddress(addr, managerPort);
			Socket socket = new Socket();
			socket.connect(sockaddr);
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String line = "";

			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("username: " + managerUser + "\r\n");
			dos.writeBytes("secret: " + managerPassword + "\r\n");
			// dos.writeBytes("ActionID: 1\r\n");
			dos.writeBytes("\r\n");

			for (String queue : queues) {
				// System.out.println("en el for1:"+queue.getName());
				// System.out.println("en el for2:"+peer.getName());
				dos.writeBytes("Action: QueueAdd\r\n");
				dos.writeBytes("Queue: " + queue + "\r\n");
				dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
				dos.writeBytes("Paused: false\r\n");
				dos.writeBytes("Priority: 1\r\n");
				// dos.writeBytes("ActionID: 3\r\n");
				dos.writeBytes("\r\n");

			}

			dos.writeBytes("Action: Logoff\r\n");
			// dos.writeBytes("ActionID: 4\r\n");
			dos.writeBytes("\r\n");

			while (line != null) {
				 System.out.println("line1: " + line);
				lines.add(line);
				line = br.readLine();
			}
			socket.close();

			//System.out.println("Termino addQueue");

			//convertLinesToResponses(lines, queues);

			return "OK";

		} catch (UnknownHostException e) {
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		

	}
	
	public String removeManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort)
			throws ServiceException {

		List<String> lines = new ArrayList<String>();

		try {

			InetAddress addr = InetAddress.getByName(serverPbx);
			SocketAddress sockaddr = new InetSocketAddress(addr, managerPort);
			Socket socket = new Socket();
			socket.connect(sockaddr);
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));

			String line = "";
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("username: " + managerUser + "\r\n");
			dos.writeBytes("secret: " + managerPassword + "\r\n");
			// dos.writeBytes("ActionID: 1\r\n");
			dos.writeBytes("\r\n");

			for (String queue : queues) {
				dos.writeBytes("Action: QueueRemove\r\n");
				dos.writeBytes("Queue: " + queue + "\r\n");
				dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
				// dos.writeBytes("ActionID: 2\r\n");
				dos.writeBytes("\r\n");
			}

			dos.writeBytes("Action: Logoff\r\n");
			// dos.writeBytes("ActionID: 3\r\n");
			dos.writeBytes("\r\n");
			while (line != null) {
				// LOGGER.info("line: "+line);
				System.out.println("line2: " + line);
				lines.add(line);
				line = br.readLine();
			}
			socket.close();
			
			
			return "OK";
			
		} catch (UnknownHostException e) {
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public String pauseManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort)
			throws ServiceException {

		try {
			
			System.out.println("ingreso a pausemanyqueues");

			List<String> lines = new ArrayList<String>();

			InetAddress addr = InetAddress.getByName(serverPbx);

			SocketAddress sockaddr = new InetSocketAddress(addr, managerPort);
			Socket socket = new Socket();
			socket.connect(sockaddr);
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));

			String line = "";
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("username: " + managerUser + "\r\n");
			dos.writeBytes("secret: " + managerPassword + "\r\n");
			// dos.writeBytes("ActionID: 1\r\n");
			dos.writeBytes("\r\n");
			for (String queue : queues) {
				dos.writeBytes("Action: QueuePause\r\n");
				dos.writeBytes("Queue: " + queue + "\r\n");
				dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
				dos.writeBytes("Paused: true\r\n");
				// dos.writeBytes("ActionID: 2\r\n");
				dos.writeBytes("\r\n");
			}
			dos.writeBytes("Action: Logoff\r\n");
			// dos.writeBytes("ActionID: 3\r\n");
			dos.writeBytes("\r\n");
			while (line != null) {
				// LOGGER.info("line: "+line);
				 System.out.println("line3: " + line);
				lines.add(line);
				line = br.readLine();
			}
			socket.close();

			//return convertLinesToResponses(lines, queues);
			return "OK";

		} catch (UnknownHostException e) {
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}
	
	
	public String unpauseManyQueues(List<String> queues, String peerName, String serverPbx, String managerUser, String managerPassword, Integer managerPort)
			throws ServiceException {

		try {

			List<String> lines = new ArrayList<String>();

			InetAddress addr = InetAddress.getByName(serverPbx);

			SocketAddress sockaddr = new InetSocketAddress(addr, managerPort);
			Socket socket = new Socket();
			socket.connect(sockaddr);
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));

			String line = "";
			dos.writeBytes("Action: Login\r\n");
			dos.writeBytes("username: " + managerUser + "\r\n");
			dos.writeBytes("secret: " + managerPassword + "\r\n");
			// dos.writeBytes("ActionID: 1\r\n");
			dos.writeBytes("\r\n");
			for (String queue : queues) {
				dos.writeBytes("Action: QueuePause\r\n");
				dos.writeBytes("Queue: " + queue + "\r\n");
				dos.writeBytes("Interface: SIP/" + peerName + "\r\n");
				dos.writeBytes("Paused: false\r\n");
				// dos.writeBytes("ActionID: 2\r\n");
				dos.writeBytes("\r\n");
			}
			dos.writeBytes("Action: Logoff\r\n");
			// dos.writeBytes("ActionID: 3\r\n");
			dos.writeBytes("\r\n");
			while (line != null) {
				// LOGGER.info("line: "+line);
				 System.out.println("line4: " + line);
				lines.add(line);
				line = br.readLine();
			}
			socket.close();
			//return convertLinesToResponses(lines, queues);
			return "OK";

		} catch (UnknownHostException e) {
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

		

	}*/
	
}
