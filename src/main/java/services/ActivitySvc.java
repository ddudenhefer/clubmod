package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Member;
import utils.Constants;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import connector.JStravaV3;
import dao.MemberDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/activity")
public class ActivitySvc {
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Path("/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubActivitiesByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		JStravaV3 strava= new JStravaV3(Constants.PUBLIC_ACCESS_TOKEN);
		List<Athlete> athletes = strava.findClubMembers(Constants.CLUB_ID,1,200);
		Collections.sort(athletes, Athlete.Comparators.NAME);	

		JsonObject obj = new JsonObject();
		JsonArray objArray = new JsonArray();

		for (Athlete athlete : athletes) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				Member member = memberDAO.getMemberByAthleteId(athlete.getId());
				if (member != null && member.getAccessToken() != null) {
					obj = new JsonObject();
					obj.addProperty("name", athlete.getFirstname() + " " + athlete.getLastname());
					strava = new JStravaV3(member.getAccessToken());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = df.parse(startDate).getTime() / 1000l;
					long endSeconds = df.parse(endDate).getTime() / 1000l;
					float totalMeters = 0;
					int totalRides = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalMeters += activity.getDistance();
				    		totalRides ++;
				    	}
				    }
					obj.addProperty("rides", totalRides);
					obj.addProperty("miles", (float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
					objArray.add(obj);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		obj = new JsonObject();
		obj.add("athletes", objArray);
		
		return obj.toString(); 
	}
	//
} 