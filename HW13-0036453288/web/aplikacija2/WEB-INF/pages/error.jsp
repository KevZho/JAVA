<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
	<body bgcolor="<%= color %>" text="<%= session.getAttribute("pickedFgCol") %>">
		<p>
			Potrebno je unjeti 3 parametra:
			<p>a € [-100, 100]</a>
			<p>b € [-100, 100]</a>
			<p>n € [1, 5]</a>
		</p>
	</body>
</html>