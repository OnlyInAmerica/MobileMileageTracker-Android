package com.deebdroid.mobilemileagetracker;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	
	//TODO: Let user input persist until Login pressed
	
	private Context c;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        c = this;
        
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginListener);
	}
	
	@Override
	public void finish() {
		super.finish();
	}
	
	private OnClickListener loginListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String response = null;
			
			EditText usernameField = (EditText)findViewById(R.id.usernameField);
			EditText passwordField = (EditText)findViewById(R.id.passwordField);
			
			String username = String.valueOf(usernameField.getText());
			String password = String.valueOf(passwordField.getText());
			
			if(username.equals("") || password.equals("")){
				new AlertDialog.Builder(c)
	        	.setMessage("Enter a username & password.")
	            .setPositiveButton("Ok", null)
	            .show();
			}
			else{
				if(checkRegisterDevice(username, password)){
					Intent data = new Intent();
					data.putExtra("username", username);
					data.putExtra("password", password);
					setResult(RESULT_OK, data);
					finish();
				}
				else{
					new AlertDialog.Builder(c)
		        	.setMessage("Login Failed")
		            .setPositiveButton("Ok", null)
		            .show();
				}
			}
		}
	};
	
	//Acts as user authenticator and device checker/registerer
	protected boolean checkRegisterDevice(String user, String pw) {
		String devices = restTest.get(MobilemileagetrackerActivity.SITE_URL, "device/", user, pw, MobilemileagetrackerActivity.SITE_PORT);
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		 
		JSONObject j;
		DeviceResponse rDevices = null; 
		try{
		    j = new JSONObject(devices);
		    rDevices = gson.fromJson(j.toString(), DeviceResponse.class);
		}
		catch(Exception e){return false;}
		
		Device[] rDevicesList = rDevices.getDevices();
		Device myDevice = null;
		String myuuid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		//TODO: check no devices for user behavior
		for(int x=0;x<rDevicesList.length;x++){
			if(rDevicesList[x].uuid.equals(myuuid))
				myDevice = rDevicesList[x];
		}
		
		if(myDevice == null){
			//If the current device is not registered for this user, create it
			
		}
		
		return true;
	}
}
