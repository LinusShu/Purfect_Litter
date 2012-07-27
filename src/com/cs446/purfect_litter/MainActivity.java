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
		DETAIL,
		PURCHASE,
		PURCHASE_DETAIL
	}
	
	// Model ========================================================
	
	Game G;
	
	// should reside in model as get/set
	Pile currentPile;
	CardInstance currentDetailCardInstance;
	CardInstance currentPurchaseCardInstance;
	
	// UI ===========================================================
	
	Screen currentScreen;

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
	
	ExpandableListView purchaseExpandableListView;
	TextView purchaseLoveTextView;
	TextView purchasePurchaseTextView;
	
	ImageView purchaseDetailImageView;
	Button purchaseDetailBuyButton;
	Button purchaseDetailBackButton;
	
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
    	
    	purchaseExpandableListView = (ExpandableListView) findViewById(R.id.purchase_expandable_list_view);
    	purchaseLoveTextView = (TextView) findViewById(R.id.purchase_love_text);
    	purchasePurchaseTextView = (TextView) findViewById(R.id.purchase_purchase_text);
    	
    	purchaseDetailImageView = (ImageView) findViewById(R.id.purchase_detail_image_view);
    	purchaseDetailBuyButton = (Button) findViewById(R.id.purchase_buy_button);
    	purchaseDetailBackButton = (Button) findViewById(R.id.purchase_back_button);

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
//		case PURCHASE_DETAIL:
//			viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.purchase_detail_view)));
//			break;
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
//    	purchaseLoveGallery.setAdapter(new ImageAdapter(this, images));
//		purchaseChiefGallery.setAdapter(new ImageAdapter(this, images));
//		purchaseGeneralGallery.setAdapter(new ImageAdapter(this, images));
    }
    
    private void registerControllers() {
        cardGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				cardDetailImageView.setImageResource((Integer) arg0.getItemAtPosition(id));
				currentDetailCardInstance = G.getMyCard(currentPile, id);
				setScreen(Screen.DETAIL);
			}
        });
    }

    private void startGame() {
    	waitForStartProgressDialog.dismiss();
    	setScreen(Screen.DASHBOARD);
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
    
    public void cardDetailCancelHandler(View view) {
    	setScreen(Screen.DASHBOARD);
    }
    
    public void cardDetailActionHandler(View view) {
    	G.doPickCard(currentDetailCardInstance);
    	setScreen(Screen.DASHBOARD);
    }
    
//    public void purchaseDoneHandler(View view) {
//    	setScreen(Screen.DASHBOARD);	// return to dashboard
//		G.doNextPhase();
//    }
    
//    public void purchaseDetailBuyHandler(View view) {
//    	
//    }
    
//    public void purchaseDetailBackHandler(View view) {
//    	setScreen(Screen.PURCHASE);
//    }
	
	// ==============================================================
    
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
