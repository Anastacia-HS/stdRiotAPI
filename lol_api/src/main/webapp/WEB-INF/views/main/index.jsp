<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ALOLS</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>
	<%@ include file="../fix/header.jsp"%>
	
	<article id="main-article">
		<section id="search-section">
			<select id="select-area">
				<option>KR</option>
				<option>EUW</option>
				<option>NA</option>
			</select>
			<input type="text" id="search-champ" placeholder="챔피언을 검색해 보세요. (e.g. 라칸)" />
		</section>
		
		<section id="ratate-champ-section">
			<h3 class="middle-title" id="ratate-section-title">금주의 로테이션 챔피언</h3>
			<div id="rotate-champ-box">
				<c:forEach var="rotateChamp" items="${thisWeekRotateChamp }">
					<div class="rotate-champ-wrapper">
						<div class="rotate-img-frame">	
							<img src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/${rotateChamp.id }_0.jpg"/>
						</div>
						<div class="test-box">
							<span>${rotateChamp.name }</span>
							<span>${rotateChamp.tags }</span>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	</article>
	
	<%@ include file="../fix/footer.jsp"%>
</body>
</html>