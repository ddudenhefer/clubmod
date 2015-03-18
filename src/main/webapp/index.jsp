<%@ page import="com.google.gson.Gson, connector.JStravaV3, entities.athlete.*, model.Member, utils.Constants, services.MemberSvc" %>

<%
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		Profile profile = null;
		boolean success = false;
		
		if (code != null) {
			String URL="https://www.strava.com/oauth/token?client_id=" + Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET + "&code="+code;
			JStravaV3 strava= new JStravaV3();
			profile = strava.authenticateAccess(code);
	        
	        if (profile != null) {
	        	Member member = new Member();
	        	member.setAthleteId(profile.getAthlete().getId());
	        	member.setAccessToken(profile.getAccessToken());
	        	member.setFirstName(profile.getAthlete().getFirstname());
	        	member.setLastName(profile.getAthlete().getLastname());
	        	member.setPictureURL(profile.getAthlete().getProfile_medium());
	        	MemberSvc memberSvc = new MemberSvc();
	        	success = memberSvc.saveMember (member);
	        }
		}
%>

<%
	if (code != null && success) {
%>
<HTML>
<HEAD>
	<TITLE>Club Mod Authorization</TITLE>
		<STYLE>
	    	.circular img {
	    		width: 124px;
	    		height: 124px; 
	    		border-radius: 62px; 
	    		-webkit-border-radius: 62px; 
	    		-moz-border-radius: 62px; 
	    		-ms-border-radius: 62px;
             	-o-border-radius: 62px;
	    	}
	    	
	    	h1 {color:#f26921; font-family:Helvetica}
	    	h2 {color:#999; font-size:16px; font-family:Helvetica}
	    	h3 {color:#777; font-size:11px; font-family:Helvetica}
		</STYLE>
</HEAD>
<BODY>

<div style="text-align:center">
<img alt="Club Mod" src="/css/images/Mod-Cyclists-Banner-2.jpg" title="Club Mod" width="60%" />
<H1>Hello <%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%> and Welcome to <span style="color:#02a0bb">CLUB MOD</span>!</H1>
<H2>Thank you for authorizing Club Mod access to Strava.<br>This allows us to create weekly rankings as well as an overall leaderboard.</H2>

<div class='circular'>
	<img alt="<%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>" src="<%=profile.getAthlete().getProfile()%>" title="<%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>" />
</div>
<br><br><br><br>
<a href="http://www.modboulder.com/" target="_blank"><img alt="ModBoulder" src="/css/images/logo.jpg" title="ModBoulder" width="20%" /></a>
<H3>© 2009-2014 Mod Boulder<br>
3030 Washington, Boulder, CO  80304 | 720.252.6051 | <a href="mailto:info@modboulder.com?Subject=" target="_top">info@modboulder.com</a>
</H3>
</div>


</BODY>
</HTML>
<%
	}
	else if (code != null && ! success) {
%>
<HTML>
<HEAD>
	<TITLE>Club Mod Authorization</TITLE>
		<STYLE>
	    	.circular img {
	    		width: 124px;
	    		height: 124px; 
	    		border-radius: 62px; 
	    		-webkit-border-radius: 62px; 
	    		-moz-border-radius: 62px; 
	    		-ms-border-radius: 62px;
             	-o-border-radius: 62px;
	    	}
	    	h1 {color:#f26921; font-family:Helvetica}
	    	h2 {color:#999; font-size:16px; font-family:Helvetica}
	    	h3 {color:#777; font-size:11px; font-family:Helvetica}
		</STYLE>
</HEAD>
<BODY>

<div style="text-align:center">
<img alt="Club Mod" src="/css/images/Mod-Cyclists-Banner-2.jpg" title="Club Mod" width="60%" />
<H1>Authorization Failed!</H1>
<H2>Please contact <a href="mailto:sean@modboulder.com?Subject=Authorization%20Failure" target="_top">Club Mod Support</a></H2>
<br><br><br><br>
<a href="http://www.modboulder.com/" target="_blank"><img alt="ModBoulder" src="/css/images/logo.jpg" title="ModBoulder" width="20%" /></a>
<H3>© 2009-2014 Mod Boulder<br>
3030 Washington, Boulder, CO  80304 | 720.252.6051 | <a href="mailto:info@modboulder.com?Subject=" target="_top">info@modboulder.com</a>
</H3>
</div>

</BODY>
</HTML>

<%	}
	else if (error != null) {
%>
<HTML>
<HEAD>
	<TITLE>Club Mod Authorization</TITLE>
	<STYLE>
	    	.circular img {
	    		width: 124px;
	    		height: 124px; 
	    		border-radius: 62px; 
	    		-webkit-border-radius: 62px; 
	    		-moz-border-radius: 62px; 
	    		-ms-border-radius: 62px;
             	-o-border-radius: 62px;
	    	}
	    	h1 {color:#f26921; font-family:Helvetica}
	    	h2 {color:#999; font-size:16px; font-family:Helvetica}
	    	h3 {color:#777; font-size:11px; font-family:Helvetica}
	</STYLE>
</HEAD>
<BODY>

<div style="text-align:center">
<img alt="Club Mod" src="/css/images/Mod-Cyclists-Banner-2.jpg" title="Club Mod" width="60%" />
<H1>Club Mod</H1>
<H2>Sorry to hear you do not want to join Club Mod Weekly Challenges at this time.<br>If you should change your mind, please visit:<br><br>
<a href="http://www.modboulder.com/clubmodsignup/" target="_blank">Join Today</a></H2>
<br><br><br><br>
<a href="http://www.modboulder.com/" target="_blank"><img alt="ModBoulder" src="/css/images/logo.jpg" title="ModBoulder" width="20%" /></a>
<H3>© 2009-2014 Mod Boulder<br>
3030 Washington, Boulder, CO  80304 | 720.252.6051 | <a href="mailto:info@modboulder.com?Subject=" target="_top">info@modboulder.com</a>
</H3>
</div>

</BODY>
</HTML>

<%	}
	else {
%>
		<%@ include file="openshifthome.html" %>

<%	}
%>		

