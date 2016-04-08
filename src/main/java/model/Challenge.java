package model;

import java.io.Serializable;
import java.util.Date;

public class Challenge implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private int challengeIndex;
   private String name;
   private String season;
   private Date startDate;
   private Date endDate;
   private String label;
   private String service;
   private int memberId;
   private String memberFullName;
   private int memberId2;
   private int memberId3;
   private int memberId4;
   private int memberId5;
   private int memberId6;
   private int memberId7;
   private int memberId8;
   private int memberId9;
   private int memberId10;


   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getChallengeIndex() {
	      return challengeIndex;
	   }

   public void setChallengeIndex(int challengeIndex) {
	      this.challengeIndex = challengeIndex;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSeason() {
      return season;
   }

   public void setSeason(String season) {
	   this.season = season;
   }

   public Date getStartDate() {
	      return startDate;
	   }

   public void setStartDate(Date startDate) {
	   this.startDate = startDate;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
	   this.endDate = endDate;
   }

   public String getLabel() {
	   return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getService() {
      return service;
   }

   public void setService(String service) {
	   this.service = service;
   }
   
   public int getMemberId() {
	   return memberId;
   }

   public void setMemberId(int memberId) {
	      this.memberId = memberId;
   }

   public String getMemberFullName() {
      return memberFullName;
   }

   public void setMemberFullName(String memberFullName) {
      this.memberFullName = memberFullName;
   }

   public int getMemberId2() {
	   return memberId2;
   }

   public void setMemberId2(int memberId2) {
	      this.memberId2 = memberId2;
   }

   public int getMemberId3() {
	   return memberId3;
   }

   public void setMemberId3(int memberId3) {
	      this.memberId3 = memberId3;
   }
   
   public int getMemberId4() {
	   return memberId4;
   }

   public void setMemberId4(int memberId4) {
	      this.memberId4 = memberId4;
   }

   public int getMemberId5() {
	   return memberId5;
   }

   public void setMemberId5(int memberId5) {
	      this.memberId5 = memberId5;
   }

   public int getMemberId6() {
	   return memberId6;
   }

   public void setMemberId6(int memberId6) {
	      this.memberId6 = memberId6;
   }

   public int getMemberId7() {
	   return memberId7;
   }

   public void setMemberId7(int memberId7) {
	      this.memberId7 = memberId7;
   }

   public int getMemberId8() {
	   return memberId8;
   }

   public void setMemberId8(int memberId8) {
	      this.memberId8 = memberId8;
   }
   
   public int getMemberId9() {
	   return memberId9;
   }

   public void setMemberId9(int memberId9) {
	      this.memberId9 = memberId9;
   }

   public int getMemberId10() {
	   return memberId10;
   }

   public void setMemberId10(int memberId10) {
	      this.memberId10 = memberId10;
   }

}