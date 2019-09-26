/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Genero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import session.GeneroFacade;

/**
 *
 * @author Asde
 */
@Named(value = "generosBean")
@SessionScoped
public class generosBean implements Serializable{

    @EJB
    private GeneroFacade gFacade;

    private List<Genero> generos;
    private List<Genero> generosFiltro;

    private Genero nuevoGenero;

    /**
     * Creates a new instance of generosBean
     */
    public generosBean() {
    }

    @PostConstruct
    public void init() {
        buscarGeneros();

        this.nuevoGenero = new Genero();
    }

    public void SeleccionarGenero(Integer idGenero) {
        this.nuevoGenero = gFacade.find(idGenero);
    }

    private void buscarGeneros() {
        this.generos = gFacade.findAll();
        this.generosFiltro = new ArrayList<>(generos);
    }

    public void crearGenero() {
        gFacade.create(nuevoGenero);
        this.nuevoGenero = new Genero();
        buscarGeneros();
    }

    public void modificarGenero() {
        gFacade.edit(nuevoGenero);
        nuevoGenero = new Genero();
        buscarGeneros();
    }

    public void eliminarGenero(Integer idGenero) {
        gFacade.remove(gFacade.find(idGenero));
        buscarGeneros();
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Genero> getGenerosFiltro() {
        return generosFiltro;
    }

    public void setGenerosFiltro(List<Genero> generosFiltro) {
        this.generosFiltro = generosFiltro;
    }

    public Genero getNuevoGenero() {
        return nuevoGenero;
    }

    public void setNuevoGenero(Genero nuevoGenero) {
        this.nuevoGenero = nuevoGenero;
    }

}
