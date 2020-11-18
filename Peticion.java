/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicaciontcp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro
 */
public class Peticion implements Serializable{
    String nombreUsuario;
    String password;
    String mensaje;
    int idTarea;
    String titulo;
    char estado;
    String fecha;
    String nombreCat;

    public Peticion(String nombreUsuario, String mensaje) {
        this.nombreUsuario = nombreUsuario;        
        this.mensaje = mensaje;
    }


public Peticion login(String usuario, String password, String mensaje){
    Peticion p = null;
    ObjectOutputStream oos;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new Peticion(usuario,mensaje);
            p.password = password;
            oos.writeObject(p);
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
};

public Peticion crearTarea(String usuario, String mensaje, String titulo,char estado, String fecha){
    Peticion p = null;    
    try {            
            ObjectOutputStream oos;
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new Peticion(usuario,mensaje);
            p.titulo = titulo;
            p.estado = estado;
            p.fecha = fecha;
            oos.writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
};

public Peticion ListaTareas(String usuario, String mensaje){
    Peticion p = null;
    ObjectOutputStream oos;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new Peticion(usuario,mensaje);            
            oos.writeObject(p);
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
}

public Peticion CrearCat(String usuario, String mensaje, String nombreCat ){
    Peticion p = null;
    ObjectOutputStream oos;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new Peticion(usuario,mensaje);
            p.nombreCat = nombreCat;
            oos.writeObject(p);
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
}

public Peticion buscarTarea(String usuario, String mensaje,int idTarea ){
    Peticion p = null;
    ObjectOutputStream oos;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new Peticion(usuario,mensaje);
            p.idTarea = idTarea;            
            oos.writeObject(p);
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
}
}

