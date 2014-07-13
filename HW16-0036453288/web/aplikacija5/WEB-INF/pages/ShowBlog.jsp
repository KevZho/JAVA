<%@page import="hr.fer.zemris.java.hw16.model.BlogEntry"%>
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
			padding-left: 5%;
			width: 600px;
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
		<p class="loginText">
			Povratan na pregled blogova, <a href="/aplikacija5/servleti/author/${autor}">blogovi</a>
		</p>
		
		<h1>Pregled bloga</h1>
		
		<table>
			<tr>
				<td class ="prviStupac">ID:</td> 
			 	<td class ="drugiStupac">
			 		${zapis.id}<br>
			  	</td>
			</tr>
			<tr>
			 	<td class ="prviStupac">Naslov:</td> 
			 	<td class ="drugiStupac">
			 		<c:out value="${zapis.title}"></c:out><br>
			  	</td>
			</tr>
			<tr>
				<td class ="prviStupac">Tekst:</td>
				<td class ="drugiStupac">
					${zapis.text}
				</td>
			</tr>
		</table>
		<p>
			<c:if test="${loged}">
				<%
					BlogEntry e = (BlogEntry) request.getAttribute("zapis");
					session.setAttribute("blogID", e.getId());
				%>
				<p><a href="/aplikacija5/servleti/author/${autor}/edit">Edit</a></p>
			</c:if>
		</p>
		
		<h2>Komentari:</h2>
		<ul>
		<c:forEach var="c" items="${zapis.comments}">
			 <li><div style="font-weight: bold">[Korisnik=${c.usersEMail}] ${c.postedOn} </div>
			 <div style="padding-left: 10px;">${c.message}</div></li>
		</c:forEach>
		</ul>
		
		<!-- Prikaži formu za dodavanje komentara -->
		<form action="/aplikacija5/servleti/author/${autor}/${zapis.id}" method="post">
			<input type="hidden" name="blogID" value='<c:out value="${zapis.id}"></c:out>'>
			<table>
			<tr>
				<td class="prviStupac">
					Komentar:
				</td>
				<td class="drugiStupac">
					<textarea name="comment" rows="16" cols="70"></textarea>
				</td>
			</tr>
			<tr>
				<td class="prviStupac">
				</td>
				<td class="drugiStupac">
					<input type="submit" name="metoda-komentar" value="Pošalji komentar">
				</td>
			</tr>
			</table>
		</form>
	</body>
</html>