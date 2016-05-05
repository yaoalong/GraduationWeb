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
<link href="css/normalize.css" rel="stylesheet">
<link href="css/skeleton.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-2.2.0.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>

	<h2 align="center" class='top'>server:${server}</h2>
	<h3 align="center" class='top'>replication servers</h3>
	<div class="container">
         <div id='search'> <input type='button' value='search resouce' class='right button-primary '></div>
		<div class="u-full-width">
			<table class="u-full-width">
				<thead>
					<tr>
						<th>ip</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entry" items="${message}">
						<tr>
							<td>${entry.ip}</td>
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
