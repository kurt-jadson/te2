<%-- 
    Document   : listar
    Created on : 25/10/2014, 01:08:52
    Author     : kurt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/templates/header.jsp" />
    <body>
		<div class="container-fluid">
			<div class="page-header">
				<h1 class="glyphicon glyphicon-folder-open">Acervo</h1>
			</div>
			<table class="table">
				<thead>
					<th>Código</th>
					<th>Título</th>
					<th>Cor</th>
					<th>Preço</th>
				</thead>
				<tbody>
					<c:forEach items="${desenhos}" var="desenhoVar">
						<tr>
							<td>${desenhoVar.id}</td>
							<td>${desenhoVar.titulo}</td>
							<td>${desenhoVar.cor.descricao}</td>
							<td>${desenhoVar.preco}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>