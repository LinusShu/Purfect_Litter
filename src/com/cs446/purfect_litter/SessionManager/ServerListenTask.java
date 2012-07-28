package com.cs446.purfect_litter.SessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;

import com.cs446.purfect_litter.LogicManager.GameState;


/**
 * Responsible for listening to incoming client requests to join the game on the ServerSocket
 */
public class ServerListenTask extends AsyncTask<GameSessionManager, Void, Void> {
	private ServerManager gsm;
	private static ServerSocket ss = null;
	private static Socket s = null;
	private static final int SERVERPORT = 6000;
	
	public ServerListenTask (ServerManager g) {
		gsm = g;
	}
	
	/**
	 * Waits for client to connect.
	 */
	@Override
	protected Void doInBackground(GameSessionManager... arg0) {
		GameState fromClient;
		int clientCount = 0;
		System.out.println("&&& Listening for new clients &&&");
		try {
			ss = new ServerSocket (SERVERPORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    //listens to incoming game state updates
		while (!this.isCancelled()) {
			try {
				// if nothing from the client yet
				s = ss.accept();
				// if there is game state update from the client
				ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				fromClient = (GameState)in.readObject();
				System.out.println ("&&& Receieved new client connection: clientID# = " + fromClient.getID() + " &&&");
				// check to see if it's a game state from the client
				if (gsm.addClient(fromClient.getID())) {
				    System.out.println("&&& Now clients are: ");
				    for (Integer client : gsm.clientIDs) 
					      System.out.println(client.intValue() + ", ");
				    	
					// create a new ServerCommsTask that listens to the connected client
					ServerCommsTask ct = new ServerCommsTask(gsm, s, clientCount);
					// execute the ServerCommsTask
				    	ct.execute(gsm);
					// add the ServerCommsTask to the client list, as well as start server-side GameLogic
					gsm.addClientTask(ct);
					clientCount ++;
				}
			} catch (IOException e) {
					e.printStackTrace();
					gsm.shutDown();
			} catch (ClassNotFoundException e) {
					e.printStackTrace();
					gsm.shutDown();
			}
		}
		return null;
	}
	
	/**
	 *  Closes both ServerSocket and the client's socket when cancelled.
	 */
	@Override
	protected void onCancelled() {
		try {
			s.close();
		    	ss.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("can not close sockets.");
		}
	}
	
}
