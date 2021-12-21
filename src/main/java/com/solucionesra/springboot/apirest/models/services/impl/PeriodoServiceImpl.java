package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IPeriodoDao;
import com.solucionesra.springboot.apirest.models.entity.Periodo;
import com.solucionesra.springboot.apirest.models.services.IPeriodoService;

@Service
public class PeriodoServiceImpl implements IPeriodoService {
	
	@Autowired
	private IPeriodoDao periodoDao;
	@Override
	@Transactional(readOnly = true)
	public List<Periodo> findAll(){
		return (List<Periodo>) periodoDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Periodo findById(Long id) {
		return periodoDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Periodo save(Periodo periodo) {
		return periodoDao.save(periodo);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		periodoDao.deleteById(id);		
	}
	@Override
	public void activar(Long id) {
		periodoDao.activar(id);
	}
	@Override
	public void desactivar(Long id) {
		periodoDao.desactivar(id);
	}
	
}
