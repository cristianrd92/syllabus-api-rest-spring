package com.solucionesra.springboot.apirest.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solucionesra.springboot.apirest.models.entity.Sistema;
import com.solucionesra.springboot.apirest.models.services.ISistemaService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class SistemaRestController {
	
	@Autowired
	private ISistemaService sistemaService;
	
	@GetMapping("/sistema")
	public ResponseEntity<?> show() {
		
		int id_int = 1;
		long id = id_int;
		
		Sistema sistema = null;
		Map<String, Object> response = new HashMap<>();
		try {
			sistema = sistemaService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(sistema==null) {
			response.put("mensaje", "El parametro no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sistema>(sistema, HttpStatus.OK);
	}
	
	public Date agregarHora(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}
	
	@PutMapping("/sistema")
	public ResponseEntity<?> update(@Valid @RequestBody Sistema sistema, BindingResult result) {
		
		System.out.println(agregarHora(sistema.getFecha(),3));
		
		int id_int = 1;
		long id = id_int;
		
		Sistema parametrosActuales = sistemaService.findById(id);
		
		Sistema parametrosActualizados = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(parametrosActuales == null) {
			response.put("mensaje", "No se pudo editar, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			parametrosActuales.setMensaje_bienvenida_sistema(sistema.getMensaje_bienvenida_sistema());
			parametrosActuales.setMensaje_recuperacion_clave(sistema.getMensaje_recuperacion_clave());
			parametrosActuales.setMensaje_reinicio_clave(sistema.getMensaje_reinicio_clave());
			parametrosActuales.setMensaje_revision_syllabus_comision(sistema.getMensaje_revision_syllabus_comision());
			parametrosActuales.setMensaje_revision_syllabus_docente(sistema.getMensaje_revision_syllabus_docente());
			parametrosActuales.setMensaje_subida_syllabus_docente(sistema.getMensaje_subida_syllabus_docente());
			parametrosActuales.setHost_correo(sistema.getHost_correo());
			parametrosActuales.setPuerto_correo(sistema.getPuerto_correo());
			parametrosActuales.setUsuario_correo(sistema.getUsuario_correo());
			parametrosActuales.setPassword_correo(sistema.getPassword_correo());
			parametrosActuales.setUrl_imagen(sistema.getUrl_imagen());
			parametrosActuales.setFecha(agregarHora(sistema.getFecha(),3));
			
			parametrosActualizados = sistemaService.save(parametrosActuales);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar los parametros en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","Los parametros han sido actualizados con exito!");
		response.put("parametros", parametrosActualizados);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
}
