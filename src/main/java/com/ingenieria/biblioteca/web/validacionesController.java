/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.web;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author alexis
 */
@Named(value = "validacionesController")
@Dependent
public class validacionesController {

    /**
     * Creates a new instance of validacionesController
     */
    public validacionesController() {
    }
      public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
         throws ValidatorException {
      if (((String)arg2).length()<5) {
         throw new ValidatorException(new FacesMessage("Al menos 5 caracteres "));
      }
   }
}
