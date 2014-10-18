package services;


import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Member;
import com.google.gson.Gson;
import dao.MemberDAO;

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/member")
public class MemberSvc {
	
	// This method is called if TEXT_PLAIN is request
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
} 