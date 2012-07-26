package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.AsyncTask;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;

public class ClientCommsTask extends AsyncTask<GameSessionManager, Void, Void>{
	GameState fromServer;
	ClientManager gsm;
	Socket s;
    ObjectOutputStream oos;
    ObjectInputStream oin;
    boolean initialized;
	
	public ClientCommsTask(ClientManager gsm, Socket in) {
		this.gsm = gsm;
		this.s = in;
		initialized = false;
		
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
		    oin = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! Can not get socket input/output stream !!!");
			gsm.shutDown();
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
				// set the id for the client when received first 
				else {
					gsm.id = fromServer.getID();
					String playername = gsm.id + "";
					initialized = true;
					gsm.gl = new GameLogic(fromServer, playername, gsm);
					gsm.receive(fromServer);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println ("!!! Can not get input stream !!!");
				gsm.shutDown();
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
				gsm.shutDown();
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
			oos.writeUnshared(g);
			oos.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! Can not send/write game state !!!");
			gsm.shutDown();
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
