package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.ICiudadDao;
import com.solucionesra.springboot.apirest.models.entity.Ciudad;
import com.solucionesra.springboot.apirest.models.services.ICiudadService;

@Service
public class CiudadServiceImpl implements ICiudadService {
	
	@Autowired
	private ICiudadDao ciudadDao;
	@Override
	@Transactional(readOnly = true)
	public List<Ciudad> findAll(){
		return (List<Ciudad>) ciudadDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Ciudad findById(Long id) {
		return ciudadDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Ciudad save(Ciudad cliente) {
		return ciudadDao.save(cliente);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		ciudadDao.deleteById(id);		
	}
	@Override
	public void activar(Long id) {
		ciudadDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		ciudadDao.desactivar(id);
	}
	@Override
	public void insertCiudad(Ciudad ciudad) {
		ciudadDao.saveProcedure(ciudad.getNombre_ciudad());
	}
}
