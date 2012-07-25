package com.cs446.purfect_litter.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs446.purfect_litter.R;
/* 
 * Used to create a general alert dialog with a title, a message, and a 
 * "OK" button to dismiss the dialog
 * NOTE: 
 * "OK" button listener should be implemented when creating the dialog in
 *  and activity
 */

public class MsgDialog extends Dialog implements android.view.View.OnClickListener{
    private String t, msg = null;
    private TextView title, message;
    private Button okButton;
    
	public MsgDialog(Context context, String title, String msg) {
		super(context);
		this.t = title;
		this.msg = msg;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_dialog);
		
		title = (TextView) findViewById(R.id.title);
		this.setTitle(t);
		
		message = (TextView) findViewById(R.id.msg);
		message.setText(msg);
		
		okButton = (Button) findViewById(R.id.okButton);
		okButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

}
