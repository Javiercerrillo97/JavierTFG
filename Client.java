package aplicaciontcp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class Client extends JFrame{

    String str;
    ResultSet rs;
    Vector records;
    GridBagLayout gbl;
    GridBagConstraints gbc;
    JScrollPane sp;
    JTextArea result;
    JLabel label;
    Datos pub;
    int i = 0;
    ObjectInputStream bt = null;
    Socket clientSocket = null;
    
    public Client(){
        label = new JLabel("Product Details");
        result = new JTextArea(20,60);
        str = "";
        pub = null;
        records = new Vector();
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        getContentPane().setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(label,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        sp = new JScrollPane(result);
        getContentPane().add(sp,gbc);
        
        
        try{
            clientSocket = new Socket("localhost",1400);
            bt = new ObjectInputStream(clientSocket.getInputStream());
            records = (Vector)bt.readObject();
            bt.close();
            result.setText("");
            int i = 0;
            
            result.append("ID\tPName\tPrice\tQty");
            result.append("\n------------------------------------------------------\n");
            
            while (i < records.size()){
                pub = (Datos) records.elementAt(i);
                str = pub.id;
                result.append(str + "\t");
                    str = pub.pname;
                result.append(str + "\t");
                    str = pub.price;
                result.append(str + "\t");
                    str = pub.qty;    
                result.append(str + "\n");
                i++;
            }
            records.removeAllElements();
        }catch(IOException ex){
            
        }catch(ClassNotFoundException f){
             
        }
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        Client server1=new Client();
      
                server1.setSize(500, 300); 
                server1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                server1.pack(); 
                server1.setVisible(true);
    }
}