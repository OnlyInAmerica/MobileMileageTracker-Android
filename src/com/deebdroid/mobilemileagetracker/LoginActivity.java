package com.deebdroid.mobilemileagetracker;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	
	//TODO: Let user input persist until Login pressed
	
	private Context c;
	private ProgressDialog pd;
	private String username;
	private String password; 
	private String device_uri;
	
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
		
		
		public void onClick(View arg0) {
			String response = null;
			
			EditText usernameField = (EditText)findViewById(R.id.usernameField);
			EditText passwordField = (EditText)findViewById(R.id.passwordField);
			
			username = String.valueOf(usernameField.getText()).trim();
			password = String.valueOf(passwordField.getText()).trim();
			
			if(username.equals("") || password.equals("")){
				new AlertDialog.Builder(c)
	        	.setMessage("Enter a username & password.")
	            .setPositiveButton("Ok", null)
	            .show();
			}
			else{
				//Create a new thread for network stuff
				pd = ProgressDialog.show(c, "Logging in...", "Beep Boop-Boop Bobba Doop",true,false);
				new LoginTask().execute(username, password);
				
			}
		}
	};
	
		//Authenticate user and register this Device if necessary
		protected boolean checkRegisterDevice(String user, String pw) {
		String devices = restTest.get(MobilemileagetrackerActivity.SITE_URL, "device/", user, pw, MobilemileagetrackerActivity.SITE_PORT);
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		DeviceResponse rDevices = null;
	
		if((rDevices = gson.fromJson(devices, DeviceResponse.class)) == null)
			return false;	// Login credentials were invalid. If user was valid, but had no devices, 
							// a valid but empty DeviceResponse will be returned
		
		Device[] rDevicesList = rDevices.getDevices();
		Device myDevice = null;
		String myuuid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		
		for(int x=0;x<rDevicesList.length;x++){
			if(rDevicesList[x].uuid.equals(myuuid))
				myDevice = rDevicesList[x];
		}
			
		if(myDevice == null){
			//If the current device is not registered for this user, create it
			restTest.post(MobilemileagetrackerActivity.SITE_URL, "device/", "{\"device_type\": \"Android\", \"name\": \""+user+" Phone\", \"uuid\": \""+myuuid+"\"}" ,user, pw, MobilemileagetrackerActivity.SITE_PORT);			
			
			//Get the ID of the newly created device.
			//TODO: Instead of repeated retrieve all user devices -> find this one, Modify the API to return created device's unique ID.
			devices = restTest.get(MobilemileagetrackerActivity.SITE_URL, "device/", user, pw, MobilemileagetrackerActivity.SITE_PORT);
			rDevices = null;
			
			rDevices = gson.fromJson(devices, DeviceResponse.class);
			rDevicesList = rDevices.getDevices();
			
			for(int x=0;x<rDevicesList.length;x++){
				if(rDevicesList[x].uuid.equals(myuuid))
					device_uri = rDevicesList[x].resource_uri;
			}
		}
		else
			device_uri = myDevice.resource_uri;
		
		return true;
	}
		
	private class LoginTask extends AsyncTask<String, Void, Boolean>{
		//Executed in new thread
		@Override
		protected Boolean doInBackground(String... params) {
			return (Boolean)checkRegisterDevice(params[0],params[1]);
		}
		
		//Executed in UI thread
		protected void onPostExecute(Boolean didLogin){
			//
			pd.dismiss();
			
			if(didLogin == true){
				Intent data = new Intent();
				data.putExtra("username", username);
				data.putExtra("password", password);
				data.putExtra("device_uri", device_uri);
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
}
