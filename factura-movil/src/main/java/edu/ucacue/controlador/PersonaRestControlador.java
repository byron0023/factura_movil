package edu.ucacue.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ucacue.infraestructura.repositorio.PersonaRepositorio;
import edu.ucacue.modelo.Persona;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")

public class PersonaRestControlador {

	@Autowired
	private PersonaRepositorio personaRepositorio;
	
	//guardar persona
		@PostMapping("/cliente")
		public ResponseEntity<?> saveCliente(@RequestBody Persona persona, BindingResult result) {
			Persona personaGrabar;
			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {
				List<String> errores = result.getFieldErrors().stream()
						.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());
				response.put("Los errores son", errores);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			try {
				personaGrabar = personaRepositorio.save(persona);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar el inserción en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("mensaje", "El Cliente se ha insertado con éxito en la BD");
			response.put("Cliente", personaGrabar);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
	
	//listar personas
	@GetMapping("/clientes")
	public List<Persona> index() {
		return personaRepositorio.findAll();
	}
}
