package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IPermisoDao;
import com.solucionesra.springboot.apirest.models.entity.Permiso;
import com.solucionesra.springboot.apirest.models.services.IPermisoService;

@Service
public class PermisoServiceImpl implements IPermisoService {
	
	@Autowired
	private IPermisoDao permisoDao;
	@Override
	@Transactional(readOnly = true)
	public List<Permiso> findAllord(){
		return (List<Permiso>) permisoDao.findAllord();
	}
	@Override
	@Transactional(readOnly = true)
	public Permiso findById(Long id) {
		return permisoDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Permiso save(Permiso facultad) {
		return permisoDao.save(facultad);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		permisoDao.deleteById(id);		
	}
	
}
