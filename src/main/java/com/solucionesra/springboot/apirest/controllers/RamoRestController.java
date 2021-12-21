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

import com.solucionesra.springboot.apirest.models.entity.Ramo;
import com.solucionesra.springboot.apirest.models.services.IRamoService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class RamoRestController {
	
	@Autowired
	private IRamoService ramoService;
	
	@Secured({"ROLE_V_RAMO"})
	@GetMapping("/ramo")
	public List<Ramo> index(){
		return ramoService.findAll();
	}
	
	@Secured({"ROLE_M_RAMO"})
	@GetMapping("/ramo/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Ramo ramo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			ramo = ramoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(ramo==null) {
			response.put("mensaje", "El ramo con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Ramo>(ramo, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_RAMO"})
	@PostMapping("/ramo")
	public ResponseEntity<?> create(@Valid @RequestBody Ramo ramo, BindingResult result) {
		
		//Ramo ramoNuevo = null;
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
			ramoService.insertRamo(ramo);
		}catch(DataAccessException e) {
			if(e.getMostSpecificCause().getMessage().equals("La consulta no retornó ningún resultado.")) {
				response.put("mensaje", "Ramo creado con exito");
				response.put("ramo", ramo);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			}
			String error = e.getMostSpecificCause().getMessage();
			String[] error2 = error.split("Where");
			error = error2[0].replace("Hint:", "");
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", error);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_RAMO"})
	@PutMapping("/ramo/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Ramo ramo, BindingResult result, @PathVariable Long id) {
		
		Ramo ramoActual = ramoService.findById(id);
		
		Ramo ramoActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(ramoActual == null) {
			response.put("mensaje", "No se pudo editar el ramo con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			ramoActual.setNombre_ramo(ramo.getNombre_ramo());
			ramoActual.setCodigo_ramo(ramo.getCodigo_ramo());
			ramoActual.setNombre_corto(ramo.getNombre_corto());
			ramoActual.setVigente(ramo.isVigente());
			
			ramoActualizado = ramoService.save(ramoActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el ramo en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El ramo ha sido actualizado con exito!");
		response.put("ramo", ramoActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_RAMO"})
	@DeleteMapping("/ramo/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			ramoService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el ramo en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El ramo se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/ramo/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ramoService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar el ramo en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El ramo se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/ramo/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ramoService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar el ramo en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El ramo se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
