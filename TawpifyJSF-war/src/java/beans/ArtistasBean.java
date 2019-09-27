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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import session.ArtistaFacade;

/**
 *
 * @author Asde
 */
@Named(value = "artistasBean")
@RequestScoped
public class ArtistasBean implements Serializable {

    @EJB
    private ArtistaFacade aFacade;

    @Inject
    SessionBean session;

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

        if (session.getArtistaSeleccionado() != null) {
            this.nuevoArtista = session.getArtistaSeleccionado();
        } else {
            this.nuevoArtista = new Artista();
        }
    }

    public void SeleccionarArtista(Integer idArtista) {
        this.nuevoArtista = aFacade.find(idArtista);
        session.setArtistaSeleccionado(nuevoArtista);

    }

    private void buscarArtistas() {
        this.artistas = aFacade.findAll();
    }

    public void crearArtista() {
        aFacade.create(nuevoArtista);

        this.nuevoArtista = new Artista();
        session.setArtistaSeleccionado(null);
        buscarArtistas();
    }

    public void modificarArtista() {
        aFacade.edit(nuevoArtista);

        this.nuevoArtista = new Artista();
        session.setArtistaSeleccionado(null);
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
