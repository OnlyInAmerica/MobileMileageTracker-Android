package com.deebdroid.mobilemileagetracker;

import com.deebdroid.mobilemileagetracker.MyLocation.LocationResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MobilemileagetrackerActivity extends Activity {
	
	public static final String SITE_URL = "dbro.pagekite.me";
	public static final int SITE_PORT = 8000;
	private static final String PREFS = "Credentials";	//For local prefs storage
	private static final int LOGIN_REQ_CODE = 0;		//For exchanging login data from LoginActivity
	public Context c;
	
	public MyLocation myLocation;
	public LocationResult locationResult;
	public boolean tracking;
	
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	
	private String username;
	private String password;
	private String device_uri;
	
	//TODO: Remove tracking and related methods. Instead create a second constructor for MyLocation
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        c = this;
        
        //Set this true once a trip is started
        //The location tracking Activity will then receive updates
        tracking = false;
        
        //Uncomment below to 'warm up' gps on app launch
        //setupLocationReceiver();
             
        prefs = getSharedPreferences(PREFS, 0);
        editor = prefs.edit();
        

        if(prefs.getBoolean("first_launch", true))
        	Login();
        else{
        	TextView tv = (TextView)findViewById(R.id.textView);
        	tv.setText("Hello " + prefs.getString("username", "You")+"!");
        	username = prefs.getString("username", "nope");
        	password = prefs.getString("password","nope");
        	device_uri = prefs.getString("device_uri","nope");
        }

        Button beginTrip = (Button)findViewById(R.id.createTripButton);
        beginTrip.setOnClickListener(createTripListener);
        
        //TODO: Create/Set Trip Create/Continue trip button listeners, pass locationResult data to TrackActivity
    }
    
    private void testtheRest(){
		String devices = restTest.get(MobilemileagetrackerActivity.SITE_URL, "device/", "dbro", "K353ndipitou5", MobilemileagetrackerActivity.SITE_PORT);
    	String devTest = restTest.post(SITE_URL, "device/", "{\"device_type\": \"Android\", \"name\": \"test Phone\", \"uuid\": \"32s33\"}" ,"dbro", "K353ndipitou5", SITE_PORT);
    	System.out.println(devTest);
    	/*
    	String tripTest = restTest.post(SITE_URL, "trip/", 
    			"{\"name\": \"Chris Trip\", "+
    			"\"device\": \"/api/v1/device/1/\"}", "dbro", "K353ndipitou5", SITE_PORT);
    	*/
    	
    }
        
    private OnClickListener createTripListener = new OnClickListener(){
    	Editable tripName;
    	public void onClick(View arg){
    		final EditText input = new EditText(c);
    		new AlertDialog.Builder(c)
    	    .setTitle("Enter New Trip Name...")
    	    .setView(input)
    	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int whichButton) {
    	            tripName = input.getText(); 
    	            Intent i = new Intent(c, TrackActivity.class);
    	    		i.putExtra("username", username);
    	    		i.putExtra("password", password);
    	    		i.putExtra("device_uri", device_uri);
    	    		i.putExtra("tripName", String.valueOf(tripName));
    	    		startActivityForResult(i, 1);
    	        }
    	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int whichButton) {
    	            // Do nothing.
    	        }
    	    }).show();
    		
    	}
    };
    
    /*	Going to relocate data tracking to TrackActivity
    private void setupLocationReceiver(){
    	locationResult = new LocationResult(){
            @Override
            public void gotLocation(final Location location){
            	double acc = location.getAccuracy()/3.2808399;
            	Toast.makeText(c, "loc +/ "+String.valueOf(acc)+"ft.",Toast.LENGTH_SHORT).show();
                if(tracking){
                	/*
                	 * Was originally thinking I'd pass location data in an Intent. but this probably won't work once this activity is paused and 
                	 * Track activity is active.
                	Intent i = new Intent(getApplicationContext(), TrackActivity.class);
                	i.putExtra(key, value);
                	startActivity(i);
                	
                }
            }
    	};
            
        myLocation = new MyLocation();
        myLocation.trackLocation(this, locationResult);
    }
   */

    
    private void Login(){
        new AlertDialog.Builder(this)
        .setMessage("Welcome to MobileMileageTracker! \n\n This application tracks your trips and allows you to upload them to www."+SITE_URL+". There, you can view detailed reports and other cool things.\n\n But first, let's grab your account info! ")
        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
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
				username = data.getExtras().getString("username");
				password = data.getExtras().getString("password");
				device_uri = data.getExtras().getString("device_uri");
		        editor.putString("username", username);
		        editor.putString("password", password);
		        editor.putString("device_uri", device_uri);
		        editor.putBoolean("first_launch", false);
		        editor.commit();
				Toast.makeText(this, data.getExtras().getString("username")+" logged in.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
    
    public boolean isTracking(){
    	return tracking;
    }
    
    public void setTracking(boolean arg){
    	tracking = arg;
    }
    
}