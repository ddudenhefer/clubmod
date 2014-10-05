package servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Member;
import services.MemberSvc;
import utils.Constants;
import connector.JStravaV3;
import entities.athlete.Profile;

@WebServlet("/authenticate")
public class Authenticate extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		Profile profile = null;

		if (code != null) {		// user accepted
			String URL="https://www.strava.com/oauth/token?client_id=" + Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET + "&code="+code;
			JStravaV3 strava= new JStravaV3();
			profile = strava.authenticateAccess(code);
	        
	        if (profile != null) {
	        	Member member = new Member();
	        	member.setAthleteId(profile.getAthlete().getId());
	        	member.setAccessToken(profile.getAccessToken());
	        	MemberSvc memberSvc = new MemberSvc();
	        	memberSvc.saveMember (member);
	        	
	        	response.setContentType("text/html");
	        	PrintWriter out = response.getWriter();
	        	
	        	out.println("<HTML>");
	        	out.println("	<HEAD>");
	        	out.println("		<TITLE>Club Mod Authentication</TITLE>");
	        	out.println("		<STYLE>");
	        	out.println("			.circular {width: 300px; height: 300px; border-radius: 150px; -webkit-border-radius: 150px; -moz-border-radius: 150px;}");
	        	out.println("		</STYLE>");
	        	out.println("	</HEAD>");
	        	out.println("	<BODY>");
	        	out.println("		<H1>" + profile.getAthlete().getFirstname() + " " + profile.getAthlete().getLastname() + ", thanks for authenticating!</H1>");
	        	out.println("		<div class='circular'>");
	        	out.println("			<img alt='" + profile.getAthlete().getFirstname()+ " " + profile.getAthlete().getLastname() + " src='" + profile.getAthlete().getProfile() + " title='" + profile.getAthlete().getFirstname() + " " + profile.getAthlete().getLastname() + "' />");
	        	out.println("		</div>");
	        	out.println("  	</BODY>");
	        	out.println("</HTML>");
	        	out.close();
	        }
		}
		
		if (error != null) {	// user did not accept

        	response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
        	
        	out.println("<HTML>");
        	out.println("	<HEAD>");
        	out.println("		<TITLE>Club Mod Authentication</TITLE>");
        	out.println("	</HEAD>");
        	out.println("	<BODY>");
        	out.println("		<H1>Oh no!  So, you do not want to participate in the Weekly Challenges?</H1>");
        	out.println("  	</BODY>");
        	out.println("</HTML>");
        	out.close();
		}
	}
}
