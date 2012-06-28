package com.cs446.purfect_litter;

import com.facebook.android.Facebook;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import com.facebook.android.*;
import com.facebook.android.Facebook.*;

public class Purfect_LitterActivity extends Activity {
    /** Called when the activity is first created. */
	Facebook facebook = new Facebook("249767381798243");
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        facebook.authorize(this, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {}

            @Override
            public void onFacebookError(FacebookError error) {}

            @Override
            public void onError(DialogError e) {}

            @Override
            public void onCancel() {}
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
}