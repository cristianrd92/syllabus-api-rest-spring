package com.solucionesra.springboot.apirest.controllers;

import java.util.ArrayList;
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

import com.solucionesra.springboot.apirest.models.entity.Perfil;
import com.solucionesra.springboot.apirest.models.entity.Permiso;
import com.solucionesra.springboot.apirest.models.services.IPerfilService;
import com.solucionesra.springboot.apirest.models.services.IPermisoService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class PerfilRestController {
	
	@Autowired
	private IPerfilService perfilService;
	
	@Autowired
	private IPermisoService permisoService;
	
	@Secured({"ROLE_V_PERFIL"})
	@GetMapping("/perfil")
	public List<Perfil> index(){
		return perfilService.findAll();
	}
	
	@Secured({"ROLE_M_PERFIL"})
	@GetMapping("/perfil/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Perfil perfil = null;
		Map<String, Object> response = new HashMap<>();
		try {
			perfil = perfilService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(perfil==null) {
			response.put("mensaje", "El perfil con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_PERFIL"})
	@PostMapping("/perfil")
	public ResponseEntity<?> create(@Valid @RequestBody Perfil perfil, BindingResult result) {
		
		List<Permiso> permisosActuales = permisoService.findAllord();
		List<Permiso> permisos = new ArrayList<>();
		for (int i = 0; i < perfil.getTemporales().length; i++) {
			int permiso = perfil.getTemporales()[i];
			for(int p=0;p<permisosActuales.size();p++) {
				if(permiso==permisosActuales.get(p).getId()) {
					permisos.add(permisosActuales.get(p));
				}
			}
		}
		Perfil perfilNuevo = null;
		perfil.setPermisos(permisos);
		perfil.setDescripcion(perfil.getName());
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
			perfilNuevo= perfilService.save(perfil);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El perfil ha sido creado con exito!");
		response.put("perfil", perfilNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_PERFIL"})
	@PutMapping("/perfil/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Perfil perfil, BindingResult result, @PathVariable Long id) {
		
		Perfil perfilActual = perfilService.findById(id);
		
		Perfil perfilActualizado = null;
		List<Permiso> permisosActuales = permisoService.findAllord();
		List<Permiso> permisos = new ArrayList<>();
		for (int i = 0; i < perfil.getTemporales().length; i++) {
			int permiso = perfil.getTemporales()[i];
			for(int p=0;p<permisosActuales.size();p++) {
				if(permiso==permisosActuales.get(p).getId()) {
					permisos.add(permisosActuales.get(p));
				}
			}
		}
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(perfilActual == null) {
			response.put("mensaje", "No se pudo editar el perfil con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			perfilActual.setPermisos(permisos);
			perfilActual.setDescripcion(perfil.getDescripcion());
			perfilActual.setName(perfil.getName());
			perfilActualizado = perfilService.save(perfilActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el perfil en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El perfil ha sido actualizado con exito!");
		response.put("perfil", perfilActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_PERFIL"})
	@DeleteMapping("/perfil/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			perfilService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el perfil en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El perfil se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/perfil/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			perfilService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar el perfil en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El perfil se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/perfil/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			perfilService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar el perfil en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El perfil se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/perfil/roles")
	public List<Permiso> listarRoles(){
		return permisoService.findAllord();
	}
}
