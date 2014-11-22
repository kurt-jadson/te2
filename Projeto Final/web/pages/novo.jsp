<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/templates/header.jsp" />
    <body>
		<div class="container-fluid">
			<div class="page-header">
				<h1 class="glyphicon glyphicon-folder-open">Novo</h1>
			</div>
			
			<form action="/projetofinal/desenhos/salvar"
				  method="post"
				  class="container form-include">
				
				<div class="input-group">
					<span class="input-group-addon lb">Título</span>
					<input type="text"
						   name="titulo"
						   class="form-control" />
			    </div>
				
				<div class="input-group">
					<span class="input-group-addon lb">Volume</span>
					<input type="number"
						   name="volume"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Tempo</span>
					<input type="number"
						   name="tempo"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Cor</span>
					<select name="cor"
							class="form-control">
						<c:forEach items="${cores}"
								   var="corVar">
							<option value="${corVar}">${corVar.descricao}</option>
						</c:forEach>
					</select>
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Ano de lançamento</span>
					<input type="number"
						   name="ano"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Recomendação</span>
					<input type="text"
						   name="recomendacao"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Região do DVD</span>
					<input type="number"
						   name="regiao"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Legenda</span>
					<input type="text"
						   name="legenda"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Formato da Tela</span>
					<input type="text"
						   name="formatoTela"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">País de Origem</span>
					<input type="text"
						   name="paisOrigem"
						   class="form-control" />
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Descrição</span>
					<textarea type="text"
							  name="paisOrigem"
							  class="form-control"></textarea>
			    </div>

				<div class="input-group">
					<span class="input-group-addon lb">Preço</span>
					<input type="currency"
						   name="preco"
						   class="form-control" />
			    </div>
				
				<button type="submit" 
						class="btn btn-block btn-default" 
						aria-label="Salvar">
					<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true">Salvar</span>
				</button>

			</form>
		</div>
	</body>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>