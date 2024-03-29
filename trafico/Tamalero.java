/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trafico;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/**
 *
 * @author Ruco
 */
public class Tamalero extends Auto {
    private  String RUTAIMAGENAUTOARRIBA = "/imagenes/tamalero/tamaleroArr.png";
    private  String RUTAIMAGENAUTOABAJO = "/imagenes/tamalero/tamaleroAba.png";
    private  String RUTAIMAGENAUTOIZQUIERDA = "/imagenes/tamalero/tamaleroIzq.png";
    private  String RUTAIMAGENAUTODERECHA = "/imagenes/tamalero/tamaleroDer.png";
    public Tamalero(Calle calle,Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforos, JTextArea textArea, boolean mostrar) {
        super(calle, miCarril, lCoches, semaforos, textArea, mostrar);
        seleccionarImagen(this.getDireccion());
        this.setVelocidadActual(0.5f);

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
   public void colisionSemaforos(){}

   public void recibirMensaje(Mensaje msj){ }

}
