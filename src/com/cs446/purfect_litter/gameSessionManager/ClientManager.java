package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cs446.purfect_litter.GameState;

public class ClientManager extends GameSessionManager {
	Socket outGoingSocket = null;
	ClientCommsTask ct;
	int id = -1;
	
	public ClientManager() {
		try {
			outGoingSocket = new Socket(InetAddress.getByName("10.0.2.2"), 4000);
			this.init();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	public boolean addServer(int id) {
		// check if the server's address is already registered 
		if (id < 0) {
			this.id = id;
			return true;
		}
		return false;
	}
	
	public int getId() {
		return this.id;
	}
	
    // default send method used to send game state updates to the game host.
	@Override
	public void send(GameState g) {
		ct.send(g);
	}
	
    public void init() {
    	GameState initState = new GameState();
    	
    	initState.setID(-1);
    	initState.currentAction = 0;
    	initState.currentLove = 0;
    	initState.currentPurchase = 0;
    	
    	try {
    		ObjectOutputStream oos = new ObjectOutputStream(outGoingSocket.getOutputStream());
    		oos.writeObject(initState);
			oos.flush();
			
			ct = new ClientCommsTask(this, outGoingSocket);	
			ct.execute();
    	} catch (IOException e) {
    		e.printStackTrace();
    		System.out.println("!!! Failed to initialize game state with server !!!");
    	}
    }


	


	

	
}
