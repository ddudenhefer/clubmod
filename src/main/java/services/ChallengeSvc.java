package services;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import model.Challenge;
import utils.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.ChallengeDAO;


@Path("/challenge")
public class ChallengeSvc {
	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllChallenges(@Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		List<Challenge> challenges = new ArrayList<Challenge>();
		
		try {
			challenges = new ChallengeDAO(session).getAllChallenges();
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
		ret = gson.toJson(challenges);
		return ret;
	}
	
	
	@GET
	@Path("/{challengeIndex}/{season}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallenge(@PathParam("challengeIndex") int challengeIndex, @PathParam("season") String season, @Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		Challenge challenge = null;
		
		try {
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			challenge = new ChallengeDAO(session).getChallenge(challengeIndex, season);
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
		if (challenge != null) {
			ret = gson.toJson(challenge);
		}
		else {
			ret = gson.toJson("");
		}
		return ret;
	}
	

	@GET
	@Path("/byDate/{currentDate}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallengeByDate(@PathParam("currentDate") String currentDate, @Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		Challenge challenge = null;
		
		try {
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			challenge = new ChallengeDAO(session).getChallengeByDate(new java.sql.Date(df.parse(currentDate).getTime()));
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
		if (challenge != null) {
			ret = gson.toJson(challenge);
		}
		else {
			ret = gson.toJson("");
		}
		return ret;
	}


	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/update")
	public String updateChallenges(String jsonData, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		ChallengeDAO challengeDAO = new ChallengeDAO(session);
		String ret = "success";
		
		try {
			Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();
			Challenge[]challengesArray = gson.fromJson(jsonData, Challenge[].class); 
	        List<Challenge> challenge=Arrays.asList(challengesArray);	

			boolean ok = challengeDAO.updateChallenges(challenge);
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