package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.ISemestreDao;
import com.solucionesra.springboot.apirest.models.entity.Semestre;
import com.solucionesra.springboot.apirest.models.services.ISemestreService;

@Service
public class SemestreServiceImpl implements ISemestreService {
	
	@Autowired
	private ISemestreDao semestreDao;
	@Override
	@Transactional(readOnly = true)
	public List<Semestre> findAll(){
		return (List<Semestre>) semestreDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Semestre findById(Long id) {
		return semestreDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Semestre save(Semestre semestre) {
		return semestreDao.save(semestre);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		semestreDao.deleteById(id);		
	}
	@Override
	public void activar(Long id) {
		semestreDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		semestreDao.desactivar(id);
	}
	
}
