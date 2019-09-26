/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import session.UsuarioFacade;

/**
 *
 * @author Asde
 */
@Named(value = "usuariosBean")
@Dependent
public class UsuariosBean {

    @EJB
    private UsuarioFacade uFacade;

    @Inject
    SessionBean session;

    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltro;

    /**
     * Creates a new instance of UsuariosBean
     */
    public UsuariosBean() {
    }

    @PostConstruct
    public void init() {
        buscarUsuarios();
    }

    private void buscarUsuarios() {
        this.usuarios = uFacade.findAll();
        this.usuariosFiltro = new ArrayList<>(usuarios);
    }

    public String verDetalleUsuario(Integer idUsuario) {
        Usuario uSeleccionado = uFacade.find(idUsuario);

        if (uSeleccionado != null) {
            session.setUsuarioSeleccinoado(uSeleccionado);
            return "modificarUsuario";
        } else {
            return null;
        }
    }

    public void eliminarUsuario(Integer idUsuario) {
        Usuario uSeleccionado = uFacade.find(idUsuario);
        boolean resp;

        if (uSeleccionado != null) {
            resp = uSeleccionado.getIdUsuario().equals(session.getUsuarioConectado().getIdUsuario());
            uFacade.remove(uSeleccionado);

            if (resp) {
                try {
                    session.setUsuarioConectado(null);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/index.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        buscarUsuarios();
    }

    public String nuevoUsuario() {
        return "modificarUsuario";
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuariosFiltro() {
        return usuariosFiltro;
    }

    public void setUsuariosFiltro(List<Usuario> usuariosFiltro) {
        this.usuariosFiltro = usuariosFiltro;
    }

}
