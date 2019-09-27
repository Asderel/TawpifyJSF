/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Cancion;
import entities.ListaReproduccion;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import session.CancionFacade;
import session.ListaReproduccionFacade;

/**
 *
 * @author Asde
 */
@Named(value = "listaReproduccionBean")
@RequestScoped
public class ListaReproduccionBean {

    @EJB
    ListaReproduccionFacade listaFacade;

    @EJB
    CancionFacade cFacade;

    @Inject
    SessionBean session;

    ListaReproduccion lista;

    /**
     * Creates a new instance of ListaReproduccionBean
     */
    public ListaReproduccionBean() {
    }

    @PostConstruct
    public void init() {
        this.lista = session.getListaSeleccionada();
    }

    public void eliminarCancion(Integer idCancion) {
        Cancion cAux = cFacade.find(idCancion);
        lista.getCancionCollection().remove(cAux);
        listaFacade.edit(lista);

        cAux.getListaReproduccionCollection().remove(lista);
        cFacade.edit(cAux);
    }

    public String formatearFecha(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(date);
    }

    public ListaReproduccion getLista() {
        return lista;
    }

    public void setLista(ListaReproduccion lista) {
        this.lista = lista;
    }

}
