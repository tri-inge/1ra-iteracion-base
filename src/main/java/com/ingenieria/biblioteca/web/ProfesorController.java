/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.web;

import com.ingenieria.biblioteca.controller.ProfesorJpaController;
import com.ingenieria.biblioteca.lib.Mailer;
import com.ingenieria.biblioteca.modelo.PersistenceUtil;
import com.ingenieria.biblioteca.modelo.Profesor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author alexis
 */
@ManagedBean
@RequestScoped
public class ProfesorController {

    private final ProfesorJpaController jpa;
    private Profesor profesor;
    private List<Profesor> lista;

    /**
     * Creates a new instance of ProfesorController
     */
    public ProfesorController() {
        jpa = new ProfesorJpaController(PersistenceUtil.getEntityManagerFactory());
        profesor = new Profesor();
        lista = jpa.findProfesorEntities();
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor p) {
        profesor = p;
    }

    public List<Profesor> getLista() {
        return lista;
    }

    public String addProfesor() {
        jpa.create(profesor);
        return "lista";
    }

    public void guardar() {
        jpa.guardar(profesor);
        lista = jpa.findProfesorEntities();
    }

    public boolean registrarProfesor() {
        String ePattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@ciencias.unam.mx";
        String numTrab = "^[0-9]+";
        Pattern pattern = Pattern.compile(ePattern);
        Pattern pat = Pattern.compile(numTrab);

        String correo = profesor.getCorreo();
        String numTrabaja = profesor.getNumTrabajador();
        String nombre = profesor.getNombre();
        String cadena = "^[A-Za-z\\s]*";
        Pattern pat1 = Pattern.compile(cadena);

        if (correoExistente(correo)) {
            muestraMensaje("El correo ya esta registrado.");
        } else {
            if (profesor.getCorreo() != null) {
                Matcher matcher = pattern.matcher(correo);
                Matcher mat = pat.matcher(numTrabaja);
                Matcher mat1 = pat1.matcher(nombre);
                if (matcher.matches()) {
                    if (mat.matches()) {
                        System.err.println("Error");
                        if (mat1.matches()) {
                            System.err.println("Numero de trabajador valido Encontrado.");
                            jpa.guardar(profesor);
                            lista = jpa.findProfesorEntities();
                            String[] params = {"biopractice20191@gmail.com", "Biopractice1234", profesor.getCorreo(), "smtp.gmail.com", "587", "Confirma tu correo", "<a href='localhost:8084/biblioteca'></a>"};
                            new Mailer().envia(params);
                            redirecciona("/registro.xhtml");
                            return true;
                        } else {
                            muestraMensaje("Nombre invalido");
                        }

                    } else {
                        muestraMensaje("Ingresa un Numero de trabajador valido");
                    }

                } else {
                    muestraMensaje("Correo invalido");
                }

            }
        }
        return false;

    }

    public void registra() {
        registrarProfesor();
    }

    public void modificar() {
        jpa.modificar(profesor);
        lista = jpa.findProfesorEntities();
    }

    public void eliminar() {
        jpa.eliminar(profesor);
        lista = jpa.findProfesorEntities();
    }

    public Profesor buscar() {
        lista.clear();
        lista = jpa.findProfesor(profesor);
        System.out.println(profesor.getId().toString());
        return jpa.findProfesor(profesor.getId());
    }

    public void activaProfesor() {
        profesor = jpa.findProfesor(profesor.getId());
        System.out.println(profesor.getId().toString());
        profesor.setActivo(true);
        jpa.modificar(profesor);
        String[] params = {"biopractice20191@gmail.com", "Biopractice1234", profesor.getCorreo(), "smtp.gmail.com", "587", "Correo activado", "<a href='localhost:8080/biopractice'></a>"};
        new Mailer().envia(params);

    }

    public void desactivaProfesor() {
        profesor = jpa.findProfesor(profesor.getId());
        System.out.println(profesor.getId().toString());
        profesor.setActivo(false);
        jpa.modificar(profesor);
    }

    public List<Profesor> getRegistrados() {
        return jpa.findProfesorEntities();
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Profesor Editado", profesor.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Profesor Editado", profesor.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void rowEditListener(RowEditEvent event) {
        final Profesor computer = (Profesor) event.getObject();
        // Realizaremos las operaciones que correspondan
    }

    private void redirecciona(String direccion) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + direccion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void muestraMensaje(String mensaje) {
        FacesMessage mensajeFace = new FacesMessage(mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(mensajeFace);
    }

    public List<String> getCorreos() {

        List<String> correosList = new ArrayList<>();

        for (Profesor alumn : lista) {
            correosList.add(alumn.getCorreo());
        }

        return correosList;
    }

    public boolean correoExistente(String correo) {

        List<String> correos = getCorreos();

        for (String email : correos) {
            if (correo.equals(email)) {
                return true;
            }
        }
        return false;
    }

}
