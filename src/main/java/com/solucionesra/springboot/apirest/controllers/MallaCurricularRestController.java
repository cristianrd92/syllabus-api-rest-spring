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

import com.solucionesra.springboot.apirest.models.dao.ICarreraDao.RamoN;
import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;
import com.solucionesra.springboot.apirest.models.entity.MallaCurricular;
import com.solucionesra.springboot.apirest.models.entity.Ramo;
import com.solucionesra.springboot.apirest.models.entity.Semestre;
import com.solucionesra.springboot.apirest.models.services.ICarreraService;
import com.solucionesra.springboot.apirest.models.services.IDetalleMallaCurricularService;
import com.solucionesra.springboot.apirest.models.services.IMallaCurricularService;
import com.solucionesra.springboot.apirest.models.services.IRamoService;
import com.solucionesra.springboot.apirest.models.services.ISemestreService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class MallaCurricularRestController {
	
	@Autowired
	private IMallaCurricularService mallaCurricularService;
	
	@Autowired
	private ICarreraService carreraService;
	
	@Autowired
	private ISemestreService semestreService;
	
	@Autowired
	private IRamoService ramoService;
	
	@Autowired
	private IDetalleMallaCurricularService detalleMallaService;
	
	@GetMapping("/malla")
	public List<MallaCurricular> index(){
		return mallaCurricularService.findAll();
	}
	
	@Secured({"ROLE_C_MALLA","ROLE_M_MALLA","ROLE_JEFE_CARRERA"})
	@GetMapping("/malla/semestre/listado")
	public List<Semestre> cargarSemestres(){
		return semestreService.findAll();
	}
	
	@Secured({"ROLE_C_MALLA","ROLE_M_MALLA","ROLE_JEFE_CARRERA"})
	@GetMapping("/malla/ramo/listado")
	public List<Ramo> cargarRamos(){
		return ramoService.findAll();
	}
	
	@GetMapping("/malla/ramo/{id}/{id_malla}")
	public ResponseEntity<?> ramoUsuarioPlanificacion(@PathVariable Long id,@PathVariable Long id_malla) {
		Map<String, Object> response = new HashMap<>();

		List<RamoN> planificacion = new ArrayList<>();
		
		try {
			planificacion = carreraService.findCarreras(id,id_malla);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (planificacion == null) {
			response.put("mensaje", "No existen ramos en la base de datos con esa carrera");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(planificacion, HttpStatus.OK);
	}
	
	@Secured({"ROLE_V_MALLA","ROLE_JEFE_CARRERA"})
	@GetMapping("/malla/detalle/{id}")
	public ResponseEntity<?> showDetalles(@PathVariable Long id) {
		List<DetalleMallaCarrera> detalleMalla = null;
		Map<String, Object> response = new HashMap<>();
		try {
			detalleMalla = mallaCurricularService.buscarDetalleMallaByMalla(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(detalleMalla==null) {
			response.put("mensaje", "La malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<DetalleMallaCarrera>>(detalleMalla, HttpStatus.OK);
	}
	
	@Secured({"ROLE_JEFE_CARRERA","ROLE_V_MALLA"})
	@GetMapping("/malla/carrera/{id}")
	public ResponseEntity<?> showMallaByCarrera(@PathVariable Long id) {
		List<MallaCurricular> mallas = null;
		Map<String, Object> response = new HashMap<>();
		try {
			mallas = mallaCurricularService.buscarMallaByCarreraId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(mallas==null) {
			response.put("mensaje", "La malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MallaCurricular>>(mallas, HttpStatus.OK);
	}
	
	@Secured({"ROLE_V_MALLA","ROLE_JEFE_CARRERA"})
	@GetMapping("/malla/detalleEditar/{id}")
	public ResponseEntity<?> showDetalleEditar(@PathVariable Long id) {
		DetalleMallaCarrera detalleMalla = null;
		Map<String, Object> response = new HashMap<>();
		try {
			detalleMalla = detalleMallaService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(detalleMalla==null) {
			response.put("mensaje", "La malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DetalleMallaCarrera>(detalleMalla, HttpStatus.OK);
	}
	
	@Secured({"ROLE_V_MALLA","ROLE_JEFE_CARRERA"})
	@GetMapping("/malla/malla/{id}")
	public ResponseEntity<?> showMalla(@PathVariable Long id) {
		List<Object[]> detalleMalla = null;
		Map<String, Object> response = new HashMap<>();
		try {
			detalleMalla = mallaCurricularService.buscarMalla(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(detalleMalla==null) {
			response.put("mensaje", "La malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Object[]>>(detalleMalla, HttpStatus.OK);
	}
	
	@Secured({"ROLE_V_MALLA","ROLE_JEFE_CARRERA"})
	@GetMapping("/malla/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		MallaCurricular malla = null;
		Map<String, Object> response = new HashMap<>();
		try {
			malla = mallaCurricularService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(malla==null) {
			response.put("mensaje", "La malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MallaCurricular>(malla, HttpStatus.OK);
	}
	
	@Secured({"ROLE_C_MALLA","ROLE_JEFE_CARRERA"})
	@PostMapping("/malla")
	public ResponseEntity<?> create(@Valid @RequestBody MallaCurricular malla, BindingResult result) {
		MallaCurricular mallaNueva = null;
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
			mallaNueva= mallaCurricularService.save(malla);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La malla ha sido creado con exito!");
		response.put("malla", mallaNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_C_MALLA","ROLE_JEFE_CARRERA"})
	@PostMapping("/malla/detalle")
	public ResponseEntity<?> create(@Valid @RequestBody DetalleMallaCarrera detalle_malla, BindingResult result) {
		DetalleMallaCarrera mallaNueva = null;
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
			mallaNueva= detalleMallaService.save(detalle_malla);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","Ramo asignado con exito a malla!");
		response.put("malla", mallaNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_MALLA","ROLE_JEFE_CARRERA"})
	@PutMapping("/malla/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody MallaCurricular malla, BindingResult result, @PathVariable Long id) {
		MallaCurricular mallaActual = mallaCurricularService.findById(id);
		MallaCurricular mallaActualizada = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(mallaActual == null) {
			response.put("mensaje", "No se pudo editar la malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			mallaActual.setDescripcion_malla(malla.getDescripcion_malla());	
			mallaActualizada = mallaCurricularService.save(mallaActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la malla en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La malla ha sido actualizado con exito!");
		response.put("malla", mallaActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_M_MALLA","ROLE_JEFE_CARRERA"})
	@PutMapping("/malla/editar/{id}")
	public ResponseEntity<?> updateDetalle(@Valid @RequestBody DetalleMallaCarrera detalle_malla, BindingResult result, @PathVariable Long id) {
		DetalleMallaCarrera detalleActual = detalleMallaService.findById(id);
		DetalleMallaCarrera detalleActualizada = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(detalleActual == null) {
			response.put("mensaje", "No se pudo editar la malla con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			detalleActual.setMalla_curricular(detalle_malla.getMalla_curricular());	
			detalleActual.setPosicion(detalle_malla.getPosicion());
			detalleActual.setRamo(detalle_malla.getRamo());
			detalleActual.setSemestre(detalle_malla.getSemestre());
			detalleActualizada = detalleMallaService.save(detalleActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la malla en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La malla ha sido actualizado con exito!");
		response.put("detalleMalla", detalleActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_E_MALLA","ROLE_JEFE_CARRERA"})
	@DeleteMapping("/malla/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			mallaCurricularService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar la malla en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La malla se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@Secured({"ROLE_E_MALLA","ROLE_JEFE_CARRERA"})
	@DeleteMapping("/malla/detalle/{id}")
	public ResponseEntity<?>  deleteDetalle(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			detalleMallaService.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar el ramo de la malla en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El ramo fue removido de esta malla con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/malla/d/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			mallaCurricularService.desactivar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al desactivar la malla en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La malla se ha desactivado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/malla/a/{id}")
	public ResponseEntity<?> activar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			mallaCurricularService.activar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al activar la malla en la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La malla se ha activado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
