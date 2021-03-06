/*
 * THANK YOU
 * http://inchoo.net/mobile-development/android-development/rest-api-with-http-authentication-android-beanstalk-example/
 */

package com.deebdroid.mobilemileagetracker;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class restTest {
	//TODO: Make this a singleton class that is initialized once with SITE_URL, PORT, username, password, device_uri
	// This will clean things up quite a bit.
	
	static final String MY_APP_TAG = "com.deebdroid.mobilemileagetracker";
	static final String API_URI = "/api/v1/";
	
	//Returns JSON String representation of the HTTPResponse
	public static String get(String host, String request, String username, String password, int port){
 
        String urlBasePath = "http://" + host + API_URI;
        String urlApiCall_FindAllRepositories = urlBasePath
                + request;
 
        try {
            HttpClient client = new DefaultHttpClient();
 
            AuthScope as = new AuthScope(host, port);
            UsernamePasswordCredentials upc = new UsernamePasswordCredentials(
                    username, password);
 
            ((AbstractHttpClient) client).getCredentialsProvider()
                    .setCredentials(as, upc);
 
            BasicHttpContext localContext = new BasicHttpContext();
 
            BasicScheme basicAuth = new BasicScheme();
            localContext.setAttribute("preemptive-auth", basicAuth);
 
            HttpHost targetHost = new HttpHost(host, port, "http");
 
            HttpGet httpget = new HttpGet(urlApiCall_FindAllRepositories);
            httpget.setHeader("Content-Type", "application/json");
 
            HttpResponse response = client.execute(targetHost, httpget,
                    localContext);
 
            HttpEntity entity = response.getEntity();
            Object content = EntityUtils.toString(entity);
            
 
            Log.d(MY_APP_TAG, "OK: " + content.toString());
            
            return content.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(MY_APP_TAG, "Error: " + e.getMessage());
        }
        
        return null;
	}

	public static String post(String host, String request, String data, String username, String password, int port){
		 
        String urlBasePath = "http://" + host + API_URI;
        String urlApiCall_FindAllRepositories = urlBasePath
                + request;
 
        try {
            HttpClient client = new DefaultHttpClient();
 
            AuthScope as = new AuthScope(host, port);
            UsernamePasswordCredentials upc = new UsernamePasswordCredentials(
                    username, password);
 
            ((AbstractHttpClient) client).getCredentialsProvider()
                    .setCredentials(as, upc);
 
            BasicHttpContext localContext = new BasicHttpContext();
 
            BasicScheme basicAuth = new BasicScheme();
            localContext.setAttribute("preemptive-auth", basicAuth);
 
            HttpHost targetHost = new HttpHost(host, port, "http");
 
            HttpPost httppost = new HttpPost(urlApiCall_FindAllRepositories);
            httppost.setHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(data));
 
            HttpResponse response = client.execute(targetHost, httppost,
                    localContext);
 
            HttpEntity entity = response.getEntity();
            Object content = EntityUtils.toString(entity);
            
 
            Log.d(MY_APP_TAG, "OK: " + content.toString());
            String deleteMe = content.toString();
            return content.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(MY_APP_TAG, "Error: " + e.getMessage());
        }
        
        return null;
	}
}
