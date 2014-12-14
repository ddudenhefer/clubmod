package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;


public class Member implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private int athleteId;
   private String accessToken;
   private String firstName;
   private String lastName;
   private String pictureURL; 
   
   // not in db
   private MemberPoints memberPoints;   
   private List<Challenge> challengeWins;   
   private int totalRides;   
   private int fantasyEntry;
   private int fantasyFirst;
   private int fantasySecond;
   private int fantasyThird;
   private int groupRides;
   private int eventRides;


   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getAthleteId() {
	      return athleteId;
	   }

   public void setAthleteId(int athleteId) {
	      this.athleteId = athleteId;
   }

   public String getAccessToken() {
      return accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public String getFirstName() {
	   return firstName;
   }

   public void setFirstName(String firstName) {
	   this.firstName = firstName;
   }

   public String getLastName() {
	   return lastName;
   }

   public void setLastName(String lastName) {
	   this.lastName = lastName;
   }
   
   public MemberPoints getMemberPoints() {
       return memberPoints;
   }

   public void setMemberPoints(MemberPoints memberPoints) {
       this.memberPoints = memberPoints;
   }
   
   public List<Challenge> getChallngeWins() {
       return challengeWins;
   }

   public void setsetChallengeWins(List<Challenge> challengeWins) {
       this.challengeWins = challengeWins;
   }

   public String getPictureURL() {
       return pictureURL;
   }

   public void setPictureURL(String pictureURL) {
       this.pictureURL = pictureURL;
   }   
   
   public int getTotalRides() {
      return totalRides;
   }

   public void setTotalRides(int totalRides) {
      this.totalRides = totalRides;
   }
  
   public int getFantasyEntry() {
      return fantasyEntry;
   }

   public void setFantasyEntry(int fantasyEntry) {
      this.fantasyEntry = fantasyEntry;
   }

   public int getFantasyFirst() {
      return fantasyFirst;
   }

   public void setFantasyFirst(int fantasyFirst) {
      this.fantasyFirst = fantasyFirst;
   }
	   
   public int getFantasySecond() {
      return fantasySecond;
   }

   public void setFantasySecond(int fantasySecond) {
      this.fantasySecond = fantasySecond;
   }

   public int getFantasyThird() {
      return fantasyThird;
   }

   public void setFantasyThird(int fantasyThird) {
      this.fantasyThird = fantasyThird;
   }
   
   public int getGroupRides() {
      return groupRides;
   }

   public void setGroupRides(int groupRides) {
      this.groupRides = groupRides;
   }
   
   public int getEventRides() {
      return eventRides;
   }

   public void setEventRides(int eventRides) {
      this.eventRides = eventRides;
   }
   
   public static class Comparators {

       public static Comparator<Member> NAME = new Comparator<Member>() {
           @Override
           public int compare(Member o1, Member o2) {
           	int ret = o1.getLastName().compareTo(o2.getLastName());
           	if (ret == 0)
           		return o1.getFirstName().compareTo(o2.getFirstName());
           	return ret;
           }
       };

       public static Comparator<Member> POINTS = new Comparator<Member>() {
           @Override
           public int compare(Member o1, Member o2) {
           	return Integer.compare(o2.getMemberPoints().getPointsYTD(), o1.getMemberPoints().getPointsYTD());
           }
       };
   }

}

