<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/templates/header.jsp" />
    <body>
		<div class="container-fluid">
			<div class="page-header">
				<h1 class="glyphicon glyphicon-folder-open">${pageHeaderTitle}</h1>
			</div>

			<form action="/projetofinal/desenhos/salvar"
				  method="post"
				  class="container form-include">
				<ul class="nav nav-tabs nav-justified">
					<li role="presentation" class="active">
						<a id="dp-tab" 
						   role="tab"
						   data-toggle="tab"
						   aria-controls="dp"
						   aria-expanded="true"
						   href="#dp">Dados principais</a>
					</li>
					<li role="presentation">
						<a id="idioma-tab"
						   role="tab"
						   data-toggle="tab"
						   aria-controls="idioma"
						   aria-expanded="false"
						   href="#idioma">Idiomas</a>
					</li>
					<li role="presentation">
						<a id="episodio-tab"
						   role="tab"
						   data-toggle="tab"
						   aria-controls="idioma"
						   aria-expanded="false"
						   href="#episodio">Episódios</a>
					</li>
				</ul>

				<div class="tab-content vertical-space">
					<div id="dp"
						 role="tabpanel"
						 class="tab-pane fade active in"
						 aria-labelledby="dp-tab">

						<input type="hidden"
							   name="codigo"
							   value="${desenho.id}" />

						<div class="input-group">
							<span class="input-group-addon lb">Título</span>
							<input type="text"
								   name="titulo"
								   value="${desenho.titulo}"
								   class="form-control"
								   required />
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Volume</span>
							<input type="number"
								   name="volume"
								   value="${desenho.volume}"
								   class="form-control" />
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Tempo</span>
							<input type="number"
								   name="tempo"
								   value="${desenho.tempo}"
								   class="form-control" />
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Cor</span>
							<select name="cor"
									value="${desenho.cor}"
									class="form-control">
								<c:forEach items="${cores}"
										   var="corVar">
									<option value="${corVar}"
											${corVar eq desenho.cor ? 'selected' : ''}>
										${corVar.descricao}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Ano de lançamento</span>
							<input type="number"
								   name="ano"
								   value="${desenho.anoLancamento}"
								   class="form-control" />
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Recomendação</span>
							<select name="recomendacao"
									value="${desenho.recomendacao}"
									class="form-control">
								<c:forEach items="${recomendacoes}"
										   var="recomendacaoVar">
									<option value="${recomendacaoVar}"
											${recomendacaoVar eq desenho.recomendacao ? 'selected' : ''}>
										${recomendacaoVar.descricao}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Região do DVD</span>
							<input type="number"
								   name="regiao"
								   value="${desenho.regiaoDvd}"
								   class="form-control" />
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Legenda</span>
							<select name="legenda"
									value="${desenho.legenda}"
									class="form-control">
								<c:forEach items="${legendas}"
										   var="legendaVar">
									<option value="${legendaVar}"
											${legendaVar eq desenho.legenda ? 'selected' : ''}>
										${legendaVar.descricao}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Formato da tela</span>
							<select name="formatoTela"
									value="${desenho.formatoTela}"
									class="form-control">
								<c:forEach items="${formatosTela}"
										   var="formatoVar">
									<option value="${formatoVar}"
											${formatoVar eq desenho.formatoTela ? 'selected' : ''}>
										${formatoVar.descricao}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">País de origem</span>
							<select name="paisOrigem"
									value="${desenho.paisOrigem}"
									class="form-control">
								<c:forEach items="${paisesOrigem}"
										   var="paisVar">
									<option value="${paisVar}"
											${paisVar eq desenho.paisOrigem ? 'selected' : ''}>
										${paisVar.descricao}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Descrição</span>
							<textarea type="text"
									  name="descricao"
									  class="form-control">${desenho.descricao}</textarea>
						</div>

						<div class="input-group">
							<span class="input-group-addon lb">Preço</span>
							<input type="currency"
								   name="preco"
								   value="${desenho.preco}"
								   class="form-control"
								   required />
						</div>
					</div>
					<div id="idioma"
						 role="tabpanel"
						 class="tab-pane fade"
						 aria-labelledby="idioma-tab">

						<div class="input-group">
							<span class="input-group-addon lb">Idioma</span>
							<select name="idioma"
									class="form-control">
								<c:forEach items="${idiomas}"
										   var="idiomaVar">
									<option value="${idiomaVar.id}">
										${idiomaVar.nome}
									</option>
								</c:forEach>
							</select>
							<button id="adicionarIdioma" 
									class="btn btn-default btn-group-justified"
									onclick="return false;">
								<span class="glyphicon glyphicon-plus"></span>
							</button>
						</div>

						<ul id="listaIdiomas" class="list-group">
							<c:forEach items="${desenho.idiomas}"
									   var="idiomaVar">
								<li class="list-group-item">
									<span>${idiomaVar.nome}</span>
									<input type="hidden" name="idiomaId" value="${idiomaVar.id}" />
									<a class="removerIdioma"
									   href="#"
									   title="Remover">
										<span class="glyphicon glyphicon-trash"></span>
									</a>
								</li>
							</c:forEach>
						</ul>

					</div>
					<div id="episodio"
						 role="tabpanel"
						 class="tab-pane fade"
						 aria-labelledby="episodio-tab">

						<div class="input-group">
							
							<span class="input-group-addon lb">Nome</span>
							<input type="text"
								   id="episNome"
								   name="episNome"
								   class="form-control" />
							
							<button id="adicionarEpisodio" 
									class="btn btn-default btn-group-justified"
									onclick="return false;">
								<span class="glyphicon glyphicon-plus"></span>
							</button>
						</div>

						<ul id="listaEpisodios" class="list-group">
							<c:forEach items="${desenho.episodios}"
									   var="episodioVar">
								<li class="list-group-item">
									<span>${episodioVar.nome}</span>
									<input type="hidden" name="episodioNome" value="${episodioVar.nome}" />
									<a class="removerEpisodio"
									   href="#"
									   title="Remover">
										<span class="glyphicon glyphicon-trash"></span>
									</a>
								</li>
							</c:forEach>
						</ul>
						
					</div>
				</div>

				<div class="control">
					<button type="submit" 
							class="btn btn-default" 
							aria-label="Salvar">
						<span class="glyphicon glyphicon-floppy-disk"
							  aria-hidden="true">Salvar</span>
					</button>
					<a href="/projetofinal/acervo" 
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