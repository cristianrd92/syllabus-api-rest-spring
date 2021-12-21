package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IFacultadDao;
import com.solucionesra.springboot.apirest.models.entity.Facultad;
import com.solucionesra.springboot.apirest.models.services.IFacultadService;

@Service
public class FacultadServiceImpl implements IFacultadService {
	
	@Autowired
	private IFacultadDao faculatdDao;
	@Override
	@Transactional(readOnly = true)
	public List<Facultad> findAll(){
		return (List<Facultad>) faculatdDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Facultad findById(Long id) {
		return faculatdDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Facultad save(Facultad facultad) {
		return faculatdDao.save(facultad);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		faculatdDao.deleteById(id);		
	}
	@Override
	public void activar(Long id) {
		faculatdDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		faculatdDao.desactivar(id);
	}
	
}
