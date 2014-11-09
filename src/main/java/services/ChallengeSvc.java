package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Challenge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.ChallengeDAO;


@Path("/challenge")
public class ChallengeSvc {
	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllChallenges() { 
		List<Challenge> challenges = new ArrayList<Challenge>();
		
		try {
			challenges = new ChallengeDAO().getAllChallenges();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(challenges);
		return ret;
	}
	
	
	@GET
	@Path("/{challengeIndex}/{currentDate}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallenge(@PathParam("challengeIndex") int challengeIndex, @PathParam("currentDate") String currentDate) { 
		Challenge challenge = null;
		
		try {
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			challenge = new ChallengeDAO().getChallenge(challengeIndex, new java.sql.Date(df.parse(currentDate).getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	@Path("/update/{jsonData}")
	public String updateChallenges(@PathParam("jsonData") String jsonData) {
		ChallengeDAO challengeDAO = new ChallengeDAO();
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
		return ret;
	}
	
	
} 