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
				   		{name:"firstname",index:"firstname"},
				   		{name:"lastname",index:"lastname"},
				   		{name:"profile_medium",index:"profile_medium", hidden: true},
				   		{name:"city",index:"city"},
				   		{name:"state",index:"state"}
				   	];	
				return jsonModel;
			}

			$(document).ready(function(){	
				$("#memberGrid").jqGrid({
				   	url:"${rootName}/rest/club/members",
					datatype: "json",
					height: 260,
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
					autowidth: true,
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
				
				$(window).bind('resize', function() {

				    // Get width of parent container
				    var width = $(window).width();
				    width = width - 2; // Fudge factor to prevent horizontal scrollbars
				    if (width > 0 &&
				        // Only resize if new width exceeds a minimal threshold
				        // Fixes IE issue with in-place resizing when mousing-over frame bars
				        Math.abs(width - $("#memberGrid").width()) > 5)
				    {
				    	$("#memberGrid").setGridWidth(width);
				    }

				}).trigger('resize');				
			});
			
		</script>
	</head>
	<body>
		<table id="memberGrid"></table>
		<div id="pager"></div>			
	</body>
</html>