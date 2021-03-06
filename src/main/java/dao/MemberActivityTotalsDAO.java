package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.MemberActivityTotal;

public class MemberActivityTotalsDAO {
	
	HttpSession session = null;
	Connection connection = null;

	public MemberActivityTotalsDAO(HttpSession session) {
		this.session = session;
	}

	public MemberActivityTotalsDAO(Connection connection) {
		this.connection = connection;
	}

	public MemberActivityTotal getMemberData(int memberId)throws Exception {
		PreparedStatement preparedStatement = null;
		MemberActivityTotal memberActivityTotal = new MemberActivityTotal();

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT memberId, fantasy_entry, fantasy_first, fantasy_second, fantasy_third, group_ride, event_ride, home_purchase, home_referral, points_redeemed FROM member_activity_totals where memberId=?");
				preparedStatement.setLong(1,memberId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					memberActivityTotal = new MemberActivityTotal();
					memberActivityTotal.setMemberId(rs.getInt("memberId"));
					memberActivityTotal.setFantasyEntry(rs.getInt("fantasy_entry"));
					memberActivityTotal.setFantasyFirst(rs.getInt("fantasy_first"));
					memberActivityTotal.setFantasySecond(rs.getInt("fantasy_second"));
					memberActivityTotal.setFantasyThird(rs.getInt("fantasy_third"));
					memberActivityTotal.setGroupRide(rs.getInt("group_ride"));
					memberActivityTotal.setEventRide(rs.getInt("event_ride"));
					memberActivityTotal.setHomePurchase(rs.getInt("home_purchase"));
					memberActivityTotal.setHomeReferral(rs.getInt("home_referral"));
					memberActivityTotal.setPointsRedeemed(rs.getInt("points_redeemed"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return memberActivityTotal;
	}
	
	
	public boolean updateMemberActivityTotals(List<MemberActivityTotal> memberActivityTotals)throws Exception {
		
		if (memberActivityTotals == null || memberActivityTotals.size() == 0)
			return false;
		
		try {
			for (MemberActivityTotal memberActivityTotal : memberActivityTotals) {
				saveMemberActivityTotals(memberActivityTotal);
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}	
	

	public boolean saveMemberActivityTotals(MemberActivityTotal memberActivityTotal)throws Exception {
	
		System.out.println("memberActivityTotal: " + memberActivityTotal);
		if (memberActivityTotal == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				MemberActivityTotal memberActivityTotalDB = getMemberData(memberActivityTotal.getMemberId());
				if (memberActivityTotalDB != null && memberActivityTotalDB.getMemberId() > 0) {	// update
					String sql = "update member_activity_totals set fantasy_entry=?, fantasy_first=?, fantasy_second=?, fantasy_third=?, group_ride=?, event_ride=?, home_purchase=?, home_referral=?, points_redeemed=? where memberId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, memberActivityTotal.getFantasyEntry());
					preparedStatement.setInt(2, memberActivityTotal.getFantasyFirst());
					preparedStatement.setInt(3, memberActivityTotal.getFantasySecond());
					preparedStatement.setInt(4, memberActivityTotal.getFantasyThird());
					preparedStatement.setInt(5, memberActivityTotal.getGroupRide());
					preparedStatement.setInt(6, memberActivityTotal.getEventRide());
					preparedStatement.setInt(7, memberActivityTotal.getHomePurchase());
					preparedStatement.setInt(8, memberActivityTotal.getHomeReferral());
					preparedStatement.setLong(9, memberActivityTotal.getPointsRedeemed());
					preparedStatement.setLong(10, memberActivityTotal.getMemberId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					String sql = "insert into member_activity_totals (memberId, fantasy_entry, fantasy_first, fantasy_second, fantasy_third, group_ride, event_ride, home_purchase, home_referral, points_redeemed) values (?,?,?,?,?,?,?,?,?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, memberActivityTotal.getMemberId());
					preparedStatement.setInt(2, memberActivityTotal.getFantasyEntry());
					preparedStatement.setInt(3, memberActivityTotal.getFantasyFirst());
					preparedStatement.setInt(4, memberActivityTotal.getFantasySecond());
					preparedStatement.setInt(5, memberActivityTotal.getFantasyThird());
					preparedStatement.setInt(6, memberActivityTotal.getGroupRide());
					preparedStatement.setInt(7, memberActivityTotal.getEventRide());
					preparedStatement.setInt(8, memberActivityTotal.getHomePurchase());
					preparedStatement.setInt(9, memberActivityTotal.getHomeReferral());
					preparedStatement.setInt(10, memberActivityTotal.getPointsRedeemed());
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return false;
	}
	
	
	public List<MemberActivityTotal> getAllMemberActivityTotals()throws Exception {
		PreparedStatement preparedStatement = null;
		List<MemberActivityTotal> memberActivityTotals = new ArrayList<MemberActivityTotal>();
		MemberActivityTotal memberActivityTotal = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT memberId, fantasy_entry, fantasy_first, fantasy_second, fantasy_third, group_ride, event_ride, home_purchase, home_referral, points_redeemed FROM member_activity_totals";
				preparedStatement = connection.prepareStatement(sql);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					memberActivityTotal = new MemberActivityTotal();
					memberActivityTotal.setMemberId(rs.getInt("memberId"));
					memberActivityTotal.setFantasyEntry(rs.getInt("fantasy_entry"));
					memberActivityTotal.setFantasyFirst(rs.getInt("fantasy_first"));
					memberActivityTotal.setFantasySecond(rs.getInt("fantasy_second"));
					memberActivityTotal.setFantasyThird(rs.getInt("fantasy_third"));
					memberActivityTotal.setGroupRide(rs.getInt("group_ride"));
					memberActivityTotal.setEventRide(rs.getInt("event_ride"));
					memberActivityTotal.setHomePurchase(rs.getInt("home_purchase"));
					memberActivityTotal.setHomeReferral(rs.getInt("home_referral"));
					memberActivityTotal.setPointsRedeemed(rs.getInt("points_redeemed"));
					memberActivityTotals.add(memberActivityTotal);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return memberActivityTotals;
	}
	
	
	public boolean deleteMemberActivityTotal(MemberActivityTotal memberActivityTotal)throws Exception {
		
		if (memberActivityTotal == null || memberActivityTotal.getMemberId() == 0)
			return false;
		
		PreparedStatement preparedStatement = null;
		
		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				MemberActivityTotal memberActivityTotalDB = getMemberData(memberActivityTotal.getMemberId());
				if (memberActivityTotalDB != null) {
					String sql = "delete from member_activity_totals where memberId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, memberActivityTotal.getMemberId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		
		return false;
	}
	
}
