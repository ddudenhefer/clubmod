package services.authenticate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Member;

import com.google.gson.Gson;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/access")
@RequestScoped
public class AccessToken {
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;
	
  // This method is called if TEXT_PLAIN is request
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String lookupMemberById(@PathParam("id") long id) { 
	  Member member = em.find(Member.class, id);
	  Gson gson = new Gson();
	  System.out.println(gson.toJson(member));
	  String ret = gson.toJson(member);	  
		return ret;
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }

} 