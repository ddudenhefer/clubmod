package utils;

public class Constants {
	
	public static final int CLUB_ID = 43953;
	public static final String PUBLIC_ACCESS_TOKEN = "e581792ffe9d458123259088cccf19c46c03f876";
	public static final String CLIENT_SECRET = "4fb119f5ab894d0bf0c998c8d32577740ca6e316";
	public static final int CLIENT_ID = 2946;
	
    public static float ConvertMetersToMiles(float meters)
    {
    	return (float) (Math.round((meters / 1609.344) * 10) / 10.0);
    }
}
