package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.cs446.purfect_litter.gameLogicManager.GameState;


import android.os.AsyncTask;

public class ClientCommsTask extends AsyncTask<GameSessionManager, Void, Void>{
	GameState fromServer;
	ClientManager gsm;
	Socket s;
    ObjectOutputStream oos;
    ObjectInputStream oin;
	
	public ClientCommsTask(ClientManager gsm, Socket in) {
		this.gsm = gsm;
		this.s = in;
		
		try {
			//System.out.println("&&& socket has: " + s.getInputStream().toString() + " &&&");
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
				// if there is game state update from the server
				fromServer = (GameState)oin.readObject();
				// notify game flow manager there is a game state update from remote player
				gsm.receive(fromServer);
				System.out.println ("&&& GameState from Server: " + fromServer.currentAction + " &&&");
				
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

}
