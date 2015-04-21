package model;

import java.io.Serializable;
import java.util.Comparator;


public class MemberPoints implements Serializable {
   private static final long serialVersionUID = 1L;

   private float milesYTD;
   private long elevationYTD;
   private int pointsYTD;   

   private int challenges;   
   private int fantasy;   
   private int groupRides;   
   private int eventRides;   
   private int miles;   
   private int elevation;   
   private int homePurchases;   
   private int homeReferrals;   

   
   public int getChallenges() {
      return challenges;
   }

   public void setChallenges(int challenges) {
      this.challenges = challenges;
   }

   public int getFantasy() {
	      return fantasy;
	   }

   public void setFantasy(int fantasy) {
	      this.fantasy = fantasy;
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

   public int getHomePurchases() {
	   return homePurchases;
   }

   public void setHomePurchases(int homePurchases) {
	   this.homePurchases = homePurchases;
   }
   
   public int getHomeReferrals() {
	   return homeReferrals;
   }

   public void setHomeReferrals(int homeReferrals) {
	   this.homeReferrals = homeReferrals;
   }
   
   
   
   public int getMiles() {
	   return miles;
   }

   public void setMiles(int miles) {
	   this.miles = miles;
   }

   public int getElevation() {
	   return elevation;
   }

   public void setElevation(int elevation) {
	   this.elevation = elevation;
   }

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

}

