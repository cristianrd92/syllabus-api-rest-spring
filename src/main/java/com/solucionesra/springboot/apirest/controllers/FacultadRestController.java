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

import com.solucionesra.springboot.apirest.models.entity.Facultad;
import com.solucionesra.springboot.apirest.models.entity.Sede;
import com.solucionesra.springboot.apirest.models.services.IFacultadService;
import com.solucionesra.springboot.apirest.models.services.ISedeService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class FacultadRestController {
	
	@Autowired
	private IFacultadService facultadService;
	
	@Autowired
	private ISedeService sedeService;
	
	@Secured({"ROLE_V_FACULTAD"})
	@GetMapping("/facultad")
	public List<Facultad> index(){
		return facultadService.findAll();
	}
	
	@Secured({"ROLE_M_FACULTAD"})
	@GetMapping("/facultad/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Facultad facultad = null;
		Map<String, Object> response = new HashMap<>();
		try {
			facultad = facultadService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(facultad==null) {
			response.put("mensaje", "La facultad con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Facultad>(facultad, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_FACULTAD"})
	@PostMapping("/facultad")
	public ResponseEntity<?> create(@Valid @RequestBody Facultad facultad, BindingResult result) {
		
		Facultad facultadNueva = null;
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
			facultadNueva= facultadService.save(facultad);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La facultad ha sido creado con exito!");
		response.put("facultad", facultadNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_FACULTAD"})
	@PutMapping("/facultad/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Facultad facultad, BindingResult result, @PathVariable Long id) {
		
		Facultad facultadActual = facultadService.findById(id);
		
		Facultad facultadActualizada = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(facultadActual == null) {
			response.put("mensaje", "No se pudo editar la facultad con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			facultadActual.setNombre_facultad(facultad.getNombre_facultad());
			facultadActual.setSede(facultad.getSede());
			facultadActualizada = facultadService.save(facultadActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la facultad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","La facultad ha sido actualizado con exito!");
		response.put("facultad", facultadActualizada);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_FACULTAD"})
	@DeleteMapping("/facultad/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			facultadService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar la facultad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La facultad se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/facultad/sedes")
	public List<Sede> listarSedes(){
		return sedeService.findAll();
	}
	
	@PutMapping("/facultad/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			facultadService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar la facultad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La facultad se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/facultad/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			facultadService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar la facultad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La facultad se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
