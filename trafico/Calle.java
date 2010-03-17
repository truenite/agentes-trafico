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
 * @author Diego
 */
public class Calle implements  Comparable<Calle> {
    private  Carril[] carriles;
    private int largoCalle;
    private int anchoCalle;
    private int anchoCarril;
    private String orientacion;
    private Point puntoInicial;
    private int camellon;
    //private int ejeCambianteMayor; // Estos tienen el valor del mayor o mejor valor posibles sobre el eje
    //private int ejeCambianteMenor; // que sí cambia.

    
    public Calle(Point puntoInicial, String orientacion, int largoCalle,int numCarrilesSentidoUno, DireccionCalle direccionCarrilesUno,
            int numCarrilesSentidoDos, DireccionCalle direccionCarrilesDos, int anchoCarril,int camellon){
        this.largoCalle=largoCalle;
        this.anchoCalle=(numCarrilesSentidoUno+numCarrilesSentidoDos)*anchoCarril+camellon;
        this.orientacion=orientacion;
        this.puntoInicial=puntoInicial;
        this.anchoCarril=anchoCarril;
        this.camellon=camellon;
        carriles= new Carril[numCarrilesSentidoUno+numCarrilesSentidoDos];
        if(orientacion.equals("vertical")){
            int posicionX=(int)puntoInicial.getX();
            int numCarril=0;
            for(; numCarril<numCarrilesSentidoUno; numCarril++){
                carriles[numCarril]= new Carril(anchoCarril, largoCalle, new Point(posicionX,(int) puntoInicial.getY()), direccionCarrilesUno);
                posicionX+=anchoCarril;
            }
            posicionX+=camellon;
            for(; numCarril<numCarrilesSentidoUno+numCarrilesSentidoDos; numCarril++){
                carriles[numCarril]= new Carril(anchoCarril, largoCalle, new Point(posicionX,(int) puntoInicial.getY()), direccionCarrilesDos);
                posicionX+=anchoCarril;
            }
        }
      if(orientacion.equals("horizontal")){
            int posicionY=(int)puntoInicial.getY();
            int numCarril=0;
            for(; numCarril<numCarrilesSentidoUno; numCarril++){
                carriles[numCarril]= new Carril(anchoCarril, largoCalle, new Point((int)puntoInicial.getX(),posicionY), direccionCarrilesUno);
                posicionY+=anchoCarril;
            }
            posicionY+=camellon;
            for(; numCarril<numCarrilesSentidoUno+numCarrilesSentidoDos; numCarril++){
                carriles[numCarril]= new Carril(anchoCarril, largoCalle, new Point((int)puntoInicial.getX(),posicionY), direccionCarrilesDos);
                posicionY+=anchoCarril;
            }
        }




    }


   public Carril randomCarril(){
       return carriles[(int)Math.random()*carriles.length];
   }

   public Carril [] getCarriles(){
       return this.carriles;
   }

   public int getAnchoCalle(){
       return this.anchoCalle;
   }

   public int getLargoCalle(){
       return  this.largoCalle;
   }
   public String getOrientacion(){
       return this.orientacion;
   }

   public Point getPosicionInicial(){
       return this.puntoInicial;
   }
   
   public void dibujarCalle(Graphics g){
   }


    public int getCarrilRandom(){
        return (int)Math.random()*carriles.length;
    }

    public Carril getCarrilesLateralMayor(Carril carril){
      int i=0;
      for(;i<carriles.length; i++){
          if(carriles[i].equals(carril)){
              break;
          }
      }
      if(i<carriles.length-1){
          if(carriles[i+1].getDireccion()==carril.getDireccion()){
              return carriles[i+1];
          }
      }
      return null;
    }
    
    public Carril getCarrilesLateralMenor(Carril carril){
      int i=0;
      for(;i<carriles.length; i++){
          if(carriles[i].equals(carril)){
              break;
          }
      }
      if(i>0){
          if(carril!= null && carriles[i-1].getDireccion()==carril.getDireccion()){
              return carriles[i-1];
          }
      }
      return null;
    }
    public int compareTo(Calle o) {
       if(this.puntoInicial.equals(o.getPosicionInicial())){
           return 0;
       }else{
           return -1;
       }
    }
}


