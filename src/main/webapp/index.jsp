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
	<TITLE>ClubMod Authorization</TITLE>
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
		</STYLE>
</HEAD>
<BODY>

<H1><%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>, thanks for authorizing Club Mod access!</H1>
<div class='circular'>
	<img alt="<%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>" src="<%=profile.getAthlete().getProfile()%>" title="<%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>" />
</div>

</BODY>
</HTML>
<%
	}
	else if (code != null && ! success) {
%>
<HTML>
<HEAD>
	<TITLE>ClubMod Authorization</TITLE>
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
		</STYLE>
</HEAD>
<BODY>

<H1>Authorization Failed!</H1>
<H2>Please contact <a href="mailto:sean@modboulder.com?Subject=Authorization%20Failure" target="_top">Club Mod Support</a></H2>

</BODY>
</HTML>

<%	}
	else if (error != null) {
%>
<HTML>
<HEAD>
	<TITLE>ClubMod Authorization</TITLE>
</HEAD>
<BODY>

<H1>Doh!  So you do not want to play?</H1>

</BODY>
</HTML>

<%	}
	else {
%>
		<%@ include file="openshifthome.html" %>

<%	}
%>		

