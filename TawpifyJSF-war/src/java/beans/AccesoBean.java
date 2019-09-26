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
@Named(value = "accesoBean")
@RequestScoped
public class AccesoBean {

    @EJB
    UsuarioFacade uFacade;

    @Inject
    SessionBean session;

    private String nombre;
    private String password;

    /**
     * Creates a new instance of AccesoBean
     */
    public AccesoBean() {
    }

    @PostConstruct
    public void init() {
        nombre = "";
        password = "";
    }

    public String acceder() {
        Usuario u = uFacade.login(nombre, password);
        if(u != null) {
            session.setUsuarioConectado(u);
            return "index";
        } else {
            return null;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
