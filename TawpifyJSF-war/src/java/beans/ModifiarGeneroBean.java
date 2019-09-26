/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Genero;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import session.GeneroFacade;

/**
 *
 * @author Asde
 */
@Named(value = "modifiarGeneroBean")
@RequestScoped
public class ModifiarGeneroBean {

    @EJB
    private GeneroFacade gFacade;

    private List<Genero> generos;
    private List<Genero> generosFiltro;

    Genero nuevoGenero;

    /**
     * Creates a new instance of ModifiarGeneroBean
     */
    public ModifiarGeneroBean() {
    }

    @PostConstruct
    public void init() {
        buscarGeneros();

        this.nuevoGenero = new Genero();
    }

    private void buscarGeneros() {
        this.generos = gFacade.findAll();
        this.generosFiltro = new ArrayList<>(generos);
    }

    public void crearGenero() {
        gFacade.create(nuevoGenero);
        this.nuevoGenero = new Genero();
    }

    public void modificarGenero(Integer idGenero) {
        gFacade.edit(gFacade.find(idGenero));
    }

    public void eliminarGenero(Integer idGenero) {
        gFacade.remove(gFacade.find(idGenero));
    }

}
