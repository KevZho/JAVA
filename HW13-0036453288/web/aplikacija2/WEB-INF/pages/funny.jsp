<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" %> 

<% 
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "white" : color;
%>

<html>
	<body bgcolor="<%= color %>" text="<%= session.getAttribute("pickedFgCol") %>">
		<p>
			A curious child asked his mother: “Mommy, why are some of your hairs turning grey?”
		</p>
		<p>
			The mother tried to use this occasion to teach her child: “It is because of you, dear. 
			Every bad action of yours will turn one of my hairs grey!”
		</p>
		<p>
			The child replied innocently: “Now I know why grandmother has only grey hairs on her head.”
		</p>
	</body>
</html>