/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Album;
import entities.Artista;
import entities.Cancion;
import entities.ListaReproduccion;
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
import session.CancionFacade;
import session.ListaReproduccionFacade;

/**
 *
 * @author Asde
 */
@Named(value = "cancionesBean")
@RequestScoped
public class CancionesBean {

    @EJB
    private ArtistaFacade aFacade;

    @EJB
    private CancionFacade cFacade;

    @EJB
    private AlbumFacade alFacade;

    @EJB
    private ListaReproduccionFacade listaFacade;

    @Inject
    SessionBean session;

    private List<Artista> artistas;
    private List<Album> albumes;
    private List<Cancion> canciones;
    private List<ListaReproduccion> listasReproduccion;

    private List<String> idAlbumesSeleccionados;
    private List<String> idArtistasSeleccionados;

    private Cancion nuevaCancion;
    private Integer idListaSeleccionada;
    private Integer idAlbumSeleccionado;

    /**
     * Creates a new instance of CancionesBean
     */
    public CancionesBean() {
    }

    @PostConstruct
    public void init() {
        initListas();

        this.idAlbumesSeleccionados = new ArrayList<>();
        this.idArtistasSeleccionados = new ArrayList<>();
        this.nuevaCancion = new Cancion();
        this.idListaSeleccionada = 0;
        this.idAlbumSeleccionado = 0;

        if (session.getCancionSeleccionada() != null) {
            this.nuevaCancion = session.getCancionSeleccionada();
        } else {
            this.nuevaCancion = new Cancion();
        }

        resetFiltro();
    }

    public void SeleccionarCancion(Integer idCancion) {
        this.nuevaCancion = cFacade.find(idCancion);
        session.setCancionSeleccionada(nuevaCancion);
    }

    private void initListas() {
        if (session.getCancionesFiltrados() != null) {
            this.canciones = new ArrayList<>(session.getCancionesFiltrados());
        } else {
            this.canciones = cFacade.selectCancionesOrdenadas();
        }
        
        this.albumes = alFacade.findAll();
        this.artistas = aFacade.findAll();
        this.listasReproduccion = listaFacade.findAll();
    }

    private void resetFiltro() {
        session.setCancionesFiltrados(null);
    }

    public String crearCancion() {
        this.session.setAlbumSeleccionado(alFacade.find(idAlbumSeleccionado));
        return "nuevaCancion";
    }

    public String modificarCancion(Integer idCancion) {
        this.session.setCancionSeleccionada(cFacade.find(idCancion));
        this.session.setAlbumSeleccionado(session.getCancionSeleccionada().getIdAlbum());

        return "modificarCancion";
    }

    public void eliminarCancion(Integer idCancion) {
        cFacade.remove(cFacade.find(idCancion));
        initListas();
    }

    public void incluirCancion() {
        ListaReproduccion listaSeleccionada = listaFacade.find(this.idListaSeleccionada);

        if (!nuevaCancion.getListaReproduccionCollection().contains(listaSeleccionada)) {
            nuevaCancion.getListaReproduccionCollection().add(listaSeleccionada);
            cFacade.edit(nuevaCancion);

            listaSeleccionada.getCancionCollection().add(nuevaCancion);
            listaFacade.edit(listaSeleccionada);
        }

        this.nuevaCancion = new Cancion();
        this.idListaSeleccionada = 0;

        this.session.setCancionSeleccionada(null);
        initListas();
    }

    public void filtrar() {
        List<Artista> a = new ArrayList<>();
        List<Album> al = new ArrayList<>();

        for (String aAux : idArtistasSeleccionados) {
            a.add(aFacade.find(Integer.parseInt(aAux)));
        }

        for (String alAux : idAlbumesSeleccionados) {
            al.add(alFacade.find(Integer.parseInt(alAux)));
        }

        this.canciones = new ArrayList<>(cFacade.filtrarCanciones(a, al));

        // FOLLADA QUE TE FLIPAS
        if (!a.isEmpty() || !al.isEmpty()) {
            session.setCancionesFiltrados(this.canciones);
        } else {
            session.setCancionesFiltrados(null);
        }
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

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public Cancion getNuevaCancion() {
        return nuevaCancion;
    }

    public void setNuevaCancion(Cancion nuevaCancion) {
        this.nuevaCancion = nuevaCancion;
    }

    public List<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public void setListasReproduccion(List<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

    public Integer getIdListaSeleccionada() {
        return idListaSeleccionada;
    }

    public void setIdListaSeleccionada(Integer idListaSeleccionada) {
        this.idListaSeleccionada = idListaSeleccionada;
    }

    public List<String> getIdAlbumesSeleccionados() {
        return idAlbumesSeleccionados;
    }

    public void setIdAlbumesSeleccionados(List<String> idAlbumesSeleccionados) {
        this.idAlbumesSeleccionados = idAlbumesSeleccionados;
    }

    public List<String> getIdArtistasSeleccionados() {
        return idArtistasSeleccionados;
    }

    public void setIdArtistasSeleccionados(List<String> idArtistasSeleccionados) {
        this.idArtistasSeleccionados = idArtistasSeleccionados;
    }

    public Integer getIdAlbumSeleccionado() {
        return idAlbumSeleccionado;
    }

    public void setIdAlbumSeleccionado(Integer idAlbumSeleccionado) {
        this.idAlbumSeleccionado = idAlbumSeleccionado;
    }

}
