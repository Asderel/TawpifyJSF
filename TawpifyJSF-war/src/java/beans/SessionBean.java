package beans;

import entities.Album;
import entities.Cancion;
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

    // Canciones
    private Cancion cancionSeleccionada;

    // Albumes
    private Album albumSeleccionado;

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

    public Cancion getCancionSeleccionada() {
        return cancionSeleccionada;
    }

    public void setCancionSeleccionada(Cancion cancionSeleccionada) {
        this.cancionSeleccionada = cancionSeleccionada;
    }

    public Album getAlbumSeleccionado() {
        return albumSeleccionado;
    }

    public void setAlbumSeleccionado(Album albumSeleccionado) {
        this.albumSeleccionado = albumSeleccionado;
    }
}
