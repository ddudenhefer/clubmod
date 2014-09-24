package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Member;

public class MemberDAO {
	
	Connection connection = Database.getConnection();
	
	public Member getMemberByAthleteId(long athleteId)throws Exception {
		Member member = null;
		try {
				PreparedStatement ps = connection.prepareStatement("SELECT id, athleteId, accessToken FROM members where athleteId=?");
				ps.setLong(1,athleteId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getLong("id"));
					member.setAthleteId(rs.getLong("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
				}
		
		} catch (Exception e) {
			throw e;
		}
		return member;
	}
	
	public boolean saveMember(Member member)throws Exception {
	
		if (member == null)
			return false;
		
		Member memberDB = getMemberByAthleteId(member.getAthleteId());
		if (memberDB != null) {	// update
			String sql = "update members set accessToken=? where athleteId=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, member.getAthleteId());
			preparedStatement.setString(2, member.getAccessToken());

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0)
				return true;
		}
		else {	// insert
			String sql = "insert into members (athleteId, accessToken) values (?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, member.getAthleteId());
			preparedStatement.setString(2, member.getAccessToken());
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0)
				return true;
		}
		
		return false;
	}

}
