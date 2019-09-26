/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Artista;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import session.ArtistaFacade;

/**
 *
 * @author Asde
 */
@Named(value = "artistasBean")
@SessionScoped
public class ArtistasBean implements Serializable{

    @EJB
    private ArtistaFacade aFacade;

    private List<Artista> artistas;

    private Artista nuevoArtista;

    /**
     * Creates a new instance of ArtistasBean
     */
    public ArtistasBean() {
    }

    @PostConstruct
    public void init() {
        buscarArtistas();

        this.nuevoArtista = new Artista();
    }

    public void SeleccionarArtista(Integer idArtista) {
        this.nuevoArtista = aFacade.find(idArtista);
    }

    private void buscarArtistas() {
        this.artistas = aFacade.findAll();
    }

    public void crearArtista() {
        aFacade.create(nuevoArtista);
        this.nuevoArtista = new Artista();
        buscarArtistas();
    }

    public void modificarArtista() {
        aFacade.edit(nuevoArtista);
        nuevoArtista = new Artista();
        buscarArtistas();
    }

    public void eliminarArtista(Integer idArtista) {
        aFacade.remove(aFacade.find(idArtista));
        buscarArtistas();
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }

    public Artista getNuevoArtista() {
        return nuevoArtista;
    }

    public void setNuevoArtista(Artista nuevoArtista) {
        this.nuevoArtista = nuevoArtista;
    }
}
