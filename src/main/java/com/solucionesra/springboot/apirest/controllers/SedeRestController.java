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
import com.solucionesra.springboot.apirest.models.entity.Sede;
import com.solucionesra.springboot.apirest.models.services.ICiudadService;
import com.solucionesra.springboot.apirest.models.services.ISedeService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class SedeRestController {
	
	@Autowired
	private ISedeService sedeService;
	
	@Autowired
	private ICiudadService ciudadService;
	
	@Secured({"ROLE_V_SEDE"})
	@GetMapping("/sede")
	public List<Sede> index(){
		return sedeService.findAll();
	}
	
	@Secured({"ROLE_M_SEDE"})
	@GetMapping("/sede/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Sede sede = null;
		Map<String, Object> response = new HashMap<>();
		try {
			sede = sedeService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(sede==null) {
			response.put("mensaje", "La sede con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sede>(sede, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_SEDE"})
	@PostMapping("/sede")
	public ResponseEntity<?> create(@Valid @RequestBody Sede sede, BindingResult result) {
		
		Sede sedeNueva = null;
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
			sedeNueva= sedeService.save(sede);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La sede ha sido creado con exito!");
		response.put("sede", sedeNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_SEDE"})
	@PutMapping("/sede/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Sede sede, BindingResult result, @PathVariable Long id) {
		
		Sede sedeActual = sedeService.findById(id);
		
		Sede sedeActualizada = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(sedeActual == null) {
			response.put("mensaje", "No se pudo editar la sede con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			sedeActual.setNombre_sede(sede.getNombre_sede());
			sedeActual.setCiudad(sede.getCiudad());
			sedeActualizada = sedeService.save(sedeActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la ciudad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","La sede ha sido actualizado con exito!");
		response.put("sede", sedeActualizada);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_SEDE"})
	@DeleteMapping("/sede/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			sedeService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar la sede en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La sede se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/sede/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			sedeService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar la sede en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La sede se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/sede/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			sedeService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar la sede en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La sede se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/sede/ciudades")
	public List<Ciudad> listarCiudades(){
		return ciudadService.findAll();
	}
	
}
