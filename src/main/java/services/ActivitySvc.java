package services;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
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

import model.Challenge;
import model.Member;
import utils.Constants;
import connector.JStravaV3;
import dao.ChallengeDAO;
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
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;

		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();
				
				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//System.out.println("challenge: " + challenge);
					
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
						
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
							float totalMeters = 0;
							long totalElevation = 0;
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		totalMeters += activity.getDistance();
						    		totalElevation += activity.getTotal_elevation_gain();						    		
						    	}
						    }
						    
						    if (totalMeters > 0) {
						    	challengeResult.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
						    	challengeResult.setElevation((long) (Math.round(Constants.ConvertMetersToFeet(totalElevation, true) * 10) / 10.0));						    	
						    	challengeResults.add(challengeResult);
						    }
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Collections.sort(challengeResults, ChallengeResult.Comparators.MILES);
		}
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}


	@GET
	@Path("/time/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTimeByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;

		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();
				
				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//System.out.println("challenge: " + challenge);
					
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
						
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
						    float totalMiles = 0;						    
							long time = 0;
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		time += activity.getMoving_time();
						    		totalMiles += activity.getDistance();						    		
						    	}
						    }
						    
						    if (time > 0) {
						    	challengeResult.setTime(time);
						    	challengeResult.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMiles, true) * 10) / 10.0));	
						    	challengeResults.add(challengeResult);
						    }						    
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Collections.sort(challengeResults, ChallengeResult.Comparators.TIME);
		}
	    
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
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		long hoursBetween = 3 * 60 * 60 * 1000;
		
		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();

				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
	
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
							int totalRides = 0;
							float totalMiles = 0;
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    Collections.reverse(activities);
						    long finishedTime = 0;
						    
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		float meters = activity.getDistance();
						    		float miles = (float) (Math.round(Constants.ConvertMetersToMiles(meters, true) * 10) / 10.0);
						    		
						    		if (miles >= 25) {
							    		String localStartDate = activity.getStart_date_local();
							    		Date d = sdf.parse(localStartDate);
							    		long startTime = d.getTime();
							    		long elapsedTime = activity.getElapsed_time() * 1000;
							    		
							    		if (finishedTime == 0 || (startTime >= finishedTime + hoursBetween))
							    			totalRides ++;
							    		finishedTime = startTime + elapsedTime;
						    			totalMiles += miles;
						    		}
						    	}
						    }
						    
						    if (totalRides > 0) {
						    	challengeResult.setRides(totalRides);
						    	challengeResult.setMiles(totalMiles);
						    	challengeResults.add(challengeResult);
						    }
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Collections.sort(challengeResults, ChallengeResult.Comparators.RIDES);
		}
	    
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
	public String getMilesByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;

		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();
				
				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
					
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
							float longestMeters = 0;
							long totalElevation = 0;
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		totalElevation += activity.getTotal_elevation_gain();
						    		if (activity.getDistance() > longestMeters)
						    			longestMeters = activity.getDistance();
						    	}
						    }
						    
						    if (longestMeters > 0) {
						    	challengeResult.setMiles((long) (Math.round(Constants.ConvertMetersToMiles(longestMeters, true) * 10) / 10.0));	
						    	challengeResult.setElevation((long) (Math.round(Constants.ConvertMetersToFeet(totalElevation, true) * 10) / 10.0));						    	
						    	challengeResults.add(challengeResult);
						    }
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Collections.sort(challengeResults, ChallengeResult.Comparators.MILES);
		}
	    
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
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;

		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();
				
				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
					
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
							int time = 0;
							float totalMeters = 0;
							challengeResult.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));					
							
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		float miles = (float) (Math.round(Constants.ConvertMetersToMiles(activity.getDistance(), true) * 10) / 10.0);
						    		if (miles < 15)
						    			continue;
						    		time += activity.getMoving_time();
						    		totalMeters += activity.getDistance();
						    	}
						    }
						    
						    if (time > 0) {
							    float totalMiles = (float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0);
							    if (totalMiles >= 50) {
							    	
							    	BigDecimal bDistance, bmph, bSec, bHour;

							    	bDistance = new BigDecimal(totalMiles);
							    	bSec = new BigDecimal(time);
							    	bHour = bSec.divide(new BigDecimal(3600), 2, RoundingMode.CEILING);

							    	float mph = (float)(Math.round(bDistance.divide(bHour, 2, RoundingMode.CEILING).floatValue() * 10) / 10.0);
								    challengeResult.setSpeed(mph);
							    	challengeResult.setMiles(totalMiles);	
								    challengeResults.add(challengeResult);
							    }
						    }
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Collections.sort(challengeResults, ChallengeResult.Comparators.SPEED);
		}
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}
	
	@GET
	@Path("/effort/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEffortByDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { 
		
		List<ChallengeResult> challengeResults = new ArrayList<ChallengeResult>();
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;

		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();
				
				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
					
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    float elevation = 0;
						    float totalMiles = 0;	
						    long achievement = 0;
						    long photo = 0;
						    long pr = 0;
						    long time = 0;
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		elevation += activity.getTotal_elevation_gain();
						    		totalMiles += activity.getDistance();
						    		if (activity.getAchievement_count() > 0) {
						    			achievement += activity.getAchievement_count();
						    			System.out.println("achievement: " + activity.getAchievement_count());
						    		}
						    		if (activity.getTotal_photo_count() > 0) {
						    			photo += activity.getTotal_photo_count();
						    			System.out.println("photo: " + activity.getTotal_photo_count());
						    		}
						    		if (activity.getPr_count() > 0) {
						    			pr += activity.getPr_count();
						    			System.out.println("pr: " + activity.getPr_count());
						    		}
						    		if (activity.getMoving_time() > 0) {
						    			time += activity.getMoving_time()/1800;	// 30 mins
						    			System.out.println("moving: " + activity.getMoving_time()/1800);
						    		}
						    	}
						    }
						    
						    if (elevation > 0 && totalMiles > 0) {
							    float effort = 0;
							    long feet = (long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0);
							    float miles = (float) (Math.round(Constants.ConvertMetersToMiles(totalMiles, true) * 10) / 10.0);
							    long extra = achievement+photo+pr+time;

							    System.out.println("elevation: " + feet);
							    System.out.println("miles: " + miles);
							    System.out.println("extra: " + extra);
							    
							    effort = (feet/miles) + extra;
						    	challengeResult.setElevation((long) (Math.round(effort * 10) / 10.0));
						    	challengeResult.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMiles, true) * 10) / 10.0));	
						    	challengeResults.add(challengeResult);
						    }
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Collections.sort(challengeResults, ChallengeResult.Comparators.ELEVATION);
		}
	    
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
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date sd = null;;
		Date ed = null;

		try {
			sd = df.parse(startDate);
			ed = df.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sd.before(new Date())) {
			MemberDAO memberDAO = new MemberDAO();
			try {
				List<Member> members = memberDAO.getAllMembers();
				
				//ChallengeDAO challengeDAO = new ChallengeDAO();
				//Challenge challenge = challengeDAO.getChallengeByDates(sDate, eDate);
				
				for (Member member : members) {
					
					System.out.println("member: " + member.getFirstName() + " " + member.getLastName());
					//if (! challengeDAO.hasMemberWonChallengeOrSeason(member.getId(), challenge.getId(), challenge.getName(), challenge.getSeason())) {				
					
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken());
						    
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
		
						    ChallengeResult challengeResult = new ChallengeResult();
						    challengeResult.setAthleteId(athlete.getId());
						    challengeResult.setFisrtName(athlete.getFirstname());
						    challengeResult.setLastName(athlete.getLastname());
							
							long startSeconds = Constants.getStartOfDay(df.parse(startDate)).getTime() / 1000l;
							long endSeconds = Constants.getEndOfDay(df.parse(endDate)).getTime() / 1000l;
						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    float elevation = 0;
						    float totalMiles = 0;						    
						    for (Activity activity : activities) {
						    	if (activity.getType().equals("Ride")) {
						    		elevation += activity.getTotal_elevation_gain();
						    		totalMiles += activity.getDistance();						    		
						    	}
						    }
						    
						    if (elevation > 0) {
						    	challengeResult.setElevation((long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0));
						    	challengeResult.setMiles((float) (Math.round(Constants.ConvertMetersToMiles(totalMiles, true) * 10) / 10.0));	
						    	challengeResults.add(challengeResult);
						    }
						}
					//}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Collections.sort(challengeResults, ChallengeResult.Comparators.ELEVATION);
		}
	    
	    Gson gson = new Gson();
		String ret = "";
		if (challengeResults != null) {
			ret = gson.toJson(challengeResults);
		}		
		return ret;	    
	}
} 