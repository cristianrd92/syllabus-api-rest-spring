package com.solucionesra.springboot.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.solucionesra.springboot.apirest.models.entity.Planificacion;
import com.solucionesra.springboot.apirest.models.entity.RamoCarrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.EmailService;
import com.solucionesra.springboot.apirest.models.services.IPlanificacionService;
import com.solucionesra.springboot.apirest.models.services.IRamoCarreraService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class PlanificacionRestController {

	@Autowired
	IPlanificacionService planificacionService;

	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	EmailService email;

	@Autowired
	IRamoCarreraService ramoCarreraService;

	@PostMapping("/planificacion/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("usuario_id") Long id,
			@RequestParam("ramo_id") Long ramo_id) throws MessagingException {

		Map<String, Object> response = new HashMap<>();
		Usuario usuario = usuarioService.findById(id);
		RamoCarrera ramo = ramoCarreraService.findById(ramo_id);
		Planificacion planificacion = new Planificacion();

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo");
				response.put("error", e.getMessage().concat(": "));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			List<Planificacion> planificaciones = planificacionService.findAll();
			for (int x = 0; x < planificaciones.size(); x++) {
				if (planificaciones.get(x).getRamo() == ramo && planificaciones.get(x).getUsuario() == usuario) {
					String nombreArchivoAnterior = planificaciones.get(x).getRuta();
					planificacion = planificaciones.get(x);
					Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreArchivoAnterior).toAbsolutePath();
					File arhivoAnterior = rutaArchivoAnterior.toFile();
					if (arhivoAnterior.exists() && arhivoAnterior.canRead()) {
						arhivoAnterior.delete();
					}
				}
			}

			planificacion.setRamo(ramo);
			planificacion.setUsuario(usuario);
			planificacion.setRuta(nombreArchivo);
			String mensaje = "";
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			mensaje+="<br><br><b>Carrera: </b>"+ramo.getCarrera().getNombre_carrera();
			mensaje+="<br><b>Ramo: </b>"+ramo.getRamo().getNombre_ramo();
			mensaje+="<br><b>Subido: </b>"+hourdateFormat.format(date);
			email.sendMail(usuario.getEmail(), "Syllabus enviado a revisión", 2, mensaje);
			planificacionService.save(planificacion);

			response.put("planificacion", planificacion);
			response.put("Mensaje", "Se ha subido correctamente el archivo " + nombreArchivo);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/planificacion/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Planificacion planificacion = planificacionService.findById(id);
			String nombreArchivoAnterior = planificacion.getRuta();
			Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreArchivoAnterior).toAbsolutePath();
			File arhivoAnterior = rutaArchivoAnterior.toFile();
			if (arhivoAnterior.exists() && arhivoAnterior.canRead()) {
				arhivoAnterior.delete();
			}
			planificacionService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el semestre en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La planificación se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	@GetMapping("planificacion/estado/{id}")
	public ResponseEntity<?> ramoUsuarioPlanificacion(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		List<Object[]> planificacion = null;
		try {
			planificacion = planificacionService.findEstadoPlanificacionRevision(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (planificacion == null) {
			response.put("mensaje", "No existe planificacion en la base de datos con ese ramo y usuario");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(planificacion, HttpStatus.OK);
	}
	
	@GetMapping("planificacion/ruta/{id}")
	public ResponseEntity<?> rutaPlanificacion(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		String planificacion;
		try {
			planificacion = planificacionService.getRutaPlanificaciones(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (planificacion == null) {
			response.put("mensaje", "No existe planificacion en la base de datos con ese ramo y usuario");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(planificacion, HttpStatus.OK);
	}
	
	@GetMapping("/planificacion/upload/ver/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo){
		Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error, no se pudo cargar el archivo: "+nombreArchivo);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
	
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}

}
