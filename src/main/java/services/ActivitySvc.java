package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import connector.JStravaV3;
import dao.MemberDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;
import entities.challenge.Challenge;

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
		List<Challenge> challengeResults = new ArrayList<Challenge>();

		for (Athlete athlete : athletes) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				Member member = memberDAO.getMemberByAthleteId(athlete.getId());
				if (member != null && member.getAccessToken() != null) {
				    strava = new JStravaV3(member.getAccessToken());
				    
				    // test authentication: if null, continue
				    Athlete verifyAthlete = strava.getCurrentAthlete();
				    if (verifyAthlete == null)
				    	continue;

				    Challenge challenge = new Challenge();
				    challenge.setAthleteId(athlete.getId());
				    challenge.setFisrtName(athlete.getFirstname());
				    challenge.setLastName(athlete.getLastname());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = getStartOfDay(df.parse(startDate)).getTime() / 1000l;
					long endSeconds = getEndOfDay(df.parse(endDate)).getTime() / 1000l;
					float totalMeters = 0;
					int totalRides = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalMeters += activity.getDistance();
				    		totalRides ++;
				    	}
				    }
				    challenge.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
				    challenge.setRides(totalRides);
				    challengeResults.add(challenge);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    Collections.sort(challengeResults, Challenge.Comparators.MILES);
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}

	private Date getStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 0, 0, 0);
	    return calendar.getTime();
	}

	private Date getEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 23, 59, 59);
	    return calendar.getTime();
	}					

} 