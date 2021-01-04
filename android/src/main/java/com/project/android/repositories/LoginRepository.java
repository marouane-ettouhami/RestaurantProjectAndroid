package com.project.android.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.android.models.ConnectionBd;
import com.project.android.models.Login;

public class LoginRepository {
	
	    Connection con = null;
	
		public LoginRepository() {
			
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
		
	public List<Login> getDataLogin() {
			
			List<Login> logins = new ArrayList<>();
			String sql = "select id_client,login,password from client";
			try
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next())
				{
					Login log = new Login();
					log.setId(rs.getInt(1));
					log.setLogin(rs.getString(2));
					log.setPassword(rs.getString(3));
					
					logins.add(log);
				}
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			return logins;
		}

	}


