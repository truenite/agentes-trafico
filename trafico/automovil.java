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
    protected Carril carrilLateralMayor;
    protected Carril carrilLateralMenor;
    protected boolean puedeCambiarCarrilMayor=false;
    protected boolean puedeCambiarCarrilMenor=false;
    protected boolean debeCambiarCarril=false;
    protected boolean colisionOtro=false;


    public automovil(Calle calle, Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforo){
       super(calle,miCarril, lCoches, semaforo);
        seleccionarImagen(this.getDireccion());
        this.setVelocidadActual(1);
        this.carrilLateralMayor=this.calle.getCarrilesLateralMayor(miCarril);
        if(carrilLateralMayor!=null){
            puedeCambiarCarrilMayor=true;
        }

        this.carrilLateralMenor=this.calle.getCarrilesLateralMenor(miCarril);
        if(carrilLateralMenor!=null){
            this.puedeCambiarCarrilMenor=true;
        }
       
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


    public void cambiarCarril(){
       if(debeCambiarCarril && colisionOtro==false){
           if(puedeCambiarCarrilMayor==true && carrilLateralMayor!=null){
               if(this.getDireccion()==DireccionCalle.IZQUIERDA){
                   this.posY=this.carrilLateralMayor.getPuntoInicial().y;
                   this.miCarril=carrilLateralMayor;
               }else{
                   this.posX=this.carrilLateralMayor.getPuntoInicial().x;
                   this.miCarril=carrilLateralMenor;
               }
           }else{
               if(this.puedeCambiarCarrilMenor==true && carrilLateralMenor!=null){
                    if(this.getDireccion()==DireccionCalle.IZQUIERDA){
                         this.posY=this.carrilLateralMenor.getPuntoInicial().y;
                         this.miCarril=carrilLateralMenor;
                   }else{
                        this.posX=this.carrilLateralMenor.getPuntoInicial().x;
                        this.miCarril=carrilLateralMenor;
                    }
               }
           }
           this.carrilLateralMayor=calle.getCarrilesLateralMayor(miCarril);
           this.carrilLateralMenor=calle.getCarrilesLateralMenor(miCarril);
       }
    }
     public void colisionesHorizontales(int posicion){
        if(carrilLateralMenor!=null){
            this.puedeCambiarCarrilMenor=true;
        }
        if(carrilLateralMayor!=null){
            puedeCambiarCarrilMayor=true;
        }
        debeCambiarCarril=false;
         puedeAvanzar = true;
         colisionOtro=false;
         for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                Auto aux= listaCoches.get(i);
                if(this.getCarril().equals(aux.getCarril())){
                    if(this.getPosX()> aux.getPosX()){
                        if((this.getPosX()-aux.getPosX())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
                           
                         }
                    }
                     if((aux instanceof Ambulancia || aux instanceof Patrulla)){
                         if((aux.getPosX()>this.getPosX()) && (aux.getPosX()-this.getPosX())<this.ALTOAUTO+3){
                             debeCambiarCarril=true;
                             
                         }
                    }

                }else{
                    if(this.carrilLateralMayor!= null && aux.getCarril().equals(this.carrilLateralMayor) ){
                        if( (aux.getPosX()+this.ALTOAUTO)>this.getPosX() && aux.getPosX()<(this.getPosX()+this.ALTOAUTO)){
                            this.puedeCambiarCarrilMayor=false;
                        }
                    }else{
                        if(this.carrilLateralMenor!=null &&  aux.getCarril().equals(this.carrilLateralMenor) ){
                            if((aux.getPosX()+this.ALTOAUTO)>this.getPosX() && aux.getPosX()<(this.getPosX()+this.ALTOAUTO)){
                                this.puedeCambiarCarrilMenor=false;
                            }
                        }
                    }
                   
                }

                // Checamos colisiones con otros autos:
                if((aux.getPosY())<= this.getPosY()+27 &&
                    (aux.getPosY()) >= this.getPosY()-45 &&
                    this.getPosX() < (aux.getPosX()+33) &&
                    this.getPosX() > aux.getPosX() &&
                   (!aux.getDireccion().equals(DireccionCalle.IZQUIERDA) && !aux.getDireccion().equals(DireccionCalle.DERECHA))){
                        puedeAvanzar = false;
                        colisionOtro=true;
                }

                //checamos patrullas ambulancias atras

            }
        }

         colisionSemaforos();
        
         avanzarX();
          cambiarCarril();


    }


}