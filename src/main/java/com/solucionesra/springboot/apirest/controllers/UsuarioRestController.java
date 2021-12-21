package com.solucionesra.springboot.apirest.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.solucionesra.springboot.apirest.models.entity.Perfil;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.EmailService;
import com.solucionesra.springboot.apirest.models.services.ICarreraService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;
import com.solucionesra.springboot.apirest.utiles.ChangePasswordForm;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private EmailService email;
	
	@Autowired
	private ICarreraService carreraService;
	
	@Secured({"ROLE_V_USUARIO"})
	@GetMapping("/usuario")
	public List<Usuario> index(){
		return usuarioService.findAll();
	}
	
	@GetMapping("/usuario/carreras")
	public List<Carrera> carreras(){
		return usuarioService.findAllCarrerasNotJefe();
	}
	
	
	@Secured({"ROLE_M_USUARIO"})
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = usuarioService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(usuario==null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/carrera/{id}")
	public ResponseEntity<?> buscarCarrera(@PathVariable String id) {
		
		Carrera carrera = null;
		Map<String, Object> response = new HashMap<>();
		try {
			carrera = carreraService.findCarreraByUsername(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(carrera==null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Carrera>(carrera, HttpStatus.OK);
	}
	
	@PostMapping("/usuario/cambiarClave")
	public ResponseEntity<?> cambiarPassword(@Valid @RequestBody ChangePasswordForm usuario, BindingResult result) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		Usuario usuarioActual = usuarioService.findByUsername(usuario.getUsername());
		Usuario usuarioActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioActual == null) {
			response.put("mensaje", "No se pudo actualizar la constraseña, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			if(!(usuario.getNewPassword().equals(usuario.getConfirmPassword()))){
				response.put("mensaje","Constraseñas nueva y de confirmación no coinciden!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}else if(encoder.matches(usuario.getNewPassword(), usuarioActual.getPassword())) {
				response.put("mensaje","La contraseña nueva debe ser distinta a la actual!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				if(encoder.matches(usuario.getCurrentPassword(), usuarioActual.getPassword())) {
					if(usuarioActual.isFirst()) {
						usuarioActual.setFirst(false);
					}
					usuarioActual.setPassword(encoder.encode(usuario.getNewPassword()));
					usuarioActualizado = usuarioService.save(usuarioActual);
					response.put("mensaje","Constraseña actualizada con exito!");
					response.put("usuario", usuarioActualizado);
					
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
				}else {
					response.put("mensaje","Las contraseña actual es erronea!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la contraseña en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Devuelve contraseña aleatoria generada de manera automatica, util para envio de contraseña por mail
	public String generateCommonLangPassword() {
	    String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
	    String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
	    String numbers = RandomStringUtils.randomNumeric(2);
	    //String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
	    String totalChars = RandomStringUtils.randomAlphanumeric(2);
	    String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
	      .concat(numbers)
	      //.concat(specialChar)
	      .concat(totalChars);
	    List<Character> pwdChars = combinedChars.chars()
	      .mapToObj(c -> (char) c)
	      .collect(Collectors.toList());
	    Collections.shuffle(pwdChars);
	    String password = pwdChars.stream()
	      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	      .toString();
	    return password;
	}	
	
	@Secured({"ROLE_C_USUARIO"})
	@PostMapping("/usuario")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) throws MessagingException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		
		Usuario usuarioNuevo = null;
		Map<String, Object> response = new HashMap<>();
		String password = generateCommonLangPassword();
		usuario.setPassword(encoder.encode(password));
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			usuarioNuevo= usuarioService.save(usuario);
			String mensaje = "";
			mensaje+="<br><br><b>Nombre usuario:</b> "+usuarioNuevo.getUsername();
			mensaje+="<br><b>Contraseña:</b> "+password;
			email.sendMail(usuario.getEmail(), "Bienvenida al sistema", 1, mensaje);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El usuario ha sido creado con exito!");
		response.put("facultad", usuarioNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_USUARIO"})
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
		
		Usuario usuarioActual = usuarioService.findById(id);
		Usuario usuarioActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioActual == null) {
			response.put("mensaje", "No se pudo editar el usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			List<Perfil> perfiles = usuarioActual.getPerfiles();
			for (Perfil perfil : perfiles) {
				if(perfil.getId()==3) {
					usuario.getPerfiles().add(perfil);
				}
				if(perfil.getId()==4) {
					usuario.getPerfiles().add(perfil);
				}
			}
			
			usuarioActual.setNombres(usuario.getNombres());
			usuarioActual.setApellidos(usuario.getApellidos());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setNombre_corto(usuario.getNombre_corto());
			usuarioActual.setPerfiles(usuario.getPerfiles());
			usuarioActual.setVigente(usuario.getVigente());
			usuarioActualizado = usuarioService.save(usuarioActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la facultad en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El usuario ha sido actualizado con exito!");
		response.put("facultad", usuarioActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_USUARIO"})
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el usuario en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/usuario/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar el usuario en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/usuario/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar el usuario en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
