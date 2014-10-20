package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import model.Challenge;

public class ChallengeDAO {
	
	public Challenge getChallenge(int challengeIndex, Date currentDate)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate FROM challenges where (? between startDate and endDate) or ";
				sql += "(endDate = (select max(endDate) from challenges where challengeIndex=?))";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setDate(1,currentDate);
				preparedStatement.setInt(2,challengeIndex);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					challenge = new Challenge();
					challenge.setId(rs.getInt("id"));
					challenge.setChallengeIndex(rs.getInt("challengeIndex"));
					challenge.setName(rs.getString("name"));
					challenge.setSeason(rs.getString("season"));
					challenge.setStartDate(rs.getDate("startDate"));
					challenge.setEndDate(rs.getDate("endDate"));
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
		return challenge;
	}
}
