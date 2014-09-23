package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Database;

import dao.MemberDAO;
import dto.MemberDTO;

public class MemberManager {
	
	
	public ArrayList<MemberDTO> GetFeeds()throws Exception {
		ArrayList<MemberDTO> feeds = null;
		try {
			    Database database= new Database();
			    Connection connection = database.Get_Connection();
				MemberDAO project= new MemberDAO();
				feeds=project.GetFeeds(connection);
		
		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}

}
