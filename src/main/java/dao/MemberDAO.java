package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.MemberDTO;

public class MemberDAO {
	
	
	public ArrayList<MemberDTO> GetFeeds(Connection connection) throws Exception
	{
		ArrayList<MemberDTO> feedData = new ArrayList<MemberDTO>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT id,athleteId,accessToken FROM members ORDER BY id DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				MemberDTO feedObject = new MemberDTO();
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
