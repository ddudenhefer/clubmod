package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import connector.JStravaV3;
import model.Challenge;
import model.Member;
import model.MemberYTDTotal;
import dao.ChallengeDAO;
import dao.MemberDAO;
import dao.MemberYTDTotalsDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;
import entities.challenge.ChallengeResult;
import services.ActivitySvc;

public class AppContextListener implements ServletContextListener {
	
	private final static long ONCE_PER_DAY = 1000*60*60*24;
	private final static long ONCE_PER_WEEK = 1000*60*60*24*7;
	private final static int ZERO_MINUTES = 0;

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Your code here
		System.out.println("AppContextListener Listener has been shutdown");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		System.out.println("AppContextListener Listener initialized.");

		TimerTask updateChallengeWinnerTask = new UpdateChallengeWinnerTask();
		Timer challengeWinnerTimer = new Timer();
		challengeWinnerTimer.scheduleAtFixedRate(updateChallengeWinnerTask, getRunDate(Calendar.MONDAY, 11), ONCE_PER_WEEK);
		//challengeWinnerTimer.schedule(updateChallengeWinnerTask, 0);

		TimerTask updateMemberYTDTask = new UpdateMemberYTDTask();
		Timer memberYTDTimer = new Timer();
		//memberYTDTimer.scheduleAtFixedRate(updateMemberYTDTask, getTonight(23,00), ONCE_PER_DAY);
		memberYTDTimer.schedule(updateMemberYTDTask, 0);
	}
	
	private static Date getTomorrow(int hour, int mins){
		Calendar tomorrow = Calendar.getInstance();
	    tomorrow.add(Calendar.DATE, 1);
	    Calendar result = new GregorianCalendar(
	    		tomorrow.get(Calendar.YEAR),
	    		tomorrow.get(Calendar.MONTH),
	    		tomorrow.get(Calendar.DATE),
	    		hour,
	    		mins
	    );
	    return result.getTime();
	 }
	
	private static Date getTonight(int hour, int mins){
		Calendar tonight = Calendar.getInstance();
	    Calendar result = new GregorianCalendar(
	    		tonight.get(Calendar.YEAR),
	    		tonight.get(Calendar.MONTH),
	    		tonight.get(Calendar.DATE),
	    		hour,
	    		mins
	    );
	    return result.getTime();
	 }

	
	private static Date getRunDate(int day, int hour){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, day);
	    Calendar result = new GregorianCalendar(
	    		date.get(Calendar.YEAR),
	    		date.get(Calendar.MONTH),
	    		date.get(Calendar.DATE),
	    		hour,
	    		ZERO_MINUTES
	    );
	    return result.getTime();
	 }
	
	class UpdateChallengeWinnerTask extends TimerTask {
		
		@Override
		public void run() {
			System.out.println("UpdateChallengeWinnerTask " + new Date().toString());
			
			List<Challenge> challenges = new ArrayList<Challenge>();
			Date today = new Date();
			
			Calendar calToday = Calendar.getInstance();
			calToday.setTime(today);
			Calendar yesterday = Calendar.getInstance();
			yesterday.setTime(today);
			yesterday.add(Calendar.DAY_OF_YEAR, -2);
			yesterday.set(Calendar.HOUR_OF_DAY, 0);
			yesterday.set(Calendar.MINUTE, 0);
			yesterday.set(Calendar.SECOND, 0);
			yesterday.set(Calendar.MILLISECOND, 0);			
			Date yesterdayDate = yesterday.getTime(); // if you need a Date object
			
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			ActivitySvc activitySvc = new ActivitySvc();
			
			try {
				challenges = new ChallengeDAO().getAllChallenges();
				for (Challenge challenge : challenges) {
					System.out.println("end date: " + challenge.getEndDate());
					System.out.println("compare date: " + yesterdayDate);
					if (challenge.getMemberId() == 0 && yesterdayDate.equals(challenge.getEndDate())) {
						String sDate = df.format(challenge.getStartDate());
						String eDate = df.format(challenge.getEndDate());
						String results = "";
						
						if (challenge.getService().equals("distance"))
							results = activitySvc.getDistanceByDateRange(sDate, eDate);
						else if (challenge.getService().equals("rides"))
							results = activitySvc.getRidesByDateRange(sDate, eDate);
						else if (challenge.getService().equals("longest"))
							results = activitySvc.getTimeByDateRange(sDate, eDate);
						else if (challenge.getService().equals("speed"))
							results = activitySvc.getSpeedByDateRange(sDate, eDate);
						else if (challenge.getService().equals("elevation"))
							results = activitySvc.getElevationByDateRange(sDate, eDate);
						
						Gson gson= new Gson();
						ChallengeResult[]challengeResultsArray= gson.fromJson(results, ChallengeResult[].class);
				        List<ChallengeResult> challengeResults=Arrays.asList(challengeResultsArray);	
				        
				        if (challengeResults.size() > 0) {
				        	int athleteId = challengeResults.get(0).getAthleteId();
				        	MemberDAO memberDAO = new MemberDAO();
				        	Member member = memberDAO.getMemberByAthleteId(athleteId);
				        	challenge.setMemberId(member.getId());
				        	
				        	ChallengeDAO challengeDAO = new ChallengeDAO();
				        	challengeDAO.saveChallenge(challenge);
				        }
					    Thread.sleep(60000); // 1 minute			    
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class UpdateMemberYTDTask extends TimerTask {
		
		@Override
		public void run() {
			System.out.println("UpdateMemberYTDTask " + new Date().toString());
			
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
					    
					    System.out.println("UpdateMemberYTDTask: " + athlete.getFirstname() + " " + athlete.getLastname());
					    
					    // save member picture
					    if (! athlete.getProfile_medium().equals(member.getPictureURL())) {
					    	MemberDAO memberDB = new MemberDAO();
						    member.setPictureURL(athlete.getProfile_medium());
						    memberDB.saveMember(member);
					    }
					    
						float totalMeters = 0;	
						float elevation = 0;
					    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
					    for (Activity activity : activities) {
					    	if (activity.getType().equals("Ride")) {
					    		totalMeters += activity.getDistance();
					    		elevation += activity.getTotal_elevation_gain();
					    	}
					    }

					    MemberYTDTotal memberYTDTotal = new MemberYTDTotal();
				    	memberYTDTotal.setMemberId(member.getId());
				    	memberYTDTotal.setMilesYTD((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
				    	memberYTDTotal.setElevationYTD((long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0));
				    	
					    MemberYTDTotalsDAO memberYTDTotalsDAO = new MemberYTDTotalsDAO();
					    memberYTDTotalsDAO.saveMemberYTDTotals(memberYTDTotal);
					    
					    Thread.sleep(60000); // 1 minute			    
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}