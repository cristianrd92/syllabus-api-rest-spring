package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solucionesra.springboot.apirest.models.dao.IUsuarioComisionDao;
import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.entity.UsuarioComision;
import com.solucionesra.springboot.apirest.models.services.IUsuarioComisionService;

@Service
public class UsuarioComisionServiceImpl implements IUsuarioComisionService{
	
	@Autowired
	private IUsuarioComisionDao usuarioComisionDao;

	@Override
	public List<UsuarioComision> findAll() {
		return (List<UsuarioComision>) usuarioComisionDao.findAll();
	}

	@Override
	public UsuarioComision findById(Long id) {
		return usuarioComisionDao.findById(id).orElse(null);
	}

	@Override
	public UsuarioComision save(UsuarioComision usuario) {
		return usuarioComisionDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		usuarioComisionDao.deleteById(id);
	}

	@Override
	public List<UsuarioComision> findUsuarioByCarreraId(Long id) {
		return usuarioComisionDao.findUsuarioByCarreraId(id);
	}

	@Override
	public List<Usuario> findUsuarioNotInComision(Long id) {
		return usuarioComisionDao.findUsuarioNotInComision(id);
	}

	@Override
	public List<Carrera> findCarrerasByUsuarioComisionId(Long id) {
		return usuarioComisionDao.findCarrerasByUsuarioComisionId(id);
	}

	@Override
	public List<Object[]> obtenerSyllabus(Long id_carrera) {
		return usuarioComisionDao.obtenerSyllabus(id_carrera);
	}
}
