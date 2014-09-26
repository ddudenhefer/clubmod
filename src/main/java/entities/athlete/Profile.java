package entities.athlete;

public class Profile {

    private String access_token;
    private Athlete athlete;


    @Override
    public String toString() {
        return access_token;
    }

    public Profile() {
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }
}
