package com.project.android.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.android.models.Category;
import com.project.android.repositories.CategoryRepository;

@Path("categories")
public class CategoryResource {
	
	private CategoryRepository repo = new CategoryRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getData() {
    	System.out.println("getCategoryCalled...");
       return repo.getDataCategory();
        
    }

}
