<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Desenho Store</title>
	<link href="/projetofinal/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="/projetofinal/assets/css/application.css" rel="stylesheet">
</head>
<nav class="navbar navbar-default" role="navigation">

	<div class="container-fluid">

		<div class="navbar-header">
			<button type="button" 
					class="navbar-toggle collapsed" 
					data-toggle="collapse" 
					data-target="#menu">
				<span class="sr-only"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/projetofinal/acervo">Desenho Store</a>
		</div>

		<form action="/projetofinal/desenhos/buscar"
			  method="post"
			  class="navbar-form navbar-right"
			  role="search">
			<div class="form-group">
			  <input type="text" 
					 name="qtitulo" 
					 class="form-control"
					 placeholder="Título">
			</div>
			<button type="submit" class="btn btn-default">Buscar</button>
        </form>
		
		<div id="menu"
			 class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="${menu eq 'acervo' ? 'active' : ''}">
					<a href="/projetofinal/acervo">
						<span class="glyphicon glyphicon-folder-open"></span>
						Acervo
					</a>
				</li>
				<li class="${menu eq 'idiomas' ? 'active' : ''}">
					<a href="/projetofinal/idiomas/listar">
						<span class="glyphicon glyphicon-flag"></span>
						Idiomas
					</a>
				</li>
				<li>
					<a href="/projetofinal/logout">
						<span class="glyphicon glyphicon-off"></span>
						Sair
					</a>
				</li>
			</ul>
		</div>

	</div>

</nav>