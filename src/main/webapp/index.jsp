<%@ page import="com.google.gson.Gson, connector.JStravaV3, entities.athlete.*, model.Member, utils.Constants, services.MemberSvc" %>

<%
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		Profile profile = null;

		if (code != null) {
			String URL="https://www.strava.com/oauth/token?client_id=" + Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET + "&code="+code;
			JStravaV3 strava= new JStravaV3();
			profile = strava.authenticateAccess(code);
	        
	        if (profile != null) {
	        	Member member = new Member();
	        	member.setAthleteId(profile.getAthlete().getId());
	        	member.setAccessToken(profile.getAccessToken());
	        	MemberSvc memberSvc = new MemberSvc();
	        	memberSvc.saveMember (member);
	        }
		}
%>

<%
	if (code != null) {
%>
<HTML>
<HEAD>
	<TITLE>ClubMod Authentication</TITLE>
		<STYLE>
			    	.circular {width: 124px; height: 124px; border-radius: 62px; -webkit-border-radius: 62px; -moz-border-radius: 62px;}
		</STYLE>
</HEAD>
<BODY>

<H1><%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>, thanks for authenticating!</H1>
<div class='circular'>
	<img alt="<%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>" src="<%=profile.getAthlete().getProfile()%>" title="<%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>" />
</div>

</BODY>
</HTML>
<%	}
	else if (error != null) {
%>
<HTML>
<HEAD>
	<TITLE>ClubMod Authentication</TITLE>
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

