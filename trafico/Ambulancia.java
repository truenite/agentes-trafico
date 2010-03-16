/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trafico;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Ruco
 */
public class Ambulancia extends Auto {
    private  String RUTAIMAGENAUTOARRIBA = "/imagenes/ambulancia/ambulanciaArr.png";
    private  String RUTAIMAGENAUTOABAJO = "/imagenes/ambulancia/ambulanciaAba.png";
    private  String RUTAIMAGENAUTOIZQUIERDA = "/imagenes/ambulancia/ambulanciaIzq.png";
    private  String RUTAIMAGENAUTODERECHA = "/imagenes/ambulancia/ambulanciaDer.png";
    
    public Ambulancia(Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforos){
        super(miCarril, lCoches, semaforos);
        seleccionarImagen(this.getDireccion());

    }
    protected  void seleccionarImagen(DireccionCalle direccion){
        switch(direccion){
            case ABAJO:
                this.imagen=new ImageIcon(getClass().getResource(RUTAIMAGENAUTOABAJO)).getImage();
            break;
            case ARRIBA:
                 this.imagen=new ImageIcon(getClass().getResource(RUTAIMAGENAUTOARRIBA)).getImage();
            break;
            case DERECHA:
                this.imagen=new ImageIcon(getClass().getResource(RUTAIMAGENAUTODERECHA)).getImage();
            break;
            case IZQUIERDA:
                 this.imagen=new ImageIcon(getClass().getResource(RUTAIMAGENAUTOIZQUIERDA)).getImage();
            break;

        }
   }
}
