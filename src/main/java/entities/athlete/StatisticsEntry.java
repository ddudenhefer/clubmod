package entities.athlete;

public class StatisticsEntry {

	private int	count;
	private float	distance;
	private int	moving_time;
	private int	elapsed_time;
	private float	elevation_gain;
	private int	achievement_count;

	public StatisticsEntry() {
		super();
	}

	public Integer getAchievement_Count() {
		return this.achievement_count;
	}

	public Integer getCount() {
		return this.count;
	}

	public Float getDistance() {
		return this.distance;
	}

	public Integer getElapsed_Time() {
		return this.elapsed_time;
	}

	public Float getElevation_Gain() {
		return this.elevation_gain;
	}

	public Integer getMoving_Time() {
		return this.moving_time;
	}

	public void setAchievement_Count(final Integer achievementCount) {
		this.achievement_count = achievementCount;
	}

	public void setCount(final Integer count) {
		this.count = count;
	}

	public void setDistance(final Float distance) {
		this.distance = distance;
	}

	public void setElapsed_Time(final Integer elapsedTime) {
		this.elapsed_time = elapsedTime;
	}

	public void setElevation_Gain(final Float elevationGain) {
		this.elevation_gain = elevationGain;
	}

	public void setMoving_Time(final Integer movingTime) {
		this.moving_time = movingTime;
	}
}
