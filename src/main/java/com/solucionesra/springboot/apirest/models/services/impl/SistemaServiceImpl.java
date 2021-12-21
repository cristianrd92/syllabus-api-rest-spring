package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.ISistemaDao;
import com.solucionesra.springboot.apirest.models.entity.Sistema;
import com.solucionesra.springboot.apirest.models.services.ISistemaService;

@Service
public class SistemaServiceImpl implements ISistemaService {
	
	@Autowired
	private ISistemaDao sistemaDao;
	@Override
	@Transactional(readOnly = true)
	public List<Sistema> findAll(){
		return (List<Sistema>) sistemaDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Sistema findById(Long id) {
		return sistemaDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Sistema save(Sistema sistema) {
		return sistemaDao.save(sistema);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		sistemaDao.deleteById(id);		
	}
	
}
