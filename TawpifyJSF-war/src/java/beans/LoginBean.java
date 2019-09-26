/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Usuario;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import session.UsuarioFacade;

/**
 *
 * @author Asde
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    @EJB
    UsuarioFacade uFacade;

    @Inject
    SessionBean session;

    private Usuario u;
    private boolean admin;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    @PostConstruct
    public void init() {
        this.u = new Usuario();
        this.admin = false;
    }

    public String crearUsuario() {
        uFacade.create(u);

        if (u.getIdUsuario() != null) {
            session.setUsuarioConectado(u);
            return "index";
        } else {
            return null;
        }
    }

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
