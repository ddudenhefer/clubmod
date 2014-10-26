package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Member;

import com.google.gson.Gson;

import connector.JStravaV3;
import dao.MemberDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;
import utils.Constants;

@Path("/club")
public class ClubSvc {
	
	@GET
	@Path("/members")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembers()  {
		
		JStravaV3 strava= new JStravaV3(Constants.PUBLIC_ACCESS_TOKEN);
		List<Athlete> athletes = strava.findClubMembers(Constants.CLUB_ID,1,200);
		Collections.sort(athletes, Athlete.Comparators.NAME);
		

	    Calendar cal = Calendar.getInstance();
		long endSeconds = Constants.getEndOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;

		cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);

        long startSeconds = Constants.getStartOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;
		
		for (Athlete athlete : athletes) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				Member member = memberDAO.getMemberByAthleteId(athlete.getId());
				if (member != null && member.getAccessToken() != null) {
				    strava = new JStravaV3(member.getAccessToken());
				    
				    // test authentication: if null, continue
				    Athlete verifyAthlete = strava.getCurrentAthlete();
				    athlete.setAuthenticated (verifyAthlete != null);
				    
					float totalMeters = 0;	
					float elevation = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalMeters += activity.getDistance();
				    		elevation += activity.getTotal_elevation_gain();
				    	}
				    }
				    athlete.setMilesYTD((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
				    athlete.setElevationYTD((long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0));					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new Gson();
		String ret = "";
		if (athletes != null) {
			ret = gson.toJson(athletes);
		}		
		return ret;
	}
} 