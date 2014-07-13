<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
	<body bgcolor="<%= color %>">
		<table border ="1" cellspacing="0">
			<c:forEach var="r" items="${parovi}">
				<tr><td>${r.broj}</td><td>${r.vrijednost}</td></tr>
			</c:forEach>
		</table>
	</body>
</html>