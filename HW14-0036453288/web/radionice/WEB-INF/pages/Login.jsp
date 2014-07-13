<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
		</style>
	</head>
   <body>
   		<h2>Unesite podatke za prijavu</h2>
   		
   		<p class="greska">
   			<c:if test="${greska != false}">
   				Korisničko ime ili lozinka su pogrešni!!
   			</c:if>
   		</p>
   		
   		<form action="login" method="post">
	   		<table>
	   		<tr>
	   			<td class="prviStupac">
	   				Korisničko ime
	   			</td>
	   			<td class="drugiStupac">
	   				<input type="text" name="username" value="" size="10">
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
   </body>
</html>