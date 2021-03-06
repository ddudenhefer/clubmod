package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import model.Member;
import model.MemberActivityTotal;

public class MemberDAO {
	
	HttpSession session = null;
	Connection connection = null;
	
	public MemberDAO(HttpSession session) {
		this.session = session;
	}
	
	public MemberDAO(Connection connection) {
		this.connection = connection;
	}

	public Member getMemberById(int memberId)throws Exception {
		PreparedStatement preparedStatement = null;
		Member member = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken, firstName, lastName, pictureURL, city, state, email FROM members where id=?");
				preparedStatement.setLong(1,memberId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
					member.setFirstName(rs.getString("firstName"));
					member.setLastName(rs.getString("lastName"));
					member.setPictureURL(rs.getString("pictureURL"));
					member.setCity(rs.getString("city"));
					member.setState(rs.getString("state"));
					member.setEmail(rs.getString("email"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return member;
	}
	
	public Member getMemberByAthleteId(int athleteId)throws Exception {
		PreparedStatement preparedStatement = null;
		Member member = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken, firstName, lastName, pictureURL, city, state, email FROM members where athleteId=?");
				preparedStatement.setLong(1,athleteId);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
					member.setFirstName(rs.getString("firstName"));
					member.setLastName(rs.getString("lastName"));
					member.setPictureURL(rs.getString("pictureURL"));
					member.setCity(rs.getString("city"));
					member.setState(rs.getString("state"));
					member.setEmail(rs.getString("email"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return member;
	}

	public List<Member> getAllMembers()throws Exception {
		PreparedStatement preparedStatement = null;
		List<Member> members = new ArrayList<Member>();
		Member member = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, athleteId, accessToken, firstName, lastName, pictureURL, city, state, email, waiver FROM members");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("id"));
					member.setAthleteId(rs.getInt("athleteId"));
					member.setAccessToken(rs.getString("accessToken"));
					member.setFirstName(rs.getString("firstName"));
					member.setLastName(rs.getString("lastName"));
					member.setPictureURL(rs.getString("pictureURL"));
					member.setCity(rs.getString("city"));
					member.setState(rs.getString("state"));
					member.setEmail(rs.getString("email"));
					member.setWaiver(rs.getString("waiver"));
					members.add(member);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		
		return members;
	}

	public boolean saveMember(Member member)throws Exception {
	
		if (member == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				Member memberDB = getMemberByAthleteId(member.getAthleteId());
				if (memberDB != null) {	// update
					String sql = "update members set accessToken=?, firstName=?, lastName=?, pictureURL=?, city=?, state=?, email=? where athleteId=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, member.getAccessToken());
					preparedStatement.setString(2, member.getFirstName());
					preparedStatement.setString(3, member.getLastName());
					preparedStatement.setString(4, member.getPictureURL());
					preparedStatement.setString(5, member.getCity() != null ? member.getCity() : "");
					preparedStatement.setString(6, member.getState() != null ? member.getState() : "");
					preparedStatement.setString(7, member.getEmail() != null ? member.getEmail() : "");
					preparedStatement.setLong(8, member.getAthleteId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					String sql = "insert into members (athleteId, accessToken, firstName, lastName, pictureURL, city, state, email) values (?,?,?,?,?,?,?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, member.getAthleteId());
					preparedStatement.setString(2, member.getAccessToken());
					preparedStatement.setString(3, member.getFirstName());
					preparedStatement.setString(4, member.getLastName());
					preparedStatement.setString(5, member.getPictureURL());
					preparedStatement.setString(6, member.getCity() != null ? member.getCity() : "");
					preparedStatement.setString(7, member.getState() != null ? member.getState() : "");
					preparedStatement.setString(8, member.getEmail() != null ? member.getEmail() : "");
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0) {
						MemberActivityTotalsDAO memberActivityTotalsDAO = null;
						if (connection == null)
							memberActivityTotalsDAO = new MemberActivityTotalsDAO(session);
						else
							memberActivityTotalsDAO = new MemberActivityTotalsDAO(connection);
						MemberActivityTotal memberActivityTotal = new MemberActivityTotal();
						try {
							Member memberLookup = getMemberByAthleteId(member.getAthleteId());
							if (memberLookup.getId() > 0) {
								memberActivityTotal.setMemberId(memberLookup.getId());
								memberActivityTotalsDAO.saveMemberActivityTotals(memberActivityTotal);
							}
						} catch (Exception e) {
							throw e;
						}	
						return true;
					}
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

	public boolean deleteMember(Member member)throws Exception {
		
		if (member == null || member.getId() == 0)
			return false;
		
		PreparedStatement preparedStatement = null;
		
		try {
			if (connection == null)
				connection = Database.getConnection(session);
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
		}
		
		return false;
	}
	
	public boolean updateMemberWaivers(List<Member> members)throws Exception {
		
		if (members == null || members.size() == 0)
			return false;
		
		try {
			for (Member member : members) {
				saveMemberWaiver(member);
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	public boolean saveMemberWaiver(Member member)throws Exception {
		
		if (member == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "update members set waiver=? where id=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, member.getWaiver());
				preparedStatement.setLong(2, member.getId());
		
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
}
