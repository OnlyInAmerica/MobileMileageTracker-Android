package com.deebdroid.mobilemileagetracker;

public class Device {
	public String name;
	public String device_type;
	public String user;
	public String uuid;
	
	public Device(){
		name = "";
		device_type="";
		user = "";
		uuid = "";
	}
	
	public void setName(String name){this.name = name;}
	public void setDevice_Type(String device_type){this.device_type = device_type;}
	public void setUser(String user){this.user = user;}
	public void setUuid(String uuid){this.uuid = uuid;}
	
	public String toString() {
        //return String.format("name:%s,device_type:%s,user:%s,uuid:%s", name, device_type, user, uuid);
		//return name+" "+device_type+" "+user+" "+uuid;
		return name;
    }


}
