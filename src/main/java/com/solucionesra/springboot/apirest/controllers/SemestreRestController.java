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

import com.solucionesra.springboot.apirest.models.entity.Semestre;
import com.solucionesra.springboot.apirest.models.services.ISemestreService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class SemestreRestController {
	
	@Autowired
	private ISemestreService semestreService;
	
	@Secured({"ROLE_V_SEMESTRE"})
	@GetMapping("semestre")
	public List<Semestre> index(){
		return semestreService.findAll();
	}
	
	@Secured({"ROLE_M_SEMESTRE"})
	@GetMapping("/semestre/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Semestre semestre = null;
		Map<String, Object> response = new HashMap<>();
		try {
			semestre = semestreService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(semestre==null) {
			response.put("mensaje", "El semestre con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Semestre>(semestre, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_SEMESTRE"})
	@PostMapping("/semestre")
	public ResponseEntity<?> create(@Valid @RequestBody Semestre semestre, BindingResult result) {
		
		Semestre semestreNuevo = null;
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
			semestreNuevo= semestreService.save(semestre);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El semestre ha sido creado con exito!");
		response.put("semestre", semestreNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_SEMESTRE"})
	@PutMapping("/semestre/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Semestre semestre, BindingResult result, @PathVariable Long id) {
		
		Semestre semestreActual = semestreService.findById(id);
		
		Semestre semestreActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(semestreActual == null) {
			response.put("mensaje", "No se pudo editar el semestre con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			semestreActual.setDescripcion_semestre(semestre.getDescripcion_semestre());
			semestreActual.setPosicion(semestre.getPosicion());
			
			semestreActualizado = semestreService.save(semestreActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el semestre en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El semestre ha sido actualizado con exito!");
		response.put("malla", semestreActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_SEMESTRE"})
	@DeleteMapping("/semestre/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			semestreService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el semestre en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El semestre se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/semestre/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			semestreService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar el semestre en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El semestre se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/semestre/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			semestreService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar el semestre en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El semestre se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
}
