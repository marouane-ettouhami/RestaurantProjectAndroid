package com.project.android.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.android.models.Category;
import com.project.android.models.ConnectionBd;

public class CategoryRepository {

	Connection con = null;
	
	public CategoryRepository() {
		
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
	
	public List<Category> getDataCategory() {
			
			List<Category> categories = new ArrayList<>();
			String sql = "select id_category,name from categories";
			try
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next())
				{
					Category cat = new Category();
					cat.setId(rs.getString(1));
					cat.setName(rs.getString(2));
					
					
					categories.add(cat);
				}
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			return categories;
		}
}
