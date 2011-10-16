package com.deebdroid.mobilemileagetracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MobilemileagetrackerActivity extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
		TextView t = (TextView)findViewById(R.id.textView);
		
		//"http://%s:%d/%s", "localhost", "8000", "api/v1/device/?format=json"
		//HttpConnector.connect("localhost", 8000, "api/v1/device/?format=json");
		//String json = RestJsonClient.connect("http://192.168.1.10:8000/api/v1/device/?format=json");
		
		try{

			String response = restTest.get("192.168.1.10", "device/", "dbro", "pw", 8000);
			
			GsonBuilder gsonb = new GsonBuilder();
			Gson gson = gsonb.create();
			 
			JSONObject j;
			DeviceResponse rDevices = null;
			 
			try
			{
			    j = new JSONObject(response);
			    rDevices = gson.fromJson(j.toString(), DeviceResponse.class);
			}
			catch(Exception e)
			{
			    e.printStackTrace();
			}
			
			
			//rDevice = gson.fromJson(response, Device.class);
			t.setText("Device " + rDevices.toString() + " successfully scooped!");
		}
		catch(Exception e){
			
		}
    	
    }
}