<%@ page import="hr.fer.zemris.web.radionice.User" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 


<%
	Object o = session.getAttribute("current.user");
%>

<html>
	<head>
		<title>Popis radionica</title>
		<style type="text/css">
			.loginText {
				text-align: right;
				font-family: fantasy;
				font-size: 1.2em;
				padding-right: 3%;
			}
		</style>
	</head>
   <body>
   <c:choose>
   	<c:when test="<%= o instanceof User %>">
   	<%
   		User currentUser = (User) o;
   	%>
   	<p class="loginText">
   		<%= currentUser.getIme()%> <%= currentUser.getPrezime()%>, <a href="logout">odjava</a>
   	</p>
   	</c:when>
   	
   	<c:otherwise>
   		<p class="loginText">
   			Anonimni korisnik, <a href="login">prijava</a>
   		</p>
   	</c:otherwise>
   </c:choose>
   
   	<h1>Lista postojećih radionica</h1>
   	<c:choose>
   		<c:when test="${radionice.isEmpty()}">
   			<p>Trenutno nemate evidentiranih radionica.</p>
   		</c:when>
   		<c:otherwise>
   			<ol>
   				<c:forEach var="zapis" items="${radionice}">
   					<li>
   						<c:out value="${zapis.naziv}"></c:out>
   						<c:out value="${zapis.datum}"></c:out>
   						<c:if test="<%= o instanceof User %>">
   							<a href="edit?id=<c:out value="${zapis.id}"></c:out>">Uredi</a>
   						</c:if>
   					</li>
   				</c:forEach>
   			</ol>
   		</c:otherwise>
   	</c:choose>

	<c:if test="<%= o instanceof User %>">   	
	   	<p>Dodavanje nove radionice:<br>
	   		<a href="new">Novi</a>
	   	</p>
	</c:if>
</html>