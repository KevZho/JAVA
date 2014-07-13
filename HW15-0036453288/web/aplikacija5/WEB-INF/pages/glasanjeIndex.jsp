<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<html>
	<p> <a href="index.html">Povratak na izbor anketa</a></p>

	<h1>${ poll.title }</h1>
	<p>${ poll.message }
	</p>
	<ol>
		<c:forEach var="r" items="${options}">
			<li><a href="glasanje-glasaj?id=${r.id}">${r.optionTitle}</a></li>
		</c:forEach>
	</ol>
	</body>
</html>
