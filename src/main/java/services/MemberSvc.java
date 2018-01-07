package services;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import utils.Constants;

import com.google.gson.Gson;
import dao.MemberDAO;

@Path("/member")
public class MemberSvc {
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMemberByAthleteId(@PathParam("id") int id, @Context HttpServletRequest request) { 
		HttpSession session = request.getSession();
		Member member = null;
		
		try {
			member = new MemberDAO(session).getMemberByAthleteId(id);
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
	public String getAllMembers(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Member> members = new ArrayList<Member>();
		
		try {
			members = new MemberDAO(session).getAllMembers();
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
		ret = gson.toJson(members);
		return ret;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public boolean saveMember(final Member member, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDAO memberDAO = new MemberDAO(session);
		boolean ret = false;
		
		try {
			ret = memberDAO.saveMember(member);
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
	public boolean deleteMember(final Member member, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDAO memberDAO = new MemberDAO(session);
		boolean ret = false;

		try {
			ret = memberDAO.deleteMember(member);
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
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateWaiver")
	public String updateMemberWaivers(String jsonData, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDAO memberDAO = new MemberDAO(session);
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