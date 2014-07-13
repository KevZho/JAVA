<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
	<body bgcolor="<%= color %>">
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<img src="reportImage">
	</body>
</html>