package aplicaciontcp;

import java.io.Serializable;
import java.util.ArrayList;


public class Respuesta implements Serializable{
   private boolean respuestaOk;
   private ArrayList lista;

    public boolean isRespuestaOk() {
        return respuestaOk;
    }

    public void setRespuestaOk(boolean respuestaOk) {
        this.respuestaOk = respuestaOk;
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList Lista) {
        this.lista = Lista;
    }
    
    
}
