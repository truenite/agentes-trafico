package trafico;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public abstract class ZonaJuego extends JPanel implements Runnable {

    public static final int ANCHO = 1100; // Dimensiones del panel
    public static final int ALTO = 478;
    private static final long RETARDO = 1000 / 60;   //1000ms/50 fps
    // Para el double buffering
    private transient Image imgFondo = null;
    private transient Graphics graphicsImgFondo;    // Estas variables booleanas, guardan el estado del juego
    private volatile boolean juegoCorriendo = false;
    private volatile boolean juegoTerminado = false;
    protected volatile boolean juegoPausado = false;

    // Constructor
    public ZonaJuego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
    }

    public boolean isJuegoCorriendo() {
        return juegoCorriendo;
    }
    // Es llamado cuando se agrega el JPanel al JFrame
    @Override
    public void addNotify() {
        super.addNotify();
        iniciarJuego(); // Arranca el Thread
    }

    // Construye y pone a correr un Thread con este JPanel
    protected void iniciarJuego() {
        Thread animador = null;
        // Sólo puede existir una instancia del Thread
        if (animador == null || !juegoCorriendo) {
            animador = new Thread(this);
            animador.start();   // Inicia el thread
        }
    }
    // Este es el método que ejecuta el Thread
    public void run() {

        // Indicamos que el juego inicia su ejecución
        juegoCorriendo = true;
        setFocusable(true);
        requestFocus();

        long tiempoActual = System.nanoTime();

        while (juegoCorriendo == true) {

            long tiempoTranscurrido = System.nanoTime() - tiempoActual;
            tiempoActual = System.nanoTime();
            // Actualiza los objetos (posición), el tiempo lo convierte a ms
            actualizarJuego(tiempoTranscurrido / 1000000);
            pintarJuegoEnMemoria();  // Pinta los objetos en memoria
            refrescarPantalla();    // Pinta sobre la pantalla


            try {   // Espera un momento para la animación
                Thread.sleep(RETARDO - tiempoTranscurrido / 1000000);  // ms. entre c/frame
            } catch (Exception e) {
            }
        }
    //System.exit(1);
    }
    // Fin del juego
    public void terminarJuego() {
        juegoCorriendo = false;
    }
    // Pausa/reanuda el juego
    public void pausarJuego() {
        juegoPausado = !juegoPausado;
    }
    //
    private void actualizarJuego(long tiempo) {
        if (!juegoTerminado /*&& !juegoPausado*/) {
            // Actualiza posiciones, revisa si hay choques, etc.
            actualizar(tiempo);   // Debe estar implementado en la subclase
        // Falta enviar el tiempo transcurrido, esto en otra version...
        }
    }

    private void pintarJuegoEnMemoria() {
        if (juegoPausado || juegoTerminado) {
            return;
        }
        if (imgFondo == null) {
            imgFondo = createImage(ANCHO, ALTO);
            if (imgFondo == null) {
                System.out.println("No puede crear dbImage");
                return;
            } else {
                graphicsImgFondo = imgFondo.getGraphics();
            }
        }
        // Dibuja los elementos sobre graphicsImgFondo
        dibujar(graphicsImgFondo);
    }

    private void refrescarPantalla() {
        Graphics g;
        try {
            g = this.getGraphics();
            if (g != null && imgFondo != null) {
                g.drawImage(imgFondo, 0, 0, null);
                Toolkit.getDefaultToolkit().sync(); // Repinta
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Error al refrescar...");
        }
    }

    public abstract void actualizar(long tiempo);

    public abstract void dibujar(Graphics g);

    public void cargar() {

        this.juegoPausado=false;
    }
}
