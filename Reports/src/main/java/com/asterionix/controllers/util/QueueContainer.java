package com.asterionix.controllers.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.asterionix.exception.AgentNotFoundException;

public class QueueContainer {
	static Logger logger = LoggerFactory.getLogger(QueueContainer.class);
	private Map<String, ArrayList<Peer>> queues;
	
	private Peer findAgentByNum(String queueName, String num){

		Peer result = null;
		ArrayList<Peer> peers = getAllPeersByQueueName(queueName);
		
		for(Peer a : peers){
			if(a.getPeerNum().equals(num)){
				result = a;
				break;
			}
		}
		return result;
	}
	private String  findQueueByName(String queueName){
		String result = null;
		for (String key : queues.keySet()) {
		    if (key.equals(queueName)){
		    	result = queueName;
		    	break;
		    }
		}
		return result;
	}
	public QueueContainer(){
		
		queues = new HashMap<String, ArrayList<Peer>>();
	}
	public void addQueueName(String queueName){

		if (findQueueByName(queueName) == null){
			queues.put(queueName, null);
			
		}
	}
	public void addPeersToQueue(String queueName, ArrayList<Peer> peers){
		
		queues.put(queueName, peers);
		
	}
	public ArrayList<Peer> getAllPeersByQueueName(String queueName){
		
		return queues.get(queueName);
		
	}
	public void addPeer(String queueName, Peer peer){
		
		if (findAgentByNum(queueName,peer.getPeerNum()) == null){
			ArrayList<Peer> peers = getAllPeersByQueueName(queueName);
			peers.add(peer);
			queues.replace(queueName, peers);
		}
	}
	public void removePeer(String queueName, String num) throws AgentNotFoundException{
		
		Peer peer = findAgentByNum(queueName, num);
		ArrayList<Peer> peers =  getAllPeersByQueueName(queueName);
		if (peer != null){
			int index = peers.indexOf(peer);
			peers.remove(index);
		//	queues.put(queueName, peers);
		}else{
			throw new AgentNotFoundException("Agent: [" + num + "] not found in Queue: ["+ queueName + "]!");
		}
	}
	public void updateAgent(String queueName ,String peerNum, String peerName, String css) {
		ArrayList<Peer> peers =  getAllPeersByQueueName(queueName);
		Peer peer = findAgentByNum(queueName, peerNum);
		if (peer != null){
			int index = peers.indexOf(peer);
			peer.setcssClass(css);
			peer.setPeerName(peerName);
			peers.set(index, peer);
			queues.put(queueName, peers);
		}else{
			logger.info("Update agent " + peerNum);
		//	peers.add(new Peer(peerNum,peerName,css,peerNum + "-" + queueName));
		//	queues.put(queueName, peers);
			//throw new AgentNotFoundException("Agent: [" + peerNum + "] not found in Queue: ["+ queueName + "]!");
		}

	}
	public void printAll(){
		for (Map.Entry<String, ArrayList<Peer>> entry : queues.entrySet()) {
		    String queueName = entry.getKey();
		    System.out.println("Queue Name: " + queueName);
		    ArrayList<Peer> peers = entry.getValue();
		    for(Peer a : peers){
		    	System.out.println("getPeerName : " + a.getPeerName() + "  getPeerNum: " + a.getPeerNum() + "   css: " + a.getcssClass());
		    }
		}
	}
	
}
