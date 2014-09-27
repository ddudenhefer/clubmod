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
		<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
		<script src="js/skel.min.js"></script>
		<script src="js/init.js"></script>
		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui.js"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-wide.css" />
			<link rel="stylesheet" href="css/style-noscript.css" />
			<link rel="stylesheet" href="css/jquery-ui.css" />
		</noscript>
		<!--[if lte IE 9]><link rel="stylesheet" href="css/ie/v9.css" /><![endif]-->
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->

<script>

function getColumns () {
	
	var colArray = new Array("Id", "First Name", "Last Name", "City", "State", "Authenticated");
	return colArray;
}

function getModel () {

	var jsonModel = [
			{name:"id",index:"id", hidden: true },
	   		{name:"firstname",index:"firstname", width:80},
	   		{name:"lastname",index:"lastname", width:400},
	   		{name:"profile_medium",index:"profile_medium", hidden: true},
	   		{name:"city",index:"city", width:80},
	   		{name:"state",index:"state", width:80}
	   	];	
	return jsonModel;
}

$(document).ready(function(){	
		$("#subdivisionGrid").jqGrid({
		   	url:"${rootName}/rest/club/members",
			datatype: "json",
		   	colNames: getColumns(),
		   	colModel: getModel(),
			jsonReader: {
				repeatitems: false,
				id: "id",
				//root: "",
				records: function (obj) {
        			return obj.length;
    			},
    			page: function () {
        			return 1;
    			},
    			total: function () {
        			return 1;
    			}
	        },
			gridview: true, 	        
	        loadonce: true,
		   	rowNum: 10,
		   	rowList: [10,20,30],
		   	pager: "#pager",
		    viewrecords: true,
		   	sortname: "lastname",
		    sortorder: "asc",
		    ignoreCase: true,
			width: 721,
	   		height: 231,
			autowidth: false,
			shrinkToFit: true,
			onSelectRow: function(rowId, rowStatus, event) {
				var rowData = $(this).getRowData(rowId);
   			}
		});
	
		$("#subdivisionGrid").jqGrid("navGrid","#pager",
			{edit:false,add:false,del:false,search:false,refresh:false},
			{}, 
			{}, 
			{}, 
			{closeAfterSearch:true,closeOnEscape:true,modal:true}, 
			{}
		);
	});
</script>


	</head>
	<body class="loading">
		<div id="wrapper">
			<div id="bg"></div>
			<div id="overlay"></div>
			<div id="main">
			
			<table id="subdivisionGrid"></table>
			<div id="pager"></div>			
			
				<!-- Header -->
					<header id="header">
						<img src="css/images/ModPatch.png" height="150" alt="ClubMod"> 
						<h1>ClubMod Cycling</h1>
						<p>Inspiring you on the bike with Strava Challenges and Achievement Prizes</p>
						<nav>
							<ul>
								<li><a href="#" class="icon fa-users"></a></li>
								<li><a href="#" class="icon fa-bicycle"></a></li>
								<li><a href="#" class="icon fa-flag"></a></li>
								<li><a href="#" class="icon fa-trophy"></a></li>								</ul>
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