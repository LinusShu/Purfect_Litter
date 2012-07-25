package com.cs446.purfect_litter;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CatCardFactory;
import com.cs446.purfect_litter.gameLogicManager.cardManager.EventCardFactory;
import com.cs446.purfect_litter.gameLogicManager.cardManager.LoveCardFactory;
import com.cs446.purfect_litter.gameSessionManager.ClientManager;
import com.cs446.purfect_litter.gameSessionManager.GameSessionManager;
import com.cs446.purfect_litter.gameSessionManager.ServerManager;
import com.cs446.purfect_litter.ui.MsgDialog;


public class PurfectLitterActivity extends Activity {
	private Button sendBt, broadcastBt;
	GameSessionManager gsm;
	GameState g = new GameState();
	MsgDialog d;

	
	// create menu and menu item "exit"
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		final CharSequence[] gameModeItems = {"Server", "Client"};
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        
        
        g.currentAction = 19;
    	g.currentLove = 89;
    	
    	/*
    	d = new MsgDialog(this, "Error", "Msg");
    	d.show();
    	*/
    	
    	// define testing buttons
    	sendBt = (Button) findViewById(R.id.sendButton);
    	sendBt.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			gsm.send(g);
    			System.out.println("UI: send button clicked");
    		}
    	});
    	
    	broadcastBt = (Button) findViewById(R.id.broadcastButton);
    	broadcastBt.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			GameState g2 = new GameState();
    			g2.currentAction = 170;
    			gsm.send(g2);
    			System.out.println("UI: boardcast button clicked");
    		}
    	});
    	
    	// Dialog boxes associated with GameSessionManager
    	// dialog box to select a game mode
    	AlertDialog.Builder selectMode = new AlertDialog.Builder(this);
    	selectMode.setTitle("Start the game as: ");
    	selectMode.setItems(gameModeItems, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        switch (item) {
    	        case 0:
    	        	// spawn a server manager when choose to start the game as a server
    	        	gsm = new ServerManager();
    	        	broadcastBt.setVisibility(View.VISIBLE);
    	        	break;
    	        case 1:
    	        	gsm = new ClientManager();
    	        	sendBt.setVisibility(View.VISIBLE);
    	        	break;
    	        }
    	        	
    	    }
    	});
    	AlertDialog alert = selectMode.create();
    	alert.show();
    	

    	
    	
    	/*
        //create card definitions using the card factory methods
        List<Card> LoveCards = new ArrayList<Card>();
        List<Card> EventCards = new ArrayList<Card>();
        List<Card> CatCards = new ArrayList<Card>();
        
        LoveCards = new LoveCardFactory().Cards();
        EventCards = new EventCardFactory().Cards();
        CatCards = new CatCardFactory().Cards();
        */
    }

    
}