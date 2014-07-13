<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
	<body bgcolor="<%= color %>">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!
	</p>
		<ol>
			<c:forEach var="r" items="${bendovi}">
				<li><a href="${r.link}">${r.name}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>
