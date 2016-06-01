<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OneM2M</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/normalize.css" rel="stylesheet">
<link href="css/skeleton.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-2.2.0.js"></script>
    <script type="text/javascript">

        $(document).ready(function(){
           $('#search').click(function(){
        	   window.location.href='resourcesManagement.jsp'; 
           }) ;
           $('#look').click(function(){
        	   window.location.href='index.jsp'; 
           }) ;
           
        });
    </script>
</head>
<body  bgcolor="rgb(90,112,167)">

	<h2 align="center" class='top'>service status:started</h2>

	<div class="container">
         <div id='search'> <input type='button' value='search resouce' class='right button-primary'></div>
         	<div id='look'> <input type='button' value='server load' class='right button-primary'></div>
		<div class="u-full-width">
			<table class="u-full-width">
				<thead>
					<tr>
						<th>No.</th>
						<th>Ip</th>
						<th>Status</th>
						<th>operate</th>
					</tr>
				</thead>
				<tbody>
					<c:set value="11" var="value"/> 
					<c:forEach var="entry" items="${message}">
					<c:set value='${entry.ip}' var="value"/> 
						<tr>
							<td>${entry.id}</td>
							<td>${entry.ip}</td>
							<c:if test="${entry.status==1 }">
									<td>started</td>
							</c:if>
							<c:if test="${entry.status!=1 }">
									<td>stoped</td>
							</c:if>
							<td><a href='${fn:replace(value,":","~")}-replicationServer.html'>look replication servers</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
