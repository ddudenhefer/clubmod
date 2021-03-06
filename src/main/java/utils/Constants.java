package utils;

import java.util.Calendar;
import java.util.Date;

public class Constants {
	
	public static final int CLUB_ID = 43953;
	public static final String PUBLIC_ACCESS_TOKEN = "e581792ffe9d458123259088cccf19c46c03f876";
	public static final String CLIENT_SECRET = "4fb119f5ab894d0bf0c998c8d32577740ca6e316";
	public static final int CLIENT_ID = 2946;
	
	public static final String DB_CONNECTION = "db_connection";
	
    public static float ConvertMetersToMiles(float meters, boolean round)
    {
    	if (round)
    		return (float) (meters / 1609.344);
    	else
    		return (float) (Math.round((meters / 1609.344) * 10) / 10.0);
    }
    
    public static float ConvertMPStoMPH (float mps, boolean round) {

    	if (round)
    		return (float) (mps / (1609.344/3600));
    	else
    		return (float) (Math.round((mps / (1609.344/3600)) * 10) / 10.0);
    }

    public static float ConvertMetersToFeet(float meters, boolean round)
    {
    	if (round)
    		return (float) (meters / 0.3048);
    	else
    		return (float) (Math.round((meters / 0.3048) * 10) / 10.0);
    }
    
    public static Date getStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 0, 0, 0);
	    return calendar.getTime();
	}

    public static Date getEveningOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 15, 0, 0);
	    return calendar.getTime();
	}

    public static Date getEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 23, 59, 59);
	    return calendar.getTime();
	}
    
    public static Date getNoonOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 14, 00, 00);
	    return calendar.getTime();
	}	    

}
