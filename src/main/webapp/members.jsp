<%@ page language="java" pageEncoding="UTF-8" autoFlush="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="rootName" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
	<head>
		<title>ClubMod Members</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />

		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui.js"></script>
		<script src="js/grid.locale-en.js"></script>
		<script src="js/jquery.jqGrid.src.js"></script>		

		<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.theme.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

		<script type="text/javascript">

			function getColumns () {
	
				var colArray = new Array("Id", "First Name", "Last Name", "Profile", "City", "State");
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
				$("#memberGrid").jqGrid({
				   	url:"${rootName}/rest/club/members",
					datatype: "json",
				   	colNames: getColumns(),
				   	colModel: getModel(),
					jsonReader: {
						repeatitems: false,
						id: "id",
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
					width: 580,
			   		height: 300,
					autowidth: false,
					shrinkToFit: true,
					onSelectRow: function(rowId, rowStatus, event) {
						var rowData = $(this).getRowData(rowId);
		   			}
				});
			
				$("#memberGrid").jqGrid("navGrid","#pager",
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
	<body>
		<table id="memberGrid"></table>
		<div id="pager"></div>			
	</body>
</html>