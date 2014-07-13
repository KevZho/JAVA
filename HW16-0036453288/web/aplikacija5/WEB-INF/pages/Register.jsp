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

	<h1>Register</h1>
	<form action="/aplikacija5/servleti/register" method="post">
		<table>
			<tr>
			 	<td class ="prviStupac">First Name</td> 
			 	<td class ="drugiStupac"><input type="text" name="firstName" value='<c:out value="${zapis.firstName}"></c:out>' size="50"><br>
				  	<c:if test="${zapis.imaPogresku('firstName')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('firstName')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td class ="prviStupac">Last Name</td> 
			 	<td class ="drugiStupac"><input type="text" name="lastName" value='<c:out value="${zapis.lastName}"></c:out>' size="50"><br>
				  	<c:if test="${zapis.imaPogresku('lastName')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('lastName')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td class ="prviStupac">E-Mail</td> 
			 	<td class ="drugiStupac"><input type="text" name="email" value='<c:out value="${zapis.email}"></c:out>' size="50"><br>
				  	<c:if test="${zapis.imaPogresku('email')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('email')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td class ="prviStupac">Nick</td> 
			 	<td class ="drugiStupac"><input type="text" name="nick" value='<c:out value="${zapis.nick}"></c:out>' size="50"><br>
				  	<c:if test="${zapis.imaPogresku('nick')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('nick')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td class ="prviStupac">Password</td> 
			 	<td class ="drugiStupac"><input type="password" name="password" value='<c:out value="${zapis.password}"></c:out>' size="50"><br>
				  	<c:if test="${zapis.imaPogresku('password')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('password')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
				<td class ="prviStupac"></td>
				<td class ="drugiStupac">
					<input type="submit" name="metoda" value="Pohrani">
					<input type="submit" name="metoda" value="Odustani">
				</td>
			</tr>
		</table>
	</form>
	
   </body>
</html>