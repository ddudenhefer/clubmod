package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Member;

public class MemberDAO {
	
	public Member getMemberById(int memberId)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Member member = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken, firstName, lastName FROM members where id=?");
				preparedStatement.setLong(1,memberId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
					member.setFirstName(rs.getString("firstName"));
					member.setLastName(rs.getString("lastName"));
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
	
	public Member getMemberByAthleteId(int athleteId)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Member member = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken, firstName, lastName FROM members where athleteId=?");
				preparedStatement.setLong(1,athleteId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
					member.setFirstName(rs.getString("firstName"));
					member.setLastName(rs.getString("lastName"));
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

	public List<Member> getAllMembers()throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Member> members = new ArrayList<Member>();
		Member member = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken, firstName, lastName FROM members");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
					member.setFirstName(rs.getString("firstName"));
					member.setLastName(rs.getString("lastName"));
					members.add(member);
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
		
		return members;
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
				if (memberDB != null) {	// update
					String sql = "update members set accessToken=?, firstName=?, lastName=? where athleteId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, member.getAccessToken());
					preparedStatement.setString(2, member.getFirstName());
					preparedStatement.setString(3, member.getLastName());
					preparedStatement.setLong(4, member.getAthleteId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					String sql = "insert into members (athleteId, accessToken, firstName, lastName) values (?,?,?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, member.getAthleteId());
					preparedStatement.setString(2, member.getAccessToken());
					preparedStatement.setString(3, member.getFirstName());
					preparedStatement.setString(4, member.getLastName());
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
