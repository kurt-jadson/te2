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
				<h1 class="glyphicon glyphicon-user">Ranking de políticos</h1>
			</div>
			<table class="table">
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
		</div>
	</body>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>