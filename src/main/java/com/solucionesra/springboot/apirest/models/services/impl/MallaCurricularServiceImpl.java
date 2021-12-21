package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IMallaCurricularDao;
import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;
import com.solucionesra.springboot.apirest.models.entity.MallaCurricular;
import com.solucionesra.springboot.apirest.models.services.IMallaCurricularService;

@Service
public class MallaCurricularServiceImpl implements IMallaCurricularService {
	
	@Autowired
	private IMallaCurricularDao mallaDao;
	@Override
	@Transactional(readOnly = true)
	public List<MallaCurricular> findAll(){
		return (List<MallaCurricular>) mallaDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public MallaCurricular findById(Long id) {
		return mallaDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public MallaCurricular save(MallaCurricular cliente) {
		return mallaDao.save(cliente);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		mallaDao.deleteById(id);		
	}
	@Override
	public List<DetalleMallaCarrera> buscarDetalleMallaByMalla(Long id) {
		return mallaDao.buscarDetalleMallaByMalla(id);
	}
	@Override
	public List<Object[]> buscarMalla(Long id2) {
		int id=id2.intValue();
		return mallaDao.buscarMalla(id);
	}
	@Override
	public List<MallaCurricular> buscarMallaByCarreraId(Long id) {
		return mallaDao.buscarMallaByCarreraId(id);
	}
	@Override
	public void activar(Long id) {
		mallaDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		mallaDao.desactivar(id);
	}
	
}
