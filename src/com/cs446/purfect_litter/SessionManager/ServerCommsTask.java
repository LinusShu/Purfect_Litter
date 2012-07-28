package com.cs446.purfect_litter.SessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.AsyncTask;

import com.cs446.purfect_litter.LogicManager.GameState;


/**
 * Responsible for communications between the server and a client.
 */
public class ServerCommsTask extends AsyncTask<GameSessionManager, Void, Void> {
    	ServerManager gsm;
    	Socket s;
    	ObjectOutputStream oos;
    	ObjectInputStream oin;
	GameState fromClient;
	int id;
	
	/**
	 *  Constructs an AsyncTask that listens on the socket associated with the client for
	 *  imcoming GameState updates.
	 */
	public ServerCommsTask(ServerManager gsm, Socket in, int pos) {
		this.gsm = gsm;
		this.s = in;
		this.id = gsm.getClientId(pos);
		
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
		    	oin = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! Can not get socket input/output stream !!!");
		}
	}
	
	/**
	 *  Listens to incoming GameState updates and notifies the Game a new GameState is received
	 *  by calling the receive() method in ServerManager
	 */
	@Override
	protected Void doInBackground(GameSessionManager... arg0) {
		System.out.println("&&& Listening to incoming game state update &&&");
		
		while (!this.isCancelled()) {
			try {
				// if there is game state update from the client
				fromClient = (GameState)oin.readObject();
				// notify game flow manager there is a game state update from remote player
				gsm.receive(fromClient);
				System.out.println ("&&& GameState from Client#" + id + ", current have Action: " + fromClient.currentAction + " &&&");
				
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println ("!!! Can not get input stream !!!");
				this.cancel(true);
				gsm.shutDown();
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println ("!!! Not valid game state object !!!");
				this.cancel(true);
				gsm.shutDown();
		    }
		}
		
		return null;
		
	}
	
	/**
	 *  Sends the GameState to the client
	 */
	public boolean send(GameState g) {
		if (g == null) {
			System.out.println("&&& Nothing to send &&&");
			return false;
		}
		
		g.setID(this.id);
		try {
			System.out.println("&&& Sending game state to client: " + this.id + " with action " + g.currentAction +  " &&&");
			oos.writeUnshared(g);
			oos.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! Can not send/write game state !!!");
			this.cancel(true);
			gsm.shutDown();
		}
		
		return false;
	}
	
	/**
	 * Closes both I/O streams on the sockect
	 */
	@Override
	public void onCancelled() {
		try {
			oos.close();
			oin.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!!! Can not close client socket's i/o stream !!!");
		}
	}

}
