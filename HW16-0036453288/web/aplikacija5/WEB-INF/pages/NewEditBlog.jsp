<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 


<html>
	<head>
		<title>New blog</title>
		<style type="text/css">
		.prviStupac {
				font-size: 0.9em;
				font-style: italic;
				text-align: right;
			}
		.drugiStupac {
		}
		
		.greska {
			font-family: fantasy;
			font-weight: bold;
			font-size: 0.9em;
			color: #FF0000;
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
		
		<c:choose>
		<c:when test="${mode == \"new\" }">
			<h1>Dodavanje novog bloga</h1>
			<form action="/aplikacija5/servleti/author/${autor}/new" method="post">
		</c:when>
		<c:otherwise>
			<h1>Izmjena bloga</h1>
			<form action="/aplikacija5/servleti/author/${autor}/edit" method="post">
		</c:otherwise>
		</c:choose>
		<table>
			<tr>
				<td class ="prviStupac">ID</td> 
			 	<td class ="drugiStupac">
			 		${zapis.id}<br>
			  	</td>
			  	<c:if test="${mode == \"edit\"}">
			  		<input type="hidden" name="id" value='<c:out value="${zapis.id}"></c:out>'>
			  	</c:if>
			</tr>
			<tr>
			 	<td class ="prviStupac">Naslov</td> 
			 	<td class ="drugiStupac"><input type="text" name="title" value='<c:out value="${zapis.title}"></c:out>' size="70"><br>
				  	<c:if test="${zapis.imaPogresku('title')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('title')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
				<td class ="prviStupac">Tekst</td>
				<td class ="drugiStupac">
					<textarea name="text" rows="16" cols="70">${zapis.text}</textarea>
				</td>
			</tr>
			<tr>
				<td class ="prviStupac"></td>
				<td class ="drugiStupac">
					<input type="submit" name="metoda-Blog" value="Pohrani">
					<input type="submit" name="metoda-Blog" value="Odustani">
				</td>
			</tr>
		</table>
		</form>
	</body>

</html>