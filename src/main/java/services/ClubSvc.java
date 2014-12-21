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

import model.Challenge;
import model.Member;
import model.MemberActivityTotal;
import model.MemberYTDTotal;

import com.google.gson.Gson;

import connector.JStravaV3;
import dao.ChallengeDAO;
import dao.MemberActivityTotalsDAO;
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
		long endSeconds = Constants.getEndOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;
	    
		cal.set(Calendar.DAY_OF_YEAR,1); //first day of the year.	    
        long startSeconds = Constants.getStartOfDay(new Date(cal.getTimeInMillis())).getTime() / 1000l;

		List<Member> members = new ArrayList<Member>();
		MemberDAO memberDAO = new MemberDAO();
		try {
			members = memberDAO.getAllMembers();
			for (Member member : members) {
				if (member != null && member.getAccessToken() != null) {
					JStravaV3 strava = new JStravaV3(member.getAccessToken());
				    
				    // test authentication: if null, continue
				    Athlete athlete = strava.getCurrentAthlete();
				    if (athlete == null)
				    	continue;
				    
				    member.setPictureURL(athlete.getProfile_medium());
				    
					float totalMeters = 0;	
					float elevation = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalMeters += activity.getDistance();
				    		elevation += activity.getTotal_elevation_gain();
				    	}
				    }
				    
				    member.setTotalRides(activities.size());
				    
				    float milesF = (float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0);
				    long elevationL = (long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0);
				    
				    ChallengeDAO challengeDAO = new ChallengeDAO();
					List<Challenge> challengeWins = challengeDAO.getChallengesByMemberId(member.getId());			
					member.setChallengeWins(challengeWins);
					
					MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(); 
					MemberActivityTotal memberActivityTotal = memberActivityTotalsDAO.getMemberData(member.getId());	
					member.setFantasyEntry(memberActivityTotal.getFantasyEntry());
					member.setFantasyFirst(memberActivityTotal.getFantasyFirst());
					member.setFantasySecond(memberActivityTotal.getFantasySecond());
					member.setFantasyThird(memberActivityTotal.getFantasyThird());
					member.setGroupRides(memberActivityTotal.getGroupRide());
					member.setEventRides(memberActivityTotal.getEventRide());
				    
				    PointsDAO pointsDAO = new PointsDAO();
				    member.setMemberPoints(pointsDAO.getMemberPoints(member.getId(), milesF, elevationL, challengeWins, memberActivityTotal));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(members, Member.Comparators.POINTS);

		Gson gson = new Gson();
		String ret = "";
		if (members != null) {
			ret = gson.toJson(members);
		}		
		return ret;
	}
} 