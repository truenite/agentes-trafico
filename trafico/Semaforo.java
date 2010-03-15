/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trafico;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
/**
 *
 * @author Ruco
 */
public class Semaforo {
    private Point posicion;
    private int tiempoVertical;
    private int tiempoHorizontal;
    private Calle calleHorizontal;
    private Calle calleVertical;
    private turno turno;
    int carrilesArriba=0;
    int carrilesAbajo=0;
    int carrilesIzquierda=0;
    int carrilesDerecha=0;
    long tiempoAcumulado=0;
    private final int  TIEMPOMAXIMO=30000;
    private final int  TIEMPOMINIMO=0;

    public Semaforo(Calle calleHorizontal,Calle CalleVertical, int tiempoHorizontal, int tiempoVertical){
           this.calleHorizontal=calleHorizontal;
           this.calleVertical=CalleVertical;
           this.tiempoHorizontal=tiempoHorizontal;
           this.tiempoVertical=tiempoVertical;
           this.posicion = new Point((int)calleVertical.getPosicionInicial().getX(),(int)calleHorizontal.getPosicionInicial().getY());
           this.turno=turno.HORIZONTAL;
           contarCarriles();
    }
    
    public Semaforo(Calle [] calles , int tiempoHorizontal, int tiempoVertical){
        if(calles.length==2){
            if(calles[0].getOrientacion().equals("vertical")){
              this.calleHorizontal=calles[1];
              this.calleVertical=calles[0];   
            }else{
              this.calleHorizontal=calles[0];
              this.calleVertical=calles[1];
            }
            this.turno=turno.HORIZONTAL;
            contarCarriles();

        }else{
            System.out.println("Algo se ha mandado a construir mal en semaforo");
            System.exit(1);
        }   
    }

    public boolean  cambiarTiempo(int tiempoHorizontal, int tiempoVertical){
        if((tiempoHorizontal>=TIEMPOMINIMO && tiempoHorizontal<=TIEMPOMAXIMO)&& (tiempoVertical>=TIEMPOMINIMO && tiempoVertical<=TIEMPOMAXIMO)){
            this.tiempoHorizontal=tiempoHorizontal;
            this.tiempoVertical=tiempoVertical;
           tiempoAcumulado=0;
        }
       
        return false;
    }

    public void apagar(){
        this.turno=turno.DESCOMPUESTO;
        tiempoAcumulado=0;

    }
    public void encender(){
          this.turno=turno.HORIZONTAL;
    }
    public void actualizar(int tiempo){
        tiempoAcumulado+=tiempo;
        if(turno==turno.VERTICAL){
            if(tiempoAcumulado>=tiempoVertical){
                cambiarTurno();
                tiempoAcumulado=0;
            }
        }else{
            if(turno==turno.HORIZONTAL){
                if(tiempoAcumulado>=tiempoHorizontal){
                    cambiarTurno();
                    tiempoAcumulado=0;
                }
            }
        }
        
    }

    public Point getPuntoInicial(){
        return this.posicion;
    }
    public Point getPuntoFinal(){
        return new Point(this.posicion.x+calleVertical.getAnchoCalle(),this.posicion.y+calleHorizontal.getAnchoCalle());
        
    }
    public turno getTurno(){
        return this.turno;
    }
    public void cambiarTurno(){
        if(this.turno==turno.VERTICAL){
            this.turno=turno.HORIZONTAL;
        }else{
            if(this.turno==turno.HORIZONTAL){
                this.turno=turno.VERTICAL;
            }
        }
    }

    public void dibujarSemaforo(Graphics g){
       if(this.turno==turno.HORIZONTAL){
           g.setColor(Color.GREEN);
           dibujarSemaforoEnCalleHorizontal(g);
           g.setColor(Color.RED);
           dibujarSemaforoEnCalleVertical(g);
       }else{
           if(turno==turno.VERTICAL){
           g.setColor(Color.RED);
           dibujarSemaforoEnCalleHorizontal(g);
           g.setColor(Color.GREEN);
           dibujarSemaforoEnCalleVertical(g);
           }else{
               //g.setColor(Color.CYAN);
               //dibujarSemaforoEnCalleHorizontal(g);
               //dibujarSemaforoEnCalleVertical(g);
           }
       }
       
        //g.fillRect((int)this.posicion.getX(), (int)this.posicion.getY(), this.calleVertical.getAnchoCalle(), this.calleHorizontal.getAnchoCalle());
    }

    public boolean isRojo(Calle calle){
        if(calleVertical.equals(calle)){
            if(this.turno==turno.HORIZONTAL){
                return true;
            }
        }
        if(calleHorizontal.equals(calle)){
            if(this.turno==turno.VERTICAL){
                return true;
            }
        }
        return false;
    }

    public int esRojoCarril(Carril carril){
        Carril[] h=this.calleHorizontal.getCarriles();
        Carril[] v=this.calleVertical.getCarriles();
        if(this.turno==turno.DESCOMPUESTO){
            return -1;
        }
        for(int i=0; i<h.length; i++){
            if(h[i].equals(carril)){
                if(this.turno==turno.VERTICAL){
                    if(carril.getDireccion()==DireccionCalle.DERECHA){
                         return (int) this.posicion.getX();
                    }else{
                         return (int) this.posicion.getX()+calleVertical.getAnchoCalle();
                    }
                }else{
                    return -1;
                }
            }
        }
        for(int i=0; i<v.length; i++){
            if(v[i].equals(carril)){
              if(this.turno==turno.HORIZONTAL){
                    if(carril.getDireccion()==DireccionCalle.ABAJO){
                     return (int) this.posicion.getY();
                    }else{
                     return (int) this.posicion.getY()+calleHorizontal.getAnchoCalle();
                    }
              }else{
                  return -1;
              }
            }
        }
        return -1;
        
    }





    public void dibujarSemaforoEnCalleHorizontal(Graphics g){
        int x=this.posicion.x;
        int y=this.posicion.y;
        g.fillRect(x+calleVertical.getAnchoCalle(), y, 5,(this.calleHorizontal.getAnchoCalle()/(carrilesIzquierda+carrilesDerecha))*carrilesIzquierda);
        g.fillRect(x, y+calleHorizontal.getAnchoCalle(), 5,(this.calleVertical.getAnchoCalle()/(carrilesIzquierda+carrilesDerecha))*carrilesDerecha);
    }
    public void dibujarSemaforoEnCalleVertical(Graphics g){
        int x=this.posicion.x;
        int y=this.posicion.y;
        g.fillRect(x, y,(this.calleVertical.getAnchoCalle()/(carrilesArriba+carrilesAbajo))*carrilesAbajo, 5);
        g.fillRect(x+(this.calleVertical.getAnchoCalle()/(carrilesArriba+carrilesAbajo))*carrilesAbajo, y+calleHorizontal.getAnchoCalle(),(this.calleVertical.getAnchoCalle()/(carrilesArriba+carrilesAbajo))*carrilesArriba, 5);
    }

    public void contarCarriles(){
        Carril [] horizontal=this.calleHorizontal.getCarriles();
        Carril [] vertical=this.calleVertical.getCarriles();
        for(int i=0; i<horizontal.length;i++){
            if(horizontal[i].getDireccion()==DireccionCalle.IZQUIERDA){
                this.carrilesIzquierda++;
            }else{
                this.carrilesDerecha++;
            }
        }
        for(int i=0; i<vertical.length;i++){
            if(vertical[i].getDireccion()==DireccionCalle.ARRIBA){
                this.carrilesArriba++;
            }else{
                this.carrilesAbajo++;
            }
        }

    }
    public int[] getTiempos(){
        int[] arregloTiempos = new int[]{tiempoVertical/1000,tiempoHorizontal/1000};
        return arregloTiempos;
    }
public void imprimirTiempos(){
    System.out.println("tiempov "+this.tiempoVertical +" tiempoH "+ this.tiempoHorizontal);
}
}
