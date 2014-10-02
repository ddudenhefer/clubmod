<%@ page language="java" pageEncoding="UTF-8" autoFlush="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="rootName" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
	<head>
		<title>ClubMod Mileage Challenge</title>
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
	
				var colArray = new Array("Id", "Name", "Rides", "Miles");
				return colArray;
			}

			function getModel () {
			
				var jsonModel = [
						{name:"id",index:"id", hidden: true },
				   		{name:"name",index:"name"},
				   		{name:"rides",index:"rides"},
				   		{name:"miles",index:"miles"}
				   	];	
				return jsonModel;
			}
			
			function loadGrid (startDate, endDate) {
				
				startDate =  (startDate.getMonth() + 1) + '-' + startDate.getDate() + '-' + startDate.getFullYear();
				endDate =  (endDate.getMonth() + 1) + '-' + endDate.getDate() + '-' + endDate.getFullYear();
				
				$("#mileageGrid").jqGrid().setGridParam({url : "${rootName}/rest/activity/"+startDate+"/"+endDate}).trigger("reloadGrid");
			}

			$(document).ready(function(){
				$("#fromdate").datepicker();
				$("#todate").datepicker();
				$("#go").button().click(function(event) {
			  		loadGrid($("#fromdate").datepicker('getDate'), $("#todate").datepicker('getDate'));
			   	});
				
				$("#mileageGrid").jqGrid({
				   	//url:"${rootName}/rest/activity/"+startDate+"/"+endDate,
					datatype: "json",
					root: "athletes",
					height: 230,
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
				   	sortname: "miles",
				    sortorder: "desc",
				    ignoreCase: true,
					autowidth: true,
					shrinkToFit: true,
					onSelectRow: function(rowId, rowStatus, event) {
						var rowData = $(this).getRowData(rowId);
		   			}
				});
				
				$("#mileageGrid").jqGrid("navGrid","#pager",
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
				    width = width - 20; // Fudge factor to prevent horizontal scrollbars
				    if (width > 0 &&
				        // Only resize if new width exceeds a minimal threshold
				        // Fixes IE issue with in-place resizing when mousing-over frame bars
				        Math.abs(width - $("#mileageGrid").width()) > 5)
				    {
				    	$("#mileageGrid").setGridWidth(width);
				    }

				}).trigger('resize');
			});
			
		</script>
	</head>
	<body>
		<p>From Date: <input type="text" id="fromdate"></p>
		<p>To Date: <input type="text" id="todate"></p>
		<button id="go">Go</button>
		<table id="mileageGrid"></table>
		<div id="pager"></div>			
	</body>
</html>