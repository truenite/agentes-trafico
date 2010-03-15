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
public class Carril implements Comparable<Carril> {
    private int ancho,longitud;
    private Point puntoInicial;
    private DireccionCalle direccion;


    public Carril (int ancho, int longitud, Point inicial, DireccionCalle direccion){
        this.ancho=ancho;
        this.longitud=longitud;
        this.puntoInicial=inicial;
        this.direccion=direccion;
    }

    public DireccionCalle getDireccion(){
        return this.direccion;
    }

    public Point getPuntoInicial(){
        return this.puntoInicial;
    }
    public int getAncho(){
        return this.ancho;
    }
    public int getLongitud(){
        return this.longitud;
    }
    public void dibujarCarril(Graphics g){
        g.setColor(Color.yellow);
        if(this.direccion==DireccionCalle.DERECHA || this.direccion==DireccionCalle.IZQUIERDA){
        g.drawRect((int)puntoInicial.getX(), (int)puntoInicial.getY(),  longitud,ancho);
        }else{
            g.drawRect((int)puntoInicial.getX(), (int)puntoInicial.getY(),  ancho,longitud);
        }
    }

    public int compareTo(Carril o) {
        if(this.getPuntoInicial().equals(o.getPuntoInicial())){
            return 0;
        }else{
            return -1;
        }

    }


    
}
