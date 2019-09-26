/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Artista;
import entities.Album;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import session.AlbumFacade;
import session.ArtistaFacade;

/**
 *
 * @author Asde
 */
@Named(value = "albumesBean")
@RequestScoped
public class AlbumesBean {

    @EJB
    private ArtistaFacade aFacade;

    @EJB
    private AlbumFacade alFacade;

    @Inject
    SessionBean session;

    private List<Artista> artistas;
    private List<Album> albumes;

    private List<String> idArtistasSeleccionados;

    private Album nuevoAlbum;

    /**
     * Creates a new instance of AlbumesBean
     */
    public AlbumesBean() {
    }

    @PostConstruct
    public void init() {
        initListas();

        this.idArtistasSeleccionados = new ArrayList<>();
        this.nuevoAlbum = new Album();
    }

    public void SeleccionarAlbum(Integer idAlbum) {
        this.nuevoAlbum = alFacade.find(idAlbum);
    }

    private void initListas() {
        this.albumes = alFacade.findAll();
        this.artistas = aFacade.findAll();
    }

    public String crearAlbum() {
        return "nuevoAlbum";
    }

    public String modificarAlbum(Integer idAlbum) {
        this.session.setAlbumSeleccionado(alFacade.find(idAlbum));

        return "modificarAlbum";
    }

    public void eliminarAlbum(Integer idAlbum) {
        alFacade.remove(alFacade.find(idAlbum));
        initListas();
    }

    public String verAlbum(Integer idAlbum) {
        this.session.setAlbumSeleccionado(alFacade.find(idAlbum));

        return "album";
    }

    public void filtrar() {
        List<Artista> a = new ArrayList<>();

        for (String aAux : idArtistasSeleccionados) {
            a.add(aFacade.find(Integer.parseInt(aAux)));
        }

        this.albumes = new ArrayList<>(alFacade.filtrarAlbumes(a));
    }

    public String formatearFecha(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(date);
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }

    public List<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(List<Album> albumes) {
        this.albumes = albumes;
    }

    public List<String> getIdArtistasSeleccionados() {
        return idArtistasSeleccionados;
    }

    public void setIdArtistasSeleccionados(List<String> idArtistasSeleccionados) {
        this.idArtistasSeleccionados = idArtistasSeleccionados;
    }

}
