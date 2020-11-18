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
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Respuestas implements Serializable {

    String contrasena;
    Date fech;
    String id;
    String titulo;
    String estado;
    String nombreCat;
    String nombreUsuario;
    PreparedStatement stmt = null;
    PreparedStatement ps;
    PreparedStatement st = null;
    Vector records = new Vector(10, 10);
    Respuestas dat;
    ResultSet rs = null;
    ServerSocket server = null;
    Socket client = null;
    Connection con = null;
    ObjectOutputStream out = null;
    String str = null;
    Peticion p = null;
    String resultado;
    Respuestas r = null;

    public Respuestas() {

    }

    public Respuestas(String contrasena, String nombreUsuario) {
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }

    public Respuestas(String contrasena, String id, String titulo, String estado, String nombreCat, String nombreUsuario, Date fecha, PreparedStatement ps, Respuestas dat) {
        this.contrasena = contrasena;
        this.id = id;
        this.titulo = titulo;
        this.estado = estado;
        this.nombreCat = nombreCat;
        this.nombreUsuario = nombreUsuario;
        this.ps = ps;
        this.dat = dat;
        this.fech = fech;
    }

    public Respuestas login(String usuario, String password) {
        Respuestas r = null;

        try {
            Socket clientsocket = new Socket("localhost", 1400);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            r = new Respuestas();
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");

            PreparedStatement ps = con.prepareStatement("SELECT nombreUsuario FROM usuario WHERE nombreUsuario = ? and contrasena = ?");
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r.resultado = usuario;
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                r.resultado = "ERROR";
                JOptionPane.showMessageDialog(null, "El usuario o la contrasena no coinciden. Por favor, vuelva a introducirlos", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            out.writeObject(r);
            out.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    public Respuestas crear_tarea(String idTarea, String titulo, String estado, Date fecha, String nombreCat, String nombreUsuario) {
        Respuestas r = null;
        try {
            Socket clientsocket = new Socket("localhost", 1400);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            r = new Respuestas();
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");

            PreparedStatement ps = con.prepareStatement("INSERT INTO tarea(idTarea, titulo, estado, fech, nombreCat, nombreUsuario) VALUES (?,?,?,?,?,?)");
            ps.setString(1, idTarea);
            ps.setString(2, titulo);
            ps.setString(3, estado);
            ps.setDate(4, fecha);
            ps.setString(5, nombreCat);
            ps.setString(6, nombreUsuario);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Operacion no realizada", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            out.writeObject(r);
            out.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public Respuestas lista_tareas() {
        Respuestas r = null;

        try {
            
            Socket clientsocket = new Socket("localhost", 1400);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            r = new Respuestas();
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");

        } catch (IOException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Respuestas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;

    }

}
