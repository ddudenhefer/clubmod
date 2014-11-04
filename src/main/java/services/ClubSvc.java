package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import dao.PointsDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;
import utils.Constants;

@Path("/club")
public class ClubSvc {
	
	@GET
	@Path("/members")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembers()  {
		
	    Calendar cal = Calendar.getInstance();
		long endSeconds = Constants.getEndOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;

		cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);

        long startSeconds = Constants.getStartOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;

		List<Athlete> athletes = new ArrayList<Athlete>();
		MemberDAO memberDAO = new MemberDAO();
		try {
			List<Member> members = memberDAO.getAllMembers();
			for (Member member : members) {
				if (member != null && member.getAccessToken() != null) {
					JStravaV3 strava = new JStravaV3(member.getAccessToken());
				    
				    // test authentication: if null, continue
				    Athlete athlete = strava.getCurrentAthlete();
				    if (athlete == null)
				    	continue;		
				    athletes.add(athlete);
		
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
				    
				    PointsDAO pointsDAO = new PointsDAO();
				    athlete.setPointsYTD(pointsDAO.getMemberPoints(member.getId(), athlete.getMilesYTD(), athlete.getElevationYTD()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(athletes, Athlete.Comparators.NAME);

		Gson gson = new Gson();
		String ret = "";
		if (athletes != null) {
			ret = gson.toJson(athletes);
		}		
		return ret;
	}
} 