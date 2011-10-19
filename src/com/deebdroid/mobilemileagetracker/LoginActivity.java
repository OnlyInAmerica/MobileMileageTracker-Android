package com.deebdroid.mobilemileagetracker;

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
				response = restTest.get(MobilemileagetrackerActivity.SITE_URL, "device/", "dbro", "pw", 8000);
				if(response != null){
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
}
