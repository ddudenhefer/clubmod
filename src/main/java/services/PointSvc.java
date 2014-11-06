package services;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Point;

import com.google.gson.Gson;

import dao.PointsDAO;

@Path("/point")
public class PointSvc {
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getPointById(@PathParam("id") int id) { 
		Point point = null;
		
		try {
			point = new PointsDAO().getPointById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		if (point != null) {
			System.out.println(gson.toJson(point));
			ret = gson.toJson(point);
		}
		else {
			ret = gson.toJson("");
		}
		return ret;
	}

	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllMembers() {
		List<Point> points = new ArrayList<Point>();
		
		try {
			points = new PointsDAO().getAllPoints();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(points);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update")
	public boolean updatePoints(@QueryParam("points") final List<Point> points) {
		PointsDAO pointsDAO = new PointsDAO();
		boolean ret = false;
		
		try {
			ret = pointsDAO.updatePoints(points);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
	    return ret; 
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public boolean deleteMember(final Point point) {
		PointsDAO pointsDAO = new PointsDAO();
		boolean ret = false;

		try {
			ret = pointsDAO.deletePoint(point);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
	    return ret; 
	}	
} 