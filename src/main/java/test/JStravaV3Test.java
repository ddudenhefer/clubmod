package test;

import entities.activity.Activity;
import entities.activity.Comment;
import entities.activity.LapEffort;
import entities.activity.Photo;
import entities.athlete.Athlete;
import entities.club.Club;
import entities.gear.Gear;
import entities.segment.*;
import entities.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Constants;
import connector.JStravaV3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.*;


public class JStravaV3Test {

    String accessToken;
    int athleteId;
    int activityId;
    int updateActivityId;
    int clubId;
    String gearId;
    long segmentId;

    @Before
    public void setUp() throws Exception {
        
    	accessToken ="e581792ffe9d458123259088cccf19c46c03f876";
        athleteId=1723749;
        activityId=188000027;
        clubId=43953;
        gearId="";
        segmentId=1090795;
        updateActivityId=0;
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test(expected = RuntimeException.class)
    public void testFailedConnection() {
    	
    	JStravaV3 strava= new JStravaV3("xxxxxxxx", true);
    }


    @Test
    public void testJStravaV3() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        Athlete athlete=strava.getCurrentAthlete();
        assertNotNull(athlete);
    }


    @Test
    public void testFindAthlete() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        Athlete athlete=strava.findAthlete(athleteId);
        assertNotNull(athlete);
        assertFalse(athlete.getBikes().isEmpty());
        assertFalse(athlete.getShoes().isEmpty());
        assertFalse(athlete.getClubs().isEmpty());
    }


    @Test
    public void testUpdateAthlete() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        HashMap optionalParameters= new HashMap();

        double weight=71;
        optionalParameters.put("weight",weight);
        Athlete athlete=strava.updateAthlete(optionalParameters);
        assertNotNull(athlete);
    }


    @Test
    public void testFindAthleteKOMs() {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<SegmentEffort> efforts= strava.findAthleteKOMs(8320);

        assertFalse(efforts.isEmpty());
        for (SegmentEffort effort:efforts) {
            System.out.println("Segment Effort KOM " + effort.toString());

        }
    }

    
    @Test
    public void testFindAthleteKOMsWithPagination() {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<SegmentEffort> efforts= strava.findAthleteKOMs(athleteId,2,1);

        assertFalse(efforts.isEmpty());
        assertTrue(efforts.size()==1);
        for (SegmentEffort effort:efforts) {
            System.out.println("Segment Effort KOM " + effort.toString());

        }
    }


    @Test
    public void testGetCurrentAthleteFriends() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.getCurrentAthleteFriends();
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Current Athlete Friends "+athlete.toString());
        }
    }


    @Test
    public void testGetCurrentAthleteFriendsWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.getCurrentAthleteFriends(2,1);
        assertFalse(athletes.isEmpty());
        assertTrue(athletes.size()==1);
        for (Athlete athlete:athletes) {
            System.out.println("Current Athlete Friends "+athlete.toString());
        }
    }


    @Test
    public void testFindAthleteFriends() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findAthleteFriends(athleteId);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athlete Friends "+athlete.toString());
        }
    }

    @Test
    public void testFindAthleteFriendsWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findAthleteFriends(athleteId,2,1);
        assertFalse(athletes.isEmpty());
        assertTrue(athletes.size()==1);
        for (Athlete athlete:athletes) {
            System.out.println("Athlete Friends with pagination "+athlete.toString());
        }
    }


    @Test
    public void testGetCurrentAthleteFollowers() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.getCurrentAthleteFollowers();
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athlete Followers "+athlete.toString());
        }
    }


    @Test
    public void testGetCurrentAthleteFollowersWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.getCurrentAthleteFollowers(2,1);
        assertTrue(athletes.size()==1);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athlete Followers "+athlete.toString());
        }
    }


    @Test
    public void testFindAthleteFollowers() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findAthleteFollowers(athleteId);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athlete Followers "+athlete.toString());
        }
    }


    @Test
    public void testFindAthleteFollowersWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findAthleteFollowers(athleteId,2,1);
        assertTrue(athletes.size()==1);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athlete Followers "+athlete.toString());
        }
    }


    @Test
    public void testFindAthleteBothFollowing() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findAthleteBothFollowing(athleteId);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athletes Both Following "+athlete.toString());
        }
    }


    @Test
    public void testFindAthleteBothFollowingWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findAthleteBothFollowing(athleteId,2,1);
        assertTrue(athletes.size()==1);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Athletes Both Following "+athlete.toString());
        }
    }


    @Test
    public void testCreateAndDeleteActivity() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        Activity activity= strava.createActivity("Test Manual Activity", "ride", "2014-03-14T09:00:00Z", 10);
        assertNotNull(activity);
        System.out.println("Activity Name "+activity.toString());
        Activity activityExtra= strava.createActivity("Test Manual Activity","ride","2014-03-14T09:00:00Z",10,"Testing manual creation",100);
        assertNotNull(activityExtra);
        System.out.println("Activity Name "+activityExtra.toString());
        strava.deleteActivity(activity.getId());
        strava.deleteActivity(activityExtra.getId());
    }


    @Test
    public void testFindActivity() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        Activity activity= strava.findActivity(activityId);
        assertNotNull(activity);
        System.out.println("Activity Name "+activity.toString());
        assertNotNull(activity.getAthlete());
        System.out.println("Athlete "+activity.getAthlete().getId());
        System.out.println("MAP"+activity.getMap().toString());

        assertFalse(activity.getSegment_efforts().isEmpty());
        for (SegmentEffort segmentEffort: activity.getSegment_efforts()) {
            System.out.println("Segment Effort "+segmentEffort.toString());
            System.out.println("  Segment Effort Athlete"+segmentEffort.getAthlete().getId());
            assertNotNull(segmentEffort.getSegment());
            System.out.println("        Matching Segment "+segmentEffort.getSegment().toString());
        }
    }


    @Test
    public void testUpdateActivity() throws Exception {

        JStravaV3 strava= new JStravaV3("e581792ffe9d458123259088cccf19c46c03f876", true);

        HashMap optionalParameters= new HashMap();

        float temp=84;
        optionalParameters.put("average_temp",temp);
        Activity activity=strava.updateActivity(329634020,optionalParameters);
        assertNotNull(activity);
    }


    @Test
    public void testGetCurrentAthleteActivities() {
    	
	    JStravaV3 strava= new JStravaV3(accessToken, true);
	    List<Activity> activities= strava.getCurrentAthleteActivities();
	    assertFalse(activities.isEmpty());
	    for (Activity activity:activities) {
	        System.out.println("Current Athlete Activity "+activity.toString());
	    }
    }


    @Test
    public void testGetCurrentAthleteActivitiesWithPagination() {
    	
        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Activity> activities= strava.getCurrentAthleteActivities(2,1);
        assertTrue(activities.size()==1);
        assertFalse(activities.isEmpty());
        for (Activity activity:activities) {
            System.out.println("Current Athlete Activity With Pagination "+activity.toString());
        }
    }


    @Test
    public void testGetCurrentFriendsActivities() {
        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Activity> activities= strava.getCurrentFriendsActivities();
        assertFalse(activities.isEmpty());
        for (Activity activity:activities) {
            System.out.println("Friend Activity "+activity.toString());
        }
    }


    @Test
    public void testGetCurrentFriendsActivitiesWithPagination() {
        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Activity> activities= strava.getCurrentFriendsActivities(2, 1);
        assertTrue(activities.size()==1);
        assertFalse(activities.isEmpty());
        for (Activity activity:activities) {
            System.out.println("Friend Activity "+activity.toString());
        }
    }

    
    @Test
    public void testFindActivityLaps() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<LapEffort>laps=strava.findActivityLaps(activityId);

        assertFalse(laps.isEmpty());

        for (LapEffort lap:laps) {
            System.out.println("Lap "+ lap.toString());
        }
    }


    @Test
    public void testFindActivityComments() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Comment> comments= strava.findActivityComments(activityId);
        assertFalse(comments.isEmpty());
        for (Comment comment:comments) {
            System.out.println(comment.getText());
        }
    }


    @Test
    public void testFindActivityCommentsWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Comment> comments= strava.findActivityComments(activityId,false,2,1);
        assertTrue(comments.size()==1);
        assertFalse(comments.isEmpty());
        for (Comment comment:comments) {
            System.out.println(comment.getText());
        }
    }


    @Test
    public void testFindActivityKudos() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findActivityKudos(activityId);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println(athlete.toString());
        }
    }


    @Test
    public void testFindActivityKudosWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findActivityKudos(activityId,2,1);
        assertTrue(athletes.size()==1);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println(athlete.toString());
        }
    }

    
    /*Expect exception if you dont have an activity with photos*/
    @Test(expected = RuntimeException.class)
    public void testFindActivityPhotos() {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Photo> photos= strava.findActivityPhotos(activityId);

        assertFalse(photos.isEmpty());
        for (Photo photo: photos) {
            System.out.println("Photo " + photo.toString());
        }
    }


    @Test
    public void testFindClubMembers() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findClubMembers(clubId);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Club Member "+athlete.toString());
        }
    }

    
    @Test
    public void testFindClubMembersWithPagination() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Athlete> athletes= strava.findClubMembers(clubId,1,200);
        assertFalse(athletes.isEmpty());
        for (Athlete athlete:athletes) {
            System.out.println("Club Member "+athlete.toString());
        }
    }


    ////////Remove EXPECTED annotation if you point to a club you are member of.
    @Test(expected=RuntimeException.class)
    public void testFindClubActivities() {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Activity> activities= strava.findClubActivities(clubId);
        assertFalse(activities.isEmpty());
        for (Activity activity:activities) {
            System.out.println("Club Activity Name "+activity.toString());
        }
    }


    ////////Remove EXPECTED annotation if you point to a club you are member of.
    @Test(expected=RuntimeException.class)
    public void testFindClubActivitiesWithPagination() {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Activity> activities= strava.findClubActivities(clubId,2,1);
        assertTrue(activities.size()==1);
        assertFalse(activities.isEmpty());
        for (Activity activity:activities) {
            System.out.println("Club Activity Name "+activity.toString());
        }
    }


    @Test
    public void testFindClub() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        Club club= strava.findClub(clubId);
        assertNotNull(club);
        System.out.println("Club Name " + club.toString());
    }


    ////////Change assert if you do have clubs
    @Test
    public void testGetCurrentAthleteClubs() {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Club> clubs= strava.getCurrentAthleteClubs();
        assertFalse(clubs.isEmpty());
        for (Club club:clubs) {
            System.out.println("Club Name " + club.toString());
        }
    }


    @Test
    public void testFindGear() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);

        Gear gear= strava.findGear(gearId);
        assertNotNull(gear);
        System.out.println("Gear Name " + gear.toString());
    }


    @Test
    public void testFindSegment() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        Segment segment= strava.findSegment(segmentId);
        assertNotNull(segment);

        System.out.println("SEGMENT "+segment.toString());
    }


    @Test
    public void testFindCurrentStarredSegments() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Segment>segments=strava.getCurrentStarredSegment();

        assertFalse(segments.isEmpty());

        for (Segment segment:segments) {
            System.out.println("Starred Segment "+ segment);
        }
    }

    
    @Test
    public void testFindSegmentLeaderBoard() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        SegmentLeaderBoard board= strava.findSegmentLeaderBoard(segmentId);
        assertNotNull(board);
        for (LeaderBoardEntry entry:board.getEntries()) {
            System.out.println("Segment LeaderBoard "+entry.toString());
        }
    }


    @Test
    public void testFindSegmentLeaderBoardWithParameters() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        HashMap optionalParameters= new HashMap();
        optionalParameters.put("gender","F");
        optionalParameters.put("page",1);
        optionalParameters.put("per_page",3);
        SegmentLeaderBoard board= strava.findSegmentLeaderBoard(segmentId,optionalParameters);
        assertNotNull(board);
        assertTrue(board.getEntries().size()==3);
        for (LeaderBoardEntry entry:board.getEntries()) {
            assertEquals("F", entry.getAthlete_gender());
            System.out.println("Segment LeaderBoard with Parameters "+entry.toString());
        }
    }


    @Test
    public void testFindSegmentExplorer() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        Bound bound= new Bound(37.821362,-122.505373,37.842038,-122.465977);
        List<Segment> segments= strava.findSegments(bound);
        assertNotNull(segments);

        for (Segment segment:segments) {
            System.out.println("Segment Explorer "+segment.toString());
            System.out.println("CLIMB CATEGORY DESCRIPTION"+segment.getClimb_category_desc());
        }
    }


    @Test
    public void testFindActivityStreams() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"});
        assertNotNull(streams);

        for (Stream stream:streams) {
            System.out.println("STREAM TYPE "+stream.getType());
             for (int i=0;i<stream.getData().size();i++) {
                 System.out.println("STREAM "+stream.getData().get(i));
             }
        }
    }


    @Test
    public void testFindActivityStreamsWithResolution() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"},"low",null);
        assertNotNull(streams);

        for (Stream stream:streams) {
            System.out.println("STREAM TYPE "+stream.getType());
            for (int i=0;i<stream.getData().size();i++) {
                assertEquals("low",stream.getResolution());
                System.out.println("STREAM " + stream.getData().get(i));
            }
        }
    }


    @Test
    public void testFindEffortStreams() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Stream> streams= strava.findEffortStreams(activityId,new String[]{"latlng","time","distance"});
        assertNotNull(streams);

        for (Stream stream:streams) {
            System.out.println("STREAM TYPE "+stream.getType());
            for (int i=0;i<stream.getData().size();i++) {
                System.out.println("STREAM "+stream.getData().get(i));
            }
        }
    }


    @Test
    public void testFindEffortStreamsWithResolution() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Stream> streams= strava.findEffortStreams(activityId,new String[]{"latlng","time","distance"},"low",null);
        assertNotNull(streams);

        for (Stream stream:streams) {
            System.out.println("STREAM TYPE "+stream.getType());
            for (int i=0;i<stream.getData().size();i++) {
                assertEquals("low",stream.getResolution());
                System.out.println("STREAM " + stream.getData().get(i));
            }
        }
    }


    @Test
    public void testFindSegmentStreams() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"});
        assertNotNull(streams);

        for (Stream stream:streams) {
            System.out.println("STREAM TYPE "+stream.getType());
            for (int i=0;i<stream.getData().size();i++) {
                System.out.println("STREAM "+stream.getData().get(i));
            }
        }
    }


    @Test
    public void testFindSegmentStreamsWithResolution() throws Exception {

        JStravaV3 strava= new JStravaV3(accessToken, true);
        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"},"low",null);
        assertNotNull(streams);

        for (Stream stream:streams) {
            System.out.println("STREAM TYPE "+stream.getType());
            for (int i=0;i<stream.getData().size();i++) {
                assertEquals("low",stream.getResolution());
                System.out.println("STREAM " + stream.getData().get(i));
            }
        }
    }
    
    @Test
    public void testGetAthleteActivitiesBetweenDates() {
    	
	    JStravaV3 strava= new JStravaV3("3ff761fc2568621422792aa096c8bb0746fdb15f", true);
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	    long startSeconds, endSeconds;
		try {
			startSeconds = df.parse("01/15/2015").getTime() / 1000l;
			endSeconds = df.parse("01/27/2015").getTime() / 1000l;
		    List<Activity> activities= strava.getAthleteActivitiesBetweenDates(startSeconds,endSeconds );
		    for (Activity activity:activities) {
		        System.out.println("Current Athlete Activity "+activity.toString());
		        System.out.println("      Date "+activity.getStart_date_local());
		        System.out.println("      Miles "+Constants.ConvertMetersToMiles(activity.getDistance(), true));
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @Test
    public void testGetAthleteSegmentEffort() {
    	
	    JStravaV3 strava= new JStravaV3("3ff761fc2568621422792aa096c8bb0746fdb15f", true);
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
	    
		try {
			Calendar cal = Calendar.getInstance();
	        Date startDate = Constants.getStartOfDay(new Date(cal.getTimeInMillis()));
			Date endDate = Constants.getEndOfDay(new Date(cal.getTimeInMillis()));
			List<SegmentEffort> segmentEfforts = strava.findAthleteSegmentEffort(9110539, 419308, df.format(startDate), df.format(endDate));
    
			if (segmentEfforts.size() > 0) {
				for (SegmentEffort segmentEffort : segmentEfforts)
					System.out.println("segmentEffort found: " + segmentEffort.getName());
		    }
		    else
			    System.out.println("segmentEffort NOT found: ");    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
