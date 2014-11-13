package model;

import java.io.Serializable;
import java.util.Comparator;

import entities.athlete.Athlete;
import entities.challenge.ChallengeResult;

public class Member implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private int athleteId;
   private String accessToken;
   private String firstName;
   private String lastName;
   
   // not in db
   private float milesYTD;
   private long elevationYTD;
   private int pointsYTD;   


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
   
   //not in db
   public float getMilesYTD() {
       return milesYTD;
   }

   public void setMilesYTD(float milesYTD) {
       this.milesYTD = milesYTD;
   }

   public long getElevationYTD() {
       return elevationYTD;
   }

   public void setElevationYTD(long elevationYTD) {
       this.elevationYTD = elevationYTD;
   }
   
   public int getPointsYTD() {
       return pointsYTD;
   }

   public void setPointsYTD(int pointsYTD) {
       this.pointsYTD = pointsYTD;
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
   }

}

