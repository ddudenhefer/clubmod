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

import model.Challenge;
import model.Member;
import dao.ChallengeDAO;
import dao.MemberDAO;
import entities.challenge.ChallengeResult;
import entities.segment.Segment;
import services.ActivitySvc;

public class AppContextListener implements ServletContextListener {
	
	private final static long ONCE_PER_DAY = 1000*60*60*24;
	private final static int ONE_DAY = 1;
	private final static int HOUR_AM = 2;
	private final static int ZERO_MINUTES = 0;

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Your code here
		System.out.println("AppContextListener Listener has been shutdown");

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		// Your code here
		System.out.println("AppContextListener Listener initialized.");

		TimerTask updatePointsTask = new UpdatePointsTask();
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(updatePointsTask, getTomorrowMorning1am(), ONCE_PER_DAY);
	    //timer.schedule(updatePointsTask, 0);
	}
	
	private static Date getTomorrowMorning1am(){
		Calendar tomorrow = Calendar.getInstance();
	    tomorrow.add(Calendar.DATE, ONE_DAY);
	    Calendar result = new GregorianCalendar(
	    		tomorrow.get(Calendar.YEAR),
	    		tomorrow.get(Calendar.MONTH),
	    		tomorrow.get(Calendar.DATE),
	    		HOUR_AM,
	    		ZERO_MINUTES
	    );
	    return result.getTime();
	  }
	

	class UpdatePointsTask extends TimerTask {
		
		@Override
		public void run() {
			System.out.println("UpdatePointsTask " + new Date().toString());
			
			List<Challenge> challenges = new ArrayList<Challenge>();
			Date today = new Date();
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			ActivitySvc activitySvc = new ActivitySvc();
			
			try {
				challenges = new ChallengeDAO().getAllChallenges();
				for (Challenge challenge : challenges) {
					if (challenge.getMemberId() == 0 && today.after(challenge.getEndDate())) {
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
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}