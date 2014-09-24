package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Members;
import dao.Database;

public class MemberDAO {
	
	public ArrayList<Members> GetFeeds()throws Exception {
		ArrayList<Members> feeds = null;
		try {
			    Connection connection = Database.getConnection();
				feeds=GetFeeds(connection);
		
		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}
	
	private ArrayList<Members> GetFeeds(Connection connection) throws Exception
	{
		ArrayList<Members> feedData = new ArrayList<Members>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT id, athleteId, accessToken FROM members ORDER BY id DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Members feedObject = new Members();
				feedObject.setId(rs.getLong("id"));
				feedObject.setAthleteId(rs.getLong("athleteId"));
				feedObject.setAccessToken(rs.getString("accessToken"));
				feedData.add(feedObject);
			}
			return feedData;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	

}
