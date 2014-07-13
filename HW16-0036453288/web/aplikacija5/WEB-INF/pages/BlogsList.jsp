<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<%
	Boolean user = Boolean.valueOf(request.getParameter("loged"));
%>

<html>
	<head>
		<title>Blog entries</title>
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
		<!-- Omogućava prikaz imena korisnika i mogućnosti odjave. -->
		<c:choose>
			<c:when test="<%= session.getAttribute(\"current.user.id\") instanceof Long %>">
				<p class="loginText">
					<%= session.getAttribute("current.user.fn")%> <%= session.getAttribute("current.user.ln")%>,
					<a href="/aplikacija5/servleti/logout">odjava</a>
				</p>
			</c:when>
			<c:otherwise>
				<p class="loginText">
					Anonimni korisnik, <a href="/aplikacija5/servleti/main">prijava</a>
				</p>
			</c:otherwise>
		</c:choose>
		
		<p class="loginText">
			Povratan na početnu, <a href="/aplikacija5/servleti/main">početna</a>
		</p>
	
		<h1>Lista blogova autora ${autor}:</h1>
		<c:if test="${entries.isEmpty()}">
			<p>Autor još nije dodao niti jedan blog.</p>
		</c:if>
		<c:forEach var="e" items="${entries}">
			<p><a href="/aplikacija5/servleti/author/${autor}/${e.id}">${e.title}</a></p>
		</c:forEach>
	</body>
	<c:if test="${loged}">
		<p><a href="/aplikacija5/servleti/author/${autor}/new">Dodaj novi blog</a></p>
	</c:if>
</html>