package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.MemberActivityTotal;

public class MemberActivityTotalsDAO {
	
	public MemberActivityTotal getMemberData(int memberId)throws Exception {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		MemberActivityTotal memberActivityTotal = new MemberActivityTotal();

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT memberId, fantasy_entry, fantasy_first, fantasy_second, fantasy_third, group_ride, event_ride FROM member_activity_totals where memberId=?");
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
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (connection != null)
				connection.close();
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
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				MemberActivityTotal memberActivityTotalDB = getMemberData(memberActivityTotal.getMemberId());
				if (memberActivityTotalDB != null) {	// update
					System.out.println("found activity");
					String sql = "update member_activity_totals set fantasy_entry=?, fantasy_first=?, fantasy_second=?, fantasy_third=?, group_ride=?, event_ride=? where memberId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, memberActivityTotal.getFantasyEntry());
					preparedStatement.setInt(2, memberActivityTotal.getFantasyFirst());
					preparedStatement.setInt(3, memberActivityTotal.getFantasySecond());
					preparedStatement.setInt(4, memberActivityTotal.getFantasyThird());
					preparedStatement.setInt(5, memberActivityTotal.getGroupRide());
					preparedStatement.setInt(6, memberActivityTotal.getEventRide());
					preparedStatement.setLong(7, memberActivityTotal.getMemberId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					System.out.println("cannot find activity");
					String sql = "insert into member_activity_totals (memberId, fantasy_entry, fantasy_first, fantasy_second, fantasy_third, group_ride, event_ride ) values (?,?,?,?,?,?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, memberActivityTotal.getMemberId());
					preparedStatement.setInt(2, memberActivityTotal.getFantasyEntry());
					preparedStatement.setInt(3, memberActivityTotal.getFantasyFirst());
					preparedStatement.setInt(4, memberActivityTotal.getFantasySecond());
					preparedStatement.setInt(5, memberActivityTotal.getFantasyThird());
					preparedStatement.setInt(6, memberActivityTotal.getGroupRide());
					preparedStatement.setInt(7, memberActivityTotal.getEventRide());
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
			if (connection != null)
			connection.close();
		}
		return false;
	}
	
	
	public List<MemberActivityTotal> getAllMemberActivityTotals()throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<MemberActivityTotal> memberActivityTotals = new ArrayList<MemberActivityTotal>();
		MemberActivityTotal memberActivityTotal = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				String sql = "SELECT memberId, fantasy_entry, fantasy_first, fantasy_second, fantasy_third, group_ride, event_ride FROM member_activity_totals";
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
					memberActivityTotals.add(memberActivityTotal);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (connection != null)
				connection.close();
		}
		return memberActivityTotals;
	}
	
	
	public boolean deleteMemberActivityTotal(MemberActivityTotal memberActivityTotal)throws Exception {
		
		if (memberActivityTotal == null || memberActivityTotal.getMemberId() == 0)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = Database.getConnection();
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
			if (connection != null)
				connection.close();
		}
		
		return false;
	}
	
	
}
