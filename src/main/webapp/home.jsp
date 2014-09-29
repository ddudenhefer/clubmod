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
		
		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui.js"></script>

		<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
		<script src="js/skel.min.js"></script>
		<script src="js/init.js"></script>

		<noscript>
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-wide.css" />
			<link rel="stylesheet" href="css/style-noscript.css" />
		</noscript>

		<link rel="stylesheet" href="css/jquery-ui.css" />
		<link rel="stylesheet" href="css/jquery-ui.theme.css" />

		<!--[if lte IE 9]><link rel="stylesheet" href="css/ie/v9.css" /><![endif]-->
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
		
		<script type="text/javascript">

			$(document).ready(function(){	
				
				$( "#members" ).click(function() {
			        $("#memberList").attr('src', "${rootName}/members.jsp");
					$( "#dialog" ).dialog({
						modal: true,
						height: 500,
						width: 640,
						title: "Members",
					    show: {
					    	effect: "fade",
					        duration: 600
					  	},
					    hide: {
					    	effect: "fade",
					        duration: 600
					   	},
			            close: function () {
			                $("#memberList").attr('src', "about:blank");
			            }
					});					
			    });
				
				$( "#activities" ).click(function() {
					$( "#dialog" ).dialog({
						modal: true,
						height: 500,
						width: 640,
						title: "Activities",
					    show: {
					    	effect: "fade",
					        duration: 600
					  	},
					    hide: {
					    	effect: "fade",
					        duration: 600
					   	}
					});					
			    });
				
				$( "#challenges" ).click(function() {
					$( "#dialog" ).dialog({
						modal: true,
						height: 500,
						width: 640,
						title: "Challenges",
					    show: {
					    	effect: "fade",
					        duration: 600
					  	},
					    hide: {
					    	effect: "fade",
					        duration: 600
					   	}
					});					
			    });
				
				$( "#achievements" ).click(function() {
					$( "#dialog" ).dialog({
						modal: true,
						height: 500,
						width: 640,
						title: "Achievements",
					    show: {
					    	effect: "fade",
					        duration: 600
					  	},
					    hide: {
					    	effect: "fade",
					        duration: 600
					   	}
					});					
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
						<div id="dialog" style="display:none;">
						    <iframe id="memberList" width="640" height="500" style="overflow: hidden"></iframe>
						</div>
					
						<img src="css/images/ModPatch.png" height="125" alt="ClubMod"> 
						<h1>ClubMod Cycling</h1>
						<p>Inspiring you on the bike with Strava Challenges and Achievement Prizes</p>
						<nav>
							<ul>
								<li><a id="members" href="#" class="icon fa-users"><span class="label">Members</span></a></li>
								<li><a id="activities" href="#" class="icon fa-bicycle"></a></li>
								<li><a id="challenges" href="#" class="icon fa-flag"></a></li>
								<li><a id="achievements" href="#" class="icon fa-trophy"></a></li>
							</ul>
						</nav>
					</header>

				<!-- Footer -->
					<footer id="footer">
						<span class="copyright">&copy; 2009-2014 Mod Boulder</span>
					</footer>
			</div>
		</div>
	</body>
</html>