<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="js/jquery-2.2.0.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>
<link href="css/index.css" rel="stylesheet">
<script src="http://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		type : "post",
		url : "serverPacketStatistics.html",
		datatype : "json",
		success : function(data) {
			 if(data.errorCode!=0){
				 $(".alert").alert()
			 }
		
				var chart = new CanvasJS.Chart("chartContainer", {
					title:{
						text: "Server Load "              
					},
					data: [              
					{
						type: "column",
						dataPoints:data
					}
					]
				});
				chart.render();
		}
	}).done(function() {
	});
});
</script>
</head>
<body>
<div id="chartContainer" style="height: 300px; width: 100%;"></div>
</body>
</html>