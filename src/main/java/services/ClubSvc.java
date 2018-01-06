package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Challenge;
import model.Member;
import model.MemberActivityTotal;
import model.MemberPoints;
import model.MemberYTDTotal;
import utils.Constants;

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
	public String getClubMembers(@Context HttpServletRequest request)  {
		HttpSession session = request.getSession();
		List<Member> members = new ArrayList<Member>();

		MemberDAO memberDAO = new MemberDAO(session);
		try {
			members = memberDAO.getAllMembers();
			for (Member member : members) {
				if (member != null && member.getAccessToken() != null) {

					MemberYTDTotalsDAO memberYTDTotalsDB = new MemberYTDTotalsDAO(session);
				    MemberYTDTotal memberYTDTotal = memberYTDTotalsDB.getMemberData(member.getId());
				    float milesF = memberYTDTotal != null ? memberYTDTotal.getMilesYTD() : 0;
				    long elevationL = memberYTDTotal != null ? memberYTDTotal.getElevationYTD() : 0;
				    
				    ChallengeDAO challengeDAO = new ChallengeDAO(session);
					List<Challenge> challengeWins = challengeDAO.getChallengesByMemberId(member.getId());			
					member.setChallengeWins(challengeWins);
					
					MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(session); 
					MemberActivityTotal memberActivityTotal = memberActivityTotalsDAO.getMemberData(member.getId());
					if (memberActivityTotal != null) {
						member.setFantasyEntry(memberActivityTotal.getFantasyEntry());
						member.setFantasyFirst(memberActivityTotal.getFantasyFirst());
						member.setFantasySecond(memberActivityTotal.getFantasySecond());
						member.setFantasyThird(memberActivityTotal.getFantasyThird());
						member.setGroupRides(memberActivityTotal.getGroupRide());
						member.setEventRides(memberActivityTotal.getEventRide());
						member.setHomeReferrals(memberActivityTotal.getHomeReferral());
						member.setPointsRedeemed(memberActivityTotal.getPointsRedeemed());
					}
				    
				    PointsDAO pointsDAO = new PointsDAO(session);
				    member.setMemberPoints(pointsDAO.getMemberPoints(member.getId(), milesF, elevationL, challengeWins, memberActivityTotal));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
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
	@Path("/membersTop3")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembersTop3()  {
		
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
		
		List<Member> membersTop3 = new ArrayList<Member>();
		membersTop3.add(members.get(0));
		membersTop3.add(members.get(1));
		membersTop3.add(members.get(2));

		Gson gson = new Gson();
		String ret = "";
		if (membersTop3 != null) {
			ret = gson.toJson(membersTop3);
		}		
		return ret;
	}	
	
	@GET
	@Path("/memberTotalsByName")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMemberTotalsByName()  {
		
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
					MemberPoints mp = new MemberPoints();
					mp.setElevationYTD(elevationL);
					mp.setMilesYTD(milesF);
				    member.setMemberPoints(mp);
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
	@Path("/membersByName")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubMembersByName()  {
		
		List<Member> members = new ArrayList<Member>();
		MemberDAO memberDAO = new MemberDAO();
		try {
			members = memberDAO.getAllMembers();
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
	public String getClubTotals(@Context HttpServletRequest request)  {
		HttpSession session = request.getSession();
		
		MemberYTDTotal memberYTDTotal = new MemberYTDTotal();
		
		try {
			MemberYTDTotalsDAO memberYTDTotalsDB = new MemberYTDTotalsDAO(session);
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