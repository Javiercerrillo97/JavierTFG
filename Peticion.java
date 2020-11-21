package aplicaciontcp;

import java.io.Serializable;

public class Peticion implements Serializable{

    private String tipoPeticion;
    private String nombreUsuario;
    private String contrasena;
    private String categoria;
    

    public Peticion(String tipoPeticion, String nombreUsuario) {
        this.tipoPeticion = tipoPeticion;
        this.nombreUsuario = nombreUsuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoPeticion() {
        return tipoPeticion;
    }

    public void setTipoPeticion(String tipoPeticion) {
        this.tipoPeticion = tipoPeticion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
