package com.solucionesra.springboot.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solucionesra.springboot.apirest.models.services.IRamoCarreraService;
import com.solucionesra.springboot.apirest.models.services.IUsuarioService;

@SpringBootApplication
public class ApiRestApplication implements CommandLineRunner{

	
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IRamoCarreraService ramoCarrera;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
      return "API Funcionando con exito!";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = "12345"; 
		for (int i=0; i<4;i++) { 
			String passwordBcrypt = passwordEncoder.encode(password); 
			System.out.println(passwordBcrypt); 
		} 
	}

}
