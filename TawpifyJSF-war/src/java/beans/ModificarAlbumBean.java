/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Album;
import entities.Artista;
import entities.Genero;
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
import session.GeneroFacade;

/**
 *
 * @author Asde
 */
@Named(value = "modificarAlbumBean")
@RequestScoped
public class ModificarAlbumBean {

    @EJB
    private ArtistaFacade aFacade;

    @EJB
    private AlbumFacade alFacade;

    @EJB
    private GeneroFacade gFacade;

    @Inject
    SessionBean session;

    private List<Artista> artistas;
    private List<Genero> generos;

    private List<String> idGenerosSeleccionados;

    private Album nuevoAlbum;
    private Integer idArtistaSeleccionado;

    /**
     * Creates a new instance of ModificarAlbumBean
     */
    public ModificarAlbumBean() {
    }

    @PostConstruct
    public void init() {
        this.artistas = aFacade.findAll();
        this.generos = gFacade.findAll();
        this.nuevoAlbum = session.getAlbumSeleccionado();
        this.idGenerosSeleccionados = new ArrayList<>();

        this.idArtistaSeleccionado = 0;
    }

    public String modificarAlbum() {
        nuevoAlbum.setIdArtista(aFacade.find(idArtistaSeleccionado));
        nuevoAlbum.setGeneroCollection(obtenerGeneros());

        alFacade.create(nuevoAlbum);

        return "albumes";
    }

    private ArrayList<Genero> obtenerGeneros() {
        ArrayList<Genero> g = new ArrayList<>();

        for (String gAux : idGenerosSeleccionados) {
            g.add(gFacade.find(Integer.parseInt(gAux)));
        }

        return g;
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

    public List<String> getIdGenerosSeleccionados() {
        return idGenerosSeleccionados;
    }

    public void setIdGenerosSeleccionados(List<String> idGenerosSeleccionados) {
        this.idGenerosSeleccionados = idGenerosSeleccionados;
    }

    public Album getNuevoAlbum() {
        return nuevoAlbum;
    }

    public void setNuevoAlbum(Album nuevoAlbum) {
        this.nuevoAlbum = nuevoAlbum;
    }

    public Integer getIdArtistaSeleccionado() {
        return idArtistaSeleccionado;
    }

    public void setIdArtistaSeleccionado(Integer idArtistaSeleccionado) {
        this.idArtistaSeleccionado = idArtistaSeleccionado;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

}
