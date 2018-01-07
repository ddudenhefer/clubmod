package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Challenge;
import model.Member;

public class ChallengeDAO {
	
	HttpSession session = null;
	Connection connection = null;
	
	public ChallengeDAO(Connection connection) {
		this.connection = connection;
	}
	
	public ChallengeDAO (HttpSession session) {
		this.session = session;
	}
	
	public boolean saveChallenge(Challenge challenge)throws Exception {
		
		if (challenge == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "update challenges set memberId=?, memberId2=?, memberId3=?, memberId4=?, memberId5=?, memberId6=?, memberId7=?, memberId8=?, memberId9=?, memberId10=? where id=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, challenge.getMemberId());
				preparedStatement.setInt(2, challenge.getMemberId2());
				preparedStatement.setInt(3, challenge.getMemberId3());
				preparedStatement.setInt(4, challenge.getMemberId4());
				preparedStatement.setInt(5, challenge.getMemberId5());
				preparedStatement.setInt(6, challenge.getMemberId6());
				preparedStatement.setInt(7, challenge.getMemberId7());
				preparedStatement.setInt(8, challenge.getMemberId8());
				preparedStatement.setInt(9, challenge.getMemberId9());
				preparedStatement.setInt(10, challenge.getMemberId10());
				preparedStatement.setInt(11, challenge.getId());
		
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
		}
		return false;
	}
	
	
	public Challenge getChallenge(int challengeIndex, String season)throws Exception {
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId, memberId2, memberId3, memberId4, memberId5, memberId6, memberId7, memberId8, memberId9, memberId10 FROM challenges where challengeIndex=? and season like ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1,challengeIndex);
				preparedStatement.setString(2,"%"+season+"%");
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
					challenge.setMemberId2(rs.getInt("memberId2"));
					challenge.setMemberId3(rs.getInt("memberId3"));
					challenge.setMemberId4(rs.getInt("memberId4"));
					challenge.setMemberId5(rs.getInt("memberId5"));
					challenge.setMemberId6(rs.getInt("memberId6"));
					challenge.setMemberId7(rs.getInt("memberId7"));
					challenge.setMemberId8(rs.getInt("memberId8"));
					challenge.setMemberId9(rs.getInt("memberId9"));
					challenge.setMemberId10(rs.getInt("memberId10"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return challenge;
	}
	
	
	public Challenge getChallengeByDate(Date currentDate)throws Exception {
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId, memberId2, memberId3, memberId4, memberId5, memberId6, memberId7, memberId8, memberId9, memberId10 FROM challenges where (? between startDate and endDate) or ";
				sql += "(? < startDate and startDate = (select min(startDate) from challenges)) or (? > endDate and endDate = (select max(endDate) from challenges))";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setDate(1,currentDate);
				preparedStatement.setDate(2,currentDate);
				preparedStatement.setDate(3,currentDate);
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
					challenge.setMemberId2(rs.getInt("memberId2"));
					challenge.setMemberId3(rs.getInt("memberId3"));
					challenge.setMemberId4(rs.getInt("memberId4"));
					challenge.setMemberId5(rs.getInt("memberId5"));
					challenge.setMemberId6(rs.getInt("memberId6"));
					challenge.setMemberId7(rs.getInt("memberId7"));
					challenge.setMemberId8(rs.getInt("memberId8"));
					challenge.setMemberId9(rs.getInt("memberId9"));
					challenge.setMemberId10(rs.getInt("memberId10"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return challenge;
	}
	
	public Challenge getChallengeById(int id)throws Exception {
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId, memberId2, memberId3, memberId4, memberId5, memberId6, memberId7, memberId8, memberId9, memberId10 FROM challenges where id=?";
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
					challenge.setMemberId2(rs.getInt("memberId2"));
					challenge.setMemberId3(rs.getInt("memberId3"));
					challenge.setMemberId4(rs.getInt("memberId4"));
					challenge.setMemberId5(rs.getInt("memberId5"));
					challenge.setMemberId6(rs.getInt("memberId6"));
					challenge.setMemberId7(rs.getInt("memberId7"));
					challenge.setMemberId8(rs.getInt("memberId8"));
					challenge.setMemberId9(rs.getInt("memberId9"));
					challenge.setMemberId10(rs.getInt("memberId10"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return challenge;
	}

	
	public Challenge getChallengeByDates(Date startDate, Date endDate)throws Exception {
		PreparedStatement preparedStatement = null;
		Challenge challenge = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId, memberId2, memberId3, memberId4, memberId5, memberId6, memberId7, memberId8, memberId9, memberId10 FROM challenges where startDate=? and endDate=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setDate(1,startDate);
				preparedStatement.setDate(2,endDate);
				
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
					challenge.setMemberId2(rs.getInt("memberId2"));
					challenge.setMemberId3(rs.getInt("memberId3"));
					challenge.setMemberId4(rs.getInt("memberId4"));
					challenge.setMemberId5(rs.getInt("memberId5"));
					challenge.setMemberId6(rs.getInt("memberId6"));
					challenge.setMemberId7(rs.getInt("memberId7"));
					challenge.setMemberId8(rs.getInt("memberId8"));
					challenge.setMemberId9(rs.getInt("memberId9"));
					challenge.setMemberId10(rs.getInt("memberId10"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return challenge;
	}

	
	public List<Challenge> getAllChallenges()throws Exception {
		PreparedStatement preparedStatement = null;
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId, memberId2, memberId3, memberId4, memberId5, memberId6, memberId7, memberId8, memberId9, memberId10 FROM challenges order by id");
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
					challenge.setMemberId2(rs.getInt("memberId2"));
					challenge.setMemberId3(rs.getInt("memberId3"));
					challenge.setMemberId4(rs.getInt("memberId4"));
					challenge.setMemberId5(rs.getInt("memberId5"));
					challenge.setMemberId6(rs.getInt("memberId6"));
					challenge.setMemberId7(rs.getInt("memberId7"));
					challenge.setMemberId8(rs.getInt("memberId8"));
					challenge.setMemberId9(rs.getInt("memberId9"));
					challenge.setMemberId10(rs.getInt("memberId10"));
					
					String fullname = "";
					if (challenge.getMemberId() > 0) {
						MemberDAO memberDB = null;
						if (connection == null)
							memberDB = new MemberDAO(session);
						else
							memberDB = new MemberDAO(connection);
							
						Member member = memberDB.getMemberById(challenge.getMemberId());
						if (member != null)
							fullname = member.getFirstName() + " " + member.getLastName();
					}
					challenge.setMemberFullName(fullname);
					challenges.add(challenge);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		
		return challenges;
	}
	

	public List<Challenge> getChallengesByMemberId(int memberId)throws Exception {
		PreparedStatement preparedStatement = null;
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				
				String sql = "SELECT id, challengeIndex, name, season, startDate, endDate, label, service, memberId, memberId2, memberId3, memberId4, memberId5, memberId6, memberId7, memberId8, memberId9, memberId10 FROM challenges where ";
				sql += "memberId=? or memberId2=? or memberId3=? or memberId4=? or memberId5=? or memberId6=? or memberId7=? or memberId8=? or memberId9=? or memberId10=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1,memberId);
				preparedStatement.setInt(2,memberId);
				preparedStatement.setInt(3,memberId);
				preparedStatement.setInt(4,memberId);
				preparedStatement.setInt(5,memberId);
				preparedStatement.setInt(6,memberId);
				preparedStatement.setInt(7,memberId);
				preparedStatement.setInt(8,memberId);
				preparedStatement.setInt(9,memberId);
				preparedStatement.setInt(10,memberId);
				
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
					challenge.setMemberId2(rs.getInt("memberId2"));
					challenge.setMemberId3(rs.getInt("memberId3"));
					challenge.setMemberId4(rs.getInt("memberId4"));
					challenge.setMemberId5(rs.getInt("memberId5"));
					challenge.setMemberId6(rs.getInt("memberId6"));
					challenge.setMemberId7(rs.getInt("memberId7"));
					challenge.setMemberId8(rs.getInt("memberId8"));
					challenge.setMemberId9(rs.getInt("memberId9"));
					challenge.setMemberId10(rs.getInt("memberId10"));
					challenges.add(challenge);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		
		return challenges;
	}
	

	public boolean hasMemberWonChallengeOrSeason(int memberId, int challengeId, String challengeName, String season)throws Exception {
		PreparedStatement preparedStatement = null;
		boolean retVal = false;

/*		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT * FROM challenges where memberId=? and id<>? and (name=? or season=?)");
				preparedStatement.setInt(1,memberId);
				preparedStatement.setInt(2,challengeId);
				preparedStatement.setString(3,challengeName);
				preparedStatement.setString(4,season);
				
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					retVal = true;
					System.out.println("MemberId: " + memberId + " won: " + challengeName + " or " + season);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
*/		
		return retVal;
	}
	
	
	public boolean updateChallenge(Challenge challenge)throws Exception {
		
		if (challenge == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				Challenge challengeDB = getChallengeById(challenge.getId());
				if (challengeDB != null) {	// update
					String sql = "update challenges set startDate=?, endDate=? where id=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setDate(1, new java.sql.Date(challenge.getStartDate().getTime()));
					preparedStatement.setDate(2, new java.sql.Date(challenge.getEndDate().getTime()));
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
