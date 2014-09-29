package services;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import connector.JStravaV3;
import entities.athlete.Athlete;
import utils.Constants;

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /club
@Path("/club")
public class ClubSvc {
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Path("/members")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembers()  {
		
		JStravaV3 strava= new JStravaV3(Constants.PUBLIC_ACCESS_TOKEN);
		List<Athlete> athletes = strava.findClubMembers(Constants.CLUB_ID,1,200);
		Collections.sort(athletes, Athlete.Comparators.NAME);
		
		Gson gson = new Gson();
		String ret = "";
		if (athletes != null) {
			ret = gson.toJson(athletes);
		}		
		return ret;
	}
} 