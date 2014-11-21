<!DOCTYPE html>
<html>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link href="/projetofinal/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/projetofinal/assets/css/signin.css" rel="stylesheet">
    <body>

		<div class="container">

		  <form class="form-signin" role="form">
			<h2 class="form-signin-heading">Acesso ao sistema</h2>
			<label for="inputEmail" class="sr-only">Usuário</label>
			<input id="inputEmail"
				   type="email"
				   class="form-control"
				   placeholder="Usuário"
				   required autofocus>
			<label for="inputPassword"
				   class="sr-only">Senha</label>
			<input id="inputPassword"
				   type="password"
				   class="form-control"
				   placeholder="Senha"
				   required>
			<button class="btn btn-lg btn-primary btn-block"
					type="submit">Entrar</button>
		  </form>

		</div>
	</body>
  
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</html>
