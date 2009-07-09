/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * ErrorElement.java
 *
 * Created on 9 de febrero de 2005, 06:36 PM
 */

package org.semanticwb.base.util;

/**
 * Objeto: Representa y contiene una exception junto con un identificador y la
 * fecha en la que se genero.
 * @author Javier Solis Gonzalez
 */
public class ErrorElement
{
    static long counter;

    private long id=0;
    private String msg=null;
    private java.util.Date date;
    private Throwable throwable;
    private Class cls;
    private String level;

    public Class getErrorClass() {
        return cls;
    }

    public String getErrorLevel() {
        return level;
    }
    
    /** Creates a new instance of ErrorElement */
    public ErrorElement(Throwable e, String msg, Class cls, String level)
    {
        id=getCounter();
        date=new java.util.Date();
        throwable=e;
        this.msg=msg;
    }
    
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
    
    public String getMessage()
    {
        if(msg!=null)return msg;
        return throwable.toString();
    }
    
    public String getStackTrace()
    {
        return printThrowable(throwable);
    }
    
    private String printThrowable(Throwable th)
    {
        StringBuffer bug=new StringBuffer();
        if(th!=null)
        {
            bug.append(date + ": " + th.getMessage()+"\n");
            StackTraceElement elements[] = th.getStackTrace();
            bug.append("// " + th +"\n");
            for (int x = 0; x < elements.length; x++)
            {
                bug.append("// " + elements[x]+"\n");
            }
            
            Throwable rth=th.getCause();
            if(rth!=null && rth!=th)
            {
                bug.append("\nRoot Cause:\n\n");
                bug.append(printThrowable(rth));
            }
        }
        return bug.toString();
    }
   
}
