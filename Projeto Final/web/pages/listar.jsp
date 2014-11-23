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
					<th>Volume</th>
					<th>Tempo</th>
					<th>Cor</th>
					<th>Ano de lançamento</th>
					<th>Recomendação</th>
					<th>Região do DVD</th>
					<th>Legenda</th>
					<th>Formato da tela</th>
					<th>País de origem</th>
					<th>Descrição</th>
					<th>Preço</th>
					<th></th>
				</thead>
				<tbody>
					<c:forEach items="${desenhos}" var="desenhoVar">
						<tr>
							<td>${desenhoVar.id}</td>
							<td>${desenhoVar.titulo}</td>
							<td>${desenhoVar.volume}</td>
							<td>${desenhoVar.tempo}</td>
							<td>${desenhoVar.cor.descricao}</td>
							<td>${desenhoVar.anoLancamento}</td>
							<td>${desenhoVar.recomendacao.descricao}</td>
							<td>${desenhoVar.regiaoDvd}</td>
							<td>${desenhoVar.legenda.descricao}</td>
							<td>${desenhoVar.formatoTela.descricao}</td>
							<td>${desenhoVar.paisOrigem.descricao}</td>
							<td>${desenhoVar.descricao}</td>
							<td>${desenhoVar.preco}</td>
							<td>
								<a class="glyphicon glyphicon-pencil" 
								   title="Editar"
								   href="/projetofinal/desenhos/editar/codigo/${desenhoVar.id}"></a>
								<a class="glyphicon glyphicon-trash" 
								   title="Remover"
								   href="/projetofinal/desenhos/remover/codigo/${desenhoVar.id}"></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>