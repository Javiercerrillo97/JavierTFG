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

public class Server extends Thread{
    
    Statement stmt = null;
    Vector records = new Vector(10,10);
    ResultSet rs = null;
    ServerSocket server = null;
    Socket client = null;
    Connection con =  null;
    ObjectOutputStream out = null;
    String str = null;
    Datos pub = null;
    
    public Server() {
        try{
            server = new ServerSocket(1400);
                System.out.println("Abriendo el servidor");
                start();
                System.out.println("Esperando un cliente...");
        }catch(IOException ex){
            java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(true){
            try{
                int cc;
                
                client = server.accept();
                System.out.println("Conexión aceptada");
                out = new ObjectOutputStream(client.getOutputStream());
                System.out.println("Se ha recibido la información");
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/smobile","root","");
                    stmt = (Statement) con.createStatement();
                    rs = stmt.executeQuery("Select * from products");
                    records.removeAllElements();
                    
            ResultSetMetaData RSMD = rs.getMetaData();
            cc = RSMD.getColumnCount();
            
            
                    while(rs.next()){
                        pub = new Datos();
                        pub.id = rs.getString(1);
                        pub.pname = rs.getString(2);
                        pub.price = rs.getString(3);
                        pub.qty = rs.getString(4);
                        records.addElement(pub);
                        System.out.println("Fila retornada");
                    }
                    
                    out.writeObject(records);
                        out.close();
                        System.out.println("String retornado");
                    
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
            new Server(); 
            
    }
}
