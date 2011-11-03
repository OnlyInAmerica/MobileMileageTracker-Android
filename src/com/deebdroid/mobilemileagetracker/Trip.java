package com.deebdroid.mobilemileagetracker;

public class Trip {
	public Device device;
	public String id;
	public String name;
	public String resource_uri;
	public String user;
	
	public Trip(){
		device = new Device();
		id = "";
		name = "";
		resource_uri="";
		user = "";
	}
	public Trip(Device D, String Id, String Name, String R_uri, String User){
		device= D;
		id = Id;
		name = Name;
		resource_uri = R_uri;
		user = User;
	}
	
	
	public String toString() {
        //return String.format("name:%s,device_type:%s,user:%s,uuid:%s", name, device_type, user, uuid);
		//return name+" "+device_type+" "+user+" "+uuid;
		return name;
    }


}
