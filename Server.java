package aplicaciontcp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

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
import java.util.logging.Logger;
import sun.util.logging.PlatformLogger;

public class Server extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    Statement stmt = null;
    Vector records = new Vector(10, 10);
    ProcesadorPeticiones dat;
    ResultSet rs = null;
    ServerSocket server = null;
    Connection con = null;
    String str = null;
    ProcesadorPeticiones procesador;

    public Server() {
    }

    public void comienzaEscucha() {
        procesador = new ProcesadorPeticiones();
        try {
            server = new ServerSocket(1400);
            System.out.println("Abriendo el servidor");
            while (true) {
                try {
                    Socket client;
                    int cc;
                    System.out.println("Esperando un cliente...");
                    client = server.accept();
                    System.out.println("Conexión aceptada");
                    atendiendoPeticion(client);
                } catch (IOException ex) {
                    LOGGER.info("Error al aceptar el cliente");
                }
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atendiendoPeticion(Socket client) {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        Respuesta respuesta = null;
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            try {
                Peticion peticion = (Peticion) in.readObject();
                LOGGER.info(peticion.getTipoPeticion());
                switch (peticion.getTipoPeticion()) {
                    case "login":
                        respuesta = procesador.login(peticion.getNombreUsuario(), peticion.getContrasena());
                        break;
                    case "crear_tarea":
                        //r.crear_tarea(r.titulo, r.id, r.estado, r.nombreCat, r.fech);
                        break;
                    case "lista de tareas":
                        respuesta = procesador.lista_tareas(peticion.getNombreUsuario());
                        break;
                    case "crear categoria":
                        respuesta = procesador.crear_cat(peticion.getNombreUsuario(), peticion.getCategoria());
                        break;
                    case "buscar tarea":
                        //r.buscarTarea(r.nombreUsuario, r.id);
                        break;

                }

            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Se ha recibido la información");
            out.writeObject(respuesta);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.comienzaEscucha();
    }
}
