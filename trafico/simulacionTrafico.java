/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trafico;

/**
 *
 * @author Ruco
 */

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Point;

public  class simulacionTrafico extends ZonaJuego {

    public int x;
    public int y;
    //objetos que se van a cargar una sola vez
    public ArrayList <Calle>listaCalles;
    private long contadorTiempo = 0;
    //arreglos que se van a utulizar
    public Calle calle1;
    public Calle calle2;
    public Calle calle3;
    public Calle calle4;
    public ArrayList <Semaforo> semaforos;
    private static String RUTAIMAGENFONDO = "/imagenes/ciudad/ciudad.jpg";
    private Image IMAGENEFONDO = new ImageIcon(getClass().getResource(RUTAIMAGENFONDO)).getImage();
    public ArrayList<Auto> autos;
    private int tiempoTotalCarros=0;
    private final int TIEMPOCREARCARRO=500;


    public simulacionTrafico() {
        calle1=new Calle(new Point(0,195), "horizontal", 1100, 4, DireccionCalle.IZQUIERDA, 0, DireccionCalle.DERECHA, 25, 0);
        calle2=new Calle(new Point(176,0), "vertical", 460, 2, DireccionCalle.ABAJO, 0, DireccionCalle.ARRIBA, 35, 0);
        calle3=new Calle(new Point(476,0), "vertical", 460, 2, DireccionCalle.ABAJO, 2, DireccionCalle.ARRIBA, 35, 10);
        calle4=new Calle(new Point(854,0), "vertical", 460, 1, DireccionCalle.ABAJO, 1, DireccionCalle.ARRIBA, 35, 10);
        semaforos=new ArrayList<Semaforo>();
        semaforos.add(new Semaforo(calle1,calle2, 4000,1000));
        semaforos.add(new Semaforo(calle1, calle3,4000,1000));
        semaforos.add(new Semaforo(calle1, calle4, 4000,1000));
         for(Semaforo s: semaforos){
                s.apagar();
         }

       autos= new ArrayList<Auto>();
       listaCalles=new ArrayList<Calle>();
       listaCalles.add(calle1);
       listaCalles.add(calle2);
       listaCalles.add(calle3);
       listaCalles.add(calle4);

    }

    @Override
    public void actualizar(long tiempo) {
        if (this.isJuegoCorriendo()) {
            contadorTiempo += tiempo;
            this.tiempoTotalCarros+=tiempo;
            if(tiempoTotalCarros>= TIEMPOCREARCARRO){
                agregarAuto();
                tiempoTotalCarros=0;
            }
            for(Semaforo s: semaforos){
                s.actualizar((int)tiempo);
            }
            for(int i= 0; i<autos.size(); i++){
                Auto aux=autos.get(i);
                if(aux.getPosX()<0 || aux.getPosX()> 1100 || aux.getPosY()<0 || aux.getPosY()>460){
                    autos.remove(i);
                    break;
                }else{
                autos.get(i).actualizar(tiempo,i);
                }
            }

        }
    }

    @Override
    public void dibujar(Graphics g) {
        if (this.isJuegoCorriendo()) {
            g.drawImage(IMAGENEFONDO, 0, 0, this);
            calle1.dibujarCalle(g);
            calle2.dibujarCalle(g);
            calle3.dibujarCalle(g);
            calle4.dibujarCalle(g);
           for(Semaforo s: semaforos){
                s.dibujarSemaforo(g);
            }
           for(int i= 0; i<autos.size(); i++){
                autos.get(i).dibujarAutomovil(g);
           }

        }
    }

      public void agregarAuto (){

        int randomCalle=(int) (Math.random() * listaCalles.size());
        int randomCarril=(int)(Math.random() * listaCalles.get(randomCalle).getCarriles().length);
        int tipo=(int)(Math.random()*10);
        if(tipo>=0 && tipo <2){
           autos.add( new Ambulancia(listaCalles.get(randomCalle),listaCalles.get(randomCalle).getCarriles()[randomCarril], autos,semaforos));
          
        }else{
            if(tipo>=2 && tipo <4){
                autos.add( new Patrulla(listaCalles.get(randomCalle),listaCalles.get(randomCalle).getCarriles()[randomCarril], autos,semaforos));
               
            }else{
                if(tipo>=4 && tipo<9){
                 autos.add( new automovil(listaCalles.get(randomCalle),listaCalles.get(randomCalle).getCarriles()[randomCarril], autos,semaforos));
                }else{
                 autos.add( new Tamalero(listaCalles.get(randomCalle),listaCalles.get(randomCalle).getCarriles()[randomCarril], autos,semaforos));
                }
            }
        }

    }


    public boolean cambiarTiempoSemaforo(int semaforo, int tiempoHorizontal, int tiempoVertical){
      return semaforos.get(semaforo).cambiarTiempo(tiempoHorizontal, tiempoVertical);
      
    

    }
    public void encenderSemaforo(int semaforo){
        semaforos.get(semaforo).encender();
        
    }
      public void apagarSemaforo(int semaforo){
        semaforos.get(semaforo).apagar();

    }
      public int[] getTiemposSemaforo(int semaforo){
          return semaforos.get(semaforo).getTiempos();
      }
}