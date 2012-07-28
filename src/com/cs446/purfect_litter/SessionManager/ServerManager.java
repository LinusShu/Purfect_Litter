package com.cs446.purfect_litter.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cs446.purfect_litter.LogicManager.Game;
import com.cs446.purfect_litter.LogicManager.GameState;

/**
 *  Responsible for managing server-side communications.
 */
 
public class ServerManager extends GameSessionManager{
	List<ServerCommsTask> clients = new ArrayList<ServerCommsTask>();
	List<Integer> clientIDs = new ArrayList<Integer>(); 
	ArrayList<String> playerNames = new ArrayList<String>();
	Game G;
	Random ran = new Random();
	ServerListenTask lt;
	/** 
	 * Constructs a ServerManager, as well as creating a ServerListenTask that listens
	 * to new ciient connections.
	 */
	public ServerManager(Game g) {
		this.G = g;

		// add server as the first 
		lt = new ServerListenTask(this);
	    	playerNames.add("Host");
		lt.execute();
	}
	
	/**
	 *  Generates a random id number for the connected client, and adds this id to the 
	 *  clientID list and playername list.
	 */
	public boolean addClient(int id) {
		// NOTE:
		// hard-coded so that only on player can be registered to the server
		// change to "if (clientIDs.size() < 3)" when need to support 3 other clients
		if (clientIDs.isEmpty() && id == -1) {
			    int assignID = ran.nextInt(1000);
			    clientIDs.add(Integer.valueOf(assignID));
			    String playername = assignID + "";
			    playerNames.add(playername);
			    return true;
			}
		return false;
	}
	
	/**
	 * Adds the ServerClientTask associated with the connected client to the clients list.
	 */
	public void addClientTask(ServerCommsTask ct) {
		clients.add(ct);
	    	G.createLogicForServer(playerNames);
	    	receive(G.getGameState());
	}
	
	/**
	 *  Returns the client's id in the clientId list at pos.
	 */
	public int getClientId(int pos) {
		return clientIDs.get(pos).intValue();
	}

	/**
	 * Default send method used to broadcast game state updates to all the clients currently have their
	 * registered in the clients list.
	 */
	@Override
	public boolean send(GameState g) {
		for (ServerCommsTask ct : clients) {
			// if fail to send to one of the clients
			if (! ct.send(g)) {
				// tear down all the connections between all clients
				for (ServerCommsTask ct2: clients) {
					// cancel the ServerCommsTask  
					ct2.cancel(true);
					clients.remove(ct2);
					// shut down the game
					this.shutDown();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 *  Notifies the GameFlowManager facde (aka Game) when received a GameState update 
	 *  from the client.
	 */
	@Override
	public void receive(GameState fromClient) {
		G.doReceiveGameState(fromClient);
	}
	
	/** Sends the GameState update to a specific player; Only used for testing purposes.
	 * Note: int player = [0,2]
	 */
	public void sendTo(GameState g, int player) {
	    clients.get(player).send(g);
	}
	
	/**
	 * Cancels the ServerListenTask and shuts down the application.
	 */
	@Override
    	public void shutDown() {
    	try {
    		// cancel the ServerListenTask
    		lt.cancel(true);
    		
    		// cancel all running ServerCommsTask
    		if (! clients.isEmpty()) {
    			for (ServerCommsTask ct : clients) 
    				ct.cancel(true);
    		}
    		// shuts down the application
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

}


