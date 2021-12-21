package com.solucionesra.springboot.apirest.models.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solucionesra.springboot.apirest.models.dao.IUsuarioDao;
import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Perfil;
import com.solucionesra.springboot.apirest.models.entity.Permiso;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService{

	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (usuario == null) {
			logger.error("Error en el login: no existe el usuario "+username+" en el sistema");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario "+username+" en el sistema");
		}
		
		List<Perfil> perfiles_usuario = usuario.getPerfiles();
		System.out.println(perfiles_usuario.size());
		List<Permiso> permisos_usuario = new ArrayList<>();
		//Primero debemos buscar los perfiles que tenga el usuario
		for (int i = 0; i < perfiles_usuario.size(); i++) {
			for (int x =0; x< perfiles_usuario.get(i).getPermisos().size(); x++) {
				System.out.println(perfiles_usuario.get(i).getPermisos().get(x).getName());
				permisos_usuario.add(perfiles_usuario.get(i).getPermisos().get(x));
			}
		}	
		List<GrantedAuthority> authorities = permisos_usuario
				.stream()
				.map( role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> logger.info("Permiso "+authority.getAuthority()))
				.collect(Collectors.toList());
		System.out.println(usuario.getPassword());
		System.out.println(username);
		System.out.println(usuario.getVigente());
		return new User(username, usuario.getPassword(), usuario.getVigente(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		
		return usuarioDao.findByUsername(username);
	}

	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		usuarioDao.deleteById(id);
	}

//	@Override
//	public List<Object[]> findByCarrera(Long id) {
//		return usuarioDao.findByCarreraId(id);
//	}

	@Override
	public List<Carrera> findAllCarrerasNotJefe() {
		return usuarioDao.findAllCarrerasNotJefe();
	}

	@Override
	public void activar(Long id) {
		usuarioDao.activar(id);
	}

	@Override
	public void desactivar(Long id) {
		usuarioDao.desactivar(id);
	}

}
