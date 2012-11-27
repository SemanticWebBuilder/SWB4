/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb;

// TODO: Auto-generated Javadoc
/**
 * Registers data or messages in the application log file with different purposes.
 * <p>Registra informaci&oacute;n o mensajes en el archivo de bit&aacute;cora de
 * la aplicaci&oacute;n con diferentes prop&oacute;sitos.</p>
 * @author Javier Solis 
 */
public interface Logger 
{
    /*
     * Los 3 siguientes metodos son usados para debugueos mas profundos
     */
    /**
     * Registers the elements of the execution stack at the moment it is called 
     * along with a message, to provide information about an exceptional condition
     * in order to trace the origin of that exceptional condition.
     * <p>Registra los elementos de la pila de ejecuci&oacute;n del hilo al momento
     * en que es llamado, junto con un mensaje para proveer informaci&oacute;n
     * sobre una condici&oacute;n excepcional a fin de rastrear su origen.</p>
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void trace(String txt, Throwable t);
    
    /**
     * Registers a message to provide information about an exceptional condition
     * in order to trace the origin of that exceptional condition.
     * <p>Registra un mensaje para proveer informaci&oacute;n sobre una condici&oacute;n
     * excepcional a fin de rastrear su origen.</p>
     * @param txt a string representing the message to include in the app's log
     */
    public void trace(String txt);
    
    /**
     * Registers the elements of the execution stack at the moment it is called
     * to provide information about an exceptional condition in order to trace the
     * origin of that exceptional condition.
     * <p>Registra los elementos de la pila de ejecuci&oacute;n del hilo al momento
     * en que es llamado para proveer informaci&oacute;n sobre una condici&oacute;n
     * excepcional a fin de rastrear su origen.</p>
     * @param t a throwable object indicating an exceptional condition
     */
    public void trace(Throwable t);

    /*
     * Los 3 siguientes metodos son usados para debugueos sencillos
     */
    /**
     * Registers an exceptional condition along with a message, to provide
     * information about that exceptional condition.
     * <p>Registra los elementos de la pila de ejecuci&oacute;n del hilo al momento
     * en que es llamado, junto con un mensaje, para proveer informaci&oacute;n
     * sobre una condici&oacute;n excepcional.</p>
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void debug(String txt, Throwable t);
    
    /**
     * Registers a message to provide information about an exceptional condition.
     * <p>Registra un mensaje para proveer informaci&oacute;n sobre una
     * condici&oacute;n excepcional.</p>
     * @param txt a string representing the message to include in the app's log
     */
    public void debug(String txt);

    /**
     * Registers an exceptional condition in the execution of the current thread.
     * <p>Registra una condici&oacute;n excepcional en la ejecuci&oacute;n del
     * hilo actual.</p>
     * @param t a throwable object indicating an exceptional condition
     */
    public void debug(Throwable t);

    /*
     * Los 3 siguientes metodos son usados para Informacion del Sistema
     */
    /**
     * Registers the data needed to provide detail on an exceptional condition.
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void info(String txt, Throwable t);

    /**
     * Registers a message to provide detail on an exceptional condition.
     * @param txt a string representing the message to include in the app's log
     */
    public void info(String txt);

    /**
     * Registers an exceptional condition in an informative manner.
     * @param t a throwable object indicating an exceptional condition
     */
    public void info(Throwable t);

    /*
     * Los 3 siguientes metodos son usados para registrar advertencias
     */
    /**
     * Registers data as a warning on an exceptional condition generated.
     * <p>Registra informaci&oacute;n a manera de advertencia sobre una condici&oacute;n
     * excepcional generada.</p>
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void warn(String txt, Throwable t);
    
    /**
     * Registers a message as a warning on an exceptional condition generated.
     * <p>Registra un mensaje a manera de advertencia sobre una condici&oacute;n
     * excepcional generada.</p>
     * @param txt a string representing the message to include in the app's log
     */
    public void warn(String txt);

    /**
     * Registers data as a warning on an exceptional condition generated.
     * <p>Registra informaci&oacute;n a manera de advertencia sobre una condici&oacute;n
     * excepcional generada.</p>
     * @param t a throwable object indicating an exceptional condition
     */
    public void warn(Throwable t);

    /*
     * Los 3 siguientes metodos son usados para registrar errores
     */
    /**
     * Registers data to report an error in the application.
     * <p>Registra informaci&oacute;n para reportar un error en la aplicaci&oacute;n.</p>
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void error(String txt, Throwable t);

    /**
     * Registers a message to report an error in the application.
     * <p>Registra un mensaje para reportar un error en la aplicaci&oacute;n.</p>
     * @param txt a string representing the message to include in the app's log
     */
    public void error(String txt);

    /**
     * Registers data to report an error in the application.
     * <p>Registra informaci&oacute;n para reportar un error en la aplicaci&oacute;n.</p>
     * @param t a throwable object indicating an exceptional condition
     */
    public void error(Throwable t);

    /*
     * Los 3 siguientes metodos son usados para registrar excepciones en tiempo de ejecuci&oacute;n
     */
    /**
     * Registers data of an exceptional condition generated at runtime.
     * <p>Registra informaci&oacute;n sobre una condici&oacute;n excepcional generada
     * en tiempo de ejecuci&oacute;n.</p>
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void fatal(String txt, Throwable t);
    
    /**
     * Registers a message about an exceptional condition generated at runtime.
     * <p>Registra un mensaje acerca de una condici&oacute;n excepcional generada
     * en tiempo de ejecuci&oacute;n.</p>
     * @param txt a string representing the message to include in the app's log
     */
    public void fatal(String txt);
    /**
     * Registers data of an exceptional condition generated at runtime.
     * <p>Registra informaci&oacute;n sobre una condici&oacute;n excepcional generada
     * en tiempo de ejecuci&oacute;n.</p>
     * @param t a throwable object indicating an exceptional condition
     */
    public void fatal(Throwable t);
    
    /*
     * Los 3 siguientes metodos son usados para notificar eventos
     */
    /**
     * Registers data when certain event occurs.
     * <p>Registra informaci&oacute;n cuando cierto evento ocurre.</p>
     * @param txt a string representing the message to include in the app's log
     * @param t a throwable object indicating an exceptional condition
     */
    public void event(String txt, Throwable t);

    /**
     * Registers a message when certain event occurs.
     * <p>Registra un mensaje cuando cierto evento ocurre.</p>
     * @param txt a string representing the message to include in the app's log
     */
    public void event(String txt);

    /**
     * Registers data when certain event occurs.
     * <p>Registra informaci&oacute;n cuando cierto evento ocurre.</p>
     * @param t a throwable object indicating an exceptional condition
     */
    public void event(Throwable t);
    
}
