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



/**
 *
 * @author Chostisli
 */
public class automovil {
    private int inicioX;//posiciones iniciales del automovil
    private int inicioY;
    private Carril miCarril;
    private DireccionCalle direccion;
    private Image imagen ;
    private int posX; //posiciones actuales del autmÃ³vil en  (X,Y)
    private int posY;
    private  ArrayList <automovil> listaCoches;
    private  ArrayList <Semaforo> semaforos;
    private final int ANCHOAUTO=25;
    private final int ALTOAUTO=50;  
    private int velocidadActual; //Velocidades actuales del automÃ³vil    private float velocidadY
    protected boolean puedeCambiarIzquierda; //Bandera que indica si puede cambiar de carril a la izquierda.
    protected boolean puedeCambiarDerecha; //Bandera que indica si puede cambiar de carril a la derecha.
    protected boolean sePuedeDibujar;
    protected boolean puedeAvanzar; //Bandera que indica si puede avanzar o no.


    public automovil( Image imagen, Carril miCarril, ArrayList <automovil>lCoches,ArrayList<Semaforo> semaforos){
        this.inicioX = posX = (int)miCarril.getPuntoInicial().getX();
        this.inicioY = posY = (int)miCarril.getPuntoInicial().getY();

        this.velocidadActual = 0;
        this.imagen = imagen;
        this.miCarril = miCarril;
        this.listaCoches = lCoches;
        this.direccion = miCarril.getDireccion();
        this.semaforos=semaforos;
        if(direccion == DireccionCalle.IZQUIERDA ){
            this.inicioX = posX = miCarril.getLongitud() - this.ALTOAUTO;
        }
        if(direccion == DireccionCalle.ARRIBA){
            this.inicioY = posY = miCarril.getLongitud() - this.ALTOAUTO;
        }
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public float getVelocidadActual(){
        return velocidadActual;
    }

    public Carril getCarril(){
        return this.miCarril;
    }

    public void setVelocidadActual(int X){
         this.velocidadActual = X;
    }
    public Point getPuntoInicial(){
        return new Point(this.inicioX,this.inicioY);
    }

    public void actualizar(long tiempoTranscurrido,int posicion){

       if(direccion == DireccionCalle.IZQUIERDA ){

               colisionesHorizontales(posicion);
       }
       if(direccion == DireccionCalle.ABAJO){

               colisionesVerticalesAbajo(posicion);
       }
       if(direccion == DireccionCalle.ARRIBA){

               colisionesVerticalesArriba(posicion);
       }
    }

    public void avanzarX(){
        if(puedeAvanzar == true && direccion == DireccionCalle.IZQUIERDA){
            setVelocidadActual(1);
            posX -= (int)velocidadActual;
        }
    }

    public void avanzarY(){
        if(puedeAvanzar == true && direccion == DireccionCalle.ABAJO){
            setVelocidadActual(1);
            posY += (int)velocidadActual;
        }else{
            if(puedeAvanzar == true && direccion == DireccionCalle.ARRIBA){
                setVelocidadActual(1);
                posY -= (int)velocidadActual;
            }else{
                setVelocidadActual(0);
            }

       }
    }

  /*  public void avanzarYArriba(){
        if(puedeAvanzar == true && direccion == DireccionCalle.ARRIBA){
            setVelocidadActual(1);
            posY -= (int)velocidadActual;
        }
    }*/

    /*public void acelerar(){
        if(puedeAvanzar == true){
           while(velocidadActual < velocidadMaxima){
                 velocidadActual += .00001f;
                System.out.println("Velocidad Actual:" + velocidadActual);
           }
        }
    }*/



    public void dibujarAutomovil(Graphics g){
      // if(sePuedeDibujar = true){

         g.drawImage(imagen, posX+3,posY, null);
         g.setColor(Color.yellow);
         g.drawRect(this.getPosX(), this.posY, 3, 3);
       //}
    }


    public void cambiarCarril(){

    }



    public void colisionesHorizontales(int posicion){
         puedeAvanzar = true;
         for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                automovil aux= listaCoches.get(i);
                if(this.getPuntoInicial().equals(aux.getPuntoInicial())){
                    if(this.getPosX()> aux.getPosX()){
                        if((this.getPosX()-aux.getPosX())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
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
                        puedeAvanzar = false;
                        break;

                }
            }
        }
         colisionSemaforos();
         avanzarX();
       

    }

    public void colisionesVerticalesArriba(int posicion){
        puedeAvanzar = true;
         for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                automovil aux= listaCoches.get(i);
                if(this.getPuntoInicial().equals(aux.getPuntoInicial())){
                    if(this.getPosY()> aux.getPosY()){
                        if((this.getPosY()-aux.getPosY())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
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
                        break;

                }
            }
        }
          colisionSemaforos();
         avanzarY();
    }


    public void colisionesVerticalesAbajo(int posicion){
        puedeAvanzar = true;
        for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                automovil aux= listaCoches.get(i);
                if(this.getPuntoInicial().equals(aux.getPuntoInicial())){
                    if(this.getPosY()< aux.getPosY()){
                        if((aux.getPosY() -this.getPosY())<this.ALTOAUTO){
                           this.puedeAvanzar = false;
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
                        break;
                 }
            }



        }
         colisionSemaforos();
         avanzarY();
    }
    public void colisionSemaforos(){
        for (int i=0; i<semaforos.size();i++){
            int rojo=semaforos.get(i).esRojoCarril(this.getCarril());
            if(rojo>0){
                if(this.direccion==direccion.ABAJO ){
                    if((rojo-(this.posY+(this.ALTOAUTO/2))>0)&&(rojo-(this.posY+(this.ALTOAUTO/2)))<this.ALTOAUTO-10){
                        this.puedeAvanzar=false;
                    }
                }
                if(this.direccion==direccion.ARRIBA ){
                    if((this.posY-rojo>0)&&(this.posY-rojo)<10){
                        this.puedeAvanzar=false;
                    }
                }
                if(this.direccion==direccion.IZQUIERDA ){
                    if((this.posX-rojo>0)&&(this.posX-rojo)<10){
                        this.puedeAvanzar=false;
                    }
                }
                 if(this.direccion==direccion.DERECHA ){
                    if((rojo-this.posX>0)&&(rojo-this.posX)<this.ALTOAUTO){
                        this.puedeAvanzar=false;
                    }
                }


             
            }
        }
    }

    public DireccionCalle getDireccion(){
        return direccion;
    }

}