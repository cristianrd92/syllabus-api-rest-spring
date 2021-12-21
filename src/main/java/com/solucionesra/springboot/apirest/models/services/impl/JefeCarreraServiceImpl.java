package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IJefeCarreraDao;
import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.JefeCarrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.IJefeCarreraService;

@Service
public class JefeCarreraServiceImpl implements IJefeCarreraService {
	
	@Autowired
	private IJefeCarreraDao jefeCarreraDao;
	@Override
	@Transactional(readOnly = true)
	public List<JefeCarrera> findAll(){
		return (List<JefeCarrera>) jefeCarreraDao.findAll();
	}
	@Override
	@Transactional
	public JefeCarrera save(JefeCarrera cliente) {
		return jefeCarreraDao.save(cliente);
	}
	@Override
	public JefeCarrera findById(Long id) {
		return jefeCarreraDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		jefeCarreraDao.deleteById(id);		
	}
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> getDocentesSinJefe() {
		return jefeCarreraDao.getDocentesSinJefe();
	}
	@Override
	@Transactional(readOnly = true)
	public List<Carrera> getCarreraSinJefe() {
		return jefeCarreraDao.getCarrerasSinJefe();
	}
}
