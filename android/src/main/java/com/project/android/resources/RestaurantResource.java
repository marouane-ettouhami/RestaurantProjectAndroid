package com.project.android.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.android.models.Restaurant;
import com.project.android.repositories.RestaurantRepository;



@Path("restaurants")
public class RestaurantResource {
	private RestaurantRepository repo = new RestaurantRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Restaurant> getData() {
    	System.out.println("getRestaurantCalled...");
       return repo.getDataRestaurant();
        
    }
    
    @GET
	@Path("restaurant/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<Restaurant> getField(@PathParam("name") String name ) {
		return repo.getRestaurant(name);
	}

}
