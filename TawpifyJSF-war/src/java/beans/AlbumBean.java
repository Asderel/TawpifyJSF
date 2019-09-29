/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Album;
import entities.Cancion;
import entities.ListaReproduccion;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import session.AlbumFacade;
import session.CancionFacade;
import session.ListaReproduccionFacade;

/**
 *
 * @author Asde
 */
@Named(value = "albumBean")
@RequestScoped
public class AlbumBean {

    @EJB
    private ListaReproduccionFacade listaFacade;

    @EJB
    private CancionFacade cFacade;

    @EJB
    private AlbumFacade alFacade;

    @Inject
    SessionBean session;

    private List<ListaReproduccion> listasReproduccion;

    private Album album;
    private Cancion cancionSeleccionada;
    private String idListaSeleccionada;

    /**
     * Creates a new instance of AlbumBean
     */
    public AlbumBean() {
    }

    @PostConstruct
    public void init() {
        this.album = session.getAlbumSeleccionado();
        this.listasReproduccion = session.getUsuarioConectado().getAdministrador() == 1 ? listaFacade.findAll() : listaFacade.selectListasReproduccionByUsuario(session.getUsuarioConectado());
        this.idListaSeleccionada = "";

        if (session.getCancionSeleccionada() != null) {
            this.cancionSeleccionada = session.getCancionSeleccionada();
        } else {
            this.cancionSeleccionada = new Cancion();
        }
    }

    public void crearCancion() {
        cancionSeleccionada.setIdAlbum(album);
        cFacade.create(cancionSeleccionada);
        album.getCancionCollection().add(cancionSeleccionada);
        alFacade.edit(album);

        session.setCancionSeleccionada(null);
        this.cancionSeleccionada = new Cancion();
    }

    public void modificarCancion() {
        album.getCancionCollection().remove(cancionSeleccionada);
        album.getCancionCollection().add(cancionSeleccionada);
        alFacade.edit(album);
        session.setCancionSeleccionada(null);
    }

    public void eliminarCancion(Integer idCancion) {
        Cancion cAux = cFacade.find(idCancion);
        album.getCancionCollection().remove(cAux);
        alFacade.edit(album);
        cFacade.remove(cAux);
    }

    public void SeleccionarCancion(Integer idCancion) {
        this.cancionSeleccionada = cFacade.find(idCancion);
        session.setCancionSeleccionada(cancionSeleccionada);
    }

    public void incluirCancion() {
        ListaReproduccion l = listaFacade.find(Integer.parseInt(idListaSeleccionada));

        if (!cancionSeleccionada.getListaReproduccionCollection().contains(l)) {
            cancionSeleccionada.getListaReproduccionCollection().add(l);
            cFacade.edit(cancionSeleccionada);

            l.getCancionCollection().add(cancionSeleccionada);
            listaFacade.edit(l);
        }
    }

    public void incluirAlbum() {
        ListaReproduccion l = listaFacade.find(Integer.parseInt(idListaSeleccionada));

        for (Cancion c : album.getCancionCollection()) {

            if (!c.getListaReproduccionCollection().contains(l)) {
                c.getListaReproduccionCollection().add(l);
                cFacade.edit(c);

                l.getCancionCollection().add(c);
                listaFacade.edit(l);
            }
        }
    }

    public String formatearFecha(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(date);
    }

    public List<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public void setListasReproduccion(List<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Cancion getCancionSeleccionada() {
        return cancionSeleccionada;
    }

    public void setCancionSeleccionada(Cancion cancionSeleccionada) {
        this.cancionSeleccionada = cancionSeleccionada;
    }

    public String getIdListaSeleccionada() {
        return idListaSeleccionada;
    }

    public void setIdListaSeleccionada(String idListaSeleccionada) {
        this.idListaSeleccionada = idListaSeleccionada;
    }
}
