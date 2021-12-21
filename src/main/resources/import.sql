--INSERT INTO docentes (nombre, apellido, email, create_at) VALUES ('Marco', 'Toranzo', 'mtoranzo@ucm.com', '2020-10-01');
--INSERT INTO docentes (nombre, apellido, email, create_at) VALUES ('Paulo', 'Gonzalez', 'paulog@ucm.com', '2020-10-01');

insert into usuario (nombres, apellidos, email, rut_usuario, username, password, vigente) values ('Cristian Daniel','Ramírez Donoso','cristianrd92@gmail.com','18041574-2','cristian','$2a$10$HtjPGGVc0I09XB6.T2PqUu3MfO2zUZk6PiRjfSz5YKajHnwHclH6y',true);
insert into usuario (nombres, apellidos, email, rut_usuario, username, password, vigente) values ('Fabián','Abarzua Cayulao','fabarzua696@gmail.com','18892741-1','fabian','$2a$10$xfLVyiWc16yxRi7Re2wv9.mn3OWSb62t7Uc6fCJ8SA7n9ZH2CyQ1W',true);
insert into usuario (nombres, apellidos, email, rut_usuario, username, password, vigente) values ('Alexis','Sepulveda','alexis.sepulveda@cftsa.cl','17361412-5','alexis','$2a$10$xfLVyiWc16yxRi7Re2wv9.mn3OWSb62t7Uc6fCJ8SA7n9ZH2CyQ1W',true);

insert into role (name) values ('ROLE_USER');
insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_SECRETARY');
insert into role (name) values ('ROLE_COMISION');

insert into usuario_role (usuario_id, role_id) values (1,1);
insert into usuario_role (usuario_id, role_id) values (1,2);
insert into usuario_role (usuario_id, role_id) values (2,1);
insert into usuario_role (usuario_id, role_id) values (2,3);
insert into usuario_role (usuario_id, role_id) values (3,4);