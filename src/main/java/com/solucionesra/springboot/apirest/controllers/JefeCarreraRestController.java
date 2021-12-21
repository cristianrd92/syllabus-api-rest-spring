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
import com.solucionesra.springboot.apirest.models.entity.JefeCarrera;
import com.solucionesra.springboot.apirest.models.entity.Perfil;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.IJefeCarreraService;
import com.solucionesra.springboot.apirest.models.services.IPerfilService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class JefeCarreraRestController {
	
	@Autowired
	private IJefeCarreraService jefeCarreraService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPerfilService perfilService;
	
	@Secured({"ROLE_V_JEFE_CARRERA"})
	@GetMapping("/jefe_carrera")
	public List<JefeCarrera> index(){
		return jefeCarreraService.findAll();
	}
	
	@Secured({"ROLE_M_JEFE_CARRERA"})
	@GetMapping("/jefe_carrera/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		JefeCarrera jefeCarrera = null;
		Map<String, Object> response = new HashMap<>();
		try {
			jefeCarrera = jefeCarreraService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(jefeCarrera==null) {
			response.put("mensaje", "El director de escuela con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<JefeCarrera>(jefeCarrera, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_JEFE_CARRERA"})
	@PostMapping("/jefe_carrera")
	public ResponseEntity<?> create(@Valid @RequestBody JefeCarrera jefeCarrera, BindingResult result) {
		
		JefeCarrera jefeCarreraNuevo = null;
		
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
			jefeCarreraNuevo= jefeCarreraService.save(jefeCarrera);
			Usuario usuario = usuarioService.findById(jefeCarrera.getUsuario().getId());
			List<Perfil> perfiles = usuario.getPerfiles();
			long id = 4;
			Perfil perfil = perfilService.findById(id);
			perfiles.add(perfil);
			usuario.setPerfiles(perfiles);
			usuarioService.save(usuario);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El director de escuela ha sido creado con exito!");
		response.put("jefeCarrera", jefeCarreraNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_JEFE_CARRERA"})
	@PutMapping("/jefe_carrera/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody JefeCarrera jefeCarrera, BindingResult result, @PathVariable Long id) {
		
		JefeCarrera jefeCarreraActual = jefeCarreraService.findById(id);
		
		JefeCarrera jefeCarreraActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(jefeCarreraActual == null) {
			response.put("mensaje", "No se pudo editar el director de escuela con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			jefeCarreraActual.setCarrera(jefeCarrera.getCarrera());
			jefeCarreraActual.setUsuario(jefeCarrera.getUsuario());
			jefeCarreraActual.setEstado(jefeCarrera.isEstado());
			jefeCarreraActualizado = jefeCarreraService.save(jefeCarreraActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el director de escuela en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El director de escuela ha sido actualizado con exito!");
		response.put("jefeCarrera", jefeCarreraActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_JEFE_CARRERA"})
	@DeleteMapping("/jefe_carrera/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		JefeCarrera jefeCarrera = null;
		Map<String, Object> response = new HashMap<>();
		try {
			jefeCarrera = jefeCarreraService.findById(id);
			jefeCarreraService.delete(id);
			Usuario usuario = usuarioService.findById(jefeCarrera.getUsuario().getId());
			List<Perfil> perfiles = usuario.getPerfiles();
			long id2 = 4;
			Perfil perfil = perfilService.findById(id2);
			perfiles.remove(perfil);
			usuario.setPerfiles(perfiles);
			usuarioService.save(usuario);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el director de escuela en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El director de escuela se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_JEFE_CARRERA"})
	@GetMapping("/jefe_carrera/carreras")
	public List<Carrera> listarCarreras(){
		return jefeCarreraService.getCarreraSinJefe();
	}
	
	@Secured({"ROLE_C_JEFE_CARRERA"})
	@GetMapping("/jefe_carrera/usuarios")
	public List<Usuario> listarUsuarios(){
		return jefeCarreraService.getDocentesSinJefe();
	}
	
}
