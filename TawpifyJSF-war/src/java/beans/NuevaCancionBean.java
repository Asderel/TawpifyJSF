/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Album;
import entities.Artista;
import entities.Cancion;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import session.ArtistaFacade;
import session.CancionFacade;

/**
 *
 * @author Asde
 */
@Named(value = "nuevaCancionBean")
@RequestScoped
public class NuevaCancionBean implements Serializable {

    @EJB
    private ArtistaFacade aFacade;

    @EJB
    private CancionFacade cFacade;

    @Inject
    SessionBean session;

    private List<Artista> artistas;
    private List<String> idArtistasSeleccionados;

    private Cancion nuevaCancion;
    private Album albumSeleccionado;

    /**
     * Creates a new instance of NuevaCancionBean
     */
    public NuevaCancionBean() {
    }

    @PostConstruct
    public void init() {
        this.albumSeleccionado = session.getAlbumSeleccionado();
        this.artistas = aFacade.findAll();
        artistas.remove(this.albumSeleccionado.getIdArtista());

        if (this.session.getCancionSeleccionada() != null) {
            this.nuevaCancion = session.getCancionSeleccionada();
        } else {
            this.nuevaCancion = new Cancion();
        }

        this.idArtistasSeleccionados = new ArrayList<>();
    }

    public String modificarCancion() {
        nuevaCancion.setArtistaCollection(obtenerArtistas());

        nuevaCancion.setIdAlbum(albumSeleccionado);
        cFacade.create(nuevaCancion);

        for (Artista a : obtenerArtistas()) {

            if (!a.getCancionCollection().contains(nuevaCancion)) {
                a.getCancionCollection().add(nuevaCancion);
                aFacade.edit(a);
            }
        }

        reset();
        return "canciones";
    }

    private ArrayList<Artista> obtenerArtistas() {
        ArrayList<Artista> a = new ArrayList<>();

        for (String aAux : idArtistasSeleccionados) {
            a.add(aFacade.find(Integer.parseInt(aAux)));
        }

        return a;
    }

    public void reset() {
        this.session.setCancionSeleccionada(null);
        this.session.setAlbumSeleccionado(null);

        this.idArtistasSeleccionados = null;
        this.nuevaCancion = new Cancion();
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

    public List<String> getIdArtistasSeleccionados() {
        return idArtistasSeleccionados;
    }

    public void setIdArtistasSeleccionados(List<String> idArtistasSeleccionados) {
        this.idArtistasSeleccionados = idArtistasSeleccionados;
    }

    public Cancion getNuevaCancion() {
        return nuevaCancion;
    }

    public void setNuevaCancion(Cancion nuevaCancion) {
        this.nuevaCancion = nuevaCancion;
    }

    public Album getAlbumSeleccionado() {
        return albumSeleccionado;
    }

    public void setAlbumSeleccionado(Album albumSeleccionado) {
        this.albumSeleccionado = albumSeleccionado;
    }

}
