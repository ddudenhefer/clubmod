<%@ page language="java" pageEncoding="UTF-8" autoFlush="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="rootName" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
	<head>
		<title>ClubMod Cycling</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />

		<script src="js/skel.min.js"></script>
		<script src="js/init.js"></script>
		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui.js"></script>
		<script src="js/grid.locale-en.js"></script>
		<script src="js/jquery.jqGrid.src.js"></script>

 		<link rel="stylesheet" href="css/skel.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/style-wide.css" />
		<link rel="stylesheet" href="css/style-noscript.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.theme.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

		<script type="text/javascript">

			$(document).ready(function(){	
				$( "#dialog" ).dialog({
					autoOpen: false,
					modal: true,
				    show: {
				    	effect: "fade",
				        duration: 1000
				  	},
				    hide: {
				    	effect: "fade",
				        duration: 1000
				   	}
				});

				$( "#members" ).click(function() {
					$("#dialog").dialog('option', 'title', 'Club Members');
					$( "#dialog" ).dialog("open" );
			    });
				$( "#activities" ).click(function() {
					$("#dialog").dialog('option', 'title', 'Activities');
					$( "#dialog" ).dialog( "open" );
			    });
				$( "#challenges" ).click(function() {
					$("#dialog").dialog('option', 'title', 'Challenges');
					$( "#dialog" ).dialog( "open" );
			    });
				$( "#achievements" ).click(function() {
					$("#dialog").dialog('option', 'title', 'Achievements');
					$( "#dialog" ).dialog( "open" );
			    });
			});
  		</script>
	</head>
	<body class="loading">
		<div id="wrapper">
			<div id="bg"></div>
			<div id="overlay"></div>
			<div id="main">
			
				<!-- Header -->
					<header id="header">
						<div id="dialog"></div>
					
						<img src="css/images/ModPatch.png" height="150" alt="ClubMod"> 
						<h1>ClubMod Cycling</h1>
						<p>Inspiring you on the bike with Strava Challenges and Achievement Prizes</p>
						<nav>
							<ul>
								<li><a id="members" href="#" class="icon fa-users"></a></li>
								<li><a id="activities" href="#" class="icon fa-bicycle"></a></li>
								<li><a id="challenges" href="#" class="icon fa-flag"></a></li>
								<li><a id="achievements" href="#" class="icon fa-trophy"></a></li>
							</ul>
						</nav>
					</header>

				<!-- Footer -->
					<footer id="footer">
						<span class="copyright">&copy; 2009-2014 Mod Boulder</span><span class="copyright">3030 Washington, Boulder, CO  80304 | 720.252.6051 | <a href="mailto:info@modboulder.com?subject=">info@modboulder.com</a></span>
					</footer>
			</div>
		</div>
	</body>
</html>