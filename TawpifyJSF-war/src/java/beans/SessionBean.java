/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;

/**
 *
 * @author Asde
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private Usuario usuarioConectado;
    private Usuario usuarioSeleccinoado;

    /**
     * Creates a new instance of SessionBean
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init(){
        this.usuarioConectado = null;
    }

    public String gotoLogin() {
        return "nuevoUsuario";
    }

    public String gotoAcceso() {
        return "accesoUsuario";
    }

    public String logout() {
        this.usuarioConectado = null;

        return "index";
    }

    // GETTER SETTER

    public Usuario getUsuarioConectado() {
        return usuarioConectado;
    }

    public void setUsuarioConectado(Usuario usuarioConectado) {
        this.usuarioConectado = usuarioConectado;
    }

    public Usuario getUsuarioSeleccinoado() {
        return usuarioSeleccinoado;
    }

    public void setUsuarioSeleccinoado(Usuario usuarioSeleccinoado) {
        this.usuarioSeleccinoado = usuarioSeleccinoado;
    }
}
