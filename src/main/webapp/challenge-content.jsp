<HTML>
<HEAD>
	<TITLE>none</TITLE>
		
<meta name="viewport" content="width=device-width, initial-scale=1" />

<style>

  	h1 {color:#f26921; font-family:Helvetica}
  	h2 {color:#999; font-size:16px; font-family:Helvetica}
  	h3 {color:#777; font-size:11px; font-family:Helvetica}

  #header {display:none}
  #footer {display:none}
  .hidden {
    display:none;
	}
  
  h1 {font-size: 18px;}
  h3 {font-size: 14px;}

table { 
  width: 100%; 
  border-collapse: collapse; 
}
/* Zebra striping */
tr:nth-of-type(odd) { 
  background: #eee; 
}
th { 
  background: #333; 
  color: white; 
  font-weight: bold; 
}
td, th { 
  padding: 6px; 
  border: 1px solid #ccc; 
  text-align: left; 
}

@media only screen 
and (min-device-width : 320px) 
and (max-device-width : 568px) {
        #table {
            width:260px;
        }
  		#canvas {
            width:260px;
    		padding-top: 0px;
    		padding-right: 0px;
    		padding-bottom: 0px;
    		padding-left: 0px;
        }
 }
  @media only screen 
  and (min-device-width : 768px)
  and (max-device-width : 1024px) {
    #table {
      width: 580px;
    }
    #canvas {
      	width : 580px;
   		padding-top: 0px;
    	padding-right: 0px;
    	padding-bottom: 0px;
    	padding-left: 0px;
    }
  }
</style>

<script src="/js/jquery.js"></script>
<script src="/js/jquery-dateformat.js"></script>

<script>
	$(document).ready(function(){
      
      var challengeIndex = parent.carousel.getCurrentThumbId() + 1;
      var today = new Date();
      var dateStr = $.format.date(today, "MM-dd-yyyy");
      
    $.ajax({
        type: "GET",
        url: 'http://services-clubmod.rhcloud.com/rest/challenge/' + challengeIndex + '/' + dateStr,
        dataType:"json",
        success: function(data){ 
          loadTable(data);
        },
        error: function() {
            alert('Error occured');
        }
    });
      
    });
  
       function loadTable(data2) {
         var restStartDate = $.format.date(Date.parse(data2.startDate), 'MM-dd-yyyy');
         var restEndDate = $.format.date(Date.parse(data2.endDate), 'MM-dd-yyyy');
         var displayStartDate = $.format.date(Date.parse(data2.startDate), 'MM/dd/yyyy');
         var displayEndDate = $.format.date(Date.parse(data2.endDate), 'MM/dd/yyyy');
         
         $("#pageTitle").html(data2.name);
         $("#dateRange").html(displayStartDate + " - " + displayEndDate);
         var value = data2.label;
         $("#col2Header").html(value);
        
         var url = "http://services-clubmod.rhcloud.com/rest/activity/" + data2.service + "/" + restStartDate + "/" + restEndDate;
         
		$.ajax({
        	type: 'GET',
        	url: "http://services-clubmod.rhcloud.com/rest/activity/" + data2.service + "/" + restStartDate + "/" + restEndDate,
        	dataType:"json",
        	success: function(data){ 
            	if(data){
                	var len = data.length;
                	var txt = "<tbody>";
                	if(len > 0){
                    	for(var i=0;i<len;i++){
                          var displayValue = "";
                          if (value == "Miles")
                            displayValue = numberWithCommas(data[i].miles);
                          else if (value == "Rides")
                            displayValue = data[i].rides;
                          else if (value == "Speed (mph)")
                            displayValue = data[i].speed;
                          else if (value == "Elevation (ft)")
                            displayValue = numberWithCommas(data[i].elevation);
                          
                        	if(data[i].firstName && data[i].lastName && displayValue){
                            txt += "<tr><td>"+data[i].firstName+" " + data[i].lastName;
                            if (i == 0)
                              txt += "<span style='float:right'><img src='/s/achievement-1st.png'></img></span>";
                              else if (i == 1)
                                txt += "<span style='float:right'><img src='/s/achievement-2nd.png'></img></span>";
                              else if (i == 2)
                                txt += "<span style='float:right'><img src='/s/achievement-3rd.png'></img></span>";
                              
                              txt +="</td><td>"+displayValue+"</td></tr>";
                        }
                    }
                    if(txt != ""){
                      txt += "</tbody>";
                        $("#table").append(txt);
                        
                      $("#tableContainer").removeClass("hidden");
$(window).trigger('resize');                      
                    }
                }
 				$("#waitIcon").hide();                  
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('error: ' + textStatus + ': ' + errorThrown);
        }
    });
 }
  
  function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    
}  
</script>		
		
</HEAD>




<BODY>

<div style="text-align:center">
<h1 id="pageTitle">&nbsp;</h1>
<h3 id="dateRange">&nbsp;</h3>

<div id="waitIcon" style="text-align:center">
  <img src="/s/ajax-loader.gif">
</div>

</div>

</BODY>
</HTML>
