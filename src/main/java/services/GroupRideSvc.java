package services;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.GroupRide;
import utils.Constants;

import com.google.gson.Gson;

import dao.GroupRideDAO;


@Path("/groupride")
public class GroupRideSvc {
	
	@GET
	@Path("/segments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllGroupRides(@Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		GroupRide groupRide = new GroupRide();
		
		try {
			groupRide = new GroupRideDAO(session).getGroupRide();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
		}		
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(groupRide);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/update")
	public String updateGroupRide(String jsonData, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		GroupRideDAO groupRideDAO = new GroupRideDAO(session);
		String ret = "success";
		
		try {
			Gson gson = new Gson();
			GroupRide[] groupRideArray = gson.fromJson(jsonData, GroupRide[].class); 
			boolean ok = groupRideDAO.saveGroupRide(groupRideArray[0]);
			if (! ok)
				ret = "failed"; 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = "failed";
		}
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
		}		
		return ret;
	}
} 