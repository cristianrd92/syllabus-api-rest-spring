package com.solucionesra.springboot.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.solucionesra.springboot.apirest.models.entity.Carrera;
import com.solucionesra.springboot.apirest.models.entity.Usuario;
import com.solucionesra.springboot.apirest.models.services.ICarreraService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICarreraService carreraService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String, Object> info = new HashMap<>();
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		Carrera carrera = carreraService.findCarreraByUsername(authentication.getName());
		
		if(carrera!=null) {
			info.put("jefe", carrera.getId());
		}else {
			info.put("jefe", carrera);
		}
		
		
		info.put("nombres", usuario.getNombres());
		info.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
		info.put("apellidos", usuario.getApellidos());
		info.put("email", usuario.getEmail());
		info.put("ref", usuario.getId());
		info.put("first", usuario.isFirst());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		System.out.println(accessToken.getAdditionalInformation());
		return accessToken;
	}

}
