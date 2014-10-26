package services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import model.Member;
import utils.Constants;
import connector.JStravaV3;
import dao.MemberDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;
import entities.challenge.ChallengeResult;

@Path("/activity")
public class ActivitySvc {
	
	@GET
	@Path("/distance/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDistanceByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();

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

				    ChallengeResult challenge = new ChallengeResult();
				    challenge.setAthleteId(athlete.getId());
				    challenge.setFisrtName(athlete.getFirstname());
				    challenge.setLastName(athlete.getLastname());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
					long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
					float totalMeters = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalMeters += activity.getDistance();
				    	}
				    }
				    challenge.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
				    challengeResults.add(challenge);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.sort(challengeResults, ChallengeResult.Comparators.MILES);
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}


	@GET
	@Path("/rides/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRidesByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();

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

				    ChallengeResult challenge = new ChallengeResult();
				    challenge.setAthleteId(athlete.getId());
				    challenge.setFisrtName(athlete.getFirstname());
				    challenge.setLastName(athlete.getLastname());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
					long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
					int totalRides = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		totalRides ++;
				    	}
				    }
				    challenge.setRides(totalRides);
				    challengeResults.add(challenge);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.sort(challengeResults, ChallengeResult.Comparators.RIDES);
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}
	
	
	@GET
	@Path("/longest/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTimeByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();

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

				    ChallengeResult challenge = new ChallengeResult();
				    challenge.setAthleteId(athlete.getId());
				    challenge.setFisrtName(athlete.getFirstname());
				    challenge.setLastName(athlete.getLastname());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
					long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
					float longestMeters = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		if (activity.getDistance() > longestMeters)
				    			longestMeters = activity.getDistance();
				    	}
				    }
				    challenge.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(longestMeters, true) * 10) / 10.0));				    
				    challengeResults.add(challenge);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.sort(challengeResults, ChallengeResult.Comparators.MILES);
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}
	

	@GET
	@Path("/speed/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpeedByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();

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

				    ChallengeResult challenge = new ChallengeResult();
				    challenge.setAthleteId(athlete.getId());
				    challenge.setFisrtName(athlete.getFirstname());
				    challenge.setLastName(athlete.getLastname());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
					long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
					float speed = 0;
					int totalRides = 0;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		speed += activity.getAverage_speed();
				    		totalRides ++;
				    	}
				    }
				    double mphSpeed = Math.round(Constants.ConvertMPStoMPH(speed, true) * 10) / 10.0;
				    challenge.setSpeed((float) (Math.round((mphSpeed/totalRides) * 10) / 10.0));
				    challengeResults.add(challenge);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.sort(challengeResults, ChallengeResult.Comparators.SPEED);
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}
	
	
	@GET
	@Path("/elevation/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getElevationByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();

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

				    ChallengeResult challenge = new ChallengeResult();
				    challenge.setAthleteId(athlete.getId());
				    challenge.setFisrtName(athlete.getFirstname());
				    challenge.setLastName(athlete.getLastname());
					
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
					long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
				    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
				    float elevation = 0;
				    for (Activity activity : activities) {
				    	if (activity.getType().equals("Ride")) {
				    		elevation += activity.getTotal_elevation_gain();
				    	}
				    }
				    challenge.setElevation((long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0));
				    challengeResults.add(challenge);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.sort(challengeResults, ChallengeResult.Comparators.ELEVATION);
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}

} 