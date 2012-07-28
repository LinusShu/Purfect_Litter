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
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cs446.purfect_litter.LogicManager.Game;
import com.cs446.purfect_litter.LogicManager.GameState.TownPile;
import com.cs446.purfect_litter.LogicManager.Player;
import com.cs446.purfect_litter.LogicManager.Player.Pile;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

/**
 * MainActivity is an Android Activity class which instantiates the application.
 * In the architecture, it acts as the controller.
 *
 */
public class MainActivity extends Activity {
	
	public enum SessionType {
		HOST,
		CLIENT
	}
	
	private enum Screen {
		START,
		DASHBOARD,
		DETAIL,
		PURCHASE,
		PURCHASE_DETAIL
	}
	
	// Model ========================================================
	
	Game G;
	
	// UI Components ================================================
	
	Screen currentScreen;
	Pile currentPile;
	CardInstance currentDetailCardInstance;
	CardInstance currentPurchaseCardInstance;

	ViewFlipper viewFlipper;
	ProgressDialog waitForStartProgressDialog;
	
	TextView playerNameTextView;
	TextView currentPhaseTextView;
	TextView gameLogTextView;
	TextView loveTextView;
	TextView actionTextView;
	TextView purchaseTextView;
	Gallery cardGallery;
	
	ImageView cardDetailImageView;
	Button cardDetailActionButton;
	Button cardDetailBackButton;
	
	TabHost purchaseTabHost;
	Gallery purchaseLoveGallery;
	Gallery purchaseChiefGallery;
	Gallery purchaseGeneralGallery;
	Button purchaseLoveButton;
	Button purchaseChiefButton;
	Button purchaseGeneralButton;
	
	ExpandableListView purchaseExpandableListView;
	
	ImageView purchaseDetailImageView;
	Button purchaseDetailBuyButton;
	Button purchaseDetailBackButton;
	TextView purchaseLoveTextView;
	TextView purchasePurchaseTextView;
	
	// ==============================================================
	
	/**
	 * Creates the Activity.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponents();
        registerListeners();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	setScreen(currentScreen);
    }
    
    /**
     * Creates the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Determines which menu items should be enabled/disabled.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	boolean status = G.isItMyTurn();
		menu.getItem(0).setEnabled(status);
    	return true;
    }
	
    /**
     * Handler for menu items.
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menu_next:
			setScreen(Screen.DASHBOARD);
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

	/**
	 * Associate variables in this class with elements in the XML layout files
	 * to facilitate registering of controllers.
	 */
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
    	
    	purchaseLoveTextView = (TextView) findViewById(R.id.purchase_love_text);
    	purchasePurchaseTextView = (TextView) findViewById(R.id.purchase_purchase_text);
    	
    	purchaseLoveButton = (Button) findViewById(R.id.buy_love_button);
    	purchaseChiefButton = (Button) findViewById(R.id.buy_chief_button);
    	purchaseGeneralButton = (Button) findViewById(R.id.buy_general_button);

    	purchaseTabHost = (TabHost) findViewById(R.id.purchase_view);
    	purchaseTabHost.setup();
    	
    	TabHost.TabSpec loveTabSpec = purchaseTabHost.newTabSpec("love");
    	loveTabSpec.setContent(R.id.loveTab);
    	loveTabSpec.setIndicator("Love");
    	purchaseTabHost.addTab(loveTabSpec);
    	
    	TabHost.TabSpec generalTabSpec = purchaseTabHost.newTabSpec("general");
    	generalTabSpec.setContent(R.id.generalTab);
    	generalTabSpec.setIndicator("General");
    	purchaseTabHost.addTab(generalTabSpec);
    	
    	TabHost.TabSpec chiefTabSpec = purchaseTabHost.newTabSpec("chief");
    	chiefTabSpec.setContent(R.id.chiefTab);
    	chiefTabSpec.setIndicator("Chief");
    	purchaseTabHost.addTab(chiefTabSpec);
    	
    	purchaseLoveGallery = (Gallery) findViewById(R.id.purchase_love_gallery);
    	purchaseChiefGallery = (Gallery) findViewById(R.id.purchase_chief_gallery);
    	purchaseGeneralGallery = (Gallery) findViewById(R.id.purchase_general_gallery);
    }
    
    /**
     * Sets the screen.
     * @param whichScreen
     */
    private void setScreen(Screen whichScreen) {
    	switch (whichScreen) {
		case DASHBOARD:
			viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.dashboard_view)));
			break;
		case DETAIL:
			viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.card_detail_view)));
			break;
		case PURCHASE:
			viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.purchase_view)));
			break;
		default:
			break;
		}
    	currentScreen = whichScreen;
    }
    
    private void setGallery(Gallery whichGallery, Pile p) {
    	currentPile = p;
    	whichGallery.setAdapter(new ImageAdapter(this, G.getMyCardPile(p)));
    }
    
    private void setPurchaseGalleries() {
    	purchaseLoveGallery.setAdapter(new ImageAdapter(this, G.getTownCardPile(TownPile.LOVE)));
		purchaseChiefGallery.setAdapter(new ImageAdapter(this, G.getTownCardPile(TownPile.CHIEF)));
		purchaseGeneralGallery.setAdapter(new ImageAdapter(this, G.getTownCardPile(TownPile.GENERAL)));
    }

    /**
     * After clients have connected, dismiss the spinner and show the main dashboard.
     */
    private void startGame() {
    	waitForStartProgressDialog.dismiss();
    	setScreen(Screen.DASHBOARD);
    }
    
    /**
     * Registers listeners on various UI components.
     */
    private void registerListeners() {
        cardGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				cardDetailImageView.setImageResource((Integer) arg0.getItemAtPosition(id));
				currentDetailCardInstance = G.getMyCard(currentPile, id);
				setScreen(Screen.DETAIL);
			}
        });
        purchaseLoveGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				currentPurchaseCardInstance = G.getTownCard(TownPile.LOVE, id);
			}
        });
        purchaseChiefGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				currentPurchaseCardInstance = G.getTownCard(TownPile.CHIEF, id);
			}
        });
        purchaseGeneralGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				currentPurchaseCardInstance = G.getTownCard(TownPile.GENERAL, id);
			}
        });
        purchaseTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("love")) {
					currentPurchaseCardInstance = G.getTownCard(TownPile.LOVE, 0);	// 1 love
				}
				else if (tabId.equals("general")) {
					currentPurchaseCardInstance = G.getTownCard(TownPile.GENERAL, 0);	// first general
				}
				else if (tabId.equals("chief")) {
					currentPurchaseCardInstance = G.getTownCard(TownPile.CHIEF, 0);	// first chief
				}
			}
		});
    }
	
	// BUTTON HANDLERS ==============================================
    
    /**
     * Handler for the "Host a Game" button on the startup screen.
     * @param view
     */
    public void startAsHostHandler(View view) {
    	G = new Game(this, SessionType.HOST);
    	waitForStartProgressDialog = ProgressDialog.show(MainActivity.this, "", "Waiting for other players...", true);
    }

    
    /**
     * Handler for the "Join a Game" button on the startup screen.
     * @param view
     */
    public void startAsClientHandler(View view) {
    	G = new Game(this, SessionType.CLIENT);
    	waitForStartProgressDialog = ProgressDialog.show(MainActivity.this, "", "Joining a game...", true);
    }
    
    /**
     * Handler for "Hand" button, to show the player cards in their hand.
     * @param view
     */
    public void handHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(true);
    	}
    	setGallery(cardGallery, Player.Pile.HAND);
		Toast.makeText(getBaseContext(), 
				"Viewing Cards in Hand", 
				Toast.LENGTH_SHORT).show();
    }
    
    public void playedHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(cardGallery, Player.Pile.PLAYED);
		Toast.makeText(getBaseContext(), 
				"Viewing Cards in Played Pile", 
				Toast.LENGTH_SHORT).show();
    }
    
    public void discardHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(cardGallery, Player.Pile.DISCARD);
		Toast.makeText(getBaseContext(), 
				"Viewing Cards in Discard Pile", 
				Toast.LENGTH_SHORT).show();
    }
    
    public void chamberHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(cardGallery, Player.Pile.CHAMBER);
		Toast.makeText(getBaseContext(), 
				"Viewing Cards in Chamber", 
				Toast.LENGTH_SHORT).show();
    }
    
    public void deckHandler(View view) {
    	if (G.isItMyTurn()) {
    		cardDetailActionButton.setEnabled(false);
    	}
    	setGallery(cardGallery, Pile.DECK);
		Toast.makeText(getBaseContext(), 
				"Viewing Cards in Deck", 
				Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Handler for "Cancel" button on Card Detail screen.
     * @param view
     */
    public void cardDetailCancelHandler(View view) {
    	setScreen(Screen.DASHBOARD);
    }
    
    /**
     * Handler for "Select" button on Card Detail screen.
     * @param view
     */
    public void cardDetailActionHandler(View view) {
    	CardInstance playAttempt = currentDetailCardInstance;
    	if (G.doPickCard(currentDetailCardInstance)) {
    		Toast.makeText(getBaseContext(), 
    				playAttempt.getDef().getName() + " played successfuly.", 
    				Toast.LENGTH_SHORT).show();
    	}
    	else {
    		Toast.makeText(getBaseContext(), 
    				playAttempt.getDef().getName() + " could not be played. Invalid move.", 
    				Toast.LENGTH_SHORT).show();
    	}
    	loveTextView.setText("Love: " + G.getGameState().currentLove);
    	actionTextView.setText("Action: " + G.getGameState().currentAction);
    	purchaseTextView.setText("Purchase: " + G.getGameState().currentPurchase);
    	setGallery(cardGallery, Pile.HAND);
    	setScreen(Screen.DASHBOARD);
    }
    
    /**
     * Handler for "Buy" button on Purchase screen.
     * @param view
     */
    public void buyHandler(View view) {
    	if (currentPurchaseCardInstance == null) {
    		Toast.makeText(getBaseContext(), 
    				"Please select a card.", 
    				Toast.LENGTH_SHORT).show();
    		return;
    	}
    	CardInstance purchaseAttempt = currentPurchaseCardInstance;
    	// Purchase successful
    	if (G.doPickCard(currentPurchaseCardInstance)) {
    		Toast.makeText(getBaseContext(), 
    				purchaseAttempt.getDef().getName() + " bought successfuly.", 
    				Toast.LENGTH_SHORT).show();
    	}
    	// Purchase unsuccessful
    	else {
    		Toast.makeText(getBaseContext(), 
    				purchaseAttempt.getDef().getName() + " could not be bought.", 
    				Toast.LENGTH_SHORT).show();
    	}
    }
	
	// ==============================================================
    
    /**
     * Method that the Model calls to update the views.
     */
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
	            		
	            		setPurchaseGalleries();
	            		currentPurchaseCardInstance = G.getTownCard(TownPile.LOVE, 0);	// 1 love
	            		purchaseLoveTextView.setText("Love Points Available: " + G.getGameState().currentLove);
	                	purchasePurchaseTextView.setText("Purchase Points Available: " + G.getGameState().currentPurchase);
	            		
	            		switch (G.getGameState().currentPhase) {
	            		case PURCHASE:
	            			setScreen(Screen.PURCHASE);
	            			break;
	            		default:
	            			break;
	            		}
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
	            	gameLogTextView.setText(G.getGameState().pullLastActions());
	            	setGallery(cardGallery, Player.Pile.HAND);
	            	
	            	break;
				case PURCHASE: 	// ----------------------------------
            		setPurchaseGalleries();
            		currentPurchaseCardInstance = G.getTownCard(TownPile.LOVE, 0);	// 1 love
            		purchaseLoveTextView.setText("Love Points Available: " + G.getGameState().currentLove);
                	purchasePurchaseTextView.setText("Purchase Points Available: " + G.getGameState().currentPurchase);
                	break;
				default: 		// ----------------------------------
					break;
				}

            }
    	});
    }
	

    /****************************************************************
     * ImageAdapter for Gallery Views
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
