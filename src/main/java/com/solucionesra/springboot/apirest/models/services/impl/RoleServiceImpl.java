package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IRoleDao;
import com.solucionesra.springboot.apirest.models.entity.Permiso;
import com.solucionesra.springboot.apirest.models.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;
	@Override
	@Transactional(readOnly = true)
	public List<Permiso> findAll(){
		return (List<Permiso>) roleDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Permiso findById(Long id) {
		return roleDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Permiso save(Permiso facultad) {
		return roleDao.save(facultad);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		roleDao.deleteById(id);		
	}
	
}
