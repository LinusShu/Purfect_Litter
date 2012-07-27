package com.cs446.purfect_litter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cs446.purfect_litter.LogicManager.Game;
import com.cs446.purfect_litter.LogicManager.Player;
import com.cs446.purfect_litter.LogicManager.Player.Pile;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

public class MainActivity extends Activity {
	
	public enum SessionType {
		HOST,
		CLIENT
	}
	
	private enum Screen {
		START,
		DASHBOARD,
		DETAIL
	}
	
	// Model ========================================================
	
	Game G;
	
	// should reside in model as get/set
	Pile currentPile;
	CardInstance currentCardInstance;
	
	// UI ===========================================================
	
	Screen currentScreen;
	
	ProgressDialog waitForStartProgressDialog;
	
	ViewFlipper viewFlipper;
	Gallery cardGallery;
	ImageView cardDetailImageView;
	Button cardDetailActionButton;
	Button cardDetailBackButton;
	TextView loveTextView;
	TextView actionTextView;
	TextView purchaseTextView;
	TextView playerNameTextView;
	TextView gameLogTextView;
	TextView currentPhaseTextView;
	
	String[] cardPilesStrings = {
			"Hand", "Played", "Discard", "Chamber", "Deck"
	};
	
	Integer[][] cards = {
			{
				R.drawable.card_cat1,
				R.drawable.card_cat2,
				R.drawable.card_cat3,
				R.drawable.card_cat4,
				R.drawable.card_cat5
			},
			{
				R.drawable.card_cat6,
				R.drawable.card_cat7,
				R.drawable.card_cat8,
				R.drawable.card_cat9,
				R.drawable.card_cat10,
				R.drawable.card_cat11,
				R.drawable.card_cat12,
				R.drawable.card_cat13
			},
			{
				R.drawable.card_cat1,
				R.drawable.card_love2,
				R.drawable.card_love3
			},
			{
				R.drawable.card_cat14,
				R.drawable.card_cat15,
				R.drawable.card_cat16,
				R.drawable.card_cat17
			}
	};
	
	// ==============================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponents();
        registerControllers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	boolean status = G.isItMyTurn();
		menu.getItem(0).setEnabled(status);
    	return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menu_next:
			G.doNextPhase();
			break;
		case R.id.menu_quit:
			try {
				int pid = android.os.Process.myPid();
				android.os.Process.killProcess(pid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
    	}
    	return true;
	}
	
	// ==============================================================
    
    private void setGallery(Pile p, int i) {
    	currentPile = p;
    	cardGallery.setAdapter(new ImageAdapter(this, G.getMyCardPile(p)));
		Toast.makeText(getBaseContext(), 
					"Viewing Cards in " + cardPilesStrings[i], 
					Toast.LENGTH_SHORT).show();
    }
    
    private void registerControllers() {
        cardGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				cardDetailImageView.setImageResource((Integer) arg0.getItemAtPosition(id));
				currentCardInstance = G.getMyCard(currentPile, id);
				viewFlipper.showNext();
				currentScreen = Screen.DETAIL;
			}
        });
    }
	
	// ==============================================================
    
    public void startAsHostHandler(View view) {
    	G = new Game(this, SessionType.HOST);
    	waitForStartProgressDialog = ProgressDialog.show(MainActivity.this, "", "Waiting for other players...", true);
    }
    
    public void startAsClientHandler(View view) {
    	G = new Game(this, SessionType.CLIENT);
    	waitForStartProgressDialog = ProgressDialog.show(MainActivity.this, "", "Joining a game...", true);
    }
    
    public void handHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(true);
    	}
    	setGallery(Player.Pile.HAND, 0);
    }
    
    public void playedHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(Player.Pile.PLAYED, 1);
    }
    
    public void discardHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(Player.Pile.DISCARD, 2);
    }
    
    public void chamberHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(Player.Pile.CHAMBER, 3);
    }
    
    public void deckHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(Pile.DECK, 4);
    }
    
    public void cardDetailCancelHandler(View view) {
    	viewFlipper.showPrevious();
    	currentScreen = Screen.DASHBOARD;
    }
    
    public void cardDetailActionHandler(View view) {
    	G.doPickCard(currentCardInstance);
    	viewFlipper.showPrevious();
    	currentScreen = Screen.DASHBOARD;
    }
	
	// ==============================================================

    private void initUIComponents() {
    	currentScreen = Screen.START;
    	
    	viewFlipper = (ViewFlipper) findViewById(R.id.mainViewFlipper);
    	cardGallery = (Gallery) findViewById(R.id.mainCardGallery);
    	cardDetailImageView = (ImageView) findViewById(R.id.mainCardDetailImageView);
    	cardDetailActionButton = (Button) findViewById(R.id.detail_action_button);
    	cardDetailBackButton = (Button) findViewById(R.id.detail_back_button);
    	
    	loveTextView = (TextView) findViewById(R.id.love_text);
    	actionTextView = (TextView) findViewById(R.id.action_text);
    	purchaseTextView = (TextView) findViewById(R.id.purchase_text);
    	playerNameTextView = (TextView) findViewById(R.id.player_name_text);
    	gameLogTextView = (TextView) findViewById(R.id.game_log_text);
    	currentPhaseTextView = (TextView) findViewById(R.id.current_phase_text);
    }

    private void startGame() {
    	waitForStartProgressDialog.dismiss();
    	viewFlipper.showNext();
    	currentScreen = Screen.DASHBOARD;
    }
    
    public void update() {
    	runOnUiThread(new Runnable() {
            @Override
        	public void run() {
            	switch (currentScreen) {
				case START: 	// ----------------------------------
					startGame();
					// cascade
				case DASHBOARD: // ----------------------------------
	            	// MY TURN
	            	if (G.isItMyTurn()) {
	            		currentPhaseTextView.setText("Current Phase: " + G.getCurrentPhase());
	                	loveTextView.setText("Love: " + G.getGameState().currentLove);
	                	actionTextView.setText("Action: " + G.getGameState().currentAction);
	                	purchaseTextView.setText("Purchase: " + G.getGameState().currentPurchase);
	                	
	            		cardDetailActionButton.setEnabled(true);
	            	}
	            	// NOT MY TURN
	            	else {
	            		currentPhaseTextView.setText("Current Phase: Not Your Turn");
	                	loveTextView.setText("");
	                	actionTextView.setText("");
	                	purchaseTextView.setText("");
	                	
	            		cardDetailActionButton.setEnabled(false);
	            	}
	            	
	            	playerNameTextView.setText("Hello, " + G.getMyName());
	            	setGallery(Player.Pile.HAND, 0);
	            	
//	            	String gameLog = (String) gameLogTextView.getText();
	            	gameLogTextView.setText(G.getGameState().pullLastActions());
	            	
	            	break;
				default: 		// ----------------------------------
					break;
				}

            }
    	});
    }
	
	// ==============================================================

    /****************************************************************
     * ImageAdapter for Gallery
     */
    public class ImageAdapter extends BaseAdapter {

    	private Context context;
    	int imageBackground;
    	Integer[] images;
    	
    	public ImageAdapter(Context c, Integer[] images) {
			this.context = c;
			this.images = images;
			TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
			imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		@Override
    	public int getCount() {
    		return images.length;
    	}

    	@Override
    	public Object getItem(int id) {
    		return images[id];
    	}

    	@Override
    	public long getItemId(int id) {
    		return id;
    	}

    	@Override
    	public View getView(int id, View view, ViewGroup viewGroup) {
    		ImageView imageView = new ImageView(context);
    		imageView.setImageResource(images[id]);
    		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    		imageView.setLayoutParams(new Gallery.LayoutParams(180, 250));
    		imageView.setBackgroundResource(imageBackground);
    		return imageView;
    	}
    }
}
