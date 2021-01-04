package com.project.android.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.android.models.ConnectionBd;
import com.project.android.models.Restaurant;



public class RestaurantRepository {
	
	Connection con = null;
	
	public RestaurantRepository() {
		
		String url="jdbc:mysql://localhost:3306/user?useSSL=true&serverTimezone=UTC";
		String username = "root";
		String password = "Localhost!";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(ConnectionBd.URL, ConnectionBd.USERNAME, ConnectionBd.PASSWORD);
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
	
	
}

	public List<Restaurant> getDataRestaurant() {
			
			List<Restaurant> restaurants = new ArrayList<>();
			String sql = "select name_restau,state,latitude,longitude,telephone,id_restaurant from restaurants";
			try
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next())
				{
					Restaurant restau = new Restaurant();
					restau.setName(rs.getString(1));
					restau.setState(rs.getString(2));
					restau.setLatitude(rs.getDouble(3));
					restau.setLongitude(rs.getDouble(4));
					restau.setTelephone(rs.getString(5));
					restau.setIdRestaurant(rs.getInt(6));
					
					
					restaurants.add(restau);
				}
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			return restaurants;
		}
	
	public List<Restaurant> getRestaurant(String name) {
		
		List<Restaurant> restaurant = new ArrayList<>();

		String sql = "select latitude, longitude, telephone, id_restaurant from restaurants where name_restau ='"+name +"'";
		//Field a = new Field();
		try
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				Restaurant a = new Restaurant();
				a.setLatitude(rs.getDouble(1));
				a.setLongitude(rs.getDouble(2));
				a.setTelephone(rs.getString(3));
				a.setIdRestaurant(rs.getInt(4));
				
				
				restaurant.add(a);
			}
			
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return restaurant;
}
}
	
