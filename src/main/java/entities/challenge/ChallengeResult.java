package entities.challenge;

import java.util.Comparator;


public class ChallengeResult implements Comparable<ChallengeResult> {

	private int athleteId;
    private String firstName;
    private String lastName;
    private float miles;
    private int rides;
    private float speed;
    private long time;
    private long elevation;

    
    @Override
    public int compareTo(ChallengeResult o) {
        return Comparators.NAME.compare(this, o);
    }
    
    
    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public ChallengeResult() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFisrtName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getMiles() {
        return miles;
    }

    public void setMiles(float miles) {
        this.miles = miles;
    }

    public int getRides() {
        return rides;
    }

    public void setRides(int rides) {
        this.rides = rides;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getElevation() {
        return elevation;
    }

    public void setElevation(long elevation) {
        this.elevation = elevation;
    }

    public static class Comparators {

        public static Comparator<ChallengeResult> NAME = new Comparator<ChallengeResult>() {
            @Override
            public int compare(ChallengeResult o1, ChallengeResult o2) {
            	int ret = o1.getLastName().compareTo(o2.getLastName());
            	if (ret == 0)
            		return o1.getFirstName().compareTo(o2.getFirstName());
            	return ret;
            }
        };

        public static Comparator<ChallengeResult> MILES = new Comparator<ChallengeResult>() {
            @Override
            public int compare(ChallengeResult o1, ChallengeResult o2) {
            	int ret =  Float.compare(o2.getMiles(), o1.getMiles());
               	if (ret == 0)
               		ret = Float.compare(o2.getElevation(), o1.getElevation());
               	return ret;
            }
        };

        public static Comparator<ChallengeResult> RIDES = new Comparator<ChallengeResult>() {
            @Override
            public int compare(ChallengeResult o1, ChallengeResult o2) {
            	int ret = Integer.compare(o2.getRides(), o1.getRides());
               	if (ret == 0)
               		ret = Float.compare(o2.getMiles(), o1.getMiles());
               	return ret;
            }
        };

        public static Comparator<ChallengeResult> SPEED = new Comparator<ChallengeResult>() {
            @Override
            public int compare(ChallengeResult o1, ChallengeResult o2) {
            	int ret = Float.compare(o2.getSpeed(), o1.getSpeed());
               	if (ret == 0)
               		ret = Float.compare(o2.getMiles(), o1.getMiles());
               	return ret;
            }
        };

        public static Comparator<ChallengeResult> TIME = new Comparator<ChallengeResult>() {
            @Override
            public int compare(ChallengeResult o1, ChallengeResult o2) {
            	int ret = Long.compare(o2.getTime(), o1.getTime());
               	if (ret == 0)
               		ret = Float.compare(o2.getMiles(), o1.getMiles());
               	return ret;
            }
        };

        public static Comparator<ChallengeResult> ELEVATION = new Comparator<ChallengeResult>() {
            @Override
            public int compare(ChallengeResult o1, ChallengeResult o2) {
            	int ret = Long.compare(o2.getElevation(), o1.getElevation());
               	if (ret == 0)
               		ret = Float.compare(o2.getMiles(), o1.getMiles());
               	return ret;
            }
        };

    }
}
