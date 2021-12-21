package com.solucionesra.springboot.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solucionesra.springboot.apirest.models.entity.Ciudad;
import com.solucionesra.springboot.apirest.models.services.ICiudadService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class CiudadRestController {
	
	@Autowired
	private ICiudadService ciudadService;
	
	@Secured({"ROLE_V_CIUDAD"})
	@GetMapping("/ciudad")
	public List<Ciudad> index(){
		return ciudadService.findAll();
	}
	
	@Secured({"ROLE_M_CIUDAD"})
	@GetMapping("/ciudad/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Ciudad ciudad = null;
		Map<String, Object> response = new HashMap<>();
		try {
			ciudad = ciudadService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(ciudad==null) {
			response.put("mensaje", "La ciudad con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Ciudad>(ciudad, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_CIUDAD"})
	@PostMapping("/ciudad")
	public ResponseEntity<?> create(@Valid @RequestBody Ciudad ciudad, BindingResult result) {
		
		//Ciudad ciudadNueva = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			ciudadService.insertCiudad(ciudad);
		}catch(DataAccessException e) {
			if(e.getMostSpecificCause().getMessage().equals("La consulta no retornó ningún resultado.")) {
				response.put("mensaje", "Ciudad creada con exito");
				response.put("ciudad", ciudad);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			}
			String error = e.getMostSpecificCause().getMessage();
			String[] error2 = error.split("Where");
			error = error2[0].replace("Hint:", "");
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", error);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_CIUDAD"})
	@PutMapping("/ciudad/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Ciudad ciudad, BindingResult result, @PathVariable Long id) {
		
		Ciudad ciudadActual = ciudadService.findById(id);
		
		Ciudad ciudadActualizada = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(ciudadActual == null) {
			response.put("mensaje", "No se pudo editar la ciudad con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			ciudadActual.setNombre_ciudad(ciudad.getNombre_ciudad());
			
			ciudadActualizada = ciudadService.save(ciudadActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la ciudad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","La ciudad ha sido actualizado con exito!");
		response.put("ciudad", ciudadActualizada);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_CIUDAD"})
	@DeleteMapping("/ciudad/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			ciudadService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar la ciudad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La ciudad se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/ciudad/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ciudadService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar la ciudad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La ciudad se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/ciudad/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ciudadService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar la ciudad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La ciudad se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
