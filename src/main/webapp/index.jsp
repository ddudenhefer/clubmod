<%@ page import="com.google.gson.Gson, utils.Results" %>


<HTML>
<HEAD>
	<TITLE>Thanks</TITLE>
</HEAD>
<BODY>

<H1>Thanks for authenticating</H1>

<%
		String code = request.getParameter("code");
		String URL="https://www.strava.com/oauth/token?client_id=2946&client_secret=4fb119f5ab894d0bf0c998c8d32577740ca6e316&code="+code;
		Results results = new Results();
        String result=results.postResult(URL);
%>

</BODY>
</HTML>
