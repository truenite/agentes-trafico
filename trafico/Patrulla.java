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
public class Patrulla extends Auto {
    private  String RUTAIMAGENAUTOARRIBA = "/imagenes/patrulla/patrullaArr.png";
    private  String RUTAIMAGENAUTOABAJO = "/imagenes/patrulla/patrullaAba.png";
    private  String RUTAIMAGENAUTOIZQUIERDA = "/imagenes/patrulla/patrullaIzq.png";
    private  String RUTAIMAGENAUTODERECHA = "/imagenes/patrulla/patrullaDer.png";



    public Patrulla(Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforos){
        super(miCarril, lCoches, semaforos);
        seleccionarImagen(this.getDireccion());
         this.setVelocidadActual(1.5f);

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
