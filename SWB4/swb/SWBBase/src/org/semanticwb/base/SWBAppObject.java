
/*
 * SWBAppObject.java
 *
 * Created on 11 de junio de 2002, 17:42
 */

package org.semanticwb.base;

/** Interfaz: se utiliza para los objetos de aplicacion que se almacenan en WBBroker
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public interface SWBAppObject
{
    void init();

    public void destroy();

    public void refresh();
}

