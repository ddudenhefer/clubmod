package services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

@Path("/member/activty")
public class MemberActivtySvc {
	
	@GET
	@Path("{memberId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMemberActivityById(@PathParam("memberId") int memberId) { 
		MemberActivityTotal memberActivityTotal = null;
		
		try {
			memberActivityTotal = new MemberActivityTotalsDAO().getMemberData(memberId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		if (memberActivityTotal != null) {
			System.out.println(gson.toJson(memberActivityTotal));
			ret = gson.toJson(memberActivityTotal);
		}
		else {
			ret = gson.toJson("");
		}
		return ret;
	}

	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllMemberActivityTotals() {
		List<MemberActivityTotal> memberActivityTotals = new ArrayList<MemberActivityTotal>();
		
		try {
			MemberDAO memberDAO = new MemberDAO();
			List<Member> members = memberDAO.getAllMembers();
			Collections.sort(members, Member.Comparators.NAME);
			
			for (Member member : members) {
				MemberActivityTotal memberActivityTotal = new MemberActivityTotalsDAO().getMemberData(member.getId());
				if (memberActivityTotal == null) {
					memberActivityTotal = new MemberActivityTotal();
					memberActivityTotal.setMemberId(member.getId());				
				}
				memberActivityTotal.setMemberName(member.getFirstName() + " " + member.getLastName());				
				memberActivityTotals.add(memberActivityTotal);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(memberActivityTotals);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/update/{jsonData}")
	public String updateMemberActivityTotals(@PathParam("jsonData") String jsonData) {
		MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO();
		String ret = "success";
		
		try {
			Gson gson = new Gson();
			MemberActivityTotal[]memberActivityTotalsArray = gson.fromJson(jsonData, MemberActivityTotal[].class); 
	        List<MemberActivityTotal> memberActivityTotals=Arrays.asList(memberActivityTotalsArray);	

			boolean ok = memberActivityTotalsDAO.updateMemberActivityTotals(memberActivityTotals);
			if (! ok)
				ret = "failed"; 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = "failed";
		}
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public boolean deleteMember(final MemberActivityTotal memberActivityTotal) {
		MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO();
		boolean ret = false;

		try {
			ret = memberActivityTotalsDAO.deleteMemberActivityTotal(memberActivityTotal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
	    return ret; 
	}	
} 