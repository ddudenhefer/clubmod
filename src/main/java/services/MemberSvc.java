package services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Member;
import model.MemberActivityTotal;

import com.google.gson.Gson;

import dao.MemberActivityTotalsDAO;
import dao.MemberDAO;
import entities.challenge.ChallengeResult;

@Path("/member")
public class MemberSvc {
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMemberByAthleteId(@PathParam("id") int id) { 
		Member member = null;
		
		try {
			member = new MemberDAO().getMemberByAthleteId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		if (member != null) {
			System.out.println(gson.toJson(member));
			ret = gson.toJson(member);
		}
		else {
			ret = gson.toJson("");
		}
		return ret;
	}

	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllMembers() {
		List<Member> members = new ArrayList<Member>();
		
		try {
			members = new MemberDAO().getAllMembers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(members);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public boolean saveMember(final Member member) {
		MemberDAO memberDAO = new MemberDAO();
		boolean ret = false;
		
		try {
			ret = memberDAO.saveMember(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
	    return ret; 
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public boolean deleteMember(final Member member) {
		MemberDAO memberDAO = new MemberDAO();
		boolean ret = false;

		try {
			ret = memberDAO.deleteMember(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
	    return ret; 
	}	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateWaiver")
	public String updateMemberWaivers(String jsonData) {
		MemberDAO memberDAO = new MemberDAO();
		String ret = "success";
		
		try {
			Gson gson = new Gson();
			Member[]memberArray = gson.fromJson(jsonData, Member[].class); 
	        List<Member> member=Arrays.asList(memberArray);	

			boolean ok = memberDAO.updateMemberWaivers(member);
			if (! ok)
				ret = "failed"; 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = "failed";
		}
		return ret;
	}
	
} 