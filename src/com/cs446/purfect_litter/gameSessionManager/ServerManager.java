package com.cs446.purfect_litter.gameSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cs446.purfect_litter.gameLogicManager.GameState;


public class ServerManager extends GameSessionManager{
	List<ServerCommsTask> clients = new ArrayList<ServerCommsTask>();
	List<Integer> clientIDs = new ArrayList<Integer>(); 
	Random ran = new Random();
	
	ServerListenTask lt = new ServerListenTask(this);
	
	public ServerManager() {
		lt.execute();
	}
	
	
	public boolean addClient(int i) {
		Integer id = new Integer(i);
		
		// if not registered and number of players(excluding the host) in the game is less than 3
		if (! clientIDs.contains(id) && clientIDs.size() < 3 ) {
			    int assignID = ran.nextInt(1000);
			    clientIDs.add(assignID);
			    return true;
			}
		return false;
	}
	
	public void addClientTask(ServerCommsTask ct) {
		clients.add(ct);
	}
	
	public int getClientId(int pos) {
		return clientIDs.get(pos).intValue();
	}

	// default send method used to send game state updates to all the clients currently have their
	// address registered in clientAddr
	@Override
	public boolean send(GameState g) {
		for (ServerCommsTask ct : clients) {
			// if fail to send to one of the clients
			if (! ct.send(g)) {
				// tear down all the connections between all clients
				for (ServerCommsTask ct2: clients) {
					ct2.tearDown();
				}
				// shut down the game
				this.shutDown();
				return false;
			}
		}
		return true;
	}
	
	// send game state updates to a specific player
	// Note: int player = [0,2]
	public void sendTo(GameState g, int player) {
	    clients.get(player).send(g);
	}


    public void shutDown() {
    	try {
    		lt.cancel(true);
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}


