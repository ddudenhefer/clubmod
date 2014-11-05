package model;

import java.io.Serializable;
import java.util.Date;

public class Point implements Serializable {
   private static final long serialVersionUID = 1L;

   private int id;
   private String type;
   private String subType;
   private int increment;
   private int limit;
   private int points;


   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getSubType() {
      return subType;
   }

   public void setSubType(String subType) {
	   this.subType = subType;
   }
   
   public int getIncrement() {
      return increment;
   }

   public void setIncrement(int increment) {
      this.increment = increment;
   }
   
   public int getLimit() {
      return limit;
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }

   public int getPoints() {
      return points;
   }

   public void setPoints(int points) {
      this.points = points;
   }
   
}