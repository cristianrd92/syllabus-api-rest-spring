package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IRamoCarreraDao;
import com.solucionesra.springboot.apirest.models.entity.RamoCarrera;
import com.solucionesra.springboot.apirest.models.services.IRamoCarreraService;

@Service
public class RamoCarreraServiceImpl implements IRamoCarreraService {
	
	@Autowired
	private IRamoCarreraDao ramoCarreraDao;
	@Override
	@Transactional(readOnly = true)
	public List<RamoCarrera> findAll(){
		return (List<RamoCarrera>) ramoCarreraDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public RamoCarrera findById(Long id) {
		return ramoCarreraDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public RamoCarrera save(RamoCarrera ramo) {
		return ramoCarreraDao.save(ramo);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		ramoCarreraDao.deleteById(id);		
	}
	@Override
	public List<RamoCarrera> findAllByUsuarioId(Long usuarioId) {
		return ramoCarreraDao.findAllByUsuarioId(usuarioId);
	}
	@Override
	public void activar(Long id) {
		ramoCarreraDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		ramoCarreraDao.desactivar(id);
	}
	
}
