package com.solucionesra.springboot.apirest.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solucionesra.springboot.apirest.models.entity.Permiso;
import com.solucionesra.springboot.apirest.models.services.IPermisoService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class RoleRestController {
	
	@Autowired
	private IPermisoService permisoService;
	
	@GetMapping("/role")
	public List<Permiso> index(){
		return permisoService.findAllord();
	}
}
