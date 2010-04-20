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
public class Patrulla extends Auto {
    private  String RUTAIMAGENAUTOARRIBA = "/imagenes/patrulla/patrullaArr.png";
    private  String RUTAIMAGENAUTOABAJO = "/imagenes/patrulla/patrullaAba.png";
    private  String RUTAIMAGENAUTOIZQUIERDA = "/imagenes/patrulla/patrullaIzq.png";
    private  String RUTAIMAGENAUTODERECHA = "/imagenes/patrulla/patrullaDer.png";
    protected Carril carrilLateralMayor;
    protected Carril carrilLateralMenor;
    protected boolean puedeCambiarCarrilMayor = false;
    protected boolean puedeCambiarCarrilMenor = false;
    protected boolean debeCambiarCarril = false;
    protected boolean colisionOtro = false;
    protected int posicion;


    public Patrulla(Calle calle,Carril miCarril, ArrayList <Auto>lCoches,ArrayList<Semaforo> semaforos, JTextArea textArea, boolean mostrar) {
        super(calle, miCarril, lCoches, semaforos, textArea, mostrar);
        seleccionarImagen(this.getDireccion());
        this.setVelocidadActual(1.5f);
        this.carrilLateralMayor = this.calle.getCarrilesLateralMayor(miCarril);
        if (carrilLateralMayor != null) {
            puedeCambiarCarrilMayor = true;
        }

        this.carrilLateralMenor = this.calle.getCarrilesLateralMenor(miCarril);
        if (carrilLateralMenor != null) {
            this.puedeCambiarCarrilMenor = true;
        }
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


     public void cambiarCarril() {
        if (debeCambiarCarril && colisionOtro == false) {
            if (puedeCambiarCarrilMayor == true && carrilLateralMayor != null) {
                if (this.getDireccion() == DireccionCalle.IZQUIERDA) {
                    this.posY = this.carrilLateralMayor.getPuntoInicial().y;
                    this.miCarril = carrilLateralMayor;
                    this.setVelocidadActual(1.5f);
                } else {
                    this.posX = this.carrilLateralMayor.getPuntoInicial().x;
                    this.miCarril = carrilLateralMayor;
                    this.setVelocidadActual(1.5f);
                }
            } else {
                if (this.puedeCambiarCarrilMenor == true && carrilLateralMenor != null) {
                    if (this.getDireccion() == DireccionCalle.IZQUIERDA) {
                        this.posY = this.carrilLateralMenor.getPuntoInicial().y;
                        this.miCarril = carrilLateralMenor;
                        this.setVelocidadActual(1.5f);
                    } else {
                        this.posX = this.carrilLateralMenor.getPuntoInicial().x;
                        this.miCarril = carrilLateralMenor;
                        this.setVelocidadActual(1.5f);
                    }
                }
            }
            this.carrilLateralMayor = calle.getCarrilesLateralMayor(miCarril);
            this.carrilLateralMenor = calle.getCarrilesLateralMenor(miCarril);
        }
    }

    public Auto colisionesHorizontales(int posicion) {
        this.posicion = posicion;
        Auto autoRegreso = null;
        if (this.carrilLateralMenor != null) {
            this.puedeCambiarCarrilMenor = true;
        }
        if (this.carrilLateralMayor != null) {
            this.puedeCambiarCarrilMayor = true;
        }
        this.debeCambiarCarril = false;
        this.puedeAvanzar = true;
        this.colisionOtro = false;
        for (int i = 0; i < listaCoches.size(); i++) {
            if (i != posicion) {
                Auto aux = listaCoches.get(i);
                if (this.getCarril().equals(aux.getCarril())) {
                    if (this.getPosX() > aux.getPosX()) {
                        if ((this.getPosX() - aux.getPosX()) < this.ALTOAUTO) {
                            this.puedeAvanzar = false;
                            autoRegreso = aux;
                        }
                    }
                    if ((aux instanceof Ambulancia )) {
                        if ((aux.getPosX() > this.getPosX()) && (aux.getPosX() - this.getPosX()) < this.ALTOAUTO + 3) {
                            debeCambiarCarril = true;

                        }
                    }

                } else {
                    if (this.carrilLateralMayor != null && aux.getCarril().equals(this.carrilLateralMayor)) {
                        if ((aux.getPosX() + this.ALTOAUTO) > this.getPosX() && aux.getPosX() < (this.getPosX() + this.ALTOAUTO)) {
                            this.puedeCambiarCarrilMayor = false;
                        }
                    } else {
                        if (this.carrilLateralMenor != null && aux.getCarril().equals(this.carrilLateralMenor)) {
                            if ((aux.getPosX() + this.ALTOAUTO) > this.getPosX() && aux.getPosX() < (this.getPosX() + this.ALTOAUTO)) {
                                this.puedeCambiarCarrilMenor = false;
                            }
                        }
                    }

                }

                // Checamos colisiones con otros autos:
                if ((aux.getPosY()) <= this.getPosY() + 27
                        && (aux.getPosY()) >= this.getPosY() - 45
                        && this.getPosX() < (aux.getPosX() + 33)
                        && this.getPosX() > aux.getPosX()
                        && (!aux.getDireccion().equals(DireccionCalle.IZQUIERDA) && !aux.getDireccion().equals(DireccionCalle.DERECHA))) {
                    puedeAvanzar = false;
                    colisionOtro = true;
                }

                //checamos patrullas ambulancias atras

            }
        }
        return autoRegreso;
    }

    public Auto colisionesVerticalesArriba(int posicion) {
        this.posicion = posicion;
        Auto autoRegreso = null;
        if (this.carrilLateralMenor != null) {
            this.puedeCambiarCarrilMenor = true;
        }
        if (this.carrilLateralMayor != null) {
            this.puedeCambiarCarrilMayor = true;
        }
        this.debeCambiarCarril = false;
        this.puedeAvanzar = true;
        this.colisionOtro = false;
        for (int i = 0; i < listaCoches.size(); i++) {
            if (i != posicion) {
                Auto aux = listaCoches.get(i);
                if (this.getCarril().equals(aux.getCarril())) {
                    if (this.getPosY() > aux.getPosY()) {
                        if ((this.getPosY() - aux.getPosY()) < this.ALTOAUTO) {
                            this.puedeAvanzar = false;
                            autoRegreso = aux;
                        }
                    }
                    if ((aux instanceof Ambulancia )) {
                        if ((aux.getPosY() > this.getPosY()) && (aux.getPosY() - this.getPosY()) < this.ALTOAUTO +3) {
                            debeCambiarCarril = true;

                        }
                    }

                } else {
                    if (this.carrilLateralMayor != null && aux.getCarril().equals(this.carrilLateralMayor)) {
                        if ((aux.getPosY() + this.ALTOAUTO) > this.getPosY() && aux.getPosY() < (this.getPosY() + this.ALTOAUTO)) {
                            this.puedeCambiarCarrilMayor = false;
                        }
                    } else {
                        if (this.carrilLateralMenor != null && aux.getCarril().equals(this.carrilLateralMenor)) {
                            if ((aux.getPosY() + this.ALTOAUTO) > this.getPosY() && aux.getPosX() < (this.getPosY() + this.ALTOAUTO)) {
                                this.puedeCambiarCarrilMenor = false;
                            }
                        }
                    }

                }
                // Checamos colisiones con otros autos:
                if ((aux.getPosX() + 50) >= (this.getPosX())
                        && (aux.getPosX()) <= (this.getPosX() + 27)
                        && getPosY() <= (aux.getPosY() + ANCHOAUTO + 5)
                        && getPosY() > aux.getPosY()
                        && (!aux.getDireccion().equals(DireccionCalle.ABAJO) && !aux.getDireccion().equals(DireccionCalle.ARRIBA))) {
                    this.puedeAvanzar = false;
                    break;

                }
            }
        }
        return autoRegreso;
    }

    public Auto colisionesVerticalesAbajo(int posicion) {
        this.posicion = posicion;
        Auto autoRegreso = null;
        if (this.carrilLateralMenor != null) {
            this.puedeCambiarCarrilMenor = true;
        }
        if (this.carrilLateralMayor != null) {
            this.puedeCambiarCarrilMayor = true;
        }
        this.debeCambiarCarril = false;
        this.puedeAvanzar = true;
        this.colisionOtro = false;
        for (int i = 0; i < listaCoches.size(); i++) {
            if (i != posicion) {
                Auto aux = listaCoches.get(i);
                if (this.getCarril().equals(aux.getCarril())) {
                    if (this.getPosY() < aux.getPosY()) {
                        if ((aux.getPosY() - this.getPosY()) < this.ALTOAUTO) {
                            this.puedeAvanzar = false;
                            autoRegreso = aux;
                        }
                    }
                    if ((aux instanceof Ambulancia )) {
                        if ((aux.getPosY() < this.getPosY()) && (this.getPosY() - aux.getPosY()) < this.ALTOAUTO + 3) {
                            debeCambiarCarril = true;

                        }
                    }


                } else {
                    if (this.carrilLateralMayor != null && aux.getCarril().equals(this.carrilLateralMayor)) {
                        if ((aux.getPosY() + this.ALTOAUTO) > this.getPosY() && aux.getPosY() < (this.getPosY() + this.ALTOAUTO)) {
                            this.puedeCambiarCarrilMayor = false;
                        }
                    } else {
                        if (this.carrilLateralMenor != null && aux.getCarril().equals(this.carrilLateralMenor)) {
                            if ((aux.getPosY() + this.ALTOAUTO) > this.getPosY() && aux.getPosX() < (this.getPosY() + this.ALTOAUTO)) {
                                this.puedeCambiarCarrilMenor = false;
                            }
                        }
                    }

                }

                // Checamos colisiones con otros autos:
                if ((aux.getPosX() + 50) >= (this.getPosX())
                        && (aux.getPosX() - 3) <= (this.getPosX() + 27)
                        && (getPosY() + ALTOAUTO) > (aux.getPosY())
                        && (getPosY() + ALTOAUTO) < (aux.getPosY() + ANCHOAUTO)
                        && (!aux.getDireccion().equals(DireccionCalle.ABAJO) && !aux.getDireccion().equals(DireccionCalle.ARRIBA))) {
                    this.puedeAvanzar = false;
                    break;
                }
            }



        }
        return autoRegreso;
    }
    public void recibirMensaje(Mensaje msj){
        String contenido = msj.getContenido();
        if(contenido.equals("Urge que te muevas")){
            Mensaje msjRespuesta = new Mensaje("tell",this,msj.getEmisor(),"Coloquial","trafico","Ahi voy",textArea);
            ((Auto)msj.getEmisor()).recibirMensaje(msjRespuesta);
            this.setVelocidadActual(2f);
            Auto enfrente = null;
            if(direccion == DireccionCalle.IZQUIERDA ){
                enfrente = colisionesHorizontales(posicion);
            }
            if(direccion == DireccionCalle.ABAJO){
                enfrente = colisionesVerticalesAbajo(posicion);
            }
            if(direccion == DireccionCalle.ARRIBA){
                enfrente = colisionesVerticalesArriba(posicion);
            }
            if(enfrente != null){
                Mensaje msjEnfrente = new Mensaje("tell",this,enfrente,"Coloquial","trafico","Urge que te muevas",textArea);
                enfrente.recibirMensaje(msjEnfrente);
            }
            puedeAvanzar = true;
            colisionSemaforos();
            if(!puedeAvanzar)
                puedeSaltarseAlto = true;
            else
                puedeSaltarseAlto = false;
        }
    }
}
