package entities.challenge;

import java.util.Comparator;


public class Challenge implements Comparable<Challenge> {

	private int athleteId;
    private String firstName;
    private String lastName;
    private float miles;
    private int rides;
    private float speed;
    private long time;
    private long elevation;

    
    @Override
    public int compareTo(Challenge o) {
        return Comparators.NAME.compare(this, o);
    }
    
    
    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public Challenge() {
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

        public static Comparator<Challenge> NAME = new Comparator<Challenge>() {
            @Override
            public int compare(Challenge o1, Challenge o2) {
            	int ret = o1.getLastName().compareTo(o2.getLastName());
            	if (ret == 0)
            		return o1.getFirstName().compareTo(o2.getFirstName());
            	return ret;
            }
        };

        public static Comparator<Challenge> MILES = new Comparator<Challenge>() {
            @Override
            public int compare(Challenge o1, Challenge o2) {
            	return Float.compare(o2.getMiles(), o1.getMiles());
            }
        };

        public static Comparator<Challenge> RIDES = new Comparator<Challenge>() {
            @Override
            public int compare(Challenge o1, Challenge o2) {
            	return Integer.compare(o2.getRides(), o1.getRides());
            }
        };

        public static Comparator<Challenge> SPEED = new Comparator<Challenge>() {
            @Override
            public int compare(Challenge o1, Challenge o2) {
            	return Float.compare(o2.getSpeed(), o1.getSpeed());
            }
        };

        public static Comparator<Challenge> TIME = new Comparator<Challenge>() {
            @Override
            public int compare(Challenge o1, Challenge o2) {
            	return Long.compare(o2.getTime(), o1.getTime());
            }
        };

        public static Comparator<Challenge> ELEVATION = new Comparator<Challenge>() {
            @Override
            public int compare(Challenge o1, Challenge o2) {
            	return Long.compare(o2.getElevation(), o1.getElevation());
            }
        };

    }
}
