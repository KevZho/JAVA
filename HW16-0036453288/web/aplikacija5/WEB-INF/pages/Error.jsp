<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<head>
		<title>Register</title>
		<style type="text/css">
			.greska {
				font-family: fantasy;
				font-weight: bold;
				font-size: 0.9em;
				color: #FF0000;
			}
			.prviStupac {
				font-size: 0.9em;
				font-style: italic;
				text-align: right;
			}
			.drugiStupac {
			}
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

	<h1>Pogreška</h1>
	<p><a href="/aplikacija5/servleti/main">Povratak na početnu</a></p>
   </body>
</html>