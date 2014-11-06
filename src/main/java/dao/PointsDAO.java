package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Challenge;
import model.Point;

public class PointsDAO {
	
	
	public Point getPointById(int id)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Point point = null;

		try {
			connection = Database.getConnection();
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
			if (connection != null)
				connection.close();
		}
		return point;
	}
	
	public List<Point> getAllPoints()throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Point> points = new ArrayList<Point>();
		Point point = null;

		try {
			connection = Database.getConnection();
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
			if (connection != null)
				connection.close();
		}
		return points;
	}

	
	public Point getPoints(String type, String subType)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Point point = null;

		try {
			connection = Database.getConnection();
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
			if (connection != null)
				connection.close();
		}
		return point;
	}
	
	
	public int getMemberPoints(int memberId, float distance, long elevation)throws Exception {
		int points = 0;
		
		ChallengeDAO challengeDAO = new ChallengeDAO();
		List<Challenge> challenges = challengeDAO.getChallengesByMemberId(memberId);
		for (Challenge challenge : challenges) {
			Point point = getPoints("challenge", challenge.getService());
			points += point.getPoints();
	    }
		
		Point point = getPoints("achievements", "miles");
		int miles = point.getIncrement();
		if (distance > point.getLimit())
			distance = point.getLimit();
		int count = (int)distance/miles;
		points += (count*point.getPoints());
		
		point = getPoints("achievements", "feet");
		int feet = point.getIncrement();
		if (elevation > point.getLimit())
			elevation = point.getLimit();
		count = (int)elevation/feet;
		points += (count*point.getPoints());

		return points;
	}
	
	public boolean updatePoint(Point point)throws Exception {
		
		if (point == null)
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = Database.getConnection();
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
			if (connection != null)
			connection.close();
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
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = Database.getConnection();
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
			if (connection != null)
				connection.close();
		}
		
		return false;
	}
	
	
}
