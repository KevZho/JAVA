<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Radionica</title>
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
			
		</style>
	</head>
   
   <body>
  	<h1>
  		<c:choose>
  			<c:when test="${zapis.id.isEmpty()}">
  				Nova radionica
  			</c:when>
  			<c:otherwise>
  				Uređivanje radionice
  			</c:otherwise>
  		</c:choose>
  	</h1>

	<form action="save" method="post">
		<input type="hidden" name="id" value='<c:out value="${zapis.id}"></c:out>'>
		<table>
			<tr>
				<td class ="prviStupac">Naziv</td>
			  	<td class ="drugiStupac">
				  	 <input type="text" name="naziv" value='<c:out value="${zapis.naziv}"></c:out>' size="40"><br>
				  	<c:if test="${zapis.imaPogresku('naziv')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('naziv')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
				<td class ="prviStupac">Datum</td> 
				<td class ="drugiStupac">
					<input type="text" name="datum" value='<c:out value="${zapis.datum}"></c:out>' size="15"><br>
				  	<c:if test="${zapis.imaPogresku('datum')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('datum')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
				<td class ="prviStupac">Oprema</td>
				<td class ="drugiStupac">
					<select name="oprema" multiple="multiple" size="10">	 
					    <c:forEach var="r" items="${opremaMap}">
					   		<c:choose>
					   			<c:when test="${zapis.oprema.contains(r.key.toString())}">
					   				<option selected="selected" value="${r.key}"><c:out value="${r.value.vrijednost}"></c:out></option>
					   			</c:when>
					   			<c:otherwise>
					   			<option value="${r.key}"><c:out value="${r.value.vrijednost}"></c:out></option>
					   			</c:otherwise>
					   		</c:choose>
					    </c:forEach>
			 		</select>
				</td>
			</tr>
			<tr>
				<td class ="prviStupac">Trajanje</td>
				<td class ="drugiStupac">
					<% boolean first = true;  %>
					<select name="trajanje" size="1">
					 <c:forEach var="r" items="${trajanjeMap}">
					 	<c:choose>
					 		<c:when test="<%= first == true %>">
					 			<option value="${r.key}" selected="selected"><c:out value="${r.value.vrijednost}"></c:out>
					 			<% first = false; %>
					 		</c:when>
					 		<c:when test="${zapis.trajanje.equals(r.key.toString())}">
					 			<option value="${r.key}" selected="selected"><c:out value="${r.value.vrijednost}"></c:out>
					 		</c:when>
					 		<c:otherwise>
					 			<option value="${r.key}"><c:out value="${r.value.vrijednost}"></c:out>
					 		</c:otherwise>
					 	</c:choose>
					 </c:forEach>
					</select>
				</td>	
			</tr>
			<tr>
				<td class ="prviStupac">
				Publika </td>
				<td class ="drugiStupac">
				 	<c:forEach var="r" items="${publikaMap}">
				 		<c:choose>
				 			<c:when test="${zapis.publika.contains(r.key.toString())}">
				 				<input type="checkbox" name="publika" value="${r.key}" checked><c:out value="${r.value.vrijednost}"></c:out><br>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="publika" value="${r.key}"><c:out value="${r.value.vrijednost}"></c:out><br>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${zapis.imaPogresku('publika')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('publika')}"></c:out> </div>
				  	</c:if>	
				</td>
			</tr>
			<tr>
			 	<td class ="prviStupac">Maksimalno polaznika</td> 
			 	<td class ="drugiStupac"><input type="text" name="maksPolaznika" value='<c:out value="${zapis.maksPolaznika}"></c:out>' size="5"><br>
				  	<c:if test="${zapis.imaPogresku('maksPolaznika')}">
				  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('maksPolaznika')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
		 	<td class ="prviStupac">E-Mail</td> 
		  	<td class ="drugiStupac"><input type="text" name="email" value='<c:out value="${zapis.email}"></c:out>' size="30"><br>
			  	<c:if test="${zapis.imaPogresku('email')}">
			  		<div class="greska"><c:out value="${zapis.dohvatiPogresku('email')}"></c:out> </div>
			  	</c:if>
		  	</td>
			</tr>
			<tr>
				<td class ="prviStupac">Dopuna</td>
				<td class ="drugiStupac">
					<textarea name="dopuna" rows="6" cols="70">${zapis.dopuna}</textarea>
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