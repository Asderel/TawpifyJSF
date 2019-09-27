/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.ListaReproduccion;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import session.ListaReproduccionFacade;

/**
 *
 * @author Asde
 */
@Named(value = "listasReproduccionBean")
@RequestScoped
public class ListasReproduccionBean {

    @EJB
    ListaReproduccionFacade listaFacade;

    @Inject
    SessionBean session;

    List<ListaReproduccion> listas;

    ListaReproduccion nuevaLista;

    /**
     * Creates a new instance of ListasReproduccionBean
     */
    public ListasReproduccionBean() {
    }

    @PostConstruct
    public void init() {
        cargarlistas();

        if (session.getListaSeleccionada() != null) {
            this.nuevaLista = session.getListaSeleccionada();
        } else {
            this.nuevaLista = new ListaReproduccion();
        }
    }

    private void cargarlistas() {
        if (session.getUsuarioConectado().getAdministrador() == 1) {
            this.listas = listaFacade.findAll();
        } else {
            this.listas = listaFacade.selectListasReproduccionByUsuario(session.getUsuarioConectado());
        }
    }

    public void seleccionarLista(Integer idLista) {
        this.nuevaLista = listaFacade.find(idLista);
        session.setListaSeleccionada(nuevaLista);
    }

    public void crearLista() {
        nuevaLista.setIdUsuario(session.getUsuarioConectado());
        nuevaLista.setFechaCreacion(Date.from(Instant.now()));
        listaFacade.create(nuevaLista);
        
        session.setListaSeleccionada(null);
        cargarlistas();

        this.nuevaLista = new ListaReproduccion();
    }

    public void modificarLista() {
        listaFacade.edit(nuevaLista);
        session.setListaSeleccionada(null);
        cargarlistas();
    }

    public void eliminarLista(Integer idLista) {
        listaFacade.remove(listaFacade.find(idLista));
        cargarlistas();
    }

    public String verLista(Integer idLista) {
        this.session.setListaSeleccionada(listaFacade.find(idLista));

        return "listaReproduccion";
    }

    public String formatearFecha(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(date);
    }

    public List<ListaReproduccion> getListas() {
        return listas;
    }

    public void setListas(List<ListaReproduccion> listas) {
        this.listas = listas;
    }

    public ListaReproduccion getNuevaLista() {
        return nuevaLista;
    }

    public void setNuevaLista(ListaReproduccion nuevaLista) {
        this.nuevaLista = nuevaLista;
    }
}
