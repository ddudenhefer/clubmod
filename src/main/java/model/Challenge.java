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


   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int ChallengeIndex() {
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

}