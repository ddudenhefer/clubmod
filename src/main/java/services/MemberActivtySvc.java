package services;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Member;
import model.MemberActivityTotal;
import utils.Constants;

import com.google.gson.Gson;

import dao.MemberActivityTotalsDAO;
import dao.MemberDAO;

@Path("/member/activty")
public class MemberActivtySvc {
	
	@GET
	@Path("{memberId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMemberActivityById(@PathParam("memberId") int memberId, @Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		MemberActivityTotal memberActivityTotal = null;
		
		try {
			memberActivityTotal = new MemberActivityTotalsDAO(session).getMemberData(memberId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
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
	public String getAllMemberActivityTotals(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<MemberActivityTotal> memberActivityTotals = new ArrayList<MemberActivityTotal>();
		
		try {
			MemberDAO memberDAO = new MemberDAO(session);
			List<Member> members = memberDAO.getAllMembers();
			Collections.sort(members, Member.Comparators.NAME);
			
			for (Member member : members) {
				MemberActivityTotal memberActivityTotal = new MemberActivityTotalsDAO(session).getMemberData(member.getId());
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
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
		}			
	
		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(memberActivityTotals);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/update")
	public String updateMemberActivityTotals(String jsonData, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(session);
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
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
		}			
		return ret;
	}
	

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/create/{memberId}")
	public boolean createMemberActivityTotals(@PathParam("memberId") int memberId, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(session);
		MemberActivityTotal memberActivityTotal = new MemberActivityTotal();
		boolean ret = false;
		
		try {
			memberActivityTotal.setMemberId(memberId);
			ret = memberActivityTotalsDAO.saveMemberActivityTotals(memberActivityTotal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
		}	
	    return ret; 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public boolean deleteMember(final MemberActivityTotal memberActivityTotal, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberActivityTotalsDAO memberActivityTotalsDAO = new MemberActivityTotalsDAO(session);
		boolean ret = false;

		try {
			ret = memberActivityTotalsDAO.deleteMemberActivityTotal(memberActivityTotal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
		finally {
			 if (session.getAttribute(Constants.DB_CONNECTION) != null) {
		        Connection con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
		        if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.removeAttribute(Constants.DB_CONNECTION);
			 }
		}			
	    return ret; 
	}	
} 