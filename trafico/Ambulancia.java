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
public class Ambulancia extends Auto {
    private  String RUTAIMAGENAUTOARRIBA = "/imagenes/ambulancia/ambulanciaArr.png";
    private  String RUTAIMAGENAUTOABAJO = "/imagenes/ambulancia/ambulanciaAba.png";
    private  String RUTAIMAGENAUTOIZQUIERDA = "/imagenes/ambulancia/ambulanciaIzq.png";
    private  String RUTAIMAGENAUTODERECHA = "/imagenes/ambulancia/ambulanciaDer.png";
    protected boolean puedeSaltarseAlto;
    
    public Ambulancia(Calle calle,Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforos, JTextArea textArea) {
        super(calle, miCarril, lCoches, semaforos, textArea);
        seleccionarImagen(this.getDireccion());
        this.setVelocidadActual(2);
        puedeSaltarseAlto = true;
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
    public void recibirMensaje(Mensaje msj){
        String contenido = msj.getContenido();
        if(contenido.equals("Dejame pasar")){
            Mensaje msjRespuesta = new Mensaje("tell",this,msj.getEmisor(),"Coloquial","trafico","Ahi voy",textArea);
            ((Auto)msj.getEmisor()).recibirMensaje(msjRespuesta);
        }
    }
    public Auto colisionesHorizontales(int posicion){
         puedeAvanzar = true;
         int ancho=this.getCarril().getAncho();
         for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                Auto aux= listaCoches.get(i);
                if(this.getCarril().equals(aux.getCarril())){
                    if(this.getPosX()> aux.getPosX()){
                        if((this.getPosX()-aux.getPosX())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
                           Mensaje msg = new Mensaje("tell",this,aux,"Coloquial","trafico","Urge que te muevas",textArea);
                           aux.recibirMensaje(msg);
                           break;
                         }
                    }

                }
                // Checamos colisiones con otros autos:
                if((aux.getPosY())<= this.getPosY()+27 &&
                    (aux.getPosY()) >= this.getPosY()-45 &&
                    this.getPosX() < (aux.getPosX()+33) &&
                    this.getPosX() > aux.getPosX() &&
                   (!aux.getDireccion().equals(DireccionCalle.IZQUIERDA) && !aux.getDireccion().equals(DireccionCalle.DERECHA))){
                        this.puedeAvanzar = false;
                        Mensaje msg = new Mensaje("tell",this,aux,"Coloquial","trafico","Urge que te muevas",textArea);
                        aux.recibirMensaje(msg);
                        break;
                }
            }
        }
        return null;
    }

    public Auto colisionesVerticalesArriba(int posicion){
        puedeAvanzar = true;
         for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                Auto aux= listaCoches.get(i);
                if(this.getCarril().equals(aux.getCarril())){
                    if(this.getPosY()> aux.getPosY()){
                        if((this.getPosY()-aux.getPosY())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
                           Mensaje msg = new Mensaje("tell",this,aux,"Coloquial","trafico","Urge que te muevas",textArea);
                           aux.recibirMensaje(msg);
                           break;
                         }
                    }

                }
                // Checamos colisiones con otros autos:
                if((aux.getPosX() + 50) >= (this.getPosX())&&
                (aux.getPosX() ) <= (this.getPosX()+ 27) &&
                getPosY() <= (aux.getPosY() + ANCHOAUTO+5) &&
                getPosY() > aux.getPosY() &&
                (!aux.getDireccion().equals(DireccionCalle.ABAJO) && !aux.getDireccion().equals(DireccionCalle.ARRIBA))){
                    this.puedeAvanzar = false;
                    Mensaje msg = new Mensaje("tell",this,aux,"Coloquial","trafico","Urge que te muevas",textArea);
                    aux.recibirMensaje(msg);
                    break;
                }
            }
        }
        return null;
    }


    public Auto colisionesVerticalesAbajo(int posicion){
        puedeAvanzar = true;
        for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                Auto aux= listaCoches.get(i);
                if(this.getCarril().equals(aux.getCarril())){
                    if(this.getPosY()< aux.getPosY()){
                        if((aux.getPosY() -this.getPosY())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
                           Mensaje msg = new Mensaje("tell",this,aux,"Coloquial","trafico","Urge que te muevas",textArea);
                           aux.recibirMensaje(msg);
                           break;
                         }
                    }

                }
                // Checamos colisiones con otros autos:
                if((aux.getPosX() + 50) >= (this.getPosX()) &&
                (aux.getPosX() -3 ) <= (this.getPosX() +27) &&
                (getPosY() + ALTOAUTO) > (aux.getPosY()) &&
                (getPosY() + ALTOAUTO) < (aux.getPosY() + ANCHOAUTO) &&
                (!aux.getDireccion().equals(DireccionCalle.ABAJO) && !aux.getDireccion().equals(DireccionCalle.ARRIBA))){
                    this.puedeAvanzar = false;
                    Mensaje msg = new Mensaje("tell",this,aux,"Coloquial","trafico","Urge que te muevas",textArea);
                    aux.recibirMensaje(msg);
                    break;
                }
            }
        }
        return null;
    }
}
