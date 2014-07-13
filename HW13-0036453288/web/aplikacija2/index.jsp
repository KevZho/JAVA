<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
   <body bgcolor="<%= color %>">
      	<p>
    		<a href="colors.jsp">Background color chooser</a>
   		</p>
   		<p>
   			<a href="squares?a=100&b=120">Squares</a>
   		</p>
   		<p>
   			<a href="stories/funny.jsp">Stories</a>
   		</p>
   		<p>
   			<a href="powers?a=1&b=100&n=3">XLS document</a>
   		</p>
   		<p>
   			<a href="appinfo.jsp">App Info</a>
   		</p>
   </body>
</html>