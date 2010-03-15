package trafico;

import java.awt.Image;


import java.util.ArrayList;
import javax.swing.ImageIcon;
/**
 * Sirve para mostrar una animación sobre el Frame, basada
 * en una serie de imágenes y tiempos
 *
 * @author Roberto Martínez Román
 */
public class Animacion
{
    private transient  ArrayList <FrameAnimacion> frames; // Frames de la animación
    private int tiempoActual;       // El tiempo en este momento de la animación
    private int duracionTotal;      // El tiempo que dura la animación
    private int duracionFrame;      // El tiempo de cada cuadro (frame)
    private int frameActual;        // El frame que debe mostrarse en este momento

    /**
     * Duración por default de cada frame
     */
    public static final int DURACION_FRAME = 250;   // Duracion por default de c/frame

    /**
     * Construye una animación vacía
     */
    public Animacion() {

        frames = new ArrayList(); // lista vacía
        duracionTotal = 0;
        duracionFrame = 0;
        iniciar();
    }
    /**
     * Crea una animación a partir de las imágenes, cuyos nombres llegan en el
     * parámetro
     *
     * @param imagenes Tiene el nombre de cada imagen que formará parte de la animación
     */
    public Animacion(String[] imagenes) {

        this(imagenes,DURACION_FRAME);
    }
    /**
     * Crea una animación a partir de las imágenes, cuyos nombres llegan en el
     * primer parámetro; cada imagen tiene una duración indicada por el seg. parámetro
     *
     * @param imagenes Tiene el nombre de cada imagen que formará un frame de la animación
     * @param duracionCadaFrame La duración de cada frame en milisegundos
     */
    public Animacion(String[] imagenes, int duracionCadaFrame) {

        frames = new ArrayList();
        int tiempoFin = 0;
        for(String nombre : imagenes) {

            Image img = new ImageIcon( getClass().getResource(nombre)).getImage();
            tiempoFin += duracionCadaFrame;
            frames.add(new FrameAnimacion(img,tiempoFin));
        }
        duracionTotal = tiempoFin;
        duracionFrame = duracionCadaFrame; /*****/
        iniciar();
    }
    /**
     * Inicia la animación (tiempo cero y frame cero)
     */
    public void iniciar() {

        tiempoActual = 0;
        frameActual = 0;
    }
    /**
     * Regresa la imagen actual (la que debe pintarse en este momento)
     *
     * @return La imagen actual que debe mostrarse en este momento
     */
    public Image getImagenActual() {

        return frames.get(frameActual).imagen;
    }

    /**
     * Selecciona el nuevo frame de acuerdo al tiempo transcurrido
     *
     * @param tiempoTranscurrido El tiempo transcurrido desde la última actualización
     */
    public void actualizar(int tiempoTranscurrido) {

        if ( frames.size()>1 ) {

            tiempoActual = (tiempoActual+tiempoTranscurrido)%duracionTotal;
            frameActual = (tiempoActual/duracionFrame)%frames.size();
        }
    }

    private class FrameAnimacion
    {
     transient   Image   imagen;
        int tiempoFin;
        public FrameAnimacion(Image img, int tf) {
            imagen = img;
            tiempoFin = tf;
        }
    }
}
