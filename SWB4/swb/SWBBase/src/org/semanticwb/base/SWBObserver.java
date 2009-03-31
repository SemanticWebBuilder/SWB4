
/*
 * SWBObserver.java
 *
 * Created on 27 de junio de 2002, 12:37
 */

package org.semanticwb.base;

/**
 * objeto: indica que el objeto puede recibir notificaciones de otro objeto.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public interface SWBObserver
{
    /** Avisa al observador de un cambio.
     * @param s
     * @param obj  */
    public void sendDBNotify(String s, Object obj);
}

