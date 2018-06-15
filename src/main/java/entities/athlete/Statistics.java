package entities.athlete;

public class Statistics {

	private float biggest_ride_distance;
	private float biggest_climb_elevation_gain;
	private StatisticsEntry recent_ride_totals;
	private StatisticsEntry recent_run_totals;
	private StatisticsEntry recent_swim_totals;
	private StatisticsEntry ytd_ride_totals;
	private StatisticsEntry ytd_run_totals;
	private StatisticsEntry ytd_swim_totals;
	private StatisticsEntry all_ride_totals;
	private StatisticsEntry all_run_totals;
	private StatisticsEntry all_swim_totals;

	public Statistics() {
	}

	public StatisticsEntry getAll_Ride_Totals() {
		return this.all_ride_totals;
	}

	public StatisticsEntry getAll_Run_Totals() {
		return this.all_run_totals;
	}

	public StatisticsEntry getAll_Swim_Totals() {
		return this.all_swim_totals;
	}

	public float getBiggest_Climb_Elevation_Gain() {
		return this.biggest_climb_elevation_gain;
	}

	public float getBiggest_Ride_Distance() {
		return this.biggest_ride_distance;
	}

	public StatisticsEntry getRecent_Ride_Totals() {
		return this.recent_ride_totals;
	}

	public StatisticsEntry getRecent_Run_Totals() {
		return this.recent_run_totals;
	}

	public StatisticsEntry getRecent_Swim_Totals() {
		return this.recent_swim_totals;
	}

	public StatisticsEntry getYtd_Ride_Totals() {
		return this.ytd_ride_totals;
	}

	public StatisticsEntry getYtd_Run_Totals() {
		return this.ytd_run_totals;
	}

	public StatisticsEntry getYtd_Swim_Totals() {
		return this.ytd_swim_totals;
	}

	public void setAll_Ride_Totals(StatisticsEntry allRideTotals) {
		this.all_ride_totals = allRideTotals;
	}

	public void setAll_Run_Totals(StatisticsEntry allRunTotals) {
		this.all_run_totals = allRunTotals;
	}

	public void setAll_Swim_Totals(StatisticsEntry allSwimTotals) {
		this.all_swim_totals = allSwimTotals;
	}

	public void setBiggest_Climb_Elevation_Gain(float biggestClimbElevationGain) {
		this.biggest_climb_elevation_gain = biggestClimbElevationGain;
	}

	public void setBiggest_Ride_Distance(float biggestRideDistance) {
		this.biggest_ride_distance = biggestRideDistance;
	}

	public void setRecent_Ride_Totals(StatisticsEntry recentRideTotals) {
		this.recent_ride_totals = recentRideTotals;
	}

	public void setRecent_Run_Totals(StatisticsEntry recentRunTotals) {
		this.recent_run_totals = recentRunTotals;
	}

	public void setRecent_Swim_Totals(StatisticsEntry recentSwimTotals) {
		this.recent_swim_totals = recentSwimTotals;
	}

	public void setYtd_Ride_Totals(StatisticsEntry ytdRideTotals) {
		this.ytd_ride_totals = ytdRideTotals;
	}

	public void setYtd_Run_Totals(StatisticsEntry ytdRunTotals) {
		this.ytd_run_totals = ytdRunTotals;
	}

	public void setYtd_Swim_Totals(StatisticsEntry ytdSwimTotals) {
		this.ytd_swim_totals = ytdSwimTotals;
	}

}
