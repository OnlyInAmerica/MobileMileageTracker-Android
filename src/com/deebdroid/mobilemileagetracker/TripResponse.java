package com.deebdroid.mobilemileagetracker;

public class TripResponse {
	
	public Trip[] trips;
	
	public TripResponse(){
		
	}
	
	public Trip[] getTrips(){
		return trips;
	}
	
	public void setTrips(Trip[] trips){
		this.trips = trips;
	}
	
	@Override
	public String toString(){
		return trips[0].toString();
	}

}
