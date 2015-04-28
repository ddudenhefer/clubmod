package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Challenge;
import model.Member;
import model.MemberActivityTotal;
import model.MemberYTDTotal;

import com.google.gson.Gson;

import dao.ChallengeDAO;
import dao.MemberActivityTotalsDAO;
import dao.MemberDAO;
import dao.MemberYTDTotalsDAO;
import dao.PointsDAO;

@Path("/club")
public class ClubSvc {
	
	@GET
	@Path("/members")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembers()  {
		
		List<Member> members = new ArrayList<Member>();
		MemberDAO memberDAO = new MemberDAO();
		try {
			members = memberDAO.getAllMembers();
			for (Member member : members) {
				if (member != null && member.getAccessToken() != null) {

					MemberYTDTotalsDAO memberYTDTotalsDB = new MemberYTDTotalsDAO();
				    MemberYTDTotal memberYTDTotal = memberYTDTotalsDB.getMemberData(member.getId());
				    float milesF = memberYTDTotal != null ? memberYTDTotal.getMilesYTD() : 0;
				    long elevationL = memberYTDTotal != null ? memberYTDTotal.getElevationYTD() : 0;
				    
				    ChallengeDAO challengeDAO = new ChallengeDAO();
					List<Challenge> challengeWins = challengeDAO.getChallengesByMemberId(member.getId());			
					member.setChallengeWins(challengeWins);
					
					MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(); 
					MemberActivityTotal memberActivityTotal = memberActivityTotalsDAO.getMemberData(member.getId());
					if (memberActivityTotal != null) {
						member.setFantasyEntry(memberActivityTotal.getFantasyEntry());
						member.setFantasyFirst(memberActivityTotal.getFantasyFirst());
						member.setFantasySecond(memberActivityTotal.getFantasySecond());
						member.setFantasyThird(memberActivityTotal.getFantasyThird());
						member.setGroupRides(memberActivityTotal.getGroupRide());
						member.setEventRides(memberActivityTotal.getEventRide());
						member.setHomePurchases(memberActivityTotal.getHomePurchase());
						member.setHomeReferrals(memberActivityTotal.getHomeReferral());
					}
				    
				    PointsDAO pointsDAO = new PointsDAO();
				    member.setMemberPoints(pointsDAO.getMemberPoints(member.getId(), milesF, elevationL, challengeWins, memberActivityTotal));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(members, Member.Comparators.POINTS);

		Gson gson = new Gson();
		String ret = "";
		if (members != null) {
			ret = gson.toJson(members);
		}		
		return ret;
	}
	
	@GET
	@Path("/membersByName")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembersByName()  {
		
		List<Member> members = new ArrayList<Member>();
		MemberDAO memberDAO = new MemberDAO();
		try {
			members = memberDAO.getAllMembers();
			for (Member member : members) {
				if (member != null && member.getAccessToken() != null) {

					MemberYTDTotalsDAO memberYTDTotalsDB = new MemberYTDTotalsDAO();
				    MemberYTDTotal memberYTDTotal = memberYTDTotalsDB.getMemberData(member.getId());
				    float milesF = memberYTDTotal != null ? memberYTDTotal.getMilesYTD() : 0;
				    long elevationL = memberYTDTotal != null ? memberYTDTotal.getElevationYTD() : 0;
				    
				    ChallengeDAO challengeDAO = new ChallengeDAO();
					List<Challenge> challengeWins = challengeDAO.getChallengesByMemberId(member.getId());			
					member.setChallengeWins(challengeWins);
					
					MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(); 
					MemberActivityTotal memberActivityTotal = memberActivityTotalsDAO.getMemberData(member.getId());
					if (memberActivityTotal != null) {
						member.setFantasyEntry(memberActivityTotal.getFantasyEntry());
						member.setFantasyFirst(memberActivityTotal.getFantasyFirst());
						member.setFantasySecond(memberActivityTotal.getFantasySecond());
						member.setFantasyThird(memberActivityTotal.getFantasyThird());
						member.setGroupRides(memberActivityTotal.getGroupRide());
						member.setEventRides(memberActivityTotal.getEventRide());
						member.setHomePurchases(memberActivityTotal.getHomePurchase());
						member.setHomeReferrals(memberActivityTotal.getHomeReferral());
					}
				    
				    PointsDAO pointsDAO = new PointsDAO();
				    member.setMemberPoints(pointsDAO.getMemberPoints(member.getId(), milesF, elevationL, challengeWins, memberActivityTotal));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(members, Member.Comparators.NAME);

		Gson gson = new Gson();
		String ret = "";
		if (members != null) {
			ret = gson.toJson(members);
		}		
		return ret;
	}	
	

	@GET
	@Path("/totals")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubTotals()  {
		
		MemberYTDTotal memberYTDTotal = new MemberYTDTotal();
		
		try {
			MemberYTDTotalsDAO memberYTDTotalsDB = new MemberYTDTotalsDAO();
			memberYTDTotal = memberYTDTotalsDB.getTotals();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String ret = "";
		if (memberYTDTotal != null) {
			ret = gson.toJson(memberYTDTotal);
		}		
		return ret;
	}
	
} 