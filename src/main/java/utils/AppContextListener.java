package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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

import com.google.gson.Gson;

import connector.JStravaV3;
import model.Challenge;
import model.GroupRide;
import model.Member;
import model.MemberActivityTotal;
import model.MemberYTDTotal;
import dao.ChallengeDAO;
import dao.Database;
import dao.GroupRideDAO;
import dao.MemberActivityTotalsDAO;
import dao.MemberDAO;
import dao.MemberYTDTotalsDAO;
import entities.activity.Activity;
import entities.athlete.Athlete;
import entities.athlete.Statistics;
import entities.challenge.ChallengeResult;
import entities.segment.SegmentEffort;
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
		
		TimerTask updateGroupRideTask = new UpdateGroupRideTask();
		Timer groupRideTimer = new Timer();
		groupRideTimer.scheduleAtFixedRate(updateGroupRideTask, getRunDate(Calendar.SUNDAY, 20), ONCE_PER_WEEK);
		//groupRideTimer.schedule(updateGroupRideTask, 0);

		TimerTask updateBCCGroupRideTask = new UpdateBCCGroupRideTask();
		Timer bccGroupRideTimer = new Timer();
		bccGroupRideTimer.scheduleAtFixedRate(updateBCCGroupRideTask, getRunDate(Calendar.WEDNESDAY, 12), ONCE_PER_WEEK);
		//bccGroupRideTimer.schedule(updateBCCGroupRideTask, 0);

		TimerTask updateChallengeWinnerTask = new UpdateChallengeWinnerTask();
		Timer challengeWinnerTimer = new Timer();
		challengeWinnerTimer.scheduleAtFixedRate(updateChallengeWinnerTask, getRunDate(Calendar.MONDAY, 12), ONCE_PER_WEEK);
		//challengeWinnerTimer.schedule(updateChallengeWinnerTask, 0);

		TimerTask updateMemberYTDTask = new UpdateMemberYTDTask();
		Timer memberYTDTimer = new Timer();
		memberYTDTimer.scheduleAtFixedRate(updateMemberYTDTask, getTonight(22,00), ONCE_PER_DAY);
		//memberYTDTimer.schedule(updateMemberYTDTask, 0);
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
			yesterday.add(Calendar.DAY_OF_YEAR, -1);
			yesterday.set(Calendar.HOUR_OF_DAY, 0);
			yesterday.set(Calendar.MINUTE, 0);
			yesterday.set(Calendar.SECOND, 0);
			yesterday.set(Calendar.MILLISECOND, 0);			
			Date yesterdayDate = yesterday.getTime(); // if you need a Date object
			
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			ActivitySvc activitySvc = new ActivitySvc();
			
			Connection con = Database.getConnection(null);			
			
			try {
				challenges = new ChallengeDAO(con).getAllChallenges();
				for (Challenge challenge : challenges) {
					System.out.println("challenge name: " + challenge.getName());
					System.out.println("challenge end date: " + challenge.getEndDate());
					System.out.println("compare date: " + yesterdayDate);
					// assume of memberid is not set, none of them are set
					if (challenge.getMemberId() == 0 && yesterdayDate.equals(challenge.getEndDate())) {
						String sDate = df.format(challenge.getStartDate());
						String eDate = df.format(challenge.getEndDate());
						String results = "";
						
						if (challenge.getService().equals("distance"))
							results = activitySvc.getDistanceByDateRange(sDate, eDate, null);
						else if (challenge.getService().equals("time"))
							results = activitySvc.getTimeByDateRange(sDate, eDate, null);
						else if (challenge.getService().equals("longest"))
							results = activitySvc.getMilesByDateRange(sDate, eDate, null);
						else if (challenge.getService().equals("speed"))
							results = activitySvc.getSpeedByDateRange(sDate, eDate, null);
						else if (challenge.getService().equals("effort"))
							results = activitySvc.getEffortByDateRange(sDate, eDate, null);
						else if (challenge.getService().equals("elevation"))
							results = activitySvc.getElevationByDateRange(sDate, eDate, null);
						
						Gson gson= new Gson();
						ChallengeResult[]challengeResultsArray= gson.fromJson(results, ChallengeResult[].class);
				        List<ChallengeResult> challengeResults=Arrays.asList(challengeResultsArray);	
				        
				        if (challengeResults.size() > 0) {
					        if (challengeResults.size() > 0) {
					        	int athleteId = challengeResults.get(0).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId(member.getId());
								System.out.println("challenge 1st: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 1) {
					        	int athleteId = challengeResults.get(1).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId2(member.getId());
								System.out.println("challenge 2nd: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 2) {
					        	int athleteId = challengeResults.get(2).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId3(member.getId());
								System.out.println("challenge 3rd: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 3) {
					        	int athleteId = challengeResults.get(3).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId4(member.getId());
								System.out.println("challenge 4th: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 4) {
					        	int athleteId = challengeResults.get(4).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId5(member.getId());
								System.out.println("challenge 5th: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 5) {
					        	int athleteId = challengeResults.get(5).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId6(member.getId());
								System.out.println("challenge 6th: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 6) {
					        	int athleteId = challengeResults.get(6).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId7(member.getId());
								System.out.println("challenge 7th: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 7) {
					        	int athleteId = challengeResults.get(7).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId8(member.getId());
								System.out.println("challenge 8th: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 8) {
					        	int athleteId = challengeResults.get(8).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId9(member.getId());
								System.out.println("challenge 9th: " + member.getFirstName() + " " +  member.getLastName());
					        }
					        if (challengeResults.size() > 9) {
					        	int athleteId = challengeResults.get(9).getAthleteId();
					        	MemberDAO memberDAO = new MemberDAO(con);
					        	Member member = memberDAO.getMemberByAthleteId(athleteId);
					        	challenge.setMemberId10(member.getId());
								System.out.println("challenge 10th: " + member.getFirstName() + " " +  member.getLastName());
					        }
				        	
				        	ChallengeDAO challengeDAO = new ChallengeDAO(con);
				        	challengeDAO.saveChallenge(challenge);
				        }
					    Thread.sleep(120000); // 2 minutes			    
					}
				}
				System.out.println("UpdateChallengeWinnerTask -> DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
	        
	        Connection con = Database.getConnection(null);

			List<Member> members = new ArrayList<Member>();
			MemberDAO memberDAO = new MemberDAO(con);
			try {
				members = memberDAO.getAllMembers();
				for (Member member : members) {
					if (member != null && member.getAccessToken() != null) {
						try {
							JStravaV3 strava = new JStravaV3(member.getAccessToken(), true);
						    // test authentication: if null, continue
						    Athlete athlete = strava.getCurrentAthlete();
						    if (athlete == null)
						    	continue;
						    
						    System.out.println("UpdateMemberYTDTask: " + athlete.getFirstname() + " " + athlete.getLastname());
						    String a_first = "";
						    String a_last = "";
						    String a_profile = "";
						    String a_city = "";
						    String a_state = "";
						    String a_email = "";
	
						    String m_first = "";
						    String m_last = "";
						    String m_picture = "";
						    String m_city = "";
						    String m_state = "";
						    String m_email = "";
						    
						    if (athlete.getFirstname() != null)
						    	a_first = athlete.getFirstname();
						    if (athlete.getLastname() != null)
						    	a_last = athlete.getLastname();
						    if (athlete.getProfile_medium() != null)
						    	a_profile = athlete.getProfile_medium();
						    if (athlete.getCity() != null)
						    	a_city = athlete.getCity();
						    if (athlete.getState() != null)
						    	a_state = athlete.getState();
						    if (athlete.getEmail() != null)
						    	a_email = athlete.getEmail();
	
						    if (member.getFirstName() != null)
						    	m_first = member.getFirstName();
						    if (member.getLastName() != null)
						    	m_last = member.getLastName();
						    if (member.getPictureURL() != null)
						    	m_picture = member.getPictureURL();
						    if (member.getCity() != null)
						    	m_city = member.getCity();
						    if (member.getState() != null)
						    	m_state = member.getState();
						    if (member.getEmail() != null)
						    	m_email = member.getEmail();
						    
						    // save member picture
						    if (! a_profile.equals(m_picture) || 
						    		! a_first.equals(m_first) ||
						    		! a_last.equals(m_last) ||
						    		! a_city.equals(m_city) ||
						    		! a_state.equals(m_state) ||
						    		! a_email.equals(m_email)) {
						    	MemberDAO memberDB = new MemberDAO(con);
							    member.setFirstName(a_first);
							    member.setLastName(a_last);
							    member.setPictureURL(a_profile);
							    member.setCity(a_city);
							    member.setState(a_state);
							    member.setEmail(a_email);
							    memberDB.saveMember(member);
						    }
						    
							float totalMeters = 0;	
							float elevation = 0;
							long rides = 0;
							long seconds = 0;
							
							
/*						    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds);
						    for (Activity activity : activities) {
						    	if (activity.getType().equalsIgnoreCase("Ride") || activity.getType().equalsIgnoreCase("VirtualRide")) {
						    		totalMeters += activity.getDistance();
						    		elevation += activity.getTotal_elevation_gain();
						    		seconds += activity.getMoving_time();
						    		rides++;
						    	}
						    }
*/	
							Statistics statistics = strava.getStatistics(1, 200);
							totalMeters = statistics.getYtd_Ride_Totals().getDistance();
							elevation = statistics.getYtd_Ride_Totals().getElevation_Gain();
							System.out.println("Statistics->Miles: " + (float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0) + " Elevation: " + (long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0));
							
							if (totalMeters > 0 && elevation > 0) {
							    MemberYTDTotal memberYTDTotal = new MemberYTDTotal();
						    	memberYTDTotal.setMemberId(member.getId());
						    	memberYTDTotal.setMilesYTD((float) (Math.round(Constants.ConvertMetersToMiles(totalMeters, true) * 10) / 10.0));
						    	memberYTDTotal.setElevationYTD((long) (Math.round(Constants.ConvertMetersToFeet(elevation, true) * 10) / 10.0));
						    	memberYTDTotal.setMovingTimeYTD(statistics.getYtd_Ride_Totals().getMoving_Time());
						    	memberYTDTotal.setRidesYTD(statistics.getYtd_Ride_Totals().getCount());
						    	
							    MemberYTDTotalsDAO memberYTDTotalsDAO = new MemberYTDTotalsDAO(con);
							    memberYTDTotalsDAO.saveMemberYTDTotals(memberYTDTotal);
							}
						    
						    Thread.sleep(120000); // 2 minute
						}
						catch (Exception e) {
							System.out.println("UpdateMemberYTDTask: Failed to get member " + member.getFirstName() + " " + member.getLastName() + " data.");
							e.printStackTrace();
						}
					}
				}
				System.out.println("UpdateMemberYTDTask -> DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	class UpdateGroupRideTask extends TimerTask {
		
		@Override
		public void run() {
			System.out.println("UpdateGroupRideTask " + new Date().toString());
			
			Calendar cal = Calendar.getInstance();
	        Date startDate = Constants.getStartOfDay(new Date(cal.getTimeInMillis()));
			Date endDate = Constants.getNoonOfDay(new Date(cal.getTimeInMillis()));
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		    
		    Connection con = Database.getConnection(null);
		    
		    GroupRideDAO groupRideDAO = new GroupRideDAO(con);		    
		    GroupRide groupRide = null;
		    
		    try {	
		    	groupRide = groupRideDAO.getGroupRide();
		    	if (groupRide != null && groupRide.getSegmentId() > 0) {
				    int segmentID = groupRide.getSegmentId();
				    int bonusSegmentID = groupRide.getBonusSegmentId();	
				    
					System.out.println("Looking for UpdateGroupRideTask Segment: " + segmentID);
					System.out.println("Looking for UpdateGroupRideTask Bonus Segment: " + bonusSegmentID);
				    
					List<Member> members = new ArrayList<Member>();
					MemberDAO memberDAO = new MemberDAO(con);
					members = memberDAO.getAllMembers();
					for (Member member : members) {
						if (member != null && member.getAccessToken() != null) {
							JStravaV3 strava = new JStravaV3(member.getAccessToken(), false);
	
							try {
							    // group ride segment
							    List<SegmentEffort> segmentEfforts = strava.findAthleteSegmentEffort(segmentID, member.getAthleteId(), df.format(startDate), df.format(endDate));
							    if (segmentEfforts.size() > 0) {
								    System.out.println("UpdateGroupRideTask: Found segment: " + segmentEfforts.get(0).getName() + " for " + member.getFirstName() + " " + member.getLastName());
								    MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(con);	
								    MemberActivityTotal memberActivityTotal = memberActivityTotalsDAO.getMemberData(member.getId());
								    if (memberActivityTotal != null && memberActivityTotal.getMemberId() > 0) {
								    	MemberActivityTotalsDAO memberActivityTotalsDB = new MemberActivityTotalsDAO(con);
								    	memberActivityTotal.setGroupRide(memberActivityTotal.getGroupRide()+1);
								    	
									    // bonus ride segment
									    if (bonusSegmentID > 0) {
										    segmentEfforts = strava.findAthleteSegmentEffort(bonusSegmentID,  member.getAthleteId(), df.format(startDate), df.format(endDate));
										    if (segmentEfforts.size() > 0) {
											    System.out.println("UpdateBonusRideTask: Found segment: " + segmentEfforts.get(0).getName() + " for " + member.getFirstName() + " " + member.getLastName());
											    memberActivityTotal.setEventRide(memberActivityTotal.getEventRide()+1);
										    }
										    else
											    System.out.println("UpdateBonusRideTask: segmentEffort " + bonusSegmentID + " NOT found for : " + member.getFirstName() + " " + member.getLastName());
									    }
								    	memberActivityTotalsDB.saveMemberActivityTotals(memberActivityTotal);
								    }
							    }
							    else
								    System.out.println("UpdateGroupRideTask: segmentEffort " + segmentID + " NOT found for : " + member.getFirstName() + " " + member.getLastName());
							    Thread.sleep(120000); // 2 minutes			    
							}
							catch (Exception e) {
								System.out.println("UpdateGroupRideTask: Failed to get member " + member.getFirstName() + " " + member.getLastName() + " data.");
								e.printStackTrace();
							}
				        }
					}
					System.out.println("UpdateGroupRideTask -> DONE");
		    	}
		    	else
					System.out.println("UpdateGroupRideTask -> NO SEGMENT SET");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}	
	
	class UpdateBCCGroupRideTask extends TimerTask {
		
		@Override
		public void run() {
			System.out.println("UpdateBCCGroupRideTask " + new Date().toString());
			
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DAY_OF_YEAR, -1);
			yesterday.set(Calendar.HOUR_OF_DAY, 0);
			yesterday.set(Calendar.MINUTE, 0);
			yesterday.set(Calendar.SECOND, 0);
			yesterday.set(Calendar.MILLISECOND, 0);			
			Date yesterdayDate = yesterday.getTime();
			
	        Date startDate = Constants.getEveningOfDay(new Date(yesterday.getTimeInMillis()));
			Date endDate = Constants.getEndOfDay(new Date(yesterday.getTimeInMillis()));
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		    DateFormat tuesdayDF = new SimpleDateFormat("MM/dd/yyyy");
		    
		    int segmentID = 0;
		    ArrayList<Date> tuesday_2349538 = new ArrayList<Date>();
		    ArrayList<Date> tuesday_632472 = new ArrayList<Date>();
		    ArrayList<Date> tuesday_2344817 = new ArrayList<Date>();
		    ArrayList<Date> tuesday_910641 = new ArrayList<Date>();
		    //ArrayList<Date> tuesday2_641237 = new ArrayList<Date>();
		    
		    try {
		    	// morgul - 632472
		    	tuesday_632472.add(tuesdayDF.parse("05/08/2018"));
		    	tuesday_632472.add(tuesdayDF.parse("06/12/2018"));
		    	tuesday_632472.add(tuesdayDF.parse("07/24/2018"));
		    	tuesday_632472.add(tuesdayDF.parse("08/28/2018"));

		    	// 47th - Foothills overpass - 2344817
		    	tuesday_2344817.add(tuesdayDF.parse("05/15/2018"));
		    	tuesday_2344817.add(tuesdayDF.parse("06/26/2018"));
		    	tuesday_2344817.add(tuesdayDF.parse("08/01/2018"));
		    	tuesday_2344817.add(tuesdayDF.parse("09/11/2018"));

		    	// Cherryvale to Baseline Hill - 2349538
		    	tuesday_2349538.add(tuesdayDF.parse("05/22/2018"));
		    	tuesday_2349538.add(tuesdayDF.parse("07/03/2018"));
		    	tuesday_2349538.add(tuesdayDF.parse("08/07/2018"));
		    	tuesday_2349538.add(tuesdayDF.parse("09/18/2018"));

		    	// lookout - 910641
		    	tuesday_910641.add(tuesdayDF.parse("05/29/2018"));
		    	tuesday_910641.add(tuesdayDF.parse("07/10/2018"));
		    	tuesday_910641.add(tuesdayDF.parse("08/12/2018"));
		    	//tuesday_910641.add(tuesdayDF.parse("09/25/2018"));

		    	// 47th - Foothills overpass - 2344817
		    	//tuesday2_1409600.add(tuesdayDF.parse("05/02/2018"));
		    	tuesday_2344817.add(tuesdayDF.parse("06/05/2018"));
		    	tuesday_2344817.add(tuesdayDF.parse("07/17/2018"));
		    	tuesday_2344817.add(tuesdayDF.parse("08/21/2018"));

		    } catch (ParseException e1){// TODO Auto-generated catch block
		    	e1.printStackTrace();
		    	return;
			}

		    // Cherryvale to Baseline Hill - 2349538
		    for (Date tues : tuesday_2349538) {
		    	if (yesterdayDate.equals(tues)) {
		    		segmentID = 2349538;
		    		break;
		    	}
		    }
		    
		    // morgul - 632472
		    if (segmentID == 0) {
			    for (Date tues : tuesday_632472) {
			    	if (yesterdayDate.equals(tues)) {
			    		segmentID = 632472;
			    		break;
			    	}
			    }
		    }

		    // 47th - Foothills overpass - 2344817
		    if (segmentID == 0) {
			    for (Date tues : tuesday_2344817) {
			    	if (yesterdayDate.equals(tues)) {
			    		segmentID = 2344817;
			    		break;
			    	}
			    }
		    }

		    // lookout - 910641
		    if (segmentID == 0) {
			    for (Date tues : tuesday_910641) {
			    	if (yesterdayDate.equals(tues)) {
			    		segmentID = 910641;
			    		break;
			    	}
			    }
		    }

		    // 47th - Foothills overpass - 2344817
		    if (segmentID == 0) {
			    for (Date tues : tuesday_2344817) {
			    	if (yesterdayDate.equals(tues)) {
			    		segmentID = 2344817;
			    		break;
			    	}
			    }
		    }
		    
		    if (segmentID == 0) {
		    	System.out.println("UpdateBCCGroupRideTask: Segment Not Set");
		    	return;
		    }
		    
		    Connection con = Database.getConnection(null);

			try {
				List<Member> members = new ArrayList<Member>();
				MemberDAO memberDAO = new MemberDAO(con);
				members = memberDAO.getAllMembers();
				for (Member member : members) {
					if (member != null && member.getAccessToken() != null) {
						JStravaV3 strava = new JStravaV3(member.getAccessToken(), false);
					    
						try {
						    // group ride segment
						    List<SegmentEffort> segmentEfforts = strava.findAthleteSegmentEffort(segmentID,  member.getAthleteId(), df.format(startDate), df.format(endDate));
						    if (segmentEfforts.size() > 0) {
							    System.out.println("UpdateBCCGroupRideTask: Found segment: " + segmentEfforts.get(0).getName() + " for " + member.getFirstName() + " " + member.getLastName());
							    
							    MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(con);	
							    MemberActivityTotal memberActivityTotal = memberActivityTotalsDAO.getMemberData(member.getId());
							    if (memberActivityTotal != null && memberActivityTotal.getMemberId() > 0) {
							    	MemberActivityTotalsDAO memberActivityTotalsDB = new MemberActivityTotalsDAO(con);
							    	memberActivityTotal.setEventRide(memberActivityTotal.getEventRide()+1);
							    	memberActivityTotalsDB.saveMemberActivityTotals(memberActivityTotal);
							    }
						    }
						    else 
							    System.out.println("UpdateBCCGroupRideTask: segmentEffort->" + segmentID + " NOT found for : " + member.getFirstName() + " " + member.getLastName() + " " + df.format(startDate) + "-->" + df.format(endDate));
						    Thread.sleep(120000); // 2 minutes			    
						}
						catch (Exception e) {
							System.out.println("UpdateBCCGroupRideTask: Failed to get member " + member.getFirstName() + " " + member.getLastName() + " data.");
							e.printStackTrace();
						}
			        }
				}
				System.out.println("UpdateBCCGroupRideTask -> DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}	
	
}
