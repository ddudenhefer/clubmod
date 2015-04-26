package model;

import java.io.Serializable;

public class MemberYTDTotal implements Serializable {
   private static final long serialVersionUID = 1L;

   private int memberId;
   private float milesYTD;
   private long elevationYTD;
   private long movingTimeYTD;
   private long ridesYTD;


   public int getMemberId() {
      return memberId;
   }

   public void setMemberId(int memberId) {
      this.memberId = memberId;
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
   
   public long getMovingTimeYTD() {
	      return movingTimeYTD;
	   }

   public void setMovingTimeYTD(long movingTimeYTD) {
	   this.movingTimeYTD = movingTimeYTD;
   }
   
   public long getRidesYTD() {
	      return ridesYTD;
	   }

   public void setRidesYTD(long ridesYTD) {
	   this.ridesYTD = ridesYTD;
}
   
   
   
}