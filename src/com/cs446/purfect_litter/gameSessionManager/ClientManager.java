package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cs446.purfect_litter.gameLogicManager.GameState;



public class ClientManager extends GameSessionManager {
	Socket outGoingSocket = null;
	ClientCommsTask ct;
	int id = -1;
	boolean serverStatus;
	
	public ClientManager() {
		try {
			outGoingSocket = new Socket(InetAddress.getByName("10.0.2.2"), 4000);
			this.init();
			serverStatus = true;
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		    serverStatus = false;
		} catch (IOException e2) {
			e2.printStackTrace();
			serverStatus = false;
		}
		
	}
	
	// check if the game state is indeed from the server
	public boolean validateGameState (GameState g) {
		return (g.getID() == this.id); 
	}
	
	// return the id of this client
	public int getId() {
		return this.id;
	}
	
	// return the server status
	public boolean getServerStatus() {
		return serverStatus;
	}
	
    // default send method used to send game state updates to the game host.
	// NOTE: make sure to call getServerStatus() whenever send() is called
	@Override
	public boolean send(GameState g) {
		if (! ct.send(g)) {
			serverStatus = false;
			this.shutDown();
			return false;
			
		}
		
		return true;
	}
	
	// send an initial game state to the server to register the client
	// also starts the client side comms task to listen to incoming game states
    public void init() {
    	GameState initState = new GameState();
    	
    	initState.setID(-1);
    	
    	try {
    		ObjectOutputStream oos = new ObjectOutputStream(outGoingSocket.getOutputStream());
    		oos.writeObject(initState);
			oos.flush();
			
			ct = new ClientCommsTask(this, outGoingSocket);	
			ct.execute();
    	} catch (IOException e) {
    		e.printStackTrace();
    		serverStatus = false;
    		System.out.println("!!! Failed to initialize game state with server !!!");
    	}
    }
    
    public void shutDown() {
    	try {
    		ct.cancel(true);
    		outGoingSocket.close();
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
