/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.web;

import com.ingenieria.biblioteca.controller.AdministradorJpaController;
import com.ingenieria.biblioteca.modelo.Administrador;
import com.ingenieria.biblioteca.modelo.PersistenceUtil;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author alexis
 */
@Named(value = "administradorController")
@Dependent
public class AdministradorController {
    AdministradorJpaController jpa;
    Administrador administrador ;
    
    /**
     * Creates a new instance of AdministradorController
     */
    public AdministradorController() {
            jpa = new AdministradorJpaController(PersistenceUtil.getEntityManagerFactory());
        administrador = new Administrador(); 
    }
        public Administrador getAdministrador(){
        return administrador;
    }
    
    public void setAdministrador(Administrador a){
        administrador = a;
    }
    
   public String addAdministrador(){
       jpa.create(administrador);
       return "lista";
   }
   
   public List<Administrador> getRegistrados(){
       return jpa.findAdministradorEntities();
   }
   
    
}
