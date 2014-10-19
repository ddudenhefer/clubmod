package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Challenge;
import com.google.gson.Gson;
import dao.ChallengeDAO;


@Path("/challenge")
public class ChallengeSvc {
	
	@GET
	@Path("/{challengeIndex]/{currentDate}")
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
} 