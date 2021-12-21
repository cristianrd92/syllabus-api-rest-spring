package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IRamoDao;
import com.solucionesra.springboot.apirest.models.entity.Ramo;
import com.solucionesra.springboot.apirest.models.services.IRamoService;

@Service
public class RamoServiceImpl implements IRamoService {
	
	@Autowired
	private IRamoDao ramoDao;
	@Override
	@Transactional(readOnly = true)
	public List<Ramo> findAll(){
		return (List<Ramo>) ramoDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Ramo findById(Long id) {
		return ramoDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Ramo save(Ramo ramo) {
		return ramoDao.save(ramo);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		ramoDao.deleteById(id);		
	}
	@Override
	public void insertRamo(Ramo ramo) {
		ramoDao.saveProcedure(ramo.getCodigo_ramo(), ramo.getNombre_ramo(),ramo.getNombre_corto());
	}
	@Override
	public void activar(Long id) {
		ramoDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		ramoDao.desactivar(id);
	}

	
}
