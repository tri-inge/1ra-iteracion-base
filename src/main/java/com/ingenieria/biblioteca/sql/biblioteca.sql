/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  alexis
 * Created: 7/03/2019
 */

CREATE DATABASE biblioteca;

Drop table if exists administrador,profesor; 

CREATE TABLE administrador(
  id serial               primary key       not null,
  nombre                  text              not null,
  correo                  text              not null,
  contrasena              text              not null,
  num_trabajador          text              not null
);


CREATE TABLE profesor(
  id serial               primary key       not null,
  nombre                  text              not null,
  correo                  text              not null,
  departamento            text              not null,
  tipoProf                text              not null,
  contrasena              text              not null,
  num_trabajador          text              not null,
  activo                  boolean           not null default False
);

INSERT INTO public.administrador(
            id, nombre, correo, contrasena, num_trabajador)
    VALUES (1 , 'alex' , 'alex.hdz.c@ciencias.unam.mx', '123456789', '313006636');
