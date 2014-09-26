package model;

import java.io.Serializable;

public class Member implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private int athleteId;
   private String accessToken;


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

}