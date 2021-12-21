package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IPerfilDao;
import com.solucionesra.springboot.apirest.models.entity.Perfil;
import com.solucionesra.springboot.apirest.models.services.IPerfilService;

@Service
public class PerfilServiceImpl implements IPerfilService {
	
	@Autowired
	private IPerfilDao perfilDao;
	@Override
	@Transactional(readOnly = true)
	public List<Perfil> findAll(){
		return (List<Perfil>) perfilDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Perfil findById(Long id) {
		return perfilDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Perfil save(Perfil facultad) {
		return perfilDao.save(facultad);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		perfilDao.deleteById(id);		
	}
	@Override
	public void activar(Long id) {
		perfilDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		perfilDao.desactivar(id);
	}
	
}
