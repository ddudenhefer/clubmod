package model;

import java.io.Serializable;

public class Member implements Serializable {
   private static final long serialVersionUID = 1L;

   private Long id;
   private Long athleteId;
   private String accessToken;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getAthleteId() {
	      return athleteId;
	   }

   public void setAthleteId(Long athleteId) {
	      this.id = athleteId;
   }

   public String getAccessToken() {
      return accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

}