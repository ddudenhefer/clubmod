package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Challenge;
import model.MemberActivityTotal;
import model.MemberPoints;
import model.Point;

public class PointsDAO {
	
	HttpSession session = null;
	Connection connection = null;

	public PointsDAO(HttpSession session) {
		this.session = session;
	}

	public PointsDAO(Connection connection) {
		this.connection = connection;
	}

	public Point getPointById(int id)throws Exception {
		PreparedStatement preparedStatement = null;
		Point point = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, type, subtype, increment, maxlimit, points FROM points where id=?");
				preparedStatement.setLong(1,id);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					point = new Point();
					point.setId(rs.getInt("id"));
					point.setType(rs.getString("type"));
					point.setSubType(rs.getString("subtype"));
					point.setIncrement(rs.getInt("increment"));
					point.setLimit(rs.getInt("maxlimit"));
					point.setPoints(rs.getInt("points"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return point;
	}
	
	public List<Point> getAllPoints()throws Exception {
		PreparedStatement preparedStatement = null;
		List<Point> points = new ArrayList<Point>();
		Point point = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT id, type, subtype, increment, maxlimit, points FROM points";
				preparedStatement = connection.prepareStatement(sql);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					point = new Point();
					point.setId(rs.getInt("id"));
					point.setType(rs.getString("type"));
					point.setSubType(rs.getString("subtype"));
					point.setIncrement(rs.getInt("increment"));
					point.setLimit(rs.getInt("maxlimit"));
					point.setPoints(rs.getInt("points"));
					points.add(point);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return points;
	}

	
	public Point getPoints(String type, String subType)throws Exception {
		PreparedStatement preparedStatement = null;
		Point point = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				String sql = "SELECT id, type, subtype, increment, maxlimit, points FROM points where type=? and subtype=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1,type);
				preparedStatement.setString(2,subType);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					point = new Point();
					point.setId(rs.getInt("id"));
					point.setType(rs.getString("type"));
					point.setSubType(rs.getString("subtype"));
					point.setIncrement(rs.getInt("increment"));
					point.setLimit(rs.getInt("maxlimit"));
					point.setPoints(rs.getInt("points"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return point;
	}
	
	
	public MemberPoints getMemberPoints(int memberId, float distance, long elevation, List<Challenge> challengeWins, MemberActivityTotal memberActivityTotal)throws Exception {
		int points = 0;
		MemberPoints mp = new MemberPoints();
		
		//challenges
		int challengePoints = 0;
		for (Challenge challenge : challengeWins) {
			//Point point = getPoints("challenge", challenge.getService());
			//challengePoints += point.getPoints();
			
			// hard coded for now
			if (challenge.getMemberId() == memberId)
				challengePoints += 20;
			else if (challenge.getMemberId2() == memberId)
				challengePoints += 18;
			else if (challenge.getMemberId3() == memberId)
				challengePoints += 16;
			else if (challenge.getMemberId4() == memberId)
				challengePoints += 14;
			else if (challenge.getMemberId5() == memberId)
				challengePoints += 12;
			else if (challenge.getMemberId6() == memberId)
				challengePoints += 10;
			else if (challenge.getMemberId7() == memberId)
				challengePoints += 8;
			else if (challenge.getMemberId8() == memberId)
				challengePoints += 6;
			else if (challenge.getMemberId9() == memberId)
				challengePoints += 4;
			else if (challenge.getMemberId10() == memberId)
				challengePoints += 2;
	    }
		mp.setChallenges(challengePoints);
		points += challengePoints;
		
		// achievements
		Point point = getPoints("achievements", "miles");
		int miles = point.getIncrement();
		if (distance > point.getLimit())
			distance = point.getLimit();
		int count = (int)distance/miles;
		int milePoints = (count*point.getPoints());
		
		mp.setMiles(milePoints);
		points += milePoints;
		
		point = getPoints("achievements", "feet");
		int feet = point.getIncrement();
		if (elevation > point.getLimit())
			elevation = point.getLimit();
		count = (int)elevation/feet;
		int elevationPoints = (count*point.getPoints());
		
		mp.setElevation(elevationPoints);
		points += elevationPoints;

		if (memberActivityTotal != null) {
			//fantasy
			int fantasyPoints = 0;
			point = getPoints("fantasy", "entry");
			fantasyPoints += (memberActivityTotal.getFantasyEntry()*point.getPoints());
	
			point = getPoints("fantasy", "1");
			fantasyPoints += (memberActivityTotal.getFantasyFirst()*point.getPoints());
	
			point = getPoints("fantasy", "2");
			fantasyPoints += (memberActivityTotal.getFantasySecond()*point.getPoints());
			
			point = getPoints("fantasy", "3");
			fantasyPoints += (memberActivityTotal.getFantasyThird()*point.getPoints());
			
			mp.setFantasy(fantasyPoints);
			points += fantasyPoints;
			
			//rides
			point = getPoints("ride", "group");
			int groupPoints = (memberActivityTotal.getGroupRide()*point.getPoints());
			
			mp.setGroupRides(groupPoints);
			points += groupPoints;
	
			point = getPoints("ride", "event");
			int eventPoints = (memberActivityTotal.getEventRide()*point.getPoints());
			
			mp.setEventRides(eventPoints);
			points += eventPoints;
			
			//home
			point = getPoints("home", "purchase");
			int purchasePoints = (memberActivityTotal.getHomePurchase()*point.getPoints());
			
			mp.setHomePurchases(purchasePoints);
			points += purchasePoints;
	
			point = getPoints("home", "referral");
			int referralPoints = (memberActivityTotal.getHomeReferral()*point.getPoints());
			
			mp.setHomeReferrals(referralPoints);
			points += referralPoints;
			
			mp.setPointsRedeemed(memberActivityTotal.getPointsRedeemed());
		}
		
		mp.setPointsYTD(points);
		mp.setMilesYTD(distance);
		mp.setElevationYTD(elevation);
		mp.setAvailablePoints(points -  mp.getPointsRedeemed());

		return mp;
	}
	
	public boolean updatePoint(Point point)throws Exception {
		
		if (point == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				Point pointDB = getPointById(point.getId());
				if (pointDB != null) {	// update
					String sql = "update points set increment=?, maxlimit=?, points=? where id=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, point.getIncrement());
					preparedStatement.setInt(2, point.getLimit());
					preparedStatement.setInt(3, point.getPoints());
					preparedStatement.setLong(4, point.getId());
		
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


	public boolean updatePoints(List<Point> points)throws Exception {
		
		if (points == null || points.size() == 0)
			return false;
		
		try {
			for (Point point : points) {
				updatePoint(point);
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	
	public boolean deletePoint(Point point)throws Exception {
		
		if (point == null || point.getId() == 0)
			return false;
		
		PreparedStatement preparedStatement = null;
		
		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				Point pointDB = getPointById(point.getId());
				if (pointDB != null) {
					String sql = "delete from points where id=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setLong(1, point.getId());
		
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
