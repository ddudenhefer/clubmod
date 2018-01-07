package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.GroupRide;

public class GroupRideDAO {

	HttpSession session = null;
	Connection connection = null;

	public GroupRideDAO(HttpSession session) {
		this.session = session;
	}
	
	public GroupRideDAO(Connection connection) {
		this.connection = connection;
	}

	public GroupRide getGroupRide()throws Exception {
		PreparedStatement preparedStatement = null;
		GroupRide groupRide = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				preparedStatement = connection.prepareStatement("SELECT id, segmentId, bonusSegmentId FROM group_ride");
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					groupRide = new GroupRide();
					groupRide.setId(rs.getInt("id"));
					groupRide.setSegmentId(rs.getInt("segmentId"));
					groupRide.setBonusSegmentId(rs.getInt("bonusSegmentId"));
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return groupRide;
	}
	

	public boolean saveGroupRide(GroupRide groupRide)throws Exception {
	
		if (groupRide == null)
			return false;
		
		PreparedStatement preparedStatement = null;

		try {
			if (connection == null)
				connection = Database.getConnection(session);
			if (connection != null) {
				GroupRide groupRideDB = getGroupRide();
				if (groupRideDB != null) {	// update
					String sql = "update group_ride set segmentId=?, bonusSegmentId=? where id=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, groupRide.getSegmentId());
					preparedStatement.setInt(2, groupRide.getBonusSegmentId());
					preparedStatement.setLong(3, groupRide.getId());
		
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0)
						return true;
				}
				else {	// insert
					String sql = "insert into group_ride (segmentId, bonusSegmentId) values (?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, groupRide.getSegmentId());
					preparedStatement.setInt(2, groupRide.getBonusSegmentId());
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
