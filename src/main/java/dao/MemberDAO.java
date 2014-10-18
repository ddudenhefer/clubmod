package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Member;

public class MemberDAO {
	
	public Member getMemberByAthleteId(int athleteId)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Member member = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken FROM members where athleteId=?");
				preparedStatement.setLong(1,athleteId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
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
		return member;
	}
	
	public boolean saveMember(Member member)throws Exception {
	
		if (member == null)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				Member memberDB = getMemberByAthleteId(member.getAthleteId());
				System.out.println("memberDB: " + memberDB);
				if (memberDB != null) {	// update
					String sql = "update members set accessToken=? where athleteId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, member.getAthleteId());
					preparedStatement.setString(2, member.getAccessToken());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					String sql = "insert into members (athleteId, accessToken) values (?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, member.getAthleteId());
					preparedStatement.setString(2, member.getAccessToken());
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

	public boolean deleteMember(Member member)throws Exception {
		
		if (member == null || member.getId() == 0)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = Database.getConnection();
			if (connection != null) {
				Member memberDB = getMemberByAthleteId(member.getAthleteId());
				if (memberDB != null) {
					String sql = "delete from members where athleteId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, member.getAthleteId());
		
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
