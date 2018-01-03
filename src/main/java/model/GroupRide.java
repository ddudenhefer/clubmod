package model;

import java.io.Serializable;

public class GroupRide implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private int segmentId;
   private int bonusSegmentId;


   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getSegmentId() {
      return segmentId;
   }

   public void setSegmentId(int segmentId) {
	      this.segmentId = segmentId;
   }
   
   public int getBonusSegmentId() {
      return bonusSegmentId;
   }

   public void setBonusSegmentId(int bonusSegmentId) {
	   this.bonusSegmentId = bonusSegmentId;
   }
}