package services.authenticate;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.MemberManager;

import com.google.gson.Gson;

import dto.MemberDTO;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/access")
public class AccessToken {
	
	@Inject 
	private EntityManager em; 


  // This method is called if TEXT_PLAIN is request
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String lookupMemberById(@PathParam("id") long id) { 
	  
	  	ArrayList<MemberDTO> feedData = null;
		MemberManager projectManager= new MemberManager();
		try {
			feedData = projectManager.GetFeeds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		System.out.println(gson.toJson(feedData));
		String feeds = gson.toJson(feedData);	  
		return feeds;
  }


  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }

} 