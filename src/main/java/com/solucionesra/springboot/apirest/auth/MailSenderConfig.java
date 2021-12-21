package com.solucionesra.springboot.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.solucionesra.springboot.apirest.models.entity.Sistema;
import com.solucionesra.springboot.apirest.models.services.ISistemaService;

@Configuration
public class MailSenderConfig {
	@Autowired
	private ISistemaService sistemaService;
	
    @Bean
    public JavaMailSender mailSender() {
    	int id_int = 1;
    	long id = id_int;
    	Sistema sistema = sistemaService.findById(id);
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(sistema.getHost_correo());
        javaMailSender.setPort(sistema.getPuerto_correo());
        javaMailSender.setUsername(sistema.getUsuario_correo());
        javaMailSender.setPassword(sistema.getPassword_correo());
        return javaMailSender;
    }
}