/*
 * Estructura de datos para almacenar los elementos {elemento, accion, 
 * intensidad, tiempo} que han sido identificados dentro de un comando de voz.
 */
package org.semanticwb.nlp.CommandTranslator;

import java.util.Calendar;

/**
 *
 * @author vieyra
 */
public class Command {
    private String element;
    private String action;
    private Calendar time;
    private int intensity;

    public Command() {
        
    }

    public Command(String element, String action, Calendar time, int intensity) {
        this.element = element;
        this.action = action;
        this.time = time;
        this.intensity = intensity;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
    
}
