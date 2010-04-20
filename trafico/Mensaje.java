/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trafico;

import javax.swing.JTextArea;

/**
 *
 * @author Diego
 */
public class Mensaje {

    private String performativa;
    private Object emisor;
    private Object receptor;
    private String lenguaje;
    private String ontologia;
    private String contenido;

    public Mensaje(String performativa,Object emisor,Object receptor,String lenguaje,String ontologia,String contenido, JTextArea textArea){
        this.performativa = performativa;
        this.emisor = emisor;
        this.receptor = receptor;
        this.lenguaje = lenguaje;
        this.ontologia = ontologia;
        this.contenido = contenido;
        textArea.append(this.toString());
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    public Mensaje(String performativa,Object emisor,Object receptor,String lenguaje,String ontologia,String contenido, JTextArea textArea, boolean mostrar){
        this.performativa = performativa;
        this.emisor = emisor;
        this.receptor = receptor;
        this.lenguaje = lenguaje;
        this.ontologia = ontologia;
        this.contenido = contenido;
        if(mostrar){
            textArea.append(this.toString());
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    public String toString(){
        return "("+performativa+" :emisor "+emisor+" :receptor "+receptor+" :lenguaje "+lenguaje+" :ontologia "+ontologia+" :contenido "+contenido+")\n";
    }
    
    public String getContenido() {
        return contenido;
    }

    public Object getEmisor() {
        return emisor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public String getOntologia() {
        return ontologia;
    }

    public String getPerformativa() {
        return performativa;
    }

    public Object getReceptor() {
        return receptor;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setEmisor(Object emisor) {
        this.emisor = emisor;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public void setOntologia(String ontologia) {
        this.ontologia = ontologia;
    }

    public void setPerformativa(String performativa) {
        this.performativa = performativa;
    }

    public void setReceptor(Object receptor) {
        this.receptor = receptor;
    }



}
