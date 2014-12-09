package model;

import java.io.Serializable;
import java.util.Comparator;


public class Member implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private int athleteId;
   private String accessToken;
   private String firstName;
   private String lastName;
   
   // not in db
   private MemberPoints memberPoints;   


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

