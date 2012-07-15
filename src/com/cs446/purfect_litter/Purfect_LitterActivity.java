package com.cs446.purfect_litter;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cs446.purfect_litter.gameLogicManager.cardManager.Card;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CatCardFactory;
import com.cs446.purfect_litter.gameLogicManager.cardManager.EventCardFactory;
import com.cs446.purfect_litter.gameLogicManager.cardManager.LoveCardFactory;


public class Purfect_LitterActivity extends Activity {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //create card definitions using the card factory methods
        List<Card> LoveCards = new ArrayList<Card>();
        List<Card> EventCards = new ArrayList<Card>();
        List<Card> CatCards = new ArrayList<Card>();
        
        LoveCards = new LoveCardFactory().Cards();
        EventCards = new EventCardFactory().Cards();
        CatCards = new CatCardFactory().Cards();
        
    }

    
}