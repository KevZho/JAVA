<%@page import="hr.fer.zemris.java.tecaj.hw15.model.PollData"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>
<html>
  <body>
  <h1>Dostupne ankete:</h1>
  <c:choose>
   		<c:when test="${ankete.isEmpty()}">
   			<p>Trenutno nemate evidentiranih anketa.</p>
   		</c:when>
   		<c:otherwise>
   			<ol>
   				<c:forEach var="zapis" items="${ankete}">
   					<li>
   						<p><a href="glasanje?pollID=<c:out value="${zapis.id}"></c:out>">${zapis.title}</a></p>
   					</li>
   				</c:forEach>
   			</ol>
   		</c:otherwise>
   	</c:choose>
  
  </body>
</html>