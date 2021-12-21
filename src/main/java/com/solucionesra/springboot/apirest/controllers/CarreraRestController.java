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

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Facultad;
import com.solucionesra.springboot.apirest.models.services.ICarreraService;
import com.solucionesra.springboot.apirest.models.services.IFacultadService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class CarreraRestController {
	
	@Autowired
	private ICarreraService carreraService;
	
	@Autowired
	private IFacultadService facultadService;
	
	@Secured({"ROLE_ADMIN","ROLE_V_CARRERA"})
	@GetMapping("/carrera")
	public List<Carrera> index(){
		return carreraService.findAllCarreras();
	}
	
	@Secured({"ROLE_ADMIN","ROLE_M_CARRERA"})
	@GetMapping("/carrera/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Carrera carrera = null;
		Map<String, Object> response = new HashMap<>();
		try {
			carrera = carreraService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(carrera==null) {
			response.put("mensaje", "La carrera con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Carrera>(carrera, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_C_CARRERA"})
	@PostMapping("/carrera")
	public ResponseEntity<?> create(@Valid @RequestBody Carrera carrera, BindingResult result) {
		
		Carrera carreraNueva = null;
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
			carreraNueva= carreraService.save(carrera);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La carrera ha sido creado con exito!");
		response.put("carrera", carreraNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_M_CARRERA"})
	@PutMapping("/carrera/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Carrera carrera, BindingResult result, @PathVariable Long id) {
		
		Carrera carreraActual = carreraService.findById(id);
		
		Carrera carreraActualizada = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(carreraActual == null) {
			response.put("mensaje", "No se pudo editar la carrera con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			carreraActual.setNombre_carrera(carrera.getNombre_carrera());
			carreraActual.setCodigo_carrera(carrera.getCodigo_carrera());
			carreraActual.setFacultad(carrera.getFacultad());
			carreraActualizada = carreraService.save(carreraActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","La carrera ha sido actualizado con exito!");
		response.put("carrera", carreraActualizada);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_E_CARRERA"})
	@DeleteMapping("/carrera/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			carreraService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar la carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La carrera se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/carrera/facultades")
	public List<Facultad> listarFacultades(){
		return facultadService.findAll();
	}
	
	@PutMapping("/carrera/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			carreraService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar la carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La carrera se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/carrera/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			carreraService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar la carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La carrera se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
