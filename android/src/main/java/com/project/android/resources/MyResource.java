package com.project.android.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.android.models.Login;
import com.project.android.repositories.LoginRepository;

@Path("login")
public class MyResource {
	
	private LoginRepository repo = new LoginRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Login> getData() {
    	System.out.println("getDataCalled...");
       return repo.getDataLogin();
        
    }
}
