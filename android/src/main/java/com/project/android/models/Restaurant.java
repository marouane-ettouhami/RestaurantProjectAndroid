package com.project.android.models;

public class Restaurant {
	String name;
	String state;
	double latitude;
	double longitude;
	String telephone;
	int idRestaurant;
	
	
	
	
	public int getIdRestaurant() {
		return idRestaurant;
	}
	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	

}
