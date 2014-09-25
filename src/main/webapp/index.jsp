<%@ page import="com.google.gson.Gson, utils.Results, entities.*" %>

<%
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		Profile profile = null;

		if (code != null) {
			String URL="https://www.strava.com/oauth/token?client_id=2946&client_secret=4fb119f5ab894d0bf0c998c8d32577740ca6e316&code="+code;
			Results results = new Results();
	        String result=results.postResult(URL);
	        Gson gson= new Gson();
	        profile = gson.fromJson(result,Profile.class);
		}
%>

<%
	if (code != null) {
%>
<HTML>
<HEAD>
	<TITLE>ClubMod Authentication</TITLE>
</HEAD>
<BODY>

<H1><%=profile.getAthlete().getFirstname()%> <%=profile.getAthlete().getLastname()%>, thanks for authenticating!</H1>
<div class='avatar avatar-athlete avatar-xl'>
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

