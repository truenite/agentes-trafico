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
 * @author Ruco
 */
public abstract class Auto {
    protected  int inicioX;//posiciones iniciales del automovil
    protected int inicioY;
    protected Carril miCarril;
    private DireccionCalle direccion;
    protected  Image imagen ;
    protected float  posX; //posiciones actuales del autmÃ³vil en  (X,Y)
    protected float posY;
    protected   ArrayList <Auto> listaCoches;
    protected   ArrayList <Semaforo> semaforos;
    protected final int ANCHOAUTO=25;
    protected  final int ALTOAUTO=50;
    private float velocidadActual; //Velocidades actuales del automÃ³vil    private float velocidadY
    protected boolean puedeCambiarIzquierda; //Bandera que indica si puede cambiar de carril a la izquierda.
    protected boolean puedeCambiarDerecha; //Bandera que indica si puede cambiar de carril a la derecha.
    protected boolean sePuedeDibujar;
    protected boolean puedeAvanzar; //Bandera que indica si puede avanzar o no.
    protected String RUTAIMAGENAUTOARRIBA;
    protected String RUTAIMAGENAUTOABAJO ;
    protected String RUTAIMAGENAUTOIZQUIERDA;
    protected String RUTAIMAGENAUTODERECHA ;
    protected Calle calle;

     public Auto(Calle calle,Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforos){
        this.inicioX =  (int)miCarril.getPuntoInicial().getX();
        this.posX =inicioX;
        this.calle=calle;
        this.inicioY =(int)miCarril.getPuntoInicial().getY();
        this. posY = inicioY;
        this.velocidadActual = 0;
        this.miCarril = miCarril;
        this.listaCoches = lCoches;
        this.direccion = miCarril.getDireccion();
        //seleccionarImagen(this.direccion);
        this.semaforos=semaforos;
        if(direccion == DireccionCalle.IZQUIERDA ){
            this.inicioX =  miCarril.getLongitud() - this.ALTOAUTO;
            posX =inicioX;
        }
        if(direccion == DireccionCalle.ARRIBA){
            this.inicioY =  miCarril.getLongitud() - this.ALTOAUTO;
            posY =inicioY;
        }
    }

    abstract void seleccionarImagen(DireccionCalle direccion);

    public int getPosX(){
        return (int) posX;
    }

    public int getPosY(){
        return(int) posY;
    }

    public float getVelocidadActual(){
        return velocidadActual;
    }

    public Carril getCarril(){
        return this.miCarril;
    }

    public void setVelocidadActual(float  X){
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
            
            posX -= velocidadActual;
        }
    }

    public void avanzarY(){
        if(puedeAvanzar == true && direccion == DireccionCalle.ABAJO){
            
            posY += velocidadActual;
        }else{
            if(puedeAvanzar == true && direccion == DireccionCalle.ARRIBA){
               
                posY -= velocidadActual;
            }else{
                
            }

       }
    }

    public void dibujarAutomovil(Graphics g){
      // if(sePuedeDibujar = true){

         g.drawImage(imagen, ((int)posX)+3,(int)posY, null);
         g.setColor(Color.yellow);
         g.drawRect(this.getPosX(), ((int)this.posY), 3, 3);
       //}
    }


    public void cambiarCarril(){

    }



    public void colisionesHorizontales(int posicion){
         puedeAvanzar = true;
         int ancho=this.getCarril().getAncho();
         for(int i =0; i < listaCoches.size(); i++){
             if(i!=posicion){
                Auto aux= listaCoches.get(i);
                if(this.getCarril().equals(aux.getCarril())){
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
                Auto aux= listaCoches.get(i);
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
                Auto aux= listaCoches.get(i);
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
                    if((rojo-(this.posY+(this.ALTOAUTO-3))>0)&&(rojo-(this.posY+(this.ALTOAUTO-3)))<10){
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
