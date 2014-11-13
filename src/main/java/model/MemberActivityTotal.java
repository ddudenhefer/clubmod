package model;

import java.io.Serializable;

public class MemberActivityTotal implements Serializable {
   private static final long serialVersionUID = 1L;

   private int memberId;
   private String memberName;
   private int fantasyEntry;
   private int fantasyFirst;
   private int fantasySecond;
   private int fantasyThird;
   private int groupRide;
   private int eventRide;


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
}