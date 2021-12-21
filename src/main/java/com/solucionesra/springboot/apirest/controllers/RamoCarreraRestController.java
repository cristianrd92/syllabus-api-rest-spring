package com.solucionesra.springboot.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.solucionesra.springboot.apirest.models.entity.Periodo;
import com.solucionesra.springboot.apirest.models.entity.Ramo;
import com.solucionesra.springboot.apirest.models.entity.RamoCarrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.ICarreraService;
import com.solucionesra.springboot.apirest.models.services.IPeriodoService;
import com.solucionesra.springboot.apirest.models.services.IRamoCarreraService;
import com.solucionesra.springboot.apirest.models.services.IRamoService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class RamoCarreraRestController {
	
	@Autowired
	private IRamoCarreraService ramoCarreraService;
	
	@Autowired
	private ICarreraService carreraService;
	
	@Autowired
	private IRamoService ramoService;
	
	@Autowired
	private IPeriodoService periodoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Secured({"ROLE_V_RAMO_CARRERA"})
	@GetMapping("/ramo_carrera")
	public List<RamoCarrera> index(){
		return ramoCarreraService.findAll();
	}
	
	@Secured({"ROLE_M_RAMO_CARRERA"})
	@GetMapping("/ramo_carrera/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		RamoCarrera ramoCarrera = null;
		Map<String, Object> response = new HashMap<>();
		try {
			ramoCarrera = ramoCarreraService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(ramoCarrera==null) {
			response.put("mensaje", "El ramo carrera con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RamoCarrera>(ramoCarrera, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_RAMO_CARRERA"})
	@PostMapping("/ramo_carrera")
	public ResponseEntity<?> create(@Valid @RequestBody RamoCarrera ramoCarrera, BindingResult result) {
		
		RamoCarrera ramoCarreraNuevo = null;
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
			ramoCarreraNuevo= ramoCarreraService.save(ramoCarrera);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El ramo carrera ha sido creado con exito!");
		response.put("ramoCarrera", ramoCarreraNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_RAMO_CARRERA"})
	@PutMapping("/ramo_carrera/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody RamoCarrera ramoCarrera, BindingResult result, @PathVariable Long id) {
		
		RamoCarrera ramoCarreraActual = ramoCarreraService.findById(id);
		
		RamoCarrera ramoCarreraActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(ramoCarreraActual == null) {
			response.put("mensaje", "No se pudo editar el ramo carrera con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			ramoCarreraActual.setCreditos(ramoCarrera.getCreditos());
			ramoCarreraActual.setAnio(ramoCarrera.getAnio());
			ramoCarreraActual.setCarrera(ramoCarrera.getCarrera());
			ramoCarreraActual.setPeriodo(ramoCarrera.getPeriodo());
			ramoCarreraActual.setRamo(ramoCarrera.getRamo());
			ramoCarreraActual.setUsuario(ramoCarrera.getUsuario());
			ramoCarreraActualizado = ramoCarreraService.save(ramoCarreraActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el ramo carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El ramo carrera ha sido actualizado con exito!");
		response.put("ramoCarrera", ramoCarreraActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_RAMO_CARRERA"})
	@DeleteMapping("/ramo_carrera/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			ramoCarreraService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el ramo carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El ramo carrera se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/ramo_carrera/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ramoCarreraService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar el ramo carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El ramo carrera se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/ramo_carrera/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ramoCarreraService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar el ramo carrera en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El ramo carrera se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/ramo_carrera/carreras")
	public List<Carrera> listarCarreras(){
		return carreraService.findAllCarreras();
	}
	
	@GetMapping("/ramo_carrera/ramos")
	public List<Ramo> listarRamos(){
		return ramoService.findAll();
	}
	
	@GetMapping("/ramo_carrera/periodos")
	public List<Periodo> listarPeriodos(){
		return periodoService.findAll();
	}
	@GetMapping("/ramo_carrera/usuarios")
	public List<Usuario> listarUsuarios(){
		return usuarioService.findAll();
	}

	@GetMapping("/ramos_docente/{id}")
	public Set<RamoCarrera> ramosDocente(@PathVariable Long id){
		Usuario usuario = usuarioService.findById(id);
		return usuario.getRamoCarrera();
	}
	
}
