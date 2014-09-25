package services.authenticate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
@Path("/access")
public class AccessToken {
	
  // This method is called if TEXT_PLAIN is request
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String lookupMemberByAthleteId(@PathParam("id") long id) { 
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
	  try {
		memberDAO.saveMember(member);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  Gson gson = new Gson();
	  String ret = "";
	  Properties prop = new Properties();
	  prop.put("Success", "true");
	  ret = gson.toJson(prop);
    
    return ret; 
  }
  	
} 