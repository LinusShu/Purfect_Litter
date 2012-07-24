package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.cs446.purfect_litter.gameLogicManager.GameState;

import android.os.AsyncTask;



public class ServerListenTask extends AsyncTask<GameSessionManager, Void, Void> {
	private ServerManager gsm;
	private static ServerSocket ss = null;
	private static final int SERVERPORT = 6000;
	
	
	public ServerListenTask (ServerManager g) {
		gsm = g;
	}
	
	@Override
	protected Void doInBackground(GameSessionManager... arg0) {
        Socket s = null;
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
				//DEBUG 
				System.out.println ("&&& Receieved new game state from client#" + fromClient.getID() + " &&&");
				// check to see if it's a game state from the client
				if (gsm.addClient(fromClient.getID())) {
					ServerCommsTask ct = new ServerCommsTask(gsm, s, clientCount);
				    ct.execute(gsm);
				    gsm.addClientTask(ct);
				    //DEBUG
				    System.out.println("&&& Now clients are: ");
				    for (Integer client : gsm.clientIDs) 
					      System.out.println(client.intValue() + ", ");
				    
				    clientCount ++;
				}
			} catch (IOException e) {
					e.printStackTrace();
			} catch (ClassNotFoundException e) {
					e.printStackTrace();
			}
		}
		
		try {
			s.close();
		    ss.close();
		    gsm.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("can not close sockets.");
		}
		
	    return null;
	}
	
}
