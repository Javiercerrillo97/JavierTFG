package aplicaciontcp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ProcesadorPeticiones {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    String contrasena;
    Date fech;
    int id;
    String titulo;
    String estado;
    String nombreCat;
    String nombreUsuario;
    PreparedStatement stmt = null;
    PreparedStatement ps;
    PreparedStatement st = null;
    Vector records = new Vector(10, 10);
    ProcesadorPeticiones dat;
    ResultSet rs = null;
    ServerSocket server = null;
    Socket client = null;
    private Connection con = null;
    ObjectOutputStream out = null;
    String str = null;
    GeneradorPeticiones p = null;
    String resultado;
    ProcesadorPeticiones r = null;

    public ProcesadorPeticiones() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");
        } catch (ClassNotFoundException ex) {
            LOGGER.severe(str);
        } catch (SQLException ex) {
            LOGGER.severe(str);
        }

    }

    public Respuesta login(String usuario, String password) {
        Respuesta respuesta = new Respuesta();

        try {

            PreparedStatement ps = con.prepareStatement("SELECT nombreUsuario FROM usuario WHERE nombreUsuario = ? and contrasena = ?");
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                respuesta.setRespuestaOk(true);
                LOGGER.info(str);
            } else {
                respuesta.setRespuestaOk(false);
                LOGGER.warning(str);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    public Respuesta crear_cat(String nombreCat, String usuario) {
        Respuesta respuesta = new Respuesta();
        try {

            PreparedStatement ps = con.prepareStatement("INSERT INTO categoria(nombreCat, usuario) VALUES (?,?)");

            ps.setString(1, nombreCat);
            ps.setString(2, usuario);

            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public Respuesta lista_tareas(String usuario) {
        Respuesta respuesta = new Respuesta();
        ArrayList lista = new ArrayList();
        try {

            PreparedStatement ps = con.prepareStatement("SELECT titulo FROM tarea WHERE nombreUsuario = ?");
            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //respuesta.setLista(rs.getString("titulo"));
                respuesta.setLista(lista);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;

    }

    public ProcesadorPeticiones crear_tarea(String usuario, int idTarea, String titulo, String estado, Date fecha) {
        ProcesadorPeticiones r = null;
        try {
            Socket clientsocket = new Socket("localhost", 1400);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            r = new ProcesadorPeticiones();
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");

            PreparedStatement ps = con.prepareStatement("INSERT INTO tarea(idTarea, titulo, estado, fech, nombreUsuario) VALUES (?,?,?,?,?)");
            ps.setInt(1, idTarea);
            ps.setString(2, titulo);
            ps.setString(3, estado);
            ps.setDate(4, fecha);
            ps.setString(5, usuario);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Operacion no realizada", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            out.writeObject(r);
            out.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public ProcesadorPeticiones buscarTarea(String usuario, int idTarea) {
        ProcesadorPeticiones r = null;
        try {
            Socket clientsocket = new Socket("localhost", 1400);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            r = new ProcesadorPeticiones();
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tarea WHERE idTarea = ?");

        } catch (IOException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcesadorPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
}
