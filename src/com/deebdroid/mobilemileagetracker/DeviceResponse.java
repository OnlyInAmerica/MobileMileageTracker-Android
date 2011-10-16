package com.deebdroid.mobilemileagetracker;

public class DeviceResponse {
	
	public Device[] devices;
	
	public DeviceResponse(){
		
	}
	
	public Device[] getDevices(){
		return devices;
	}
	
	public void setDevices(Device[] devices){
		this.devices = devices;
	}
	
	@Override
	public String toString(){
		return devices[0].toString();
	}

}
