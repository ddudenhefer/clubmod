package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.util.ArrayList;
import java.util.List;

import model.Challenge;

public class ChallengeDAO {
	
	
	public boolean saveChallenge(Challenge challenge)throws Exception {
		
		if (challenge == null)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				String sql = "update challenges set memberId=? where id=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, challenge.getMemberId());
				preparedStatement.setInt(2, challenge.getId());
		
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0)
					return true;
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
	
	
	public Challenge getChallenge(int challengeIndex, Date currentDate)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId FROM challenges where (? between startDate and endDate) or ";
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
					challenge.setLabel(rs.getString("label"));
					challenge.setService(rs.getString("service"));
					challenge.setMemberId(rs.getInt("memberId"));
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
	
	
	public Challenge getChallengeById(int id)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId FROM challenges where id=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1,id);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					challenge = new Challenge();
					challenge.setId(rs.getInt("id"));
					challenge.setChallengeIndex(rs.getInt("challengeIndex"));
					challenge.setName(rs.getString("name"));
					challenge.setSeason(rs.getString("season"));
					challenge.setStartDate(rs.getDate("startDate"));
					challenge.setEndDate(rs.getDate("endDate"));
					challenge.setLabel(rs.getString("label"));
					challenge.setService(rs.getString("service"));
					challenge.setMemberId(rs.getInt("memberId"));
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

	
	public List<Challenge> getAllChallenges()throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId FROM challenges order by startDate");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					challenge = new Challenge();
					challenge.setId(rs.getInt("id"));
					challenge.setChallengeIndex(rs.getInt("challengeIndex"));
					challenge.setName(rs.getString("name"));
					challenge.setSeason(rs.getString("season"));
					challenge.setStartDate(rs.getDate("startDate"));
					challenge.setEndDate(rs.getDate("endDate"));
					challenge.setLabel(rs.getString("label"));
					challenge.setService(rs.getString("service"));
					challenge.setMemberId(rs.getInt("memberId"));
					challenges.add(challenge);
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
		
		return challenges;
	}
	

	public List<Challenge> getChallengesByMemberId(int memberId)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId FROM challenges where memberId=?");
				preparedStatement.setInt(1,memberId);
				
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					challenge = new Challenge();
					challenge.setId(rs.getInt("id"));
					challenge.setChallengeIndex(rs.getInt("challengeIndex"));
					challenge.setName(rs.getString("name"));
					challenge.setSeason(rs.getString("season"));
					challenge.setStartDate(rs.getDate("startDate"));
					challenge.setEndDate(rs.getDate("endDate"));
					challenge.setLabel(rs.getString("label"));
					challenge.setService(rs.getString("service"));
					challenge.setMemberId(rs.getInt("memberId"));
					challenges.add(challenge);
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
		
		return challenges;
	}
	
	public boolean updateChallenge(Challenge challenge)throws Exception {
		
		if (challenge == null)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				Challenge challengeDB = getChallengeById(challenge.getId());
				if (challengeDB != null) {	// update
					String sql = "update challenges set startDate=?, endDate=? where id=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setDate(1, (Date) challenge.getStartDate());
					preparedStatement.setDate(2, (Date) challenge.getEndDate());
					preparedStatement.setLong(3, challenge.getId());
		
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


	public boolean updateChallenges(List<Challenge> challenges)throws Exception {
		
		if (challenges == null || challenges.size() == 0)
			return false;
		
		try {
			for (Challenge challenge : challenges) {
				updateChallenge(challenge);
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	
}
