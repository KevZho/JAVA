<%@ page import="java.util.Date" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<%
Date start = (Date) getServletContext().getAttribute("uptime");
Date now = new Date();

long diff = now.getTime() - start.getTime();
long days = diff / (1000 * 60 * 60 * 24);
diff -= days * (1000 * 60 * 60 * 24);

long hours = diff / (1000 * 60 * 60);
diff -= hours * (1000 * 60 * 60);

long mins = diff / (1000 * 60);
diff -= mins * (1000 * 60);

long secs = diff / 1000;
diff -= secs * 1000;

long mils = diff;
%>

<html>
   <body bgcolor="<%= color %>">
   <p>Uptime: <%= days %> days <%= hours %> hours <%= mins %> minutes <%= secs %> seconds <%= mils %> milliseconds</p>
   </body>
</html>