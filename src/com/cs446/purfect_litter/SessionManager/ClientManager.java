package com.cs446.purfect_litter.SessionManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cs446.purfect_litter.LogicManager.Game;
import com.cs446.purfect_litter.LogicManager.GameState;


/**
 *  Responsible for managing the client side communication tasks.
 */
public class ClientManager extends GameSessionManager {
	Socket outGoingSocket = null;
	ClientCommsTask ct;
	int id = -1;
	boolean serverStatus;
	Game G;
	
	/**
	 *  Constructs the ClientManager and set up sockect communication with the server
	 */
	public ClientManager(Game G) {
		this.G = G;
		
		try {
			//TODO: allow the user to enter the server ip address
			//NOTE: hard-coded server ip address for testing on Android emulators
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
	
	/**
	 * Checks if the GameState is indeed from the server
	 */
	public boolean validateGameState (GameState g) {
		return (g.getID() == this.id); 
	}
	
	/**
	 * Returns the id of this client
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns the server status
	 */
	public boolean getServerStatus() {
		return serverStatus;
	}
	
    	/**
    	 *  Default send method used to send game state updates to the game host.
	 */
	@Override
	public boolean send(GameState g) {
		if (! ct.send(g)) {
			serverStatus = false;
			this.shutDown();
			return false;
		}
		return true;
	}
	
	/**
	 *  Notifes the Game when new GameState update arrives.
	 */
	@Override
	public void receive(GameState fromServer) {
		G.doReceiveGameState(fromServer);
	}
	
	/** Sends an initial GameState to the server to register the client also 
	 *  starts the client side comms task to listen to incoming GameStates.
	 */
    public void init() {
    	GameState initState = new GameState();
    	// initial game state will always have it's id set to -1.
    	initState.setID(-1);
    	
    	try {
    		// sends the initial game state to the server
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
    
    /**
     *  Cancels the client comms task and close the client socket
     */
    @Override
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
    
    public Game getGame() {
    	return this.G;
    }
    
}
