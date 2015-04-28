package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.MemberYTDTotal;

public class MemberYTDTotalsDAO {
	
	public MemberYTDTotal getMemberData(int memberId)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		MemberYTDTotal memberYTDTotal = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT memberId, milesYTD, elevationYTD, movingTimeYTD, ridesYTD FROM member_ytd_totals where memberId=?");
				preparedStatement.setLong(1,memberId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					memberYTDTotal = new MemberYTDTotal();
					memberYTDTotal.setMemberId(rs.getInt("memberId"));
					memberYTDTotal.setMilesYTD(rs.getFloat("milesYTD"));
					memberYTDTotal.setElevationYTD(rs.getInt("elevationYTD"));
					memberYTDTotal.setMovingTimeYTD(rs.getInt("movingTimeYTD"));
					memberYTDTotal.setRidesYTD(rs.getInt("ridesYTD"));
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
		return memberYTDTotal;
	}
	

	public boolean saveMemberYTDTotals(MemberYTDTotal memberYTDTotal)throws Exception {
	
		if (memberYTDTotal == null)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				MemberYTDTotal memberYTDTotalDB = getMemberData(memberYTDTotal.getMemberId());
				if (memberYTDTotalDB != null) {	// update
					String sql = "update member_ytd_totals set milesYTD=?, elevationYTD=?, movingTimeYTD=?, ridesYTD=? where memberId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setFloat(1, memberYTDTotal.getMilesYTD());
					preparedStatement.setLong(2, memberYTDTotal.getElevationYTD());
					preparedStatement.setLong(3, memberYTDTotal.getMovingTimeYTD());
					preparedStatement.setLong(4, memberYTDTotal.getRidesYTD());
					preparedStatement.setLong(5, memberYTDTotal.getMemberId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					String sql = "insert into member_ytd_totals (memberId, milesYTD, elevationYTD, movingTimeYTD, ridesYTD ) values (?,?,?,?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, memberYTDTotal.getMemberId());
					preparedStatement.setFloat(2, memberYTDTotal.getMilesYTD());
					preparedStatement.setLong(3, memberYTDTotal.getElevationYTD());
					preparedStatement.setLong(4, memberYTDTotal.getMovingTimeYTD());
					preparedStatement.setLong(5, memberYTDTotal.getRidesYTD());
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
	
	public MemberYTDTotal getTotals()throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		MemberYTDTotal memberYTDTotal = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT sum(milesYTD) as miles, sum(elevationYTD) as elevation, sum(movingTimeYTD) as time, sum(ridesYTD) as rides FROM member_ytd_totals");
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					memberYTDTotal = new MemberYTDTotal();
					memberYTDTotal.setMilesYTD(rs.getFloat("miles"));
					memberYTDTotal.setElevationYTD(rs.getInt("elevation"));
					memberYTDTotal.setMovingTimeYTD(rs.getInt("time"));
					memberYTDTotal.setRidesYTD(rs.getInt("rides"));
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
		return memberYTDTotal;
	}
	
}
