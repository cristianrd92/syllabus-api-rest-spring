package com.solucionesra.springboot.apirest.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Perfil;
import com.solucionesra.springboot.apirest.models.entity.Planificacion;
import com.solucionesra.springboot.apirest.models.entity.Revision;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.entity.UsuarioComision;
import com.solucionesra.springboot.apirest.models.services.EmailService;
import com.solucionesra.springboot.apirest.models.services.IPerfilService;
import com.solucionesra.springboot.apirest.models.services.IPlanificacionService;
import com.solucionesra.springboot.apirest.models.services.IRevisionService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioComisionService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class UsuarioComisionRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IUsuarioComisionService usuarioComisionService;
	
	@Autowired
	private IPlanificacionService planificacionService;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private IPerfilService perfilService;
	
	@Autowired
	private EmailService emailService;
	
	@Secured({"ROLE_V_COMISION"})
	@GetMapping("/usuariocomision")
	public List<Usuario> index(){
		return usuarioService.findAll();
	}
	
	@Secured({"ROLE_C_COMISION"})
	@GetMapping("/usuariocomision/listado/{id}")
	public ResponseEntity<?> listadoUsuarios(@PathVariable Long id){
		List<Usuario> usuarios = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuarios = usuarioComisionService.findUsuarioNotInComision(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(usuarios==null) {
			response.put("mensaje", "La carrera con el ID: ".concat(id.toString().concat(" no tiene usuarios registrados")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	
	@Secured({"ROLE_C_COMISION"})
	@GetMapping("/usuariocomision/{id}")
	public ResponseEntity<?> showComision(@PathVariable Long id) {
		
		List<UsuarioComision> usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = usuarioComisionService.findUsuarioByCarreraId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(usuario==null) {
			response.put("mensaje", "La carrera con ID: ".concat(id.toString().concat(" no tiene usuarios registrados")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<UsuarioComision>>(usuario, HttpStatus.OK);
	}
	
	@GetMapping("/usuariocomision/planificacion/{id}")
	public ResponseEntity<?> showPlanificacion(@PathVariable Long id) {
		
		Planificacion planificacion = null;
		Map<String, Object> response = new HashMap<>();
		try {
			planificacion = planificacionService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(planificacion==null) {
			response.put("mensaje", "La planificacion con ID: ".concat(id.toString().concat(" no existe")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Planificacion>(planificacion, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_COMISION"})
	@GetMapping("/usuariocomision/carreras/{id}")
	public ResponseEntity<?> listarCrarreras(@PathVariable Long id) {
		
		List<Carrera> carrera = null;
		Map<String, Object> response = new HashMap<>();
		try {
			carrera = usuarioComisionService.findCarrerasByUsuarioComisionId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(carrera==null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no tiene usuarios registrados")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Carrera>>(carrera, HttpStatus.OK);
	}
	
	@Secured({"ROLE_COMISION"})
	@GetMapping("/usuariocomision/syllabus/{id}")
	public ResponseEntity<?> obtenerSyllabus(@PathVariable Long id) {
		
		List<Object[]> syllabus = null;
		Map<String, Object> response = new HashMap<>();
		try {
			syllabus = usuarioComisionService.obtenerSyllabus(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(syllabus==null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no tiene usuarios registrados")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Object[]>>(syllabus, HttpStatus.OK);
	}
	
	
	
	@Secured({"ROLE_C_COMISION"})
	@PostMapping("/usuariocomision")
	public ResponseEntity<?> create(@Valid @RequestBody UsuarioComision usuarioComision, BindingResult result) {
		
		UsuarioComision usuarioNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		Usuario usuario= usuarioService.findById(usuarioComision.getUsuario().getId());
		List<Perfil> perfiles = usuario.getPerfiles();
		long id = 3;
		Perfil perfil = perfilService.findById(id);
		perfiles.add(perfil);
		usuario.setPerfiles(perfiles);
		usuarioService.save(usuario);
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			usuarioNuevo= usuarioComisionService.save(usuarioComision);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El usuario ha sido asignado con exito en la comisión!");
		response.put("comision", usuarioNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_COMISION"})
	@PostMapping("/usuariocomision/revision")
	public ResponseEntity<?> crearRevision(@Valid @RequestBody Revision revision, BindingResult result) throws MessagingException {
		System.out.println(revision.getId_usuario());
		System.out.println(revision.getId_planificacion());
		Planificacion planificacion = planificacionService.findById(Long.parseLong(revision.getId_planificacion()));
		Usuario usuario = usuarioService.findById(Long.parseLong(revision.getId_usuario()));
		revision.setPlanificacion(planificacion);
		revision.setUsuario(usuario);
		if(revision.getComentarios()==null) {
			revision.setComentarios("No se indicaron comentarios");
		}
		Revision revisionNueva = null;
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
			//ENVIAR CORREO A DOCENTE COMISION
			String mensaje = "";
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			mensaje+="<br><br><b>Docente revisado: </b>"+revision.getPlanificacion().getUsuario().getNombres()+" "+revision.getPlanificacion().getUsuario().getApellidos();
			mensaje+="<br><br><b>Carrera: </b>"+revision.getPlanificacion().getRamo().getCarrera().getNombre_carrera();
			mensaje+="<br><b>Ramo: </b>"+revision.getPlanificacion().getRamo().getRamo().getNombre_ramo();
			mensaje+="<br><b>Fecha Revisión: </b>"+hourdateFormat.format(date);
			mensaje+="<br><b>Comentarios: </b>"+revision.getComentarios();
			mensaje+="<br><b>Resultado revisión: </b>"+revision.getEstado();
			emailService.sendMail(usuario.getEmail(), "Comprobante Revisión", 3, mensaje);
			String mensaje2="";
			mensaje+="<br><br><b>Docente revisor: </b>"+usuario.getNombres()+" "+usuario.getApellidos();
			mensaje+="<br><b>Fecha Revisión: </b>"+hourdateFormat.format(date);
			mensaje+="<br><br><b>Carrera: </b>"+revision.getPlanificacion().getRamo().getCarrera().getNombre_carrera();
			mensaje+="<br><b>Ramo: </b>"+revision.getPlanificacion().getRamo().getRamo().getNombre_ramo();
			mensaje+="<br><b>Resultado revisión: </b>"+revision.getEstado();
			emailService.sendMail(revision.getPlanificacion().getUsuario().getEmail(), "Revisión Syllabus", 4, mensaje2);
			revisionNueva= revisionService.save(revision);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La revision se llevo a cabo de manera exitosa!");
		response.put("revision", revisionNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_COMISION","ROLE_E_COMISION"})
	@DeleteMapping("/usuariocomision/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		UsuarioComision usuarioComision = usuarioComisionService.findById(id);
		Usuario usuario= usuarioComision.getUsuario();
		List<Perfil> perfiles = usuarioComision.getUsuario().getPerfiles();
		long id2 = 3;
		Perfil perfil = perfilService.findById(id2);
		perfiles.remove(perfil);
		usuario.setPerfiles(perfiles);
		usuarioService.save(usuario);
		try {
			usuarioComisionService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el usuario de la comisión en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario se ha eliminado con exito de la comisión");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
