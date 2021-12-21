package com.solucionesra.springboot.apirest.models.services;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.solucionesra.springboot.apirest.models.entity.Sistema;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

	@Autowired
	private ISistemaService sistemaService;
	
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, int numero, String mensaje_usuario) throws MessagingException {
    	int id_int = 1;
		long id = id_int;
		String mensaje="";
		Sistema sistema = sistemaService.findById(id);
        if(numero==1) {
        	//Bienvenida
        	mensaje=sistema.getMensaje_bienvenida_sistema();
        	mensaje+=mensaje_usuario;
        	mensaje+="<br><br>Para iniciar sesión haga clic ";
        	mensaje+="<a href='"+sistema.getUrl_sistema()+"'>aquí</a>";
        }else if(numero==2) {
        	//Subida syllabus docente
        	mensaje=sistema.getMensaje_subida_syllabus_docente();
        	mensaje+=mensaje_usuario;
        	mensaje+="<br><br>Una vez que un miembro de la comisión lo revise, será notificado mediante correo electronico";
        }else if(numero==3) {
        	mensaje=sistema.getMensaje_revision_syllabus_comision();
        	mensaje+=mensaje_usuario;
        	mensaje+="<br><br>Se le ha enviado un correo al docente para informarle de la revisión";
        }else if(numero==4) {
        	mensaje=sistema.getMensaje_revision_syllabus_docente();
        	mensaje+=mensaje_usuario;
        }
    	String cuerpo = "<img src='"+sistema.getUrl_imagen()+"'  height='60'><br><br>"+mensaje;
        MimeMessage mes = javaMailSender.createMimeMessage();
        mes.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mes, true);
        helper.setFrom("info@ucm.cl");
        helper.setTo(toEmail);
        helper.setText(cuerpo, true);
        javaMailSender.send(mes);

    }
    
    public void sendMailContacto(String fromEmail, String subject, String message) throws MessagingException {
        
    	String cuerpo = "<img src='https://i.imgur.com/ssnIFYL.png'  height='60'><br><br>"+message;
        MimeMessage mes = javaMailSender.createMimeMessage();
        mes.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mes, true);
        helper.setFrom(fromEmail);
        helper.setTo("kipuse3@gmail.com");
        helper.setText(cuerpo, true);
        javaMailSender.send(mes);

    }

}
