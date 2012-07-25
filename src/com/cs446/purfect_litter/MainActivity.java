package com.cs446.purfect_litter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	
	ViewFlipper viewFlipper;
	Gallery cardGallery;
	ImageView cardDetailImageView;
	Button cardDetailCancelButton;
	
	Integer[] cards = {
			R.drawable.card_cat1,
			R.drawable.card_cat2,
			R.drawable.card_cat3,
			R.drawable.card_cat4,
			R.drawable.card_cat5,
			R.drawable.card_cat6,
			R.drawable.card_cat7,
			R.drawable.card_cat8
	};
	
//	card_badhabit.png              card_cat4.png
//	card_cat1.png                  card_cat5.png
//	card_cat10.png                 card_cat6.png
//	card_cat11.png                 card_cat7.png
//	card_cat12.png                 card_cat8.png
//	card_cat13.png                 card_cat9.png
//	card_cat14.png                 card_illness.png
//	card_cat15.png                 card_love1.png
//	card_cat16.png                 card_love2.png
//	card_cat17.png                 card_love3.png
//	card_cat18.png                 ic_action_search.png
//	card_cat2.png                  ic_launcher.png
//	card_cat3.png                  ic_menu_close_clear_cancel.png


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponents();
        registerListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void initUIComponents() {
    	viewFlipper = (ViewFlipper) findViewById(R.id.mainViewFlipper);
    	cardGallery = (Gallery) findViewById(R.id.mainCardGallery);
    	cardDetailImageView = (ImageView) findViewById(R.id.mainCardDetailImageView);
    	cardDetailCancelButton = (Button) findViewById(R.id.mainCardDetailCancelButton);
    	
    	cardGallery.setAdapter(new ImageAdapter(this, cards));
    }
    
    public void registerListeners() {
        cardGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int id, long arg3) {
				Toast.makeText(getBaseContext(), 
						"You have selected picture " + (id + 1) + " of Antartica", 
						Toast.LENGTH_SHORT).show();
				cardDetailImageView.setImageResource(cards[id]);
				viewFlipper.showNext();
			}
        });
    }
    
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
    		return id;
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
