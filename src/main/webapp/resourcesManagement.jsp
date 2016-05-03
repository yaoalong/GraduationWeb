<html>
<head>
<title>resouce manage</title>
<script type="text/javascript" src="js/jquery-2.2.0.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>
<link href="css/index.css" rel="stylesheet">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min.js"></script>

<script type="text/javascript">
	function createTable(servers, clomnNum) {
		var table = $("<table border = \"1\"  class=\"table table-striped table-bordered\">");
		table.appendTo(($("#myTable")));
		for (var i = 0; i < servers.length; i++) {

			var tr = $("<tr></tr>");
			tr.appendTo(table);
			for (var j = 0; j < clomnNum; j++) {
				var td = $("<td>" + servers[i] + "</td>");
				td.appendTo(tr);
			}
		}
		$("#createTable").append("</table>");
	}

	$(document).ready(function() {

		$('#search').click(function() {
			$.ajax({
				type : "post",
				url : "test.html",
				data : {
					key : $('#key').val(),
				},
				datatype : "json",
				success : function(data) {
					console.log("data" + data);
					if (data.servers.length <= 0) {
						console.log("no");
						$('#myTable').text("no server for this key");
					} else {
						createTable(data.servers, 1);
					}
				}
			}).done(function() {
			});
		}

		);
	});
</script>
</head>
</head>
<body
	style="background: url('img/background.jpg') no-repeat; background-size: 100%">

	<div style="padding: 20px; width: 700px; margin: 200px auto;"
		class='top'>
		<form class="bs-example bs-example-form " role="form">
			<div class="input-group">
				<span class="input-group-addon"> resource key</span> <input
					type="text" style="padding: 5px; width: 500px;"
					class="form-control" placeholder="resource key" id="key">
			</div>
			<br>
		</form>
		<div id='search'>
			<input type='button' value='search resouce' class='button right'>
		</div>
		<div id='myTable' class='top'></div>
	</div>
</body>
</html>