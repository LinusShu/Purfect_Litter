package com.cs446.purfect_litter.gameSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;

import com.cs446.purfect_litter.MainActivity;
import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.Player;


public class ServerManager extends GameSessionManager{
	List<ServerCommsTask> clients = new ArrayList<ServerCommsTask>();
	List<Integer> clientIDs = new ArrayList<Integer>(); 
	ArrayList<String> playerNames = new ArrayList<String>();
	MainActivity mainActivity;
	
	Random ran = new Random();
	GameLogic gl;	
	ServerListenTask lt;
	
	public ServerManager(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		// add server as the first 
		lt = new ServerListenTask(this);
	    playerNames.add("Host");
		lt.execute();
	}
	
	
	public boolean addClient(int id) {
		// NOTE:
		// hard-coded so that only on player can be registered to the server
		// change to if (clientIDs.size() < 3) when need to support 3 other clients
		if (clientIDs.isEmpty() && id == -1) {
			    int assignID = ran.nextInt(1000);
			    clientIDs.add(new Integer(assignID));
			    String playername = assignID + "";
			    playerNames.add(playername);
			    return true;
			}
		return false;
	}
	
	public void addClientTask(ServerCommsTask ct) {
		clients.add(ct);
	    gl = new GameLogic(playerNames, this);
	    receive(gl.table);
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
	
	@Override
	public void receive(GameState fromClient) {
		mainActivity.update(fromClient);
	}
	
	// send game state updates to a specific player
	// Note: int player = [0,2]
	public void sendTo(GameState g, int player) {
	    clients.get(player).send(g);
	}


    public void shutDown() {
    	try {
    		// cancel the ServerListenTask
    		lt.cancel(true);
    		
    		// cancel all running ServerCommsTask
    		if (! clients.isEmpty()) {
    			for (ServerCommsTask ct : clients) 
    				ct.cancel(true);
    		}
    		
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public Player getMe() {
    	return gl.me;
    }
	
	public GameLogic getGl() {
		return gl;
	}

}


