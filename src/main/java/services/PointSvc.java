package services;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import model.Point;
import utils.Constants;

import com.google.gson.Gson;
import dao.PointsDAO;

@Path("/point")
public class PointSvc {
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getPointById(@PathParam("id") int id, @Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		Point point = null;
		
		try {
			point = new PointsDAO(session).getPointById(id);
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
	public String getAllPoints(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Point> points = new ArrayList<Point>();
		
		try {
			points = new PointsDAO(session).getAllPoints();
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
		ret = gson.toJson(points);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/update")
	public String updatePoints(String jsonData, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		PointsDAO pointsDAO = new PointsDAO(session);
		String ret = "success";
		
		try {
			Gson gson = new Gson();
	        Point[]pointsArray = gson.fromJson(jsonData, Point[].class); 
	        List<Point> points=Arrays.asList(pointsArray);	

			boolean ok = pointsDAO.updatePoints(points);
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
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public boolean deleteMember(final Point point, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		PointsDAO pointsDAO = new PointsDAO(session);
		boolean ret = false;

		try {
			ret = pointsDAO.deletePoint(point);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
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