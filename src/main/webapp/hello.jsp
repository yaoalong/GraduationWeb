<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="shortcut icon" href="images/favicon.ico" type="favicon.ico" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OneM2M</title>
<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="css/index.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-2.2.0.js"></script>
    <script type="text/javascript">

        $(document).ready(function(){
           $('#search').click(function(){
        	   window.location.href='resourcesManagement.jsp'; 
           }) ;
        });
    </script>
</head>
<body  style="background:url('img/background.jpg')no-repeat; background-size:100%">

	<h2 align="center" class='top'>service status:started</h2>
	<div class="container">
         <div id='search'> <input type='button' value='search resouce' class='right'></div>
		<div class="table-responsive">
			<table class="table table-striped table-bordered top">
				<thead>
					<tr>
						<th>No.</th>
						<th>Ip</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entry" items="${message}">
						<tr>
							<td>${entry.id}</td>
							<td>${entry.ip}</td>
							<td>${entry.status}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>