package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IPlanificacionDao;
import com.solucionesra.springboot.apirest.models.entity.Planificacion;
import com.solucionesra.springboot.apirest.models.services.IPlanificacionService;
@Service
public class PlanificacionServiceImpl implements IPlanificacionService {
	
	@Autowired
	private IPlanificacionDao planificacionDao;
	@Override
	@Transactional(readOnly = true)
	public List<Planificacion> findAll(){
		return (List<Planificacion>) planificacionDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Planificacion findById(Long id) {
		return planificacionDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Planificacion save(Planificacion cliente) {
		return planificacionDao.save(cliente);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		planificacionDao.deleteById(id);		
	}
	
	@Override
	public List<Object[]> findEstadoPlanificacionRevision(Long usuario_id){
		return planificacionDao.findEstadoPlanificacionRevision(usuario_id);
	}
	@Override
	public String getRutaPlanificaciones(Long id) { 
		return planificacionDao.getRutaPlanificaciones(id.intValue());
	}
}
