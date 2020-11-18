package aplicaciontcp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sun.istack.internal.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import sun.util.logging.PlatformLogger;

public class Server extends Thread {

    Statement stmt = null;
    Vector records = new Vector(10, 10);
    Respuestas dat;
    ResultSet rs = null;
    ServerSocket server = null;
    Socket client = null;
    Connection con = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    String str = null;
    Respuestas pub = null;
    Peticion p = null;
    Respuestas r = new Respuestas();

    public Server() {
        try {
            server = new ServerSocket(1400);
            System.out.println("Abriendo el servidor");
            start();
            System.out.println("Esperando un cliente...");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                int cc;

                client = server.accept();
                System.out.println("Conexión aceptada");
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                try {
                    p = (Peticion) in.readObject();
                    System.out.println(p.mensaje);
                    switch (p.mensaje) {
                        case "login":
                            System.out.println("Hola mundo");       
                            r.login(r.nombreUsuario, r.contrasena);
                            break;
                        case "crear_tarea":
                            r.crear_tarea(r.id, r.titulo, r.estado, r.fech, r.nombreCat, r.nombreUsuario);
                            break;
                        case "mostrar tareas":

                    }
                   
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Se ha recibido la información");
                
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public static void main(String[] args) {
        new Server();

    }
}
