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
		<table>
			<thead>
				<th>Código</th>
				<th>Descrição</th>
				<th>Data cadastro</th>
				<th>Estágio</th>
			</thead>
			<tbody>
				<c:forEach items="${atividades}" var="atividadeVar">
					<tr>
						<td>${atividadeVar.codigo}</td>
						<td>${atividadeVar.descricao}</td>
						<td>${atividadeVar.dataCadastro}</td>
						<td>${atividadeVar.estagio}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</body>
</html>
