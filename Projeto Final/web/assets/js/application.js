(function($) {
	$(function() {

		var registrarEventoRemoverIdioma = function() {
			$('.removerIdioma').on('click', function() {
				$(this).parent().remove();
			});
		};

		$('#adicionarIdioma').on('click', function() {
			var sel = $('select[name="idioma"]');
			var value = sel.val();
			var valueText = sel.find('option[value="' + value + '"]').text().trim();

			$('#listaIdiomas').append('<li class="list-group-item"><span>' + valueText
					+ '</span><input type="hidden" name="idiomaId" value="' + value
					+ '" /><a class="removerIdioma" href="#" title="Remover">'
					+ '<span class="glyphicon glyphicon-trash"></span></a></li>');
			
			//TODO: remover os listeners antes de registrar outros
			registrarEventoRemoverIdioma();
		});
		
		registrarEventoRemoverIdioma();
	});
})(jQuery);