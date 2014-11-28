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
				<h1>
					<span class="glyphicon glyphicon-folder-open"></span>
					${pageHeaderTitle}
				</h1>
			</div>
			<ul class="nav nav-pills">
				<li role="presentation"><a href="/projetofinal/idiomas/novo">Novo</a></li>
			</ul>
			<table class="table">
				<thead>
					<th>Nome</th>
					<th>Sistema de som</th>
					<th></th>
				</thead>
				<tbody>
					<c:forEach items="${idiomas}" var="idiomaVar">
						<tr>
							<td>${idiomaVar.nome}</td>
							<td>${idiomaVar.sistemaSom.descricao}</td>
							<td>
								<a class="glyphicon glyphicon-pencil" 
								   title="Editar"
								   href="/projetofinal/idiomas/editar/codigo/${idiomaVar.id}"></a>
								<a class="glyphicon glyphicon-trash" 
								   title="Remover"
								   href="/projetofinal/idiomas/remover/codigo/${idiomaVar.id}"></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>