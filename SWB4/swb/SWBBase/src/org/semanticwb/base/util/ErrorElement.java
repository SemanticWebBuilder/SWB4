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
package org.semanticwb.base.util;

// TODO: Auto-generated Javadoc
/**
 * Objeto: Representa y contiene una exception junto con un identificador y la
 * fecha en la que se genero.
 * @author Javier Solis Gonzalez
 */
public class ErrorElement {

    /** The counter. */
    static long counter;
    
    /** The id. */
    private long id = 0;
    
    /** The msg. */
    private String msg = null;
    
    /** The date. */
    private java.util.Date date;
    
    /** The throwable. */
    private Throwable throwable;
    
    /** The cls. */
    private Class cls;
    
    /** The level. */
    private String level;

    /**
     * Gets the error class.
     * 
     * @return the error class
     */
    public Class getErrorClass()
    {
        return cls;
    }

    /**
     * Gets the error level.
     * 
     * @return the error level
     */
    public String getErrorLevel()
    {
        return level;
    }

    /**
     * Creates a new instance of ErrorElement.
     * 
     * @param e the e
     * @param msg the msg
     * @param cls the cls
     * @param level the level
     */
    public ErrorElement(Throwable e, String msg, Class cls, String level)
    {
        id = getCounter();
        date = new java.util.Date();
        throwable = e;
        this.msg = msg;
    }

    /**
     * Gets the counter.
     * 
     * @return the counter
     */
    public static synchronized long getCounter()
    {
        return counter++;
    }

    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public long getId()
    {
        return id;
    }

    /**
     * Getter for property date.
     * @return Value of property date.
     */
    public java.util.Date getDate()
    {
        return date;
    }

    /**
     * Getter for property throwable.
     * @return Value of property throwable.
     */
    public java.lang.Throwable getThrowable()
    {
        return throwable;
    }

    /**
     * Gets the message.
     * 
     * @return the message
     */
    public String getMessage()
    {  
        if (msg != null)
        {
            return msg; //La alternativa a este multiple return implica crear objetos que no se utilizaran
        }
        return throwable.toString();
    }

    /**
     * Gets the stack trace.
     * 
     * @return the stack trace
     */
    public String getStackTrace()
    {
        return printThrowable(throwable);
    }

    /**
     * Prints the throwable.
     * 
     * @param th the th
     * @return the string
     */
    private String printThrowable(Throwable th)
    {
        StringBuffer bug = new StringBuffer();
        if (th != null)
        {
            bug.append(date + ": " + th.getMessage() + "\n");
            StackTraceElement elements[] = th.getStackTrace();
            bug.append("// " + th + "\n");
            for (int x = 0; x < elements.length; x++)
            {
                bug.append("// " + elements[x] + "\n");
            }

            Throwable rth = th.getCause();
            if (rth != null && rth != th)
            {
                bug.append("\nRoot Cause:\n\n");
                bug.append(printThrowable(rth));
            }
        }
        return bug.toString();
    }
}
