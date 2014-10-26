<%-- 
    Document   : listar
    Created on : 25/10/2014, 01:08:52
    Author     : kurt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
		<h1>Atividades</h1>
		<c:forEach items="${atividades}" var="atividadeVar">
			<p>${atividadeVar.descricao}</p>
		</c:forEach>
    </body>
</html>
