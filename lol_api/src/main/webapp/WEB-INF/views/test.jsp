<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TEST PAGE</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
	<h1>index</h1>
	<%-- <c:forEach var="champ" items="${rotate_champ }">
		<img src="https://ddragon.leagueoflegends.com/cdn/${champ.version }/img/champion/${champ.id }.png"/>
	</c:forEach> --%>
	<c:forEach var="champ" items="${rotate_champ }">
		<div class="test-box">	
			<img src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/${champ.id }_0.jpg"/>
		</div>
	</c:forEach>
</body>
</html>