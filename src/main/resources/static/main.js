$(document).ready(function() {  // Asegurar que el documento HTML esté completamente cargado.

	$("button").click(function() { // Selecciona el elemento <button> de index.html - Agrega un evento de click

		$.ajax({  // Enviar una solicitud HTTP al servidor.
			type : 'POST',  // Esto indica que se enviarán datos al servidor.
			url : 'http://localhost:8080/shortenurl',  // URL a la que se enviará la solicitud. ‘shortenurl’ es la clase principal donde se aplica el Hash. 
			data : JSON.stringify({ // Carga util Se convierte en una cadena JSON para su uso. 
				"full_url" : $("#urlinput").val()
			}), // El valor de full_url se obtiene del elemento con el ID urlinput en el html. 

			contentType : "application/json; charset=utf-8", // Carga útil en formato JSON en utf-8.
			success : function(data) {
				$("#shorturltext").val(data.short_url); 
			} // El valor de short_url se asigna al elemento con el ID shorturltext del HTML.
		});
	});
});