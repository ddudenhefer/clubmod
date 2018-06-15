package entities.athlete;

public class Statistics {

	private Float biggestRideDistance;
	private Float biggestClimbElevationGain;
	private StatisticsEntry recentRideTotals;
	private StatisticsEntry recentRunTotals;
	private StatisticsEntry recentSwimTotals;
	private StatisticsEntry ytdRideTotals;
	private StatisticsEntry ytdRunTotals;
	private StatisticsEntry ytdSwimTotals;
	private StatisticsEntry allRideTotals;
	private StatisticsEntry allRunTotals;
	private StatisticsEntry allSwimTotals;

	public Statistics() {
	}

	public StatisticsEntry getAllRideTotals() {
		return this.allRideTotals;
	}

	public StatisticsEntry getAllRunTotals() {
		return this.allRunTotals;
	}

	public StatisticsEntry getAllSwimTotals() {
		return this.allSwimTotals;
	}

	public Float getBiggestClimbElevationGain() {
		return this.biggestClimbElevationGain;
	}

	public Float getBiggestRideDistance() {
		return this.biggestRideDistance;
	}

	public StatisticsEntry getRecentRideTotals() {
		return this.recentRideTotals;
	}

	public StatisticsEntry getRecentRunTotals() {
		return this.recentRunTotals;
	}

	public StatisticsEntry getRecentSwimTotals() {
		return this.recentSwimTotals;
	}

	public StatisticsEntry getYtdRideTotals() {
		return this.ytdRideTotals;
	}

	public StatisticsEntry getYtdRunTotals() {
		return this.ytdRunTotals;
	}

	public StatisticsEntry getYtdSwimTotals() {
		return this.ytdSwimTotals;
	}

	public void setAllRideTotals(final StatisticsEntry allRideTotals) {
		this.allRideTotals = allRideTotals;
	}

	public void setAllRunTotals(final StatisticsEntry allRunTotals) {
		this.allRunTotals = allRunTotals;
	}

	public void setAllSwimTotals(final StatisticsEntry allSwimTotals) {
		this.allSwimTotals = allSwimTotals;
	}

	public void setBiggestClimbElevationGain(final Float biggestClimbElevationGain) {
		this.biggestClimbElevationGain = biggestClimbElevationGain;
	}

	public void setBiggestRideDistance(final Float biggestRideDistance) {
		this.biggestRideDistance = biggestRideDistance;
	}

	public void setRecentRideTotals(final StatisticsEntry recentRideTotals) {
		this.recentRideTotals = recentRideTotals;
	}

	public void setRecentRunTotals(final StatisticsEntry recentRunTotals) {
		this.recentRunTotals = recentRunTotals;
	}

	public void setRecentSwimTotals(final StatisticsEntry recentSwimTotals) {
		this.recentSwimTotals = recentSwimTotals;
	}

	public void setYtdRideTotals(final StatisticsEntry ytdRideTotals) {
		this.ytdRideTotals = ytdRideTotals;
	}

	public void setYtdRunTotals(final StatisticsEntry ytdRunTotals) {
		this.ytdRunTotals = ytdRunTotals;
	}

	public void setYtdSwimTotals(final StatisticsEntry ytdSwimTotals) {
		this.ytdSwimTotals = ytdSwimTotals;
	}

}
