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

		<link rel="stylesheet" href="css/jquery-ui.css" />
		<link rel="stylesheet" href="css/jquery-ui.theme.css" />
		<link rel="stylesheet" href="css/ui.jqgrid.css" />
 		<link rel="stylesheet" href="css/skel.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/style-wide.css" />
		<link rel="stylesheet" href="css/style-noscript.css" />

<script type="text/javascript">

function getColumns () {
	
	var colArray = new Array("Id", "First Name", "Last Name", "City", "State", "Authenticated");
	return colArray;
}

function getModel () {

	var jsonModel = [
			{name:"id",index:"id", hidden: true },
	   		{name:"firstname",index:"firstname", width:100},
	   		{name:"lastname",index:"lastname", width:200},
	   		{name:"profile_medium",index:"profile_medium", hidden: true},
	   		{name:"city",index:"city", width:100},
	   		{name:"state",index:"state", width:100}
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
				root: "",
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
			width: 500,
	   		height: 200,
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
			
				<!-- Header -->
					<header id="header">
								<table id="subdivisionGrid"></table>
								<div id="pager"></div>			
					
						<img src="css/images/ModPatch.png" height="150" alt="ClubMod"> 
						<h1>ClubMod Cycling</h1>
						<p>Inspiring you on the bike with Strava Challenges and Achievement Prizes</p>
						<nav>
							<ul>
								<li><a href="#" class="icon fa-users"></a></li>
								<li><a href="#" class="icon fa-bicycle"></a></li>
								<li><a href="#" class="icon fa-flag"></a></li>
								<li><a href="#" class="icon fa-trophy"></a></li>
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