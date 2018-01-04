package services;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.GroupRide;

import com.google.gson.Gson;

import dao.GroupRideDAO;


@Path("/groupride")
public class GroupRideSvc {
	
	@GET
	@Path("/segments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllGroupRides() { 
		GroupRide groupRide = new GroupRide();
		
		try {
			groupRide = new GroupRideDAO().getGroupRide();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(groupRide);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/update")
	public String updateGroupRide(String jsonData) {
		GroupRideDAO groupRideDAO = new GroupRideDAO();
		String ret = "success";
		
		try {
			Gson gson = new Gson();
			GroupRide groupRide = gson.fromJson(jsonData, GroupRide.class); 

			boolean ok = groupRideDAO.saveGroupRide(groupRide);
			if (! ok)
				ret = "failed"; 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = "failed";
		}
		return ret;
	}
} 