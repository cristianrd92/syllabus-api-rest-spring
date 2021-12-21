package com.solucionesra.springboot.apirest.models.services;

import java.util.List;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.entity.UsuarioComision;

public interface IUsuarioComisionService {

	public List<UsuarioComision> findAll();

	public List<UsuarioComision> findUsuarioByCarreraId(Long id);
	
	public List<Carrera> findCarrerasByUsuarioComisionId(Long id);
	
	public List<Object[]> obtenerSyllabus(Long id_carrera);
	
	public UsuarioComision findById(Long id);
	
	public UsuarioComision save(UsuarioComision usuario_comision);
	
	public List<Usuario> findUsuarioNotInComision(Long id);
	
	public void delete(Long id);

}
