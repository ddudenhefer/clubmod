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

// Plain old Java Object it does not extend as class or implements 
// an interface

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
			Properties prop = new Properties();
			prop.put("Success", "false");
			ret = gson.toJson(prop);
		}
		return ret;
	}
  

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public String saveMember(final Member member) {
		MemberDAO memberDAO = new MemberDAO();
		Gson gson = new Gson();
		String ret = "";
		Properties prop = new Properties();
		try {
			memberDAO.saveMember(member);
			prop.put("Success", "true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			prop.put("Success", "false");
		}
		ret = gson.toJson(prop);
	    return ret; 
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public String deleteMember(final Member member) {
		MemberDAO memberDAO = new MemberDAO();
		Gson gson = new Gson();
		String ret = "";
		Properties prop = new Properties();
		try {
			memberDAO.deleteMember(member);
			prop.put("Success", "true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			prop.put("Success", "false");
		}
		ret = gson.toJson(prop);
	    return ret; 
	}	
} 