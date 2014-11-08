package model;

import java.io.Serializable;

public class MemberYTDTotal implements Serializable {
   private static final long serialVersionUID = 1L;

   private int memberId;
   private float milesYTD;
   private long elevationYTD;


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
}