package com.cs446.purfect_litter.gameSessionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.cs446.purfect_litter.gameLogicManager.GameState;

import android.os.AsyncTask;


public class ServerCommsTask extends AsyncTask<GameSessionManager, Void, Void> {
    ServerManager gsm;
    Socket s;
    ObjectOutputStream oos;
    ObjectInputStream oin;
	GameState fromClient;
	int id;
	
	public ServerCommsTask(ServerManager gsm, Socket in, int pos) {
		this.gsm = gsm;
		this.s = in;
		this.id = pos;
		GameState initState = new GameState();
		
		initState.setID(gsm.getClientId(pos));
		initState.currentAction = 180;
		
		try {
			//System.out.println("&&& socket has: " + s.getInputStream().toString() + " &&&");
			oos = new ObjectOutputStream(s.getOutputStream());
		    oin = new ObjectInputStream(s.getInputStream());
		    //reply to the client's initial state
		    oos.writeObject(initState);
		    oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! Can not get socket input/output stream !!!");
		}
	}
	
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
		
		g.setID(id);
		try {
			oos.reset();
			System.out.println("&&& Sending game state to client: " + this.id + " &&&");
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
