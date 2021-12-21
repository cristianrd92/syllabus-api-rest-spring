package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.ISedeDao;
import com.solucionesra.springboot.apirest.models.entity.Sede;
import com.solucionesra.springboot.apirest.models.services.ISedeService;

@Service
public class SedeServiceImpl implements ISedeService {
	
	@Autowired
	private ISedeDao sedeDao;
	@Override
	@Transactional(readOnly = true)
	public List<Sede> findAll(){
		return (List<Sede>) sedeDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Sede findById(Long id) {
		return sedeDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Sede save(Sede sede) {
		return sedeDao.save(sede);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		sedeDao.deleteById(id);		
	}
	@Override
	public void activar(Long id) {
		sedeDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		sedeDao.desactivar(id);
	}
	
}
