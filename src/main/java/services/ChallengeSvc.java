package services;


import java.sql.Date;

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

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/challenge")
public class ChallengeSvc {
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Path("/{challengeIndex}/{currentDate}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallenge(@PathParam("challengeIndex") int challengeIndex, @PathParam("currentDate") Date currentDate) { 
		Challenge challenge = null;
		
		try {
			challenge = new ChallengeDAO().getChallenge(challengeIndex,currentDate );
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