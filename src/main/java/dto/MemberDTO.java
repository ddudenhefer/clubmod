package dto;

public class MemberDTO {
	
	private Long id;
	private Long athleteId;
	private String accessToken;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(Long athleteId) {
		this.athleteId = athleteId;
	}
}
