package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.activity.Activity;
import model.Challenge;
import model.Point;

public class PointsDAO {
	
	public Point getPoints(String type, String subType)throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Point point = null;

		try {
			connection = Database.getConnection();
			if (connection != null) {
				String sql = "SELECT id, type, subtype, points FROM points where type=? and subtype=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1,type);
				preparedStatement.setString(2,subType);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					point = new Point();
					point.setId(rs.getInt("id"));
					point.setType(rs.getString("type"));
					point.setSubType(rs.getString("subtype"));
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
		
		Point point = getPoints("achievements", "500 miles");
		String parts[] = null;
		parts = point.getSubType().split(" ");
		int miles = Integer.parseInt(parts[0]);
		int count = (int)distance/miles;
		points += (count*point.getPoints());
		
		point = getPoints("achievements", "25000 feet");
		parts = point.getSubType().split(" ");
		int feet = Integer.parseInt(parts[0]);
		count = (int)elevation/feet;
		points += (count*point.getPoints());

		return points;
	}
	
}
