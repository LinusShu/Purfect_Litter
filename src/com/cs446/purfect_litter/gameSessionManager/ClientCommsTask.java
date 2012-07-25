package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.cs446.purfect_litter.GameState;

import android.os.AsyncTask;

public class ClientCommsTask extends AsyncTask<GameSessionManager, Void, Void>{
	GameState fromServer;
	ClientManager gsm;
	Socket s;
    ObjectOutputStream oos;
    ObjectInputStream oin;
    int id;
    boolean initialized;
	
	public ClientCommsTask(ClientManager gsm, Socket in) {
		this.gsm = gsm;
		this.s = in;
		this.id = gsm.getId();
		initialized = false;
		
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
		    oin = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! Can not get socket input/output stream !!!");
		}
	}
	
	@Override
	protected Void doInBackground(GameSessionManager... arg0) {
		while (!this.isCancelled()) {
			try {
				// receive game state update from the server
				fromServer = (GameState)oin.readObject();
				// if the initial game state was received
				if (initialized) {
				// notify game flow manager there is a game state update from remote player
				gsm.receive(fromServer);
				System.out.println ("&&& GameState from Server: " + fromServer.currentAction + " &&&");
				}
				// set the id for the client when received 
				else {
					gsm.id = fromServer.getID();
					initialized = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println ("!!! Can not get input stream !!!");
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
		    }
		}
		return null;
	}
	
	public boolean send(GameState g) {
		if (g == null) {
			System.out.println("&&& Nothing to send &&&");
			return false;
		}
		
		g.setID(gsm.getId());
		try {
			oos.reset();
			oos.writeObject(g);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!!! Can not send/write game state !!!");
		}
		
		return false;
	}
	
	@Override
	protected void onCancelled() {
		try {
			oos.close();
			oin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
