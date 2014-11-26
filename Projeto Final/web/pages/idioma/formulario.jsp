<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/templates/header.jsp" />
    <body>
		<div class="container-fluid">
			<div class="page-header">
				<h1 class="glyphicon glyphicon-flag">${pageHeaderTitle}</h1>
			</div>

			<form action="/projetofinal/idiomas/salvar"
				  method="post"
				  class="container form-include">

				<input type="hidden"
					   name="codigo"
					   value="${idioma.id}" />

				<div class="input-group">
					<span class="input-group-addon lb">Nome</span>
					<input type="text"
						   name="nome"
						   value="${idioma.nome}"
						   class="form-control"
						   required />
				</div>

				<div class="input-group">
					<span class="input-group-addon lb">Sistema de som</span>
					<select name="sistemaSom"
							value="${idioma.sistemaSom}"
							class="form-control">
						<c:forEach items="${sistemasSom}"
								   var="sistemaSomVar">
							<option value="${sistemaSomVar}"
									${sistemaSomVar eq idioma.sistemaSom ? 'selected' : ''}>
								${sistemaSomVar.descricao}
							</option>
						</c:forEach>
					</select>
				</div>

				<div class="control">
					<button type="submit" 
							class="btn btn-default" 
							aria-label="Salvar">
						<span class="glyphicon glyphicon-floppy-disk"
							  aria-hidden="true">Salvar</span>
					</button>
					<a href="/projetofinal/idiomas/listar" 
					   class="btn btn-default" 
					   aria-label="Cancelar">
						<span class="glyphicon glyphicon-remove"
							  aria-hidden="true">Cancelar</span>
					</a>
				</div>

			</form>
		</div>
	</body>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>