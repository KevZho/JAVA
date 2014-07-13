<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<%
	Object o = session.getAttribute("greska");
	Object id = session.getAttribute("current.user.id");
%>

<html>
	<head>
		<title>Login</title>
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

		<!-- Omogućava prikazivanje login forme ako korisnik nije logiran. -->
   		<c:if test="<%= !(id instanceof Long) %>">
	   		<h2>Unesite podatke za prijavu</h2>
			<p class="greska">
				<c:if test="<%= o instanceof Boolean %>">
					Korisničko ime ili lozinka su pogrešni!!
				</c:if>
			</p>
			<form action="/aplikacija5/servleti/main" method="post">
				<table>
				<tr>
					<td class="prviStupac">
						Korisničko ime
					</td>
					<td class="drugiStupac">
						<input type="text" name="username"
						<c:choose>
							<c:when test="<%= o instanceof Boolean %>">
			  					value="<%= session.getAttribute("current.user.nick") %>"
							</c:when>
							<c:otherwise>
								value=""
							 </c:otherwise>
						</c:choose>
						size="10">
					</td>
				</tr>
				<tr>
					<td class="prviStupac">
						Zaporka
					</td>
					<td class="drugiStupac">
						<input type="password" name="password" value="" size="10">
					</td>
				</tr>
				<tr>
					<td class="prviStupac">
					</td>
					<td class="drugiStupac">
						<input type="submit" name="metoda" value="Prijava">
					</td>
				</tr>
				</table>
				</form>
   		</c:if>
   		
   		<p><a href="/aplikacija5/servleti/register">Registracija novog korisnika</a></p>
   		<p>Lista registriranih autora:</p>
   		<table>
   		<tr>
   			<td>Nadimak</td><td>Ime</td><td>Prezime</td>
   		</tr>
   		<c:forEach var="u" items="${users}">
   			<tr>
   				<td><a href="/aplikacija5/servleti/author/${u.nick}">${u.nick}</a></td>
   				<td>${u.firstName}</td>
   				<td>${u.lastName}</td>
   			</tr>
		</c:forEach>
		</table>
   </body>
</html>