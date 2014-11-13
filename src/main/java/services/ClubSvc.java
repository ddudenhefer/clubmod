package services;

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
import model.MemberYTDTotal;

import com.google.gson.Gson;

import connector.JStravaV3;
import dao.MemberDAO;
import dao.MemberYTDTotalsDAO;
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
        long startSeconds = Constants.getStartOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;
		long endSeconds = Constants.getEndOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;

		List<Member> membersIn = new ArrayList<Member>();
		List<Member> membersOut = new ArrayList<Member>();
		MemberDAO memberDAO = new MemberDAO();
		try {
			membersIn = memberDAO.getAllMembers();
			for (Member member : membersIn) {
				if (member != null && member.getAccessToken() != null) {
					JStravaV3 strava = new JStravaV3(member.getAccessToken());
				    
				    // test authentication: if null, continue
				    Athlete athlete = strava.getCurrentAthlete();
				    if (athlete == null)
				    	continue;
				    
					float totalMeters = 0;	
					float elevation = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalMeters += activity.getDistance();
				    		elevation += activity.getTotal_elevation_gain();
				    	}
				    }
				    
				    MemberYTDTotalsDAO memberYTDTotalsDB = new MemberYTDTotalsDAO();
				    MemberYTDTotal memberYTDTotal = memberYTDTotalsDB.getMemberData(member.getId());
				    if (memberYTDTotal != null) {
				    	float milesYTD_DB = memberYTDTotal.getMilesYTD();
				    	long elevationYTD_DB = memberYTDTotal.getElevationYTD();
				    	
				    	milesYTD_DB += (float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0);
				    	elevationYTD_DB += (long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0);
				    	
				    	member.setMilesYTD((float)(Math.round(milesYTD_DB * 10) / 10.0));
				    	member.setElevationYTD((long)(Math.round(elevationYTD_DB *10) /10.0));
				    }
				    
				    PointsDAO pointsDAO = new PointsDAO();
				    member.setPointsYTD(pointsDAO.getMemberPoints(member.getId(), member.getMilesYTD(), member.getElevationYTD()));
				    membersOut.add(member);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(membersOut, Member.Comparators.NAME);

		Gson gson = new Gson();
		String ret = "";
		if (membersOut != null) {
			ret = gson.toJson(membersOut);
		}		
		return ret;
	}
} 