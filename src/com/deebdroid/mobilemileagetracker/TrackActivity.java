package com.deebdroid.mobilemileagetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.deebdroid.mobilemileagetracker.MyLocation.LocationResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TrackActivity extends Activity{
	
	private LocationResult locationResult;
	public MyLocation myLocation;
	
	private String username;
	private String password;
	private String device_uri;
	private String tripName;
	private String trip_uri;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	username = extras.getString("username");
        	password = extras.getString("password");
        	tripName = extras.getString("tripName");
        	device_uri = extras.getString("device_uri");
        }
        
        Button stopButton = (Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(stopListener);
        
        setupLocationReceiver();   
        
	}
	
    private OnClickListener stopListener = new OnClickListener(){
    	public void onClick(View arg){
    		finish();
    	}
    };
	
	public void onPause(){
		super.onPause();
		myLocation.stopTracking();
	}
	
	private void setupLocationReceiver(){
		//Create Trip
		String cTrip = restTest.post(MobilemileagetrackerActivity.SITE_URL, "trip/",   "{\"name\": \""+tripName+"\", \"device\": \""+device_uri+"\"}", username, password, MobilemileagetrackerActivity.SITE_PORT);
      //			   restTest.post(MobilemileagetrackerActivity.SITE_URL, "device/", "{\"device_type\": \"Android\", \"name\": \""+user+" Phone\", \"uuid\": \""+myuuid+"\"}" ,user, pw, MobilemileagetrackerActivity.SITE_PORT);			
	
		
		String dog = "{\"name\": \""+tripName+"\", \"device\": \""+device_uri+"\"}";

		//Get Created Trip ID
		String trips = restTest.get(MobilemileagetrackerActivity.SITE_URL, "trip/", username, password, MobilemileagetrackerActivity.SITE_PORT);
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		DeviceResponse rDevices = null;
		
		TripResponse tR = gson.fromJson(trips, TripResponse.class);
		Trip[] tripList = tR.trips;
		
		for(int x=0;x<tripList.length;x++){
			if(tripList[x].name.equals(tripName))
				trip_uri = tripList[x].resource_uri;
		}
		
		locationResult = new LocationResult(){
            @Override
            public void gotLocation(final Location location){
            	//double acc = location.getAccuracy()/3.2808399;
            	//POST locations to current trip
            	String cLocData = "{" +
            			"\"altitude\": 1.0, " +
            			"\"horizontal_accuracy\":"+location.getAccuracy()+
            			",\"latitude\": "+ String.valueOf(location.getLatitude()) +
            			",\"longitude\": "+ String.valueOf(location.getLongitude())+
            			",\"timestamp\": \""+String.valueOf(location.getTime())+"\""+
            			",\"trip\": \""+trip_uri+"\", " +
            			"\"vertical_accuracy\": 0.0}";
            	String cLoc = restTest.post(MobilemileagetrackerActivity.SITE_URL, "location/", "{" +
            			"\"altitude\": 1.0, " +
            			"\"horizontal_accuracy\":"+location.getAccuracy()+
            			",\"latitude\": "+ String.valueOf(location.getLatitude()) +
            			",\"longitude\": "+ String.valueOf(location.getLongitude())+
            			",\"timestamp\": \""+String.valueOf(location.getTime())+"\""+
            			",\"trip\": \""+trip_uri+"\", " +
            			"\"vertical_accuracy\": 0.0}" ,
            			username, password, MobilemileagetrackerActivity.SITE_PORT);			

                	
                }
            };
            
            myLocation = new MyLocation();
            myLocation.trackLocation(this, locationResult);
    	}
            
        
    }
