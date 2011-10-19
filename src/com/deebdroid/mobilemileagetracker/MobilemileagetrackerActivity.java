package com.deebdroid.mobilemileagetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MobilemileagetrackerActivity extends Activity {
	
	public static final String SITE_URL = "10.0.1.7.";
	private static final String PREFS = "Credentials";	//For local prefs storage
	private static final int LOGIN_REQ_CODE = 0;		//For exchanging login data from LoginActivity
	private Context c;
	
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        c = this;
        
        prefs = getSharedPreferences(PREFS, 0);
        editor = prefs.edit();
        
        
        if(prefs.getBoolean("first_launch", true))
        	Login();
        else{
        	TextView tv = (TextView)findViewById(R.id.textView);
        	tv.setText("Hello " + prefs.getString("username", "You")+"!");
        }
        	
        	
    }
    
    
    private void Login(){
        new AlertDialog.Builder(this)
        .setMessage("Welcome to MobileMileageTracker! \n\n This application tracks your trips and allows you to upload them to www."+SITE_URL+". There, you can view detailed reports and other cool things.\n\n But first, let's grab your account info! ")
        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
        	@Override
        	public void onClick(DialogInterface dialog, int which) {
        		Intent i = new Intent(c, LoginActivity.class);
        		startActivityForResult(i, LOGIN_REQ_CODE);					
        	}
        })
        .show(); 	
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == LOGIN_REQ_CODE) {
			if (data.hasExtra("username") && data.hasExtra("password")) {
				String username = data.getExtras().getString("username");
				String password = data.getExtras().getString("password");
		        editor.putString("username", username);
		        editor.putString("password", password);
		        editor.putBoolean("first_launch", false);
		        editor.commit();
				Toast.makeText(this, data.getExtras().getString("username")+" logged in.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
    
}