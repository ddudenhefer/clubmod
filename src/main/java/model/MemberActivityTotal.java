package model;

import java.io.Serializable;

public class MemberActivityTotal implements Serializable {
   private static final long serialVersionUID = 1L;

   private int memberId = 0;
   private String memberName = "";
   private int fantasyEntry = 0;
   private int fantasyFirst = 0;
   private int fantasySecond = 0;
   private int fantasyThird = 0;
   private int groupRide = 0;
   private int eventRide = 0;
   private int homePurchase = 0;
   private int homeReferral = 0;
   private int pointsRedeemed = 0;


   public int getMemberId() {
      return memberId;
   }

   public void setMemberId(int memberId) {
      this.memberId = memberId;
   }

   public String getMemberName() {
	   return memberName;
   }

   public void setMemberName(String memberName) {
	   this.memberName = memberName;
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

   public int getGroupRide() {
	   return groupRide;
   }

   public void setGroupRide(int groupRide) {
	   this.groupRide = groupRide;
   }

   public int getEventRide() {
	   return eventRide;
   }

   public void setEventRide(int eventRide) {
	   this.eventRide = eventRide;
   }

   public int getHomePurchase() {
	   return homePurchase;
   }

   public void setHomePurchase(int homePurchase) {
	   this.homePurchase = homePurchase;
   }

   public int getHomeReferral() {
	   return homeReferral;
   }

   public void setHomeReferral(int homeReferral) {
	   this.homeReferral = homeReferral;
   }

   public int getPointsRedeemed() {
	   return pointsRedeemed;
   }

   public void setPointsRedeemed(int pointsRedeemed) {
	   this.pointsRedeemed = pointsRedeemed;
   }

}