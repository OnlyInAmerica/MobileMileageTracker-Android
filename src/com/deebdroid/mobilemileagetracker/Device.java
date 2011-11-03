package com.deebdroid.mobilemileagetracker;

public class Device {
	public String device_type;
	public String id;
	public String name;
	public String resource_uri;
	public String user;
	public String uuid;
	
	public Device(){
		device_type="";
		id = "";
		name = "";
		resource_uri="";
		user = "";
		uuid = "";
	}
	public Device(String Dt, String Id, String Name, String R_uri, String User, String Uuid){
		device_type= Dt;
		id = Id;
		name = Name;
		resource_uri = R_uri;
		user = User;
		uuid = Uuid;
	}
	
	public void setId(String Id){this.id = Id;}
	public void setResource_uri(String r_uri){this.resource_uri = r_uri;}
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
