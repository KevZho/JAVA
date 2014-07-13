<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
   <body bgcolor="<%= color %>">
	   <p>
	     <a href="setcolor?pickedBgCol=white">WHITE</a>
	   </p>
	   <p>
	     <a href="setcolor?pickedBgCol=red">RED</a>
	   </p>
	   <p>
	     <a href="setcolor?pickedBgCol=green">GREEN</a>
	   </p>
	   <p>
	     <a href="setcolor?pickedBgCol=cyan">CYAN</a>
	   </p>
   </body>
</html>