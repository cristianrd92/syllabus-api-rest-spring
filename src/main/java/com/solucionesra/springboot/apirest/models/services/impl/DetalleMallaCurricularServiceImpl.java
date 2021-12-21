package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IDetalleMallaCurricularDao;
import com.solucionesra.springboot.apirest.models.entity.DetalleMallaCarrera;
import com.solucionesra.springboot.apirest.models.services.IDetalleMallaCurricularService;

@Service
public class DetalleMallaCurricularServiceImpl implements IDetalleMallaCurricularService {
	
	@Autowired
	private IDetalleMallaCurricularDao mallaDao;
	@Override
	@Transactional(readOnly = true)
	public List<DetalleMallaCarrera> findAll(){
		return (List<DetalleMallaCarrera>) mallaDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public DetalleMallaCarrera findById(Long id) {
		return mallaDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public DetalleMallaCarrera save(DetalleMallaCarrera cliente) {
		return mallaDao.save(cliente);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		mallaDao.deleteById(id);		
	}
	
}
