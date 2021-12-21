package com.solucionesra.springboot.apirest.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	//public Usuario findByUsername2(String username);
	
	@Query("select u from Usuario u where u.username=?1 and u.vigente=true")
	public Usuario findByUsername(String username);
	
//	public List<Object[]> findByCarreraId(Long id);
	
//	@Query("select u from Usuario u where u.vigente=true")
//	public List<Usuario> findAll();
	
	@Transactional
	@Modifying
	@Query("update Usuario u set u.vigente=false where u.id=?1")
	public void desactivar(Long id);
	
	@Transactional
	@Modifying
	@Query("update Usuario u set u.vigente=true where u.id=?1")
	public void activar(Long id);

	@Query("select c from Carrera c where c.id not in (select u.carrera.id from JefeCarrera u where u.estado=true) and c.vigente=true")
	public List<Carrera> findAllCarrerasNotJefe();
	
}
