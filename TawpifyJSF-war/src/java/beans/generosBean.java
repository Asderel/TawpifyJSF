/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Genero;
import entities.Genero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import session.GeneroFacade;

/**
 *
 * @author Asde
 */
@Named(value = "generosBean")
@RequestScoped
public class generosBean implements Serializable{

    @EJB
    private GeneroFacade gFacade;

    @Inject
    SessionBean session;

    private List<Genero> generos;

    private Genero nuevoGenero;

    /**
     * Creates a new instance of generosBean
     */
    public generosBean() {
    }

    @PostConstruct
    public void init() {
        buscarGeneros();

        if (session.getGeneroSeleccionado() != null) {
            this.nuevoGenero = session.getGeneroSeleccionado();
        } else {
            this.nuevoGenero = new Genero();
        }
    }

    public void SeleccionarGenero(Integer idGenero) {
        this.nuevoGenero = gFacade.find(idGenero);
        session.setGeneroSeleccionado(nuevoGenero);
    }

    private void buscarGeneros() {
        this.generos = gFacade.findAll();
    }

    public void crearGenero() {
        gFacade.create(nuevoGenero);
        this.nuevoGenero = new Genero();

        session.setGeneroSeleccionado(null);
        buscarGeneros();
    }

    public void modificarGenero() {
        gFacade.edit(nuevoGenero);
        nuevoGenero = new Genero();

        session.setGeneroSeleccionado(null);
        buscarGeneros();
    }

    public void eliminarGenero(Integer idGenero) {
        gFacade.remove(gFacade.find(idGenero));

        session.setGeneroSeleccionado(null);
        buscarGeneros();
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public Genero getNuevoGenero() {
        return nuevoGenero;
    }

    public void setNuevoGenero(Genero nuevoGenero) {
        this.nuevoGenero = nuevoGenero;
    }

}
