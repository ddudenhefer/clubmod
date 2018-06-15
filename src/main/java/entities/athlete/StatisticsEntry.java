package entities.athlete;

public class StatisticsEntry {

	private Integer	count;
	private Float	distance;
	private Integer	movingTime;
	private Integer	elapsedTime;
	private Float	elevationGain;
	private Integer	achievementCount;

	public StatisticsEntry() {
		super();
	}

	public Integer getAchievementCount() {
		return this.achievementCount;
	}

	public Integer getCount() {
		return this.count;
	}

	public Float getDistance() {
		return this.distance;
	}

	public Integer getElapsedTime() {
		return this.elapsedTime;
	}

	public Float getElevationGain() {
		return this.elevationGain;
	}

	public Integer getMovingTime() {
		return this.movingTime;
	}

	public void setAchievementCount(final Integer achievementCount) {
		this.achievementCount = achievementCount;
	}

	public void setCount(final Integer count) {
		this.count = count;
	}

	public void setDistance(final Float distance) {
		this.distance = distance;
	}

	public void setElapsedTime(final Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public void setElevationGain(final Float elevationGain) {
		this.elevationGain = elevationGain;
	}

	public void setMovingTime(final Integer movingTime) {
		this.movingTime = movingTime;
	}
}
