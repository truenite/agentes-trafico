/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trafico;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;



/**
 *
 * @author Chostisli
 */
public class automovil extends Auto {
    protected String RUTAIMAGENAUTOARRIBA = "/imagenes/carro/carroArriba.png";
    protected String RUTAIMAGENAUTOABAJO = "/imagenes/carro/carroAbajo.png";
    protected String RUTAIMAGENAUTOIZQUIERDA = "/imagenes/carro/carro.png";
    protected String RUTAIMAGENAUTODERECHA = "/imagenes/carro/carroDerecha.png";


    public automovil( Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforo){
       super(miCarril, lCoches, semaforo);
        seleccionarImagen(this.getDireccion());
        this.setVelocidadActual(1);
       
    }
       protected   void seleccionarImagen(DireccionCalle direccion){
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