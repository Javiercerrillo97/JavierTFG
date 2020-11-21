/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicaciontcp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro
 */
public class GeneradorPeticiones {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    String nombreUsuario;
    int idTarea;
    String titulo;
    char estado;
    String fecha;
    String nombreCat;

    public GeneradorPeticiones(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;        
    }


public boolean login(String password){    
    ObjectOutputStream oos;
    ObjectInputStream ois;
    boolean retorno = false;
    Respuesta respuesta;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());                        
            Peticion peticion = new Peticion("login",this.nombreUsuario);
            peticion.setContrasena(password);
            oos.writeObject(peticion);
            respuesta = (Respuesta) ois.readObject();
            retorno = respuesta.isRespuestaOk();
            //cambiar codigo al cliente , crear el la lectura en el propio metodp
        } catch (IOException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    return retorno;      
}

public void CrearCat(String usuario, String mensaje, String nombreCat ){
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Respuesta respuesta;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            Peticion peticion = new Peticion("crearCategoria", this.nombreUsuario);
            peticion.setCategoria(nombreCat);
            oos.writeObject(peticion);
            respuesta = (Respuesta) ois.readObject();
            
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}

public ArrayList ListaTareas(String usuario){
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Respuesta respuesta;
    ArrayList lista = new ArrayList();
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());  
            Peticion peticion = new Peticion("listaTareas",this.nombreUsuario);          
            oos.writeObject(peticion);
            respuesta = (Respuesta) ois.readObject();
            LOGGER.info(respuesta.getLista().toString());
            lista = respuesta.getLista();
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
}

/*public GeneradorPeticiones crearTarea(String usuario, String mensaje, String titulo,char estado, String fecha){
    GeneradorPeticiones p = null;    
    try {            
            ObjectOutputStream oos;
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new GeneradorPeticiones(usuario,mensaje);
            p.titulo = titulo;
            p.estado = estado;
            p.fecha = fecha;
            oos.writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
};*/





/*public GeneradorPeticiones buscarTarea(String usuario, String mensaje,int idTarea ){
    GeneradorPeticiones p = null;
    ObjectOutputStream oos;
        try {
            Socket clientSocket = new Socket("localhost",1400);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            p = new GeneradorPeticiones(usuario,mensaje);
            p.idTarea = idTarea;            
            oos.writeObject(p);
            
            //este es el metodo que le he programado para que envie la peticion, la captura de la respuesta esta en el form de diseño 
        } catch (IOException ex) {
            Logger.getLogger(GeneradorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
}*/
}

