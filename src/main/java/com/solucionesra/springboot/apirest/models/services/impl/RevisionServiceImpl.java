package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IRevisionDao;
import com.solucionesra.springboot.apirest.models.entity.Revision;
import com.solucionesra.springboot.apirest.models.services.IRevisionService;

@Service
public class RevisionServiceImpl implements IRevisionService {
	
	@Autowired
	private IRevisionDao revisionDao;
	@Override
	@Transactional(readOnly = true)
	public List<Revision> findAll(){
		return (List<Revision>) revisionDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Revision findById(Long id) {
		return revisionDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Revision save(Revision revision) {
		return revisionDao.save(revision);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		revisionDao.deleteById(id);		
	}
	
}
