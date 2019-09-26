package beans;

import entities.Genero;
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

    // Usuarios
    private Usuario usuarioConectado;
    private Usuario usuarioSeleccinoado;

    // Generos
    private Genero generoSeleccinoado;

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

    public Genero getGeneroSeleccinoado() {
        return generoSeleccinoado;
    }

    public void setGeneroSeleccinoado(Genero generoSeleccinoado) {
        this.generoSeleccinoado = generoSeleccinoado;
    }
}
